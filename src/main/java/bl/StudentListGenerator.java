package bl;

import util.excelUtil.ExcelReader;
import util.excelUtil.People;
import util.excelUtil.Student;
import util.excelUtil.Tutor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StudentListGenerator {
    List<Tutor> mTutorList = new ArrayList<>();//教师列表
    String mExcelPath;//excel路径
    List<Student> mStudentList = new ArrayList<>();
    List<Student> mWhiteList = new ArrayList<>();

    /**
     *
     * @param excelPath excel表所在的地址
     */
    public StudentListGenerator(String excelPath){
        this.mExcelPath = excelPath;
    }

    public void start(){
        this.getTutorList();
        this.setTargetStudents();
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

    private void setTargetStudents(){
        //getWhiteList
        List<People> people = ExcelReader.readSimpleExcel(mExcelPath, "白名单");
        Student s;
        for(int i = 0; i < people.size(); i++){
            s = (Student) people.get(i);
            mWhiteList.add(s);
        }

        //获取全部失踪学生
        getStudentList();

        //将白名单中的学生去除
        removeSublist(mStudentList, mWhiteList);

    }

    /**
     * 将b列表从a列表中去除
     * @param a 被减
     * @param b
     */
    private void removeSublist(List<Student> a, List<Student> b){
        //get Iterator
        Iterator<Student> iterator = a.iterator();
        while(iterator.hasNext()){
            if(b.contains(iterator.next())){
                iterator.remove();
            }
        }
    }

    /**
     * 将mStudentList中加入失踪学生名单
     */
    private void getStudentList(){
    //TODO
    }


    private void sendEmail(){
    //TODO
    }
}
