package bl;

import entity.ListGeneratResult;
import entity.MailResult;
import service.DASservice;

public class Controller implements DASservice{
    String mInputExcelPath;
    String mOutputExcelPath;

    public ListGeneratResult StudentListGenerate(String excelPath, String password){
        this.mInputExcelPath = excelPath;
        StudentListGenerator slg = new StudentListGenerator(excelPath, password);
        try{
            slg.start();
            this.mOutputExcelPath = slg.getOutputExcelPath();
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return ListGeneratResult.No_Such_File;
        }

        return ListGeneratResult.Success;
    }

    public MailResult MailSend(){
        EmailSender es = new EmailSender(mInputExcelPath, mOutputExcelPath);
        try{
            es.start();
        }catch (Exception e){
            e.printStackTrace();
            return MailResult.NOT_OK;
        }
        return MailResult.OK;
    }
}
