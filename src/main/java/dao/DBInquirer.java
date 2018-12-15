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
	 */
	public static List<Student> querySuspiciousStudent(String userName, String password) {
		String JDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";// 指定sql server驱动
		String url = "jdbc:sqlserver://<host>:<port>;datebaseName=<database name>";// 不太懂网络不过据说差不多就是这么连的

		try {
			Class.forName(JDriver);
		} catch (Exception e) {
			System.out.println("加载数据库引擎失败");
			e.printStackTrace();
			return null;
		}
		System.out.println("数据库加载成功");
		System.out.println("准备连接....");

		try {
			Connection connection = DriverManager.getConnection(url, userName, password);
			System.out.println("连接成功");
			// further developing...

		} catch (SQLException e) {
			System.out.println("连接数据库引擎失败");
			e.printStackTrace();
			return null;
		}
		return null;
	}

}