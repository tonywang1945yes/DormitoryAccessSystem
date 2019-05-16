package exception.daoException;

import java.sql.SQLException;

public class DBConnectionException extends SQLException {
    protected DBConnectionException() {

    }

    public DBConnectionException(String message) {
        super(message);
    }
}
