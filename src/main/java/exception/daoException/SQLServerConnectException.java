package exception.daoException;

import java.sql.SQLException;

public class SQLServerConnectException extends SQLException {
	protected SQLServerConnectException() {
		
	}
	
	public SQLServerConnectException(String message) {
		super(message);
	}
}
