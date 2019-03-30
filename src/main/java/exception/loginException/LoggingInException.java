package exception.loginException;

import java.sql.SQLException;

public class LoggingInException extends SQLException{
	protected LoggingInException() {
		
	}
	
	public LoggingInException(String message) {
		super(message);
	}
}

