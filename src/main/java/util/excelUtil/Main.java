package util.excelUtil;


import java.util.List;


public class Main {
    public static void main(String[] args){
        String sheetname = "白名单";
        List<People> list = ExcelReader.readSimpleExcel("C:\\Users\\Feng KX\\Documents\\Tencent Files\\727296166\\FileRecv\\测试数据.xlsx", sheetname);
        if (sheetname == "辅导员"){
            for (int i = 0; i < list.size(); i ++){
                Tutor tutor = (Tutor) list.get(i);
                System.out.println(tutor.getInstitute() + " " + tutor.getGrade() + " " + tutor.getName() + " " + tutor.getEmailAddress());
            }
        }
        else if (sheetname == "白名单"){
            for (int i = 0; i < list.size(); i ++){
                Student student = (Student) list.get(i);
                System.out.println(student.getInstitute() + " " + student.getGrade() + " " + student.getName() + " " + student.getId());
            }
        }
        else return;

    }
}
