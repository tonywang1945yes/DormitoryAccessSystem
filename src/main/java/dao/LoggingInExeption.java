package dao;

import java.sql.SQLException;

public class LoggingInExeption extends SQLException{
	protected LoggingInExeption() {
		
	}
	
	protected LoggingInExeption(String message) {
		super(message);
	}
}

