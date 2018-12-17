package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import entity.Student;

public class DBInquirer {
	/**
	 * 查询怀疑夜不归宿的学生
	 * 
	 * @param userName 用户名
	 * @param password 密码
	 * @return 装填好的学生列表
	 * @throws DriverErrorException 驱动错误
	 * @throws LogginginException 登录错误
	 * @throws SQLServerConnectException 连接错误
	 */
	public static List<Student> querySuspiciousStudent(String userName, String password) throws DriverErrorException,LoggingInExeption, SQLServerConnectException{
		String JDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";// 指定sql server驱动
		String url = "jdbc:sqlserver://<host>:<port>;datebaseName=<database name>";// 不太懂网络不过据说差不多就是这么连的

		try {
			Class.forName(JDriver);
		} catch (ClassNotFoundException e) {
			//数据库加载失败，可能为驱动问题
			throw new DriverErrorException("驱动加载失败");
		}

		try {
			Connection connection = DriverManager.getConnection(url, userName, password);
			// further developing...

		} catch (SQLException e) {
			if(e.getMessage().contains(userName))//原异常为SQLServerException,e.message="用户 '<userName>' 登录失败"
				throw new LoggingInExeption("登录失败，请检查密码");
			else
				throw new SQLServerConnectException("SQLServer连接失败");
		}
		return null;
	}

}