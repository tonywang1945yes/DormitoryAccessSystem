package exception.mailException;

/**
 * @author Wen Sun
 * @date 2018/12/16
 */
public abstract class MailException extends Exception {
    protected MailException() {

    }

    protected MailException(String message) {
        super(message);
    }
}
