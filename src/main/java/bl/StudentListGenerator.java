package bl;

import util.excelUtil.ExcelReader;
import util.excelUtil.People;
import util.excelUtil.Student;
import util.excelUtil.Tutor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StudentListGenerator {

    String mExcelPath;//excel路径
    List<Student> mStudentList = new ArrayList<>();
    List<Student> mWhiteList = new ArrayList<>();

    /**
     * 初始化时输入excel表所在位置.表中包含教师列表以及白名单
     * @param excelPath excel表所在的地址
     */
    public StudentListGenerator(String excelPath){
        this.mExcelPath = excelPath;
    }

    /**
     * 如果没有的话，默认这个表存放在当前程序所在文件夹下，名字叫做cnm.xlsx
     */
    public StudentListGenerator(){
        this.mExcelPath = System.getProperty("user.dir") + "/cnm.xlsx";
    }

    public void start(){
        this.setTargetStudents();
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
        List<Student> result = new ArrayList<>();


    }

}
