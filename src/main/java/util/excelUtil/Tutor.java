package util.excelUtil;

import org.apache.poi.ss.usermodel.Row;

import java.util.List;


public class Tutor extends People{
    private String institute;
    private String name;
    private String grade;
    private String emailAddress;


    /**
     * 通过传入某一行来构造一个Tutor对象
     * @param row
     */
    public Tutor(Row row){
        this.institute = row.getCell(0).toString();
        this.grade = row.getCell(1).toString().substring(0, row.getCell(1).toString().length() - 2);
        this.name = row.getCell(2).toString();
        if (checkValid(row.getCell(3).toString())){
            this.emailAddress = row.getCell(3).toString();
        }
        else {
            this.emailAddress = "";
        }

    }

    /**
     * 检查Email地址是否是合法的
     * @param address
     * @return
     */
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

    public String getInstitute() {
        return institute;
    }

    public String getName() {
        return name;
    }

    public String getGrade() {
        return grade;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
}
