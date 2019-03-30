package dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 数据库所使用的配置信息，以Java类的形式呈现
 */
public class DaoConfig {
    static final String FIRSTBUFFER = "table_1";
    static final int FIRSTBUFFERINTERVAL = 3;
    static final String SECONDBUFFER = "table_2";
    static final int SECONDBUFFERINTERVAL = 7;
    static final String THIRDBUFFER = "table_3";
    static final int THIRDBUFFERINTERVAL = 30;

    static final String url = "jdbc:mysql://114.212.99.210:3306/test";
    static final String username = "dbtest";
    static final String password = "njuacdbtest";

    public static String timeFormatAdapter(Calendar startTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return format.format(startTime);
    }
}
