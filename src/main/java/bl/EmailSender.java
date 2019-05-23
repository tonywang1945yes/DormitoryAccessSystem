package bl;

import entity.SuspectStudent;
import entity.Tutor;
import exception.mailException.MailException;
import util.mail.Mail;

import javax.mail.MessagingException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class EmailSender {

    private List<SuspectStudent> students;
    private Map<Tutor, List<String>> tsMap;//辅导员与对应的异常学生id列表


    public EmailSender(Map<Tutor, List<String>> tsMap, List<SuspectStudent> students) {
        this.tsMap = tsMap;
        this.students = students;
    }


    /**
     * 向老师发邮件
     *
     * @param hostAddress 邮箱号
     * @param password    密码
     * @throws MailException
     * @throws GeneralSecurityException
     * @throws MessagingException
     */
    public void sendEmails(String hostAddress, String password) throws MailException, GeneralSecurityException, MessagingException {
        if (students.size() == 0)
            return;

        System.out.println("--sending email");
        for (Map.Entry<Tutor, List<String>> e : tsMap.entrySet()) {
            Tutor tutor = e.getKey();
            List<String> list = e.getValue();
            SimpleDateFormat format = new SimpleDateFormat("yyyy年M月d日");
            Date today = new Date();

            StringBuilder builder = new StringBuilder();
            builder.append(tutor.getName())
                    .append("老师你好。今日(")
                    .append(format.format(today))
                    .append(")")
                    .append(tutor.getInstitute())
                    .append("的")
                    .append(tutor.getGrade())
                    .append("级的学生刷卡记录检测情况是:\n");
            students.stream()
                    .filter(o -> list.contains(o.getStudentId()))
                    .forEach(o -> {
                        builder.append(o.getStudentId()).append(" ").append(o.getStatus()).append("\n");
                    });
            Mail.sendSimpleMail(hostAddress, password, "学生刷卡记录检测", tutor.getEmailAddress(), builder.toString());
            System.out.println("-finish sending");
        }


    }


}
