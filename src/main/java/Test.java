
import entity.People;
import entity.Student;
import util.excelUtil.ExcelReader;
import entity.Tutor;
import util.excelUtil.ExcelWritter;
import util.mailUtil.Mail;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wen Sun
 * @date 2018/12/12
 */
public class Test {
    public static void main(String[] args) {

        try {
            Mail.sendMailWithAttachment("171250662@smail.nju.edu.cn", "***", "This is not a test",
                    "595033456@qq.com", "This is a test with an attachment.", "D:/test");
            List<Student> students = new ArrayList<>();
            //students.add(new Student("软件学院", "冯柯翔", "2017", "171250639"));
            //students.add(new Student("软件学院", "徐凌云", "2017", "171250026"));
            ExcelWritter.writeSimpleExcel(students, "D:/test/org/apache/commons/excel123.xlsx");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
