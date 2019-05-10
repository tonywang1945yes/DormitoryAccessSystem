package dao;

import entity.IdMap;
import entity.PassRecord;
import entity.TimePair;
import exception.daoException.DatabaseErrorException;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import util.AppLog;

import javax.sql.DataSource;
import java.sql.Timestamp;
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
     * @param url      数据库url
     * @param username 登录用户名
     * @param password 登录密码
     * @return probe对象
     */
    public static MDProbe build(String url, String username, String password) {
        MDProbe probe = new MDProbe();

        String driver = DaoConfig.driver;
        DataSource dataSource = new PooledDataSource(driver, url, username, password);
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);

        configuration.addMapper(PassRecordMapper.class);

        probe.sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        return probe;
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
    public void check() throws DatabaseErrorException {
        //TODO 等待与文件键值对读写工具协作
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String today = format.format(new Date());
        AppLog log = AppLog.getInstance();
        if (log.get("latestCheckDate").equals(today))
            return;

        SqlSession session = sqlSessionFactory.openSession();
        try {
            PassRecordMapper mapper = session.getMapper(PassRecordMapper.class);
            List<PassRecord> todayRecords = mapper.getRecordByDate(new Date());//假设已经排序好了(可以在sql语句中实现)
            List<String> errorRecords = new ArrayList<>();

            //判断有没有数据断层，用于监测数据更新量过少的情况
            todayRecords.stream().reduce((a, b) -> {
                Calendar c1 = Calendar.getInstance();
                c1.setTime(a.getPassTime());
                Calendar c2 = Calendar.getInstance();
                c2.setTime(b.getPassTime());
                c1.add(Calendar.MINUTE, 30);
                if (c1.after(c2))
                    errorRecords.add(String.format("%s--%s", a.getPassTime().toString(), b.getPassTime().toString()));
                return b;
            });

            if (errorRecords.size() != 0)
                throw new DatabaseErrorException("Database lost data.");
        } finally {
            session.close();
        }
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
                        .groupingBy(IdMap::getCredentialId))
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

        return res;
    }

}
