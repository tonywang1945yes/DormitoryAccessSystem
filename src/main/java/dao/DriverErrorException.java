package dao;

import java.sql.SQLException;

public class DriverErrorException extends SQLException {
	protected DriverErrorException() {
		
	}
	
	protected DriverErrorException(String message) {
		super(message);
	}
}
