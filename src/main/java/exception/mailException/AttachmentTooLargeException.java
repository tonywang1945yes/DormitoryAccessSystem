package exception.mailException;

/**
 * @author Wen Sun
 * @date 2018/12/16
 */
public class AttachmentTooLargeException extends MailException {
    public AttachmentTooLargeException() {

    }

    public AttachmentTooLargeException(String message) {
        super(message);
    }
}
