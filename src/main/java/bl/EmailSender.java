package bl;

import entity.People;
import entity.Student;
import entity.Tutor;
import util.excelUtil.ExcelReader;

import java.util.ArrayList;
import java.util.List;

public class EmailSender {

    private List<Student> mStudentList = new ArrayList<>();
    private String mStudentExcelPath;//之前输出的缺席学生名单
    private String mInputExcelPath;//最开始输入老师的文件
    List<Tutor> mTutorList = new ArrayList<>();//教师列表

    /**
     * 所有失踪、不在白名单内的学生列表的excel位置
     * @param inputExcelPath 最开始的输入文件
     * @param studentExcelPath 里面是缺席的学生名单
     */
    public EmailSender(String inputExcelPath, String studentExcelPath){
        this.mStudentExcelPath = studentExcelPath;
        this.mInputExcelPath = inputExcelPath;
    }

    /**
     * 没有地址，默认input在当前文件夹下的cnm.xlsx，默认student在次文件夹的haha.xlsx
     */
    public EmailSender(){
        this.mInputExcelPath = System.getProperty("user.dir") + "\\input.xlsx";
        this.mStudentExcelPath = System.getProperty("user.dir") + "\\失踪学生名单_不包含白名单.xlsx";
        //this.mInputExcelPath = "C:\\Users\\12509\\Desktop\\wrh" + "\\input.xlsx";
        //this.mStudentExcelPath = "C:\\Users\\12509\\Desktop\\wrh" + "\\student.xlsx";
    }

    public void start(){
        this.getStudentList();
        this.getTutorList();
        this.sendEmail();

    }

    /**
     * 获取教师列表，将mTutorList中放入所有老师
     */
    private void getTutorList(){
        List<People> people = ExcelReader.readSimpleExcel(mInputExcelPath, "辅导员");
        Tutor t;
        for(int i = 0; i < people.size(); i++){
            t = (Tutor) people.get(i);
            mTutorList.add(t);
        }
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
            text = "";
            t = mTutorList.get(i);
            for(int j = 0; j < mStudentList.size(); j++){
                s = mStudentList.get(j);
                if(SameProp(t, s)){
                    //一样的年级、院系
                    text += s.getId() + s.getName() + "\n";
                }
            }
            if(text == ""){
                text = "全体学生到齐";
            }else{
                text = "以下学生失联\n" + text;
            }
            text = t.getName() + "您好，这一次" + t.getInstitute() + "的" + t.getGrade() + "学生夜不归宿情况是:\n" + text;
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
    private boolean SameProp(Tutor a, Student b){
        if(!a.getInstitute().equals(b.getInstitute())){
            return false;
        }else if(a.getGrade().contains(b.getGrade()) || a.getGrade().equals("全部")){
            return true;
        }
        return false;
    }



    /**
     * 获得黑名单
     */
    private void getStudentList(){
        List<People> people = ExcelReader.readSimpleExcel(mStudentExcelPath, "学生名单");
        Student s;
        for(int i = 0; i < people.size(); i++){
            s = (Student) people.get(i);
            mStudentList.add(s);
        }
    }

}
