import util.mailUtil.Mail;

/**
 * @author Wen Sun
 * @date 2018/12/12
 */
public class Test {
    public static void main(String[] args) {
        try {
            Mail.sendMailWithAttachment("***@smail.nju.edu.cn", "*****", "595033456@qq.com",
                    "This is a test with an attachment.", "D:/test/test.xlsx");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
