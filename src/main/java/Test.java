import util.excelUtil.ExcelReader;
import util.excelUtil.Tutor;
import util.mailUtil.Mail;

/**
 * @author Wen Sun
 * @date 2018/12/12
 */
public class Test {
    public static void main(String[] args) {
        try {
            Mail.sendMailWithAttachment("171250662@smail.nju.edu.cn", "***", "595033456@qq.com",
                    "This is a test with an attachment.", "D:/test");
            for (Tutor t : ExcelReader.readSimpleExcel("C:\\Users\\12509\\Desktop\\wrh\\haha.xlsx", "Sheet1")) {
                System.out.println(t);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
