package bl;

import util.excelUtil.ExcelReader;
import util.excelUtil.People;
import util.excelUtil.Student;
import util.excelUtil.Tutor;
import util.mailUtil.Mail;

import java.util.ArrayList;
import java.util.List;

public class EmailSender {
    List<Tutor> mTutorList = new ArrayList<>();//教师列表
    private List<Student> mStudentList;
    private String mExcelPath;

    /**
     * 所有失踪、不在白名单内的学生列表的excel位置
     * @param excelPath
     */
    public EmailSender(String excelPath){
        this.mExcelPath = excelPath;
    }

    /**
     * 没有地址，默认在当前文件夹下的cnm.xlsx
     */
    public EmailSender(){
        this.mExcelPath = System.getProperty("user.dir") + "/cnm.xlsx";
    }

    public void start(){
        this.getStudentList();
        this.getTutorList();
        this.sendEmail();

    }

    private void sendEmail(){
        //全校没有一个人缺席的情况
        if(mStudentList.size() == 0){
            return;
        }


        Tutor t;//当前老师
        Student s;
        String text = "";//属于某个老师的学生列表
        //对每一个老师，查找属于她的学生
        for(int i = 0; i < mTutorList.size(); i++){
            t = mTutorList.get(i);
            for(int j = 0; j < mStudentList.size(); j++){
                s = mStudentList.get(j);
                if(!notSameProp(t, s)){
                    //一样的年级、院系
                    text += s.getId() + s.getName() + "   ";
                }
            }
            if(text == ""){
                text = "全体学生到齐";
            }else{
                text = "以下学生失联\n";
            }
            text = "老师您好，这一次" + t.getInstitute() + "的" + t.getGrade() + "学生夜不归宿情况是:\n" + text;
            try{
                System.out.println(text);
                //Mail.sendSimpleMail("1250925329@qq.com", "nrmwztvhmdlsgfdi", t.getEmailAddress(), text);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    /**
     * 学生是否归这个老师管
     * @param a 老师
     * @param b 学生
     * @return
     */
    private boolean notSameProp(Tutor a, Student b){
        if(a.getInstitute() != b.getInstitute()){
            return true;
        }else if(!a.getGrade().contains(b.getGrade())){
            return true;
        }
        return false;
    }

    /**
     * 获取教师列表，将mTutorList中放入所有老师
     */
    private void getTutorList(){
        List<People> people = ExcelReader.readSimpleExcel(mExcelPath, "辅导员");
        Tutor t;
        for(int i = 0; i < people.size(); i++){
            t = (Tutor) people.get(i);
            mTutorList.add(t);
        }
    }

    /**
     * 获得黑名单
     */
    private void getStudentList(){
        List<People> people = ExcelReader.readSimpleExcel(mExcelPath, "白名单");
        Student s;
        for(int i = 0; i < people.size(); i++){
            s = (Student) people.get(i);
            mStudentList.add(s);
        }
    }
}
