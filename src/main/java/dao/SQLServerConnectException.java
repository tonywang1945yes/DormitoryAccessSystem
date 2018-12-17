package dao;

import java.sql.SQLException;

public class SQLServerConnectException extends SQLException {
	protected SQLServerConnectException() {
		
	}
	
	protected SQLServerConnectException(String message) {
		super(message);
	}
}
