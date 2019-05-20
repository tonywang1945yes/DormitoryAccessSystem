package util.logUtil;

import enums.Type;
import org.ho.yaml.Yaml;
import org.ho.yaml.YamlDecoder;
import org.ho.yaml.YamlEncoder;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class RecordOpe implements AppLog {
    private static RecordOpe instance = null;

    public static RecordOpe getInstance() {
        if (instance == null)
            instance = new RecordOpe();
        return instance;
    }

    private RecordOpe() {
        super();
    }


    /**
     * 新建一条异常记录
     *
     * @param str
     */
    @Override
    public void createExceptionRecord(String str) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间
        df = new SimpleDateFormat("HH:mm:ss");
        String time = df.format(new Date());
        Record record = new Record(Type.Exception, str, date, time);
        dump(record);
    }

    @Override
    public void createExceptionRecord(String str, String date) {
        Record record = new Record(Type.Exception, str, date, "00:00:00");
        dump(record);
    }

    /**
     * 新建单条插入记录
     *
     * @param str
     */
    @Override
    public void createInsertionRecord(String str) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间
        df = new SimpleDateFormat("HH:mm:ss");
        String time = df.format(new Date());
        Record record = new Record(Type.SingleRecord, str, date, time);
        dump(record);
    }

    @Override
    public void createInsertionRecord(String white, String relat, String concern, String res) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间
        df = new SimpleDateFormat("HH:mm:ss");
        String time = df.format(new Date());
        Record record = new Record(date, time, Type.Start, 1, "", white, relat, concern, res);
        dumpfirst(record);
    }

    /**
     * 统计一段时间内插入条数
     *
     * @param start
     * @param end
     * @return
     */
    @Override
    public int sumInsertion(Date start, Date end) {
        int res = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        long[] now = new long[1];
        for (now[0] = start.getTime(); now[0] <= end.getTime(); now[0] += 1000L * 3600 * 24) {
            Record oneDay = readYaml()
                    .stream()
                    .filter(o -> o.getType().equals(Type.SumRecord) && o.getDate().equals(format.format(new Date(now[0]))))
                    .findFirst().get();
            res += oneDay.getNum();
        }
        return res;
    }

    ;

    /**
     * 新建一条单日插入总数记录
     *
     * @param num
     */
    @Override
    public void createInsSumRecord(int num, String date, String time) {
        Record record = new Record(date, time, Type.SumRecord, num, "This is a sum");
        dump(record);
    }

    /**
     * 新建一条起始记录，标记机器启动时间
     */
    @Override
    public void createStartRecord() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间
        df = new SimpleDateFormat("HH:mm:ss");
        String time = df.format(new Date());
        Record record = new Record(Type.Start, "Starting", date, time);
        dump(record);
    }

    @Override
    public Record getLatestStartRecord() {
        List<Record> records = readYaml()
                .stream()
                .filter(o -> o.getType().equals(Type.Start))
                .collect(Collectors.toList());

        if (records.size() == 0)
            return null;
        return records.get(records.size() - 1);
    }

    /**
     * 获取当日的错误记录
     *
     * @param date
     * @return
     */
    @Override
    public List<Record> getExceptionByDate(String date) {
        List<Record> records = readYaml().stream().filter(o -> (o.getType().equals(Type.Exception) && (o.getDate().equals(date)))).collect(Collectors.toList());
        return records;
    }

    /**
     * 获取最新的错误记录
     *
     * @return
     */
    @Override
    public Record getLatestException() {
        List<Record> records = readYaml().stream().filter(o -> (o.getType().equals(Type.Exception))).collect(Collectors.toList());
        return records.get(records.size() - 1);
    }

    /**
     * 写入
     *
     * @param record
     */
    private void dump(Record record) {
        List<Record> records = readYaml();
        File dumpFile = new File(System.getenv("APPDATA") + "/das/applog.yaml");
        records.add(record);
        try {
            YamlEncoder enc = new YamlEncoder(new FileOutputStream(dumpFile));
            for (int i = 0; i < records.size(); i++) {
                enc.writeObject(records.get(i));
                enc.flush();
            }
            enc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //    修改 起始设置
    private void dumpfirst(Record record) {
        List<Record> records = readYaml();
        File dumpFile = new File(System.getenv("APPDATA") + "/das/applog.yaml");
        if (records.size() == 0) {
            dump(record);
            return;
        } else {
            records.set(0, record);
        }
        try {
            YamlEncoder enc = new YamlEncoder(new FileOutputStream(dumpFile));
            for (int i = 0; i < records.size(); i++) {
                enc.writeObject(records.get(i));
                enc.flush();
            }
            enc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * 读入
     *
     * @return
     */
    private List<Record> readYaml() {
        List<Record> records = new ArrayList<Record>();
        try {
            File dumpFile = new File(System.getenv("APPDATA") + "/das/applog.yaml");
            Record record = (Record) Yaml.loadType(dumpFile, Record.class);
            YamlDecoder dec = new YamlDecoder(new FileInputStream(dumpFile));
            while (true) {
                record = (Record) dec.readObject();
                records.add(record);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (EOFException e) {
            System.out.println("done.");
        } finally {
            return records;
        }
    }
}
