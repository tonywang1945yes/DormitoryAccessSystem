package entity;

import org.apache.poi.ss.usermodel.Row;

public class Student extends People {

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
        String grade = row.getCell(3).toString();
        if (grade.endsWith(".0")){
            grade = grade.substring(0, grade.length() - 2);
        }
        this.grade = grade;
        this.name = row.getCell(1).toString();
        String id = row.getCell(2).toString();
        if (id.endsWith(".0")){
            id = id.substring(0, id.length() - 2);
        }
        this.id = id;
    }
    public Student(String institute, String name, String grade, String id){
        this.id = id;
        this.institute = institute;
        this.grade = grade;
        this.name = name;
    }

    public String getId() {
        return id;
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

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setId(String id) {
        this.id = id;
    }
}
