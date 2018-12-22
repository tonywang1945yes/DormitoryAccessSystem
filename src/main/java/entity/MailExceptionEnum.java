package entity;

import service.ExceptionEnum;

/**
 * @author Wen Sun
 * @date 2018/12/22
 */
public enum MailExceptionEnum implements ExceptionEnum {
    ATTACHMENT_TO_LARGE(1, "Attachment too large."),//附件过大
    SOURCE_PATH_NOT_EXIST(2, "Source file not exists."),//源文件不存在
    DIRECTORY_IS_EMPTY(3, "Directory is empty"),//文件夹为空
    ZIP_PATH_NOT_EXIST(4, "Zip path not exists."),//压缩文件路径不存在
    IO_STREAM_EXCEPTION(5, "Error occurred when IO."),//IO流异常
    ADDRESS_ERROR(6, "Address error."),//地址错误
    MESSAGING_EXCEPTION(7, "Error occurred when messaging."),//发送过程中的其他错误
    SECURITY_EXCEPTION(8, "SSL Security exception."),//安全异常
    UNKNOWN_HOST(9, "Host unknown.");//未知邮件服务器

    private int errorCode;
    private String errorMessage;

    private MailExceptionEnum(int code, String message) {
        this.errorCode = code;
        this.errorMessage = message;
    }

    public void setCode(int code) {
        this.errorCode = code;
    }

    public void setMessage(String message) {
        this.errorMessage = message;
    }

    @Override
    public int getCode() {
        return this.errorCode;
    }

    @Override
    public String getMessage() {
        return this.errorMessage;
    }}
