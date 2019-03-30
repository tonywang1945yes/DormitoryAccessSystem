package dao;

import entity.Student;
import entity.TimeRequirement;
import exception.daoException.DriverErrorException;
import exception.daoException.SQLServerConnectException;
import exception.loginException.LoggingInException;

import java.util.List;

public class DBInquirer {
    /**
     * 查询怀疑夜不归宿的学生
     *
     * @param userName 用户名
     * @param password 密码
     * @return 装填好的学生列表
     * @throws DriverErrorException      驱动错误
     * @throws LoggingInException        登录错误
     * @throws SQLServerConnectException 连接错误
     */
    public static List<Student> querySuspiciousStudent(String userName, String password, TimeRequirement timeCondition) throws DriverErrorException, LoggingInException, SQLServerConnectException {
        String JDriver = "com.mysql.jdbc.Driver";// 指定mysql驱动
        String url = "jdbc:mysql://114.212.99.210:3306/test";

//        try {
//            Class.forName(JDriver);
//        } catch (ClassNotFoundException e) {
//            //数据库加载失败，可能为驱动问题
//            throw new DriverErrorException("驱动加载失败");
//        }
//
//        try {
//            Connection connection = DriverManager.getConnection(url, userName, password);
//            Statement stmt = connection.createStatement();
//            String sql;
//
//        } catch (SQLException e) {
//            if (e.getMessage().contains(userName))//原异常为SQLServerException,e.message="用户 '<userName>' 登录失败"
//                throw new LoggingInException("登录失败，请检查密码");
//            else
//                throw new SQLServerConnectException("远程数据库连接失败");
//        }
        return null;
    }

}