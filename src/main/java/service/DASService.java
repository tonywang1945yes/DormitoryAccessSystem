package service;


import bl.strategy.LongStayInspector;
import entity.TimeRequirement;
import enums.CheckResult;
import enums.MailResult;

public interface DASService {
    CheckResult generateStudentList(String excelPath, String password, LongStayInspector inspector, TimeRequirement requirement);

    MailResult sendMail();
}
