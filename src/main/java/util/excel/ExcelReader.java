package util.excel;

import entity.People;
import entity.SuspectStudent;
import exception.excelException.FileNotFoundException;
import exception.excelException.SheetNameException;

import java.util.List;

public class ExcelReader {


    /**
     * 读取excel表格，并按照不同的sheetName返回不同类型的List
     *
     * @param filepath
     * @param sheetName
     * @return
     */
    public static List<People> readSimpleExcel(String filepath, String sheetName)

            throws FileNotFoundException, SheetNameException {
//        Workbook workbook = null;
//
////        由路径获取文件并解析
//        if (filepath == null) {
//            return null;
//        }
//        String type = filepath.substring(filepath.lastIndexOf("."));
//        try {
//            InputStream inputStream = new FileInputStream(filepath);
//            if (type.equals(".xls")) {
//                workbook = new HSSFWorkbook(inputStream);
//            } else if (type.equals(".xlsx")) {
//                workbook = new XSSFWorkbook(inputStream);
//
//            }
//        } catch (IOException e) {
//            throw new FileNotFoundException("The file cannot be found.");
//        }
//
////        读取文件内容
//
//        Sheet sheet = workbook.getSheet(sheetName);
//        if (sheetName.equals("辅导员")) {
//
//
//            List<People> tutorList = new ArrayList();
//            int rowNum = sheet.getLastRowNum();
//
//            try {
//                for (int i = 1; i <= rowNum; i++) {
//                    Tutor tutor = new Tutor(sheet.getRow(i));
//                    tutorList.add(tutor);
//                }
//            } catch (NullPointerException ex) {
//                throw new NullPointerException("File is empty!");
//            }
//            Collections.sort(tutorList, new Comparator<People>() {
//                //            @Override
//                public int compare(People p1, People p2) {
//                    Tutor t1 = (Tutor) p1;
//                    Tutor t2 = (Tutor) p2;
//                    if (t1.getInstitute().equals(t2.getInstitute())) {
//                        if (t1.getGrade().equals("全部")) {
//                            return -1;
//                        } else if (t2.getGrade().equals("全部")) {
//                            return 1;
//                        }
//                        return Integer.valueOf(t1.getGrade()) - Integer.valueOf(t2.getGrade());
//                    } else {
//                        return t1.getInstitute().charAt(0) - t2.getInstitute().charAt(0);
//                    }
//                }
//            });
//            return tutorList;
//
//        } else if (sheetName.equals("白名单") || sheetName.equals("学生名单") || sheetName.equals("转宿舍名单")) {
//
//            List<People> studentList = new ArrayList<>();
//            int rowNum = sheet.getLastRowNum();
//            for (int i = 1; i <= rowNum; i++) {
//                Student student = new Student(sheet.getRow(i));
//                studentList.add(student);
//            }
//
//            Collections.sort(studentList, new Comparator<People>() {
//                //                            @Override
//                public int compare(People p1, People p2) {
//                    Student s1 = (Student) p1;
//                    Student s2 = (Student) p2;
//                    if (s1.getInstitute().equals(s2.getInstitute())) {
//                        return Integer.valueOf(s1.getGrade()) - Integer.valueOf(s2.getGrade());
//                    } else {
//                        return s1.getInstitute().charAt(0) - s2.getInstitute().charAt(0);
//                    }
//                }
//            });
//
//
//            return studentList;
//        } else throw new SheetNameException(sheetName);

        return null;
    }

    public static List<SuspectStudent> readSuspectedStudentList(String filePath, String fileName) throws
            FileNotFoundException {
        return null;

    }

}