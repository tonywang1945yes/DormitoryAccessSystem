package util.mailUtil.mailException;

import service.ExceptionEnum;

/**
 * @author Wen Sun
 * @date 2018/12/16
 */
public class MailException extends RuntimeException {

    private ExceptionEnum exceptionEnum;
    private int errorCode;
    private String errorMessage;

    public MailException(ExceptionEnum exceptionEnum) {
        this.exceptionEnum = exceptionEnum;
        this.errorCode = exceptionEnum.getCode();
        this.errorMessage = exceptionEnum.getMessage();
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }


}