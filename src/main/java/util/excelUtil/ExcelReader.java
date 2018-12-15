package util.excelUtil;

import entity.People;
import entity.Student;
import entity.Tutor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ExcelReader {


    /**
     * 读取excel表格，并按照不同的sheetName返回不同类型的List
     * @param filepath
     * @param sheetName
     * @return
     */
    public static List<People> readSimpleExcel(String filepath, String sheetName){
        Workbook workbook = null;

//        由路径获取文件并解析
        if (filepath == null){
            return null;
        }
        String type = filepath.substring(filepath.lastIndexOf("."));
        try {
            InputStream inputStream = new FileInputStream(filepath);
            if (type.equals(".xls")){
                workbook = new HSSFWorkbook(inputStream);
            }
            else if (type.equals(".xlsx")){
                workbook = new XSSFWorkbook(inputStream);
            }
            else {
                workbook = null;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        读取文件内容
        if (workbook == null){
            return null;
        }
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheetName == "辅导员") {


            List<People> tutorList = new ArrayList();
            int rowNum = sheet.getLastRowNum();
            for (int i = 1; i <= rowNum; i ++){
                Tutor tutor = new Tutor(sheet.getRow(i));
                tutorList.add(tutor);
            }

//            Collections.sort(tutorList, new Comparator<People>() {
//                //            @Override
//                public int compare(People p1, People p2) {
//                    Tutor t1 = (Tutor)p1;
//                    Tutor t2 = (Tutor)p2;
//                    if (t1.getInstitute().equals(t2.getInstitute())){
//                        if (t1.getGrade().equals("全部")){
//                            return -1;
//                        }
//                        else if (t2.getGrade().equals("全部")){
//                            return 1;
//                        }
//                        return Integer.valueOf(t1.getGrade()) - Integer.valueOf(t2.getGrade());
//                    }
//                    else {
//                        return t1.getInstitute().charAt(0) - t2.getInstitute().charAt(0);
//                    }
//                }
//            });
            return tutorList;
        }

        else if (sheetName == "白名单"){

            List<People> studentList = new ArrayList<>();
            int rowNum = sheet.getLastRowNum();
            for (int i = 1; i <= rowNum; i ++){
                Student student = new Student(sheet.getRow(i));
                studentList.add(student);
            }

            Collections.sort(studentList, new Comparator<People>() {
                //                            @Override
                public int compare(People p1, People p2) {
                    Student s1 = (Student)p1;
                    Student s2 = (Student)p2;
                    if (s1.getInstitute().equals(s2.getInstitute())){
                        return Integer.valueOf(s1.getGrade()) - Integer.valueOf(s2.getGrade());
                    }
                    else {
                        return s1.getInstitute().charAt(0) - s2.getInstitute().charAt(0);
                    }
                }
            });


            return studentList;
        }

        else return null;
    }

}