package dao;

import entity.IdMap;
import entity.PassRecord;
import entity.TimePair;
import exception.logException.LogException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import util.AppLog;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 使用Mybatis辅助数据库查询
 *
 * @author Nosolution
 * @version 1.0
 * @since 2019/4/26
 */
public class MDProbe {
    private SqlSessionFactory sqlSessionFactory;

    //强制使用静态方法创建probe对象
    private MDProbe() {
    }

    /**
     * 使用参数构建SqlSessionFactory字段
     *
     * @param password 登录密码
     * @return probe对象
     */
    public static MDProbe build(String password) {
        MDProbe probe = new MDProbe();

//        String driver = DaoConfig.driver;
//        DataSource dataSource = new PooledDataSource(driver, url, username, password);
//        TransactionFactory transactionFactory = new JdbcTransactionFactory();
//        Environment environment = new Environment("development", transactionFactory, dataSource);
//        Configuration configuration = new Configuration(environment);
//
//        configuration.addMapper(PassRecordMapper.class);
//
//        probe.sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);

        //因为代码配置失败，使用xml文件配置
        String resource = "mybatis-config.xml";
        InputStream is = null;
        Properties props = null;
        try {
            is = Resources.getResourceAsStream(resource);
            props = new Properties();
            props.load(Resources.getResourceAsStream("jdbc.properties"));
            props.setProperty("jdbc.password", password);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //构建sqlSession的工厂
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is, props);
        probe.sqlSessionFactory = sessionFactory;

        SqlSession session = probe.sqlSessionFactory.openSession();
        session.close();

        return probe;
    }

    public List<PassRecord> getRecordWithLimit(int limit) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            PassRecordMapper mapper = session.getMapper(PassRecordMapper.class);
            List<PassRecord> records = mapper.getRecordWithLimit(limit);
            return records;
        } finally {
            session.close();
        }
    }

    public List<PassRecord> getAllRecords() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            PassRecordMapper mapper = session.getMapper(PassRecordMapper.class);
            List<PassRecord> records = mapper.getAllRecords();
            return records;
        } finally {
            session.close();
        }
    }

    public List<IdMap> getIdMap() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            PassRecordMapper mapper = session.getMapper(PassRecordMapper.class);
            List<IdMap> maps = mapper.getUserIdMaps();
            return maps;
        } finally {
            session.close();
        }
    }


    /**
     * 获取真实id与刷卡记录的映射列表，用于做后续判断
     *
     * @return 真实id与刷卡记录的映射列表
     */
    public Map<String, List<PassRecord>> getRecordsGrouped() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            PassRecordMapper mapper = session.getMapper(PassRecordMapper.class);
            List<PassRecord> records = mapper.getAllRecords();
            List<IdMap> maps = mapper.getUserIdMaps();
            return group(records, maps);
        } finally {
            session.close();
        }
    }

    /**
     * 检查数据库自上次应用启动后是否出现异常情况
     */
    public List<String> check() throws LogException {
        //TODO 等待与文件键值对读写工具协作
        List<String> res = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String today = format.format(new Date());
        AppLog log = AppLog.getInstance();

        if (log.get("inited").equals("false"))
            init();

        if (log.get("latestCheckDate").equals(today))
            return res;

        Date now = new Date();
        Date then;
        try {
            then = new Date(format.parse(log.get("latestCheckDate")).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            throw new LogException("日志格式错误");
        }

        List<Date> weirdDates = hasExceptionInDuration(alignDate(then), alignDate(now));
        weirdDates.forEach(d -> {
            //写入系统记录
            res.add(format.format(d));
        });

        return res;


    }

    private void init() {
        AppLog log = AppLog.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        log.put("inited", "true");
        log.put("latestCheckDate", format.format(date));

        SqlSession session = sqlSessionFactory.openSession();
        try {
            PassRecordMapper mapper = session.getMapper(PassRecordMapper.class);
            //这里会有很大的时间花销
            List<PassRecord> records = mapper.getAllRecords();
            Map<String, List<PassRecord>> recordDateMap = records.stream().collect(Collectors.groupingBy(o -> format.format(o.getPassTime())));
            recordDateMap.forEach((k, r) -> {
                if (lostData(r))
                    ;//写入系统记录

            });
        } finally {
            session.close();
        }

    }

    /**
     * 在给定的日期段内判断每一天是否存在数据丢失情况，返回带有丢失数据的日期的列表或者空;
     * 默认日期的时间为00:00:00，判断日期范围为[d1, d2)
     *
     * @param d1 起始日期的毫秒数
     * @param d2 终止日期的毫秒数
     * @return 包含丢失数据日期的列表(可能为空)
     */
    private List<Date> hasExceptionInDuration(long d1, long d2) {
        List<Date> res = new ArrayList<>();
        SqlSession session = sqlSessionFactory.openSession();
        PassRecordMapper mapper = session.getMapper(PassRecordMapper.class);

        try {
            for (long cur = d1; cur < d2; cur += 1000L * 3600 * 24) {
                List<PassRecord> records = mapper.getRecordByDate(new Date());//假设已经排序好了(可以在sql语句中实现)
                if (lostData(records))
                    res.add(new Date(cur));
            }
            return res;

        } finally {
            session.close();
        }

    }


    /**
     * 判断某段记录是否出现断层(数据丢失)
     *
     * @param records 待判断的记录
     * @return 如果出现数据丢失，返回true，否则为false
     */
    private boolean lostData(List<PassRecord> records) {
        int[] count = new int[1];
//        List<String> errorRecords = new ArrayList<>();

        records.stream().reduce((a, b) -> {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(a.getPassTime());
            Calendar c2 = Calendar.getInstance();
            c2.setTime(b.getPassTime());
            c1.add(Calendar.MINUTE, 30);
            if (c1.after(c2))
                count[0]++;
//                        errorRecords.add(String.format("%s--%s", a.getPassTime().toString(), b.getPassTime().toString()));
            return b;
        });

        return count[0] > 0;
    }

    /**
     * 实现刷卡记录与学生真正id的映射
     *
     * @param records 刷卡记录
     * @param maps    伪id与真实id的映射
     * @return 真实id与刷卡记录的映射
     */
    private Map<String, List<PassRecord>> group(List<PassRecord> records, List<IdMap> maps) {
        Map<String, List<PassRecord>> res = new HashMap<>();
        Map<String, List<PassRecord>> recordMap = records.stream().collect(Collectors.groupingBy(PassRecord::getUserId));

        maps.stream()
                .collect(Collectors
                        .groupingBy(IdMap::getCridentialId))
                .forEach((key, list) -> {
                    //每一个userId对应此studentId的有效时间区间
                    Map<String, TimePair> idTimeMap = new LinkedHashMap<>();
                    list.sort(Comparator.comparing(IdMap::getUpdateTime));//从时间晚到早排序

                    final Timestamp[] earliest = {new Timestamp(Calendar.getInstance().getTimeInMillis())};
                    final String[] earliestId = new String[1];
                    list.stream().reduce((a, b) -> {
                        TimePair tp = new TimePair(b.getUpdateTime(), a.getUpdateTime());
                        earliest[0] = b.getUpdateTime();
                        earliestId[0] = b.getUserId();
                        idTimeMap.put(a.getUserId(), tp);
                        return b;
                    });
                    //加入最早的时间，设定时间区间为一年
                    idTimeMap.put(earliestId[0], new TimePair(new Timestamp(earliest[0].getTime() - 365L * 24 * 60 * 60 * 1000), earliest[0]));

                    Set<String> idSet = idTimeMap.keySet();
                    List<PassRecord> toPut = new ArrayList<>();

                    recordMap.entrySet()
                            .stream()
                            .filter(e -> idSet.contains(e.getKey()))
                            .map(Map.Entry::getValue)
                            .forEach(l ->
                                    l.forEach(i -> {
                                        if (idTimeMap.get(i.getUserId()).include(i.getPassTime()))
                                            toPut.add(i);
                                    }));

                    res.put(key, toPut);
                });

        res.values().removeIf(l -> l.size() == 0);
        res.forEach((k, v) -> System.out.println(k + " " + v));
        return res;
    }

    /**
     * 对齐日期，将日期类的小时-分钟-秒数减到0
     *
     * @param date 待对齐的日期
     * @return 当前日期且小时-分钟-秒数为00-00-00的毫秒总数
     */
    private long alignDate(Date date) {
        return date.getTime() - date.getTime() % (24 * 3600 * 1000L) - (8 * 3600 * 1000L);
    }

}
