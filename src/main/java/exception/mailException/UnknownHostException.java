package exception.mailException;

/**
 * @author Wen Sun
 * @date 2018/12/16
 */
public class UnknownHostException extends MailException {
    public UnknownHostException() {

    }

    public UnknownHostException(String message) {
        super(message);
    }
}