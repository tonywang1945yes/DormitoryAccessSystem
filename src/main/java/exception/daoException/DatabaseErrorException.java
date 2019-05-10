package exception.daoException;

/**
 * simple introduction
 *
 * @author Nosolution
 * @version 1.0
 * @since 2019/4/26
 */
public class DatabaseErrorException extends RuntimeException {

    public DatabaseErrorException(String message) {
        super(message);
    }

    public DatabaseErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
