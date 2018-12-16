package bl;

import util.excelUtil.ExcelReader;
import entity.People;
import entity.Student;
import entity.Tutor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StudentListGenerator {

    String mInputExcelPath;//excel路径
    List<Student> mStudentList = new ArrayList<>();
    List<Student> mWhiteList = new ArrayList<>();


    /**
     * 初始化时输入excel表所在位置.表中包含教师列表以及白名单
     * @param excelPath excel表所在的地址
     */
    public StudentListGenerator(String excelPath){
        this.mInputExcelPath = excelPath;
    }

    /**
     * 如果没有的话，默认这个表存放在当前程序所在文件夹下，名字叫做cnm.xlsx
     */
    public StudentListGenerator(){
        this.mInputExcelPath = System.getProperty("user.dir") + "/cnm.xlsx";
    }

    public void start(){
        this.setTargetStudents();
    }


    /**
     * 获得所有失踪且不在黑名单中的学生
     */
    private void setTargetStudents(){
        //getWhiteList
        List<People> people = ExcelReader.readSimpleExcel(mInputExcelPath, "白名单");
        Student s;
        for(int i = 0; i < people.size(); i++){
            s = (Student) people.get(i);
            mWhiteList.add(s);
        }

        //获取全部失踪学生
        getStudentList();
        System.out.println("所有不在的学生有：");
        for(Student a : mStudentList){
            System.out.println(a.getInstitute() + " " + a.getName());
        }
        System.out.println();

        //将白名单中的学生去除
        removeSublist(mStudentList, mWhiteList);

        System.out.println("除去白名单，不在的学生有：");
        for(Student a : mStudentList){
            System.out.println(a.getInstitute() + " " + a.getName());
        }
        System.out.println();



    }

    /**
     * 将b列表从a列表中去除
     * @param a 被减
     * @param b
     */
    private void removeSublist(List<Student> a, List<Student> b){
        Iterator<Student> it = a.iterator();
        String id;
        while(it.hasNext()){
            id = it.next().getId();
            for(int i = 0; i < b.size(); i++){
                if(id.equals(b.get(i).getId())){
                    it.remove();
                    break;
                }
            }
        }
    }



    /**
     * 将mStudentList中加入失踪学生名单
     */
    private void getStudentList(){
        //TODO
        List<Student> result = new ArrayList<>();
        mStudentList.add(new Student("软件学院", "冯二", "2017", "1"));
        mStudentList.add(new Student("商学院", "李六", "2017", "5"));
        mStudentList.add(new Student("文学院", "张三", "2018", "4"));
        mStudentList.add(new Student("软件学院", "cnm", "2016", "2"));
        mStudentList.add(new Student("软件学院", "冯三", "2017", "1"));
        mStudentList.add(new Student("商学院", "李六六", "2017", "5"));
        mStudentList.add(new Student("文学院", "张三三", "2018", "4"));
        mStudentList.add(new Student("软件学院", "cnmm", "2016", "2"));
        mStudentList.add(new Student("软件学院", "冯二二二", "2017", "1"));
        mStudentList.add(new Student("商学院", "李六六六", "2017", "5"));
        mStudentList.add(new Student("文学院", "张三三三", "2018", "4"));
        mStudentList.add(new Student("软件学院", "cnmmm", "2016", "2"));
        mStudentList.add(new Student("软件学院", "冯二二二", "2017", "1"));
        mStudentList.add(new Student("商学院", "李六六六", "2017", "5"));
        mStudentList.add(new Student("文学院", "张三三三", "2018", "4"));
        mStudentList.add(new Student("软件学院", "cnmm", "2016", "2"));
    }


}
