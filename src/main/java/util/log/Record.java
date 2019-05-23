package util.log;

import enums.Type;

public class Record {
    private String date;
    private String time;
    private Type type;
    private int num;
    private String description;
    private String whiteSheet;
    private String relationSheet;
    private String concernSheet;
    private String resultSheet;
    private String festival;

    public String getRelationSheet() {
        return relationSheet;
    }

    public void setRelationSheet(String relationSheet) {
        this.relationSheet = relationSheet;
    }

    public String getConcernSheet() {
        return concernSheet;
    }

    public void setConcernSheet(String concernSheet) {
        this.concernSheet = concernSheet;
    }

    public String getResultSheet() {
        return resultSheet;
    }

    public void setResultSheet(String resultSheet) {
        this.resultSheet = resultSheet;
    }

    public String getWhiteSheet() {
        return whiteSheet;
    }

    public void setWhiteSheet(String whiteSheet) {
        this.whiteSheet = whiteSheet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getFestival() {
        return festival;
    }

    public void setFestival(String festival) {
        this.festival = festival;
    }

    public Record(){

    }

    public Record(Type type,String description,String date,String time){
        this.type = type;
        this.description = description;
        this.date = date;
        this.time = time;
        this.num = 1;
    }

    public Record(String date, String time, Type type, int num, String description) {
        this.date = date;
        this.time = time;
        this.type = type;
        this.num = num;
        this.description = description;
    }

    public Record(String date, String time, Type type, int num, String description, String whiteSheet, String relationSheet, String concernSheet,String festival, String resultSheet) {
        this.date = date;
        this.time = time;
        this.type = type;
        this.num = num;
        this.description = description;
        this.whiteSheet = whiteSheet;
        this.relationSheet = relationSheet;
        this.concernSheet = concernSheet;
        this.festival = festival;
        this.resultSheet = resultSheet;
    }
}
