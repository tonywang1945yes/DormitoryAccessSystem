package exception.excelException;

public class FileNotWritable extends ExcelException {
    public FileNotWritable(){

    }

    public FileNotWritable(String message){
        super(message);
    }
}
