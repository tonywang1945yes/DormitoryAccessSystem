package dao;

import entity.PassRecord;
import entity.Student;
import entity.TimeRequirement;
import exception.daoException.DriverErrorException;
import exception.daoException.SQLServerConnectException;
import exception.loginException.LoggingInException;

import java.sql.*;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 执行筛选逻辑的DB探针类
 * 后续会考虑架空DBInquirer
 */
public class DBProbe {
    private String url;
    private String userName;
    private String password;
    private Connection connection;
    private Statement stmt;

    public DBProbe(String url, String userName, String password) {
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

    public void buildConnection() throws DriverErrorException, LoggingInException, SQLServerConnectException {
        String JDriver = "com.mysql.jdbc.Driver";// 指定mysql驱动
        try {
            Class.forName(JDriver);
        } catch (ClassNotFoundException e) {
            //数据库加载失败，可能为驱动问题
            throw new DriverErrorException("驱动加载失败");
        }

        try {
            connection = DriverManager.getConnection(url, userName, password);
            stmt = connection.createStatement();

        } catch (SQLException e) {
            if (e.getMessage().contains(userName))//原异常为SQLServerException,e.message="用户 '<userName>' 登录失败"
                throw new LoggingInException("登录失败，请检查密码");
            else
                throw new SQLServerConnectException("远程数据库连接失败");
        }
    }

    public List<Student> querySuspiciousStudent(TimeRequirement timeRequirement) {
        try {
            if (timeRequirement.day + 1 < DaoConfig.FIRSTBUFFERINTERVAL) {
                return queryOneBuffer(timeRequirement);
            } else if (timeRequirement.day + 1 < DaoConfig.SECONDBUFFERINTERVAL) {
                return queryTwoBuffer(timeRequirement);
            } else {
                return queryThreeBuffer(timeRequirement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        /*
        一、夜不归宿
        1.已经回来了并且已经刷了2次以上的卡
        2.已经回来了，刷了1次卡
        3.还没回来

        1、2可归为一类，主要是怎么从记录中判断有人的间隔大于提供的条件，感觉好像没法直接用sql语句实现
        3独自一类，可直接用sql语句筛选出来
         */
        return null;
    }

    public List<Student> queryOneBuffer(TimeRequirement timeRequirement) throws SQLException {
        Instant st = Instant.now();
//        String start = DaoConfig.timeFormatAdapter(timeRequirement.startTime);
        String sql = "SELECT UserId FROM passinfo GROUP BY UserId";
        ResultSet rs = stmt.executeQuery(sql);

        List<String> idList = new ArrayList<>();
        while (rs.next())
            idList.add(rs.getString("UserId"));
        Instant et = Instant.now();
        Duration duration = Duration.between(et, st);
        System.out.println("找出所有id耗时 " + duration + " 秒"); //平均消耗时间大约为5秒
//        List<String> res = idList.stream()
//                .map(this::loadPersonalRecord)
//                .filter(o1 -> judge(o1, timeRequirement))
//                .map(o1 -> o1.get(0).getUserId())
//                .collect(Collectors.toList());


        /*
        这里需要进一步的处理
         */
        return null;

    }

    private List<Student> queryTwoBuffer(TimeRequirement timeRequirement) {
        return null;
    }

    private List<Student> queryThreeBuffer(TimeRequirement timeRequirement) {
        return null;
    }

    public boolean judge(List<PassRecord> records, TimeRequirement requirement) {
//        List<PassRecord> yesterday = records
//                .stream()
//                .filter(o1 -> o1.getPassTime().get(Calendar.DATE) == requirement.startTime.get(Calendar.DATE))
//                .collect(Collectors.toList());
//        if (yesterday.size() != 0 && yesterday.get(0).getDirection().equals("1")) {
//            //如果前一天有进出记录(可能因为各种原因没有记录)且最后一次刷卡记录为出去，被怀疑
//            return true;
//        }
        return true;
    }


    //将id对应的学生的通过记录装进List中
    public List<PassRecord> loadPersonalRecord(String id) {//这一步需要耗费略多于3秒的时间
        List<PassRecord> result = new ArrayList<>();
        String sql = "SELECT * FROM passinfo WHERE UserId = '" + id + "' order by PassTime DESC ;";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                PassRecord pr = rs2pr(rs);
                result.add(pr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //将一条ResultSet记录中的内容填充进PassRecord实例
    private PassRecord rs2pr(ResultSet rs) throws SQLException {
        PassRecord pr = new PassRecord();
        pr.setId(rs.getInt("id"));
        pr.setUserId(rs.getInt("UserId"));
        pr.setSysId(rs.getInt("SysId"));
        pr.setPassStatus(rs.getInt(("PassStatus")));
        pr.setDirection(rs.getInt(("Direction")));
        Calendar c = Calendar.getInstance();
        c.setTime(rs.getTime("PassTime"));
        pr.setPassTime(c);
        return pr;
    }
}