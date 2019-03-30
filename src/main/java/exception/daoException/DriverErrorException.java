package exception.daoException;

import java.sql.SQLException;

public class DriverErrorException extends SQLException {
	protected DriverErrorException() {
		
	}
	
	public DriverErrorException(String message) {
		super(message);
	}
}
