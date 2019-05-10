package bl;

import bl.strategy.LongStayInspector;
import dao.DaoConfig;
import dao.MDProbe;
import entity.*;
import enums.CheckResult;
import enums.MailResult;
import exception.daoException.DatabaseErrorException;
import exception.excelException.ExcelException;
import exception.excelException.FileNotFoundException;
import exception.excelException.FileNotWritable;
import exception.mailException.MailException;
import service.DASService;

import javax.mail.MessagingException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;

public class Controller implements DASService {
    String inputExcelPath;
    String outputExcelPath;
    FileLoader fileLoader;
    LongStayInspector inspector;
    MDProbe probe;
    Map<Tutor, List<String>> tsMaps;


//    public CheckResult generateStudentList(String excelPath, String password) {
//        this.inputExcelPath = excelPath;
//        try {
//            StudentListGenerator slg = new StudentListGenerator(excelPath, password);
//            slg.start();
//            mTutorList = slg.getTutorList();
//            this.outputExcelPath = slg.getOutputExcelPath();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            e.printStackTrace();
//            return CheckResult.NO_SUCH_FILE;
//        }
//
//        return CheckResult.SUCCESS;
//    }

    private void readFile(String excelPath) throws ExcelException {
//        this.inputExcelPath = excelPath;
        List<WhiteStudent> whiteList = fileLoader.getWhiteStudentList(excelPath, "白名单");
        List<BlackStudent> blackList = fileLoader.getBlackStudentList(excelPath, "关注名单");
        tsMaps = fileLoader.getTutorStudentMaps(excelPath, "学生辅导员对应名单");

        inspector.setWhiteList(whiteList);
        inspector.setBlackList(blackList);
    }

    private void writeFile(List<SuspectStudent> students) throws FileNotWritable {

    }

    private void prepareDatabase(String password) throws DatabaseErrorException {
        probe = MDProbe.build(DaoConfig.url, DaoConfig.username, password);
        probe.check();

    }

    private List<SuspectStudent> doCheck(TimeRequirement requirement) {
        Map<String, List<PassRecord>> recordMaps = probe.getRecordsGrouped();
        inspector.setRecordMaps(recordMaps);
        return inspector.apply(requirement);
    }


    @Override
    public CheckResult generateStudentList(String excelPath, String password, LongStayInspector inspector, TimeRequirement requirement) {
        this.inspector = inspector;

        try {
            readFile(excelPath);
            prepareDatabase(password);
            List<SuspectStudent> students = doCheck(requirement);
            writeFile(students);
        } catch (ExcelException e) {
            e.printStackTrace();
            return CheckResult.NO_SUCH_FILE;
        } catch (DatabaseErrorException e) {
            e.printStackTrace();
            return e.getMessage().equals("密码错误") ? CheckResult.WRONG_PASSWORD : CheckResult.DatabaseError;
        }

        return CheckResult.SUCCESS;

    }

    @Override
    public MailResult sendMail() {
        try {
            EmailSender es = new EmailSender(tsMaps, outputExcelPath);
            es.start();
        } catch (MailException | GeneralSecurityException | MessagingException e) {
            e.printStackTrace();
            return MailResult.NOT_OK;
        } catch (FileNotFoundException e) {
            return MailResult.NO_SUCH_FILE;
        }
        return MailResult.OK;
    }
}
