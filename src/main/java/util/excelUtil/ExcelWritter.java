package util.excelUtil;

import entity.Student;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import util.excelUtil.ExcelException.FileNotClosable;
import util.excelUtil.ExcelException.FileNotWritable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


public class ExcelWritter {
    /**
     * 新建excel文件。并把list中学生数据写入
     * @param studentList（已排好序）
     * @param filepath
     */
    public static void writeSimpleExcel(List<Student> studentList, String filepath)
            throws FileNotFoundException, FileNotWritable, FileNotClosable {

        Workbook workbook;
        if (filepath.endsWith(".xls")){
            workbook = new HSSFWorkbook();
        }
        else {
            workbook = new XSSFWorkbook();
        }

        Sheet sheet = workbook.createSheet("学生名单");
        Row title = sheet.createRow(0);
        Cell cell0 = title.createCell(0);
        cell0.setCellValue("院系");
        Cell cell1 = title.createCell(1);
        cell1.setCellValue("姓名");
        Cell cell2 = title.createCell(2);
        cell2.setCellValue("学号");
        Cell cell3 = title.createCell(3);
        cell3.setCellValue("年级");

        if (studentList != null || studentList.size() != 0){
            for (int i = 0; i < studentList.size(); i ++){
                Student student = studentList.get(i);
                Row row = sheet.createRow(i + 1);
                Cell institute = row.createCell(0);
                institute.setCellValue(student.getInstitute());
                Cell name = row.createCell(1);
                name.setCellValue(student.getName());
                Cell id = row.createCell(2);
                id.setCellValue(student.getId());
                Cell grade = row.createCell(3);
                grade.setCellValue(student.getGrade());
            }
        }

//        新建文件。找不到文件夹。
        FileOutputStream out = null;
        try {
            File file = new File(filepath).getParentFile();
            if (!file.exists()){
                file.mkdirs();
            }
            out = new FileOutputStream(filepath);
            workbook.write(out);
        } catch (IOException e) {
            throw new FileNotWritable("The file cannot be written to.");
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                throw new FileNotClosable("The file cannot be closed normally.");
            }
        }

    }
}
