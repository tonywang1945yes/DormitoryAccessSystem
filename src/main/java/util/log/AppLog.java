package util.log;

import java.util.Date;
import java.util.List;

public interface AppLog {
    /**
     * 新建一条异常记录
     *
     * @param
     */
    void createInsertionRecord(String white, String relat, String concern, String fest, String res);

    void createExceptionRecord(String str);

    void createExceptionRecord(String str, String date);

    /**
     * 新建单条插入记录
     *
     * @param str
     */
    void createInsertionRecord(String str);


    Record getLatestInsertionRecord();

    /**
     * 统计一段时间内插入条数
     *
     * @param start
     * @param end
     * @return
     */
    int sumInsertion(Date start, Date end);

    /**
     * 新建一条单日插入总数记录
     *
     * @param num
     */
    void createInsSumRecord(int num, String date, String time);

    /**
     * 新建一条起始记录，标记机器启动时间
     */
    void createStartRecord();

    /**
     * 获取最新软件启动记录
     *
     * @return 最新的软件启动日期日志
     */
    Record getLatestStartRecord();

    /**
     * 获取当日的错误记录
     *
     * @param date 指定的日期
     * @return 当日的错误记录
     */
    List<Record> getExceptionByDate(String date);

    /**
     * 获取最新的错误记录
     *
     * @return 最新一条错误记录
     */
    Record getLatestException();

    String readPath(int which);
}
