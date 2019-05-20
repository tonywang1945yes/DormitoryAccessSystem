package util.logUtil;

import java.util.Date;
import java.util.List;

public interface AppLog {
    /**
     * 新建一条异常记录
     *
     * @param
     */
    public void createInsertionRecord(String white, String relat, String concern, String res);

    public void createExceptionRecord(String str);

    public void createExceptionRecord(String str, String date);

    /**
     * 新建单条插入记录
     *
     * @param str
     */
    public void createInsertionRecord(String str);

    /**
     * 统计一段时间内插入条数
     *
     * @param start
     * @param end
     * @return
     */
    public int sumInsertion(Date start, Date end);

    /**
     * 新建一条单日插入总数记录
     *
     * @param num
     */
    public void createInsSumRecord(int num, String date, String time);

    /**
     * 新建一条起始记录，标记机器启动时间
     */
    public void createStartRecord();

    /**
     * 获取最新软件启动记录
     *
     * @return 最新的软件启动日期日志
     */
    public Record getLatestStartRecord();

    /**
     * 获取当日的错误记录
     *
     * @param date 指定的日期
     * @return 当日的错误记录
     */
    public List<Record> getExceptionByDate(String date);

    /**
     * 获取最新的错误记录
     *
     * @return 最新一条错误记录
     */
    public Record getLatestException();

    public String readpath(int which);
}
