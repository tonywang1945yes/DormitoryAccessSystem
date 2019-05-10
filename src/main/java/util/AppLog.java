package util;

/**
 * 日志管理类，辅助程序作出判断
 *
 * @author Nosolution
 * @version 1.0
 * @since 2019/4/26
 */
public class AppLog {

    private static AppLog instance;

    private AppLog() {

    }

    public static AppLog getInstance() {
        if (instance == null)
            instance = new AppLog();
        return instance;
    }

    public String get(String key) {
        return "";
    }

    public void put(String key, String value) {

    }

    public boolean flush() {
        return true;
    }

    public String toString() {
        return "";
    }
}
