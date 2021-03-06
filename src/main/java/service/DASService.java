package service;


import entity.TimePair;
import entity.TimeRequirement;
import enums.CheckResult;
import enums.MailResult;

import java.util.List;
import java.util.Map;

public interface DASService {
    /**
     * 检查数据，生成异常学生名单
     *
     * @param longStayRequirement 长时间停留的时间要求
     * @param nightRequirement    夜不归宿的时间要求
     * @return 检查的结果
     */
    CheckResult generateStudentList(TimeRequirement longStayRequirement, TimePair nightRequirement);

    /**
     * 检查数据库连接并检查到现在为止的每一天数据库有出错情况发生
     *
     * @return 检查结果(如果为数据库出错 ， 包含出错的日期)
     */
    Map<CheckResult, List<String>> testDatabase(String url, String username, String password);

    /**
     * 读取生成的学生名单，发送邮件
     *
     * @param hostAddress 使用的邮箱
     * @param password    邮箱密码
     * @return 发送结果
     */
    MailResult sendMail(String hostAddress, String password);
}
