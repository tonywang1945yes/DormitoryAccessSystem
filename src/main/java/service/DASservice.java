package service;

import entity.ListGenerateResult;
import entity.MailResult;

public interface DASservice {
    public ListGenerateResult StudentListGenerate(String excelPath, String password);
    public MailResult MailSend();
}
