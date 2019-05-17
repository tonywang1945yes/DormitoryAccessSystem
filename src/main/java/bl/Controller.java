package bl;

import bl.strategy.LongStayInspector;
import dao.MDProbe;
import entity.*;
import enums.CheckResult;
import enums.MailResult;
import exception.daoException.DatabaseErrorException;
import exception.excelException.*;
import exception.mailException.MailException;
import service.DASService;
import util.excel.ExcelUtil;

import javax.mail.MessagingException;
import java.security.GeneralSecurityException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Controller implements DASService {
    String whiteListPath;
    String blackListPath;
    String tutorMapList;
    String outputExcelPath;
    LongStayInspector inspector;

    MDProbe probe;
    Map<Tutor, String> tutorMap;

    private void readFile() throws FileNotFoundException, SheetNameException, WrongFormatException {
        List<WhiteStudent> whiteList = ExcelUtil.readWhiteList(whiteListPath, "白名单");
        List<BlackStudent> blackList = ExcelUtil.readBlackList(blackListPath, "关注名单");
        tutorMap = ExcelUtil.readTutorStudentMaps(tutorMapList, "学生辅导员对应名单");

        inspector.setWhiteList(whiteList);
        inspector.setBlackList(blackList);
    }

    public void setWhiteListPath(String whiteListPath) {
        this.whiteListPath = whiteListPath;
    }

    public void setBlackListPath(String blackListPath) {
        this.blackListPath = blackListPath;
    }

    public void setTutorMapList(String tutorMapList) {
        this.tutorMapList = tutorMapList;
    }

    public void setOutputExcelPath(String outputExcelPath) {
        this.outputExcelPath = outputExcelPath;
    }

    public void setInspector(LongStayInspector inspector) {
        this.inspector = inspector;
    }

    private void writeFile(List<SuspectStudent> students) throws FileNotWritable, FileNotClosable {
        ExcelUtil.writeSuspectStudent(students, outputExcelPath);
    }

//    private List<String> prepareDatabase(String password) throws DatabaseErrorException, DriverErrorException, DBConnectionException {
//        probe = MDProbe.build(password);
//        List<String> res;
//        try {
//            res = probe.check();
//        } catch (Exception e) {
//            if (e.getMessage().contains("ClassNotFoundException"))
//                throw new DriverErrorException("数据库驱动异常");
//            else
//                throw new DBConnectionException("连接数据库发生异常");
//        }
//        return res;
//
//    }


    private List<SuspectStudent> doCheck(TimeRequirement requirement) {
        Map<String, List<PassRecord>> recordMaps = probe.getRecordsGrouped();
        inspector.setRecordMaps(recordMaps);
        return inspector.apply(requirement);
    }


    @Override
    public CheckResult generateStudentList(TimeRequirement requirement) {

        //TODO 细分异常情况 done
        try {
            readFile();
            List<SuspectStudent> students = doCheck(requirement);
            writeFile(students);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return CheckResult.NO_SUCH_FILE;
        } catch (SheetNameException e) {
            e.printStackTrace();
            return CheckResult.SHEET_NAME_ERROR;
        } catch (WrongFormatException e) {
            e.printStackTrace();
            return CheckResult.WRONG_FORMAT;
        } catch (DatabaseErrorException e) {
            e.printStackTrace();
            return CheckResult.CONNECTION_ERROR;
        } catch (FileNotWritable | FileNotClosable e) {
            e.printStackTrace();
            return CheckResult.FILE_WRITING_ERROR;
        }


        return CheckResult.SUCCESS;

    }

    @Override
    public Map<CheckResult, List<String>> testDatabase(String password) {
        Map<CheckResult, List<String>> res = new HashMap<>();
        List<String> weridDates;
        probe = MDProbe.build(password);
        try {
            weridDates = probe.check();
            res.put(CheckResult.DATABASE_ERROR, weridDates);
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage().contains("ClassNotFoundException"))
                res.put(CheckResult.DRIVER_ERROR, null);
            else
                res.put(CheckResult.CONNECTION_ERROR, null);
        }
        return res;
    }

    @Override
    public MailResult sendMail(String hostName, String password) {
        try {
            List<SuspectStudent> students = getSuspectedStudents();
            Map<Tutor, List<String>> tsMap = mappingIds(tutorMap, students
                    .stream()
                    .map(SuspectStudent::getStudentId)
                    .collect(Collectors.toList()));

            EmailSender es = new EmailSender(tsMap, students);
            es.sendEmails(hostName, password);
        } catch (WrongFormatException | MailException | GeneralSecurityException | MessagingException e) {
            e.printStackTrace();
            return MailResult.NOT_OK;
        } catch (FileNotFoundException | SheetNameException e) {
            return MailResult.NO_SUCH_FILE;
        }
        return MailResult.OK;
    }

    private Map<Tutor, List<String>> mappingIds(Map<Tutor, String> tutorMap, List<String> ids) {
        Map<Tutor, List<String>> res = new HashMap<>();
        tutorMap.forEach((k, v) ->
                res.put(k, new ArrayList<>())
        );

        ids.forEach(id -> {
            tutorMap.entrySet()
                    .stream()
                    .filter(e -> Pattern.matches(e.getValue() + ".*", id))
                    .max(Comparator.comparing(e -> e.getValue().length()))
                    .ifPresent(e -> {
                        res.get(e.getKey()).add(e.getValue());
                    });
        });
        return res;
    }

    private List<SuspectStudent> getSuspectedStudents() throws FileNotFoundException, SheetNameException, WrongFormatException {
        return ExcelUtil.readSuspectStudent(outputExcelPath, "异常学生名单");
    }
}
