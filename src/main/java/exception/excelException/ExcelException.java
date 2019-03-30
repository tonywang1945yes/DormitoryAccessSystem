package exception.excelException;

public abstract class ExcelException extends Exception {

    protected ExcelException(){

    }

    protected ExcelException(String message){
        super(message);
    }
}
