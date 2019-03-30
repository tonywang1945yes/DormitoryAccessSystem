package service;

import enums.ListGenerateResult;
import enums.MailResult;

public interface DASservice {
    public ListGenerateResult StudentListGenerate(String excelPath, String password);
    public MailResult MailSend();
}
