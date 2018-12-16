package service;

import entity.ListGeneratResult;
import entity.MailResult;

public interface DASservice {
    public ListGeneratResult StudentListGenerate(String excelPath, String password);
    public MailResult MailSend();
}
