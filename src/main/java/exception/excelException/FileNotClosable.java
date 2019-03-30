package exception.excelException;

public class FileNotClosable extends ExcelException {

    public FileNotClosable(){

    }

    public FileNotClosable(String message){
        super(message);
    }
}
