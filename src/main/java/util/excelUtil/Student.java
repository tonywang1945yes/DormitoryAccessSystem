package util.excelUtil;

import org.apache.poi.ss.usermodel.Row;

public class Student extends People{
    private String institute;
    private String name;
    private String grade;
    private String id;

    /**
     * 通过传入某一行来构造一个Student对象
     * @param row
     */
    public Student(Row row){
        this.institute = row.getCell(0).toString();
        this.grade = row.getCell(3).toString().substring(0, row.getCell(3).toString().length() - 2);
        this.name = row.getCell(1).toString();
        this.id = row.getCell(2).toString().substring(0, row.getCell(2).toString().length() - 2);
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

    public String getId() {
        return id;
    }
}
