package util.excelUtil;

import org.apache.poi.ss.usermodel.Row;

public class Tutor {
    String institute;
    String name;
    String grade;
    String emailAddress;

    public Tutor(Row row){
        this.institute = row.getCell(0).toString();
        if (row.getCell(1).toString().contains(".")){
            this.grade = row.getCell(1).toString().substring(0, row.getCell(1).toString().lastIndexOf("."));
        }
        else {
            this.grade = row.getCell(1).toString();
        }
        this.name = row.getCell(2).toString();
        if (checkValid(row.getCell(3).toString())){
            this.emailAddress = row.getCell(3).toString();
        }
        else {
            this.emailAddress = "";
        }

    }

    private boolean checkValid(String address){
        if (address == null || address.length() == 0 || address.length() > 30){
            return false;
        }
        if (!address.contains("@") || !address.contains(".")){
            return false;
        }
        if (address.lastIndexOf("@") > address.lastIndexOf(".")
                || address.charAt(address.length() - 1) == '.'){
            return false;
        }
        char capital = address.charAt(0);
        if (!(capital == '_'
                || (capital >= 'a' && capital <= 'z')
                || (capital >= 'A' && capital <= 'Z')
                || (capital >= '0' && capital <= '9'))){
            return false;
        }
        return true;
    }
}
