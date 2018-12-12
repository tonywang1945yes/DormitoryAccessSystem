import util.mailUtil.Mail;

/**
 * @author Wen Sun
 * @date 2018/12/12
 */
public class Test {
    public static void main(String[] args) {
        try {
            Mail.sendMailWithAttachment("171250662@smail.nju.edu.cn", "MB6L7D9J6J8cQsoV", "595033456@qq.com",
                    "This is a test with an attachment.", "D:/test/test.xlsx");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
