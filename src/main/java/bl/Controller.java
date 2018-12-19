package bl;

import dao.DriverErrorException;
import dao.LoggingInExeption;
import dao.SQLServerConnectException;
import entity.ListGeneratResult;
import entity.MailResult;
import entity.Tutor;
import service.DASservice;
import util.excelUtil.ExcelException.FileNotClosable;
import util.excelUtil.ExcelException.FileNotWritable;
import util.mailUtil.mailException.MailException;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.security.GeneralSecurityException;
import java.util.List;

public class Controller implements DASservice{
    String mInputExcelPath;
    String mOutputExcelPath;
    List<Tutor> mTutorList;

    public ListGeneratResult StudentListGenerate(String excelPath, String password){
        this.mInputExcelPath = excelPath;
        try{
            StudentListGenerator slg = new StudentListGenerator(excelPath, password);
            slg.start();
            mTutorList = slg.getTutorList();
            this.mOutputExcelPath = slg.getOutputExcelPath();
        }catch (FileNotFoundException | FileNotWritable |
                FileNotClosable e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return ListGeneratResult.No_Such_File;
        }catch (DriverErrorException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return ListGeneratResult.Driver_Error;
        }catch (LoggingInExeption e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return ListGeneratResult.Wrong_Password;
        }catch (SQLServerConnectException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return ListGeneratResult.Connection_Error;
        }

        return ListGeneratResult.Success;
    }

    public MailResult MailSend(){
        try{
            EmailSender es = new EmailSender(mTutorList, mOutputExcelPath);
            es.start();
        } catch (MailException | GeneralSecurityException | MessagingException e){
            e.printStackTrace();
            return MailResult.NOT_OK;
        }catch (java.io.FileNotFoundException e){
            return MailResult.No_FILE;
        }
        return MailResult.OK;
    }
}
