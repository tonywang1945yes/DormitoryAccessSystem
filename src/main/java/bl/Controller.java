package bl;

import entity.Tutor;
import enums.ListGenerateResult;
import enums.MailResult;
import exception.mailException.MailException;
import service.DASservice;

import javax.mail.MessagingException;
import java.security.GeneralSecurityException;
import java.util.List;

public class Controller implements DASservice {
    String mInputExcelPath;
    String mOutputExcelPath;
    List<Tutor> mTutorList;

    public ListGenerateResult StudentListGenerate(String excelPath, String password) {
        this.mInputExcelPath = excelPath;
        try {
            StudentListGenerator slg = new StudentListGenerator(excelPath, password);
            slg.start();
            mTutorList = slg.getTutorList();
            this.mOutputExcelPath = slg.getOutputExcelPath();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return ListGenerateResult.NO_SUCH_FILE;
        }

        return ListGenerateResult.SUCCESS;
    }

    public MailResult MailSend() {
        try {
            EmailSender es = new EmailSender(mTutorList, mOutputExcelPath);
            es.start();
        } catch (MailException | GeneralSecurityException | MessagingException e) {
            e.printStackTrace();
            return MailResult.NOT_OK;
        } catch (java.io.FileNotFoundException e) {
            return MailResult.NO_SUCH_FILE;
        }
        return MailResult.OK;
    }
}
