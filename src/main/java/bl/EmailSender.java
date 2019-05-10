package bl;

import entity.Student;
import entity.SuspectStudent;
import entity.Tutor;
import exception.excelException.FileNotFoundException;
import exception.mailException.MailException;
import util.excelUtil.ExcelReader;
import util.mailUtil.Mail;

import javax.mail.MessagingException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmailSender {

    private List<SuspectStudent> studentList = new ArrayList<>();
    private String studentExcelPath;//之前输出的缺席学生名单
    private Map<Tutor, List<String>> tutorList;//教师列表

    private String hostAddress;
    private String password;


    /**
     * 所有失踪、不在白名单内的学生列表的excel位置
     *
     * @param studentExcelPath 里面是缺席的学生名单
     */
    public EmailSender(Map<Tutor, List<String>> tutorList, String studentExcelPath) {
        this.studentExcelPath = studentExcelPath;
        this.tutorList = tutorList;

        //TODO 这部分应该使用配置文件来填入
        hostAddress = "171250530@smail.nju.edu.cn";
        password = "xxxx";

    }

    /**
     * 没有地址，默认input在当前文件夹下的cnm.xlsx，默认student在次文件夹的haha.xlsx
     */
    public EmailSender(Map<Tutor, List<String>> tutorList) {
        this(tutorList, System.getProperty("user.dir") + "\\失踪学生名单_不包含白名单.xlsx");

        //this.inputExcelPath = "C:\\Users\\12509\\Desktop\\wrh" + "\\input.xlsx";
        //this.studentExcelPath = "C:\\Users\\12509\\Desktop\\wrh" + "\\student.xlsx";

    }

    public void start() throws MailException, GeneralSecurityException, MessagingException, FileNotFoundException {
        getStudentList();
        sendEmails();

    }


//    private void sendEmails() throws MailException, GeneralSecurityException, MessagingException {
//        //全校没有一个人缺席的情况
//        if (studentList.size() == 0) {
//            return;
//        }
//
//
//        Tutor t;//当前老师
//        Student s;
//        String text = "";//属于某个老师的学生列表
//        //对每一个老师，查找属于她的学生
//        for (int i = 0; i < tutorList.size(); i++) {
//            text = "";
//            t = tutorList.get(i);
//            for (int j = 0; j < studentList.size(); j++) {
//                s = studentList.get(j);
//                if (SameProp(t, s)) {
//                    //一样的年级、院系
//                    text += s.getId() + s.getName() + "\n";
//                }
//            }
//            if (text == "") {
//                text = "全体学生到齐";
//            } else {
//                text = "以下学生失联\n" + text;
//            }
//            text = t.getName() + "您好，这一次" + t.getInstitute() + "的" + t.getGrade() + "学生夜不归宿情况是:\n" + text;
//
//            System.out.println(text);
//            //Thread.sleep(500);
//            Mail.sendSimpleMail("1250925329@qq.com", "noalhxlgbyndgdgc", "主题", t.getEmailAddress(), text);
//
//
//        }
//    }

    /**
     * 向负责各学生的辅导员发送邮件
     *
     * @throws MailException
     * @throws GeneralSecurityException
     * @throws MessagingException
     */
    private void sendEmails() throws MailException, GeneralSecurityException, MessagingException {
        if (studentList.size() == 0)
            return;

        for (Map.Entry<Tutor, List<String>> e : tutorList.entrySet()) {
            Tutor tutor = e.getKey();
            List<String> list = e.getValue();

            StringBuilder builder = new StringBuilder();
            builder.append(tutor.getName())
                    .append("您好，今日")
                    .append(tutor.getInstitute())
                    .append("的")
                    .append(tutor.getGrade())
                    .append("级的学生刷卡记录检测情况是\n");
            studentList.stream()
                    .filter(o -> list.contains(o.getStudentId()))
                    .forEach(o -> {
                        //TODO 邮件内容还需要再仔细处理
                        builder.append(o.getStudentId()).append(" ").append(o.getStatus()).append("\n");
                    });
            Mail.sendSimpleMail(hostAddress, password, "学生刷卡记录检测", tutor.getEmailAddress(), builder.toString());
        }


    }

    /**
     * 学生是否归这个老师管
     *
     * @param a 老师
     * @param b 学生
     * @return
     */
    private boolean SameProp(Tutor a, Student b) {
        if (!a.getInstitute().equals(b.getInstitute())) {
            return false;
        } else if (a.getGrade().contains(b.getGrade()) || a.getGrade().equals("全部")) {
            return true;
        }
        return false;
    }


    /**
     * 获得被怀疑学生名单
     */
//    private void getStudentList() throws FileNotFoundException {
//        List<People> people = ExcelReader.readSimpleExcel(studentExcelPath, "学生名单");
//        Student s;
//        for (int i = 0; i < people.size(); i++) {
//            s = (Student) people.get(i);
//            studentList.add(s);
//        }
//    }
    private void getStudentList() throws FileNotFoundException {
        //TODO
        studentList.addAll(ExcelReader.readSuspectedStudentList(studentExcelPath, "学生名单"));

    }

}
