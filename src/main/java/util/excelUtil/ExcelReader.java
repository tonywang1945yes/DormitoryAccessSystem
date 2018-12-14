package util.excelUtil;

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

    public static List<Tutor> readSimpleExcel(String filepath, String sheetName){
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
        List<Tutor> tutorList = new ArrayList();
        int rowNum = sheet.getLastRowNum();
        for (int i = 1; i <= rowNum; i ++){
            Tutor tutor = new Tutor(sheet.getRow(i));
            tutorList.add(tutor);
        }

        Collections.sort(tutorList, new Comparator<Tutor>() {
            //@Override
            public int compare(Tutor t1, Tutor t2) {
                if (t1.institute.equals(t2.institute)){
                    return Integer.valueOf(t1.grade) - Integer.valueOf(t2.grade);
                }
                else {
                    return t1.institute.charAt(0) - t2.institute.charAt(0);
                }
            }
        });

        return tutorList;
    }

}
