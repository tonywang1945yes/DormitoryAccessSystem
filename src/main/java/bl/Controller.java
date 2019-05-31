package bl;

import bl.strategy.LongStayInspector;
import bl.strategy.NightAbsenceInspector;
import dao.MDProbe;
import entity.*;
import enums.CheckResult;
import enums.MailResult;
import enums.StudentStatus;
import exception.daoException.DBConnectionException;
import exception.daoException.DatabaseErrorException;
import exception.excelException.*;
import exception.mailException.MailException;
import service.DASService;
import util.excel.ExcelUtil;

import javax.mail.MessagingException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


//java -Xms800m -Xmx800m -XX:PermSize=256m -XX:MaxPermSize=512m -XX:MaxNewSize=512m -jar xxx.jar
//TODO 待机显示需要多次测试, 释放内存
public class Controller implements DASService {
    String whiteListPath;
    String blackListPath;
    String tutorMapListPath;
    String outputExcelPath;
    String holidayExcelPath;
    LongStayInspector longInspector;
    NightAbsenceInspector nightInspector = new NightAbsenceInspector();

    MDProbe probe;
    Map<Tutor, String> tutorMap;

    private void readFile() throws FileNotFoundException, SheetNameException, WrongFormatException {
        System.out.println("-reading files");
        List<WhiteStudent> whiteList = ExcelUtil.readWhiteList(whiteListPath, "Sheet1");
        List<BlackStudent> blackList = ExcelUtil.readBlackList(blackListPath, "Sheet1");
        tutorMap = ExcelUtil.readTutorStudentMaps(tutorMapListPath, "Sheet1");
        List<Holiday> holidayList = ExcelUtil.readHoliday(holidayExcelPath, "Sheet1");

        longInspector.setWhiteList(whiteList);
        longInspector.setBlackList(blackList);
        longInspector.setHolidays(holidayList);
    }

    public void setWhiteListPath(String whiteListPath) {
        this.whiteListPath = whiteListPath;
    }

    public void setBlackListPath(String blackListPath) {
        this.blackListPath = blackListPath;
    }

    public void setTutorMapListPath(String tutorMapListPath) {
        this.tutorMapListPath = tutorMapListPath;
    }

    public void setOutputExcelPath(String outputExcelPath) {
        this.outputExcelPath = outputExcelPath;
    }

    public void setHolidayExcelPath(String holidayExcelPath) {
        this.holidayExcelPath = holidayExcelPath;
    }

    public void setLongInspector(LongStayInspector longInspector) {
        this.longInspector = longInspector;
    }

    private void writeFile(List<SuspectStudent> students) throws FileNotWritable, FileNotClosable {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        ExcelUtil.writeSuspectStudent(students, outputExcelPath + "/" + format.format(today) + "异常学生名单.xlsx");
    }


    private List<SuspectStudent> doCheck(TimeRequirement longRequirement, TimePair nightRequirement) {
        System.out.println("-doing check");
        System.out.println("--getting records");
        Map<String, List<PassRecord>> recordMaps = probe.getRecordsGrouped();
        longInspector.setRecordMaps(recordMaps);
        nightInspector.setRecordMaps(recordMaps);

        List<SuspectStudent> r1 = longInspector.apply(longRequirement);
        List<SuspectStudent> r2 = nightInspector.apply(nightRequirement);
        return mergeRes(r1, r2);
    }


    @Override
    public CheckResult generateStudentList(TimeRequirement longStayRequirement, TimePair nightRequirement) {

        try {
            probe = MDProbe.getInstance();
            readFile();
            List<SuspectStudent> students = doCheck(longStayRequirement, nightRequirement);
            translateStatus(students);
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
    public Map<CheckResult, List<String>> testDatabase(String secret) {
        System.out.println("-checking database");
        Map<CheckResult, List<String>> res = new HashMap<>();
        List<String> weirdDates;

        if (secret == null || secret.length() == 0) {
            res.put(CheckResult.WRONG_SECRET, null);
            return res;
        }

        try {
            probe = MDProbe.build(secret);
            probe.checkConnection();
            weirdDates = probe.checkError();
            res.put(CheckResult.DATABASE_ERROR, weirdDates);

        } catch (DBConnectionException e) {
            res.put(CheckResult.WRONG_SECRET, null);
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
        System.out.println("-preparing to send emails");
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
                        res.get(e.getKey()).add(id);
                    });
        });
        return res;
    }

    private List<SuspectStudent> getSuspectedStudents() throws FileNotFoundException, SheetNameException, WrongFormatException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        return ExcelUtil.readSuspectStudent(outputExcelPath + "/" + format.format(today) + "异常学生名单.xlsx", "Sheet1");
    }

    private void translateStatus(List<SuspectStudent> students) {
        students.forEach(s -> {
            List<String> statusList = Arrays.stream(s.getStatus().split(":")).filter(o -> o.length() > 0).collect(Collectors.toList());
            StringBuilder builder = new StringBuilder();

            if (statusList.contains(StudentStatus.NIGHT_ABSENCE.name())) {
                builder.append("学生昨夜未归; ");
            }
            if (statusList.contains(StudentStatus.STILL_OUT.name()))
                builder.append("学生长时间在外，到现在仍未回; ");

            if (statusList.contains(StudentStatus.LONG_IN.name())) {
                builder.append("存在长时间待在宿舍的情况");
            } else if (statusList.contains(StudentStatus.LONG_OUT.name())) {
                builder.append("存在长时间在外的情况");
            }

            if (statusList.contains(StudentStatus.ABOUT_HOLIDAY.name()))
                builder.append(", 异常记录与设置的假期有关");

            if (statusList.contains(StudentStatus.WITH_CONFUSION.name()))
                builder.append(", 但存在混淆的记录");

            if (statusList.contains(StudentStatus.WATCHED.name()))
                builder.append("; 该学生在关注名单上");


            s.setStatus(builder.toString());
        });
    }

    private List<SuspectStudent> mergeRes(List<SuspectStudent> longStayStudents, List<SuspectStudent> nightAbsentStudents) {
        List<SuspectStudent> res = new ArrayList<>();
        List<String> longStayIds = longStayStudents.stream()
                .map(SuspectStudent::getStudentId)
                .collect(Collectors.toList());
        List<String> nightAbsentIds = nightAbsentStudents.stream()
                .map(SuspectStudent::getStudentId)
                .collect(Collectors.toList());
        //夜不归宿且没有长时间停留的学生
        res.addAll(nightAbsentStudents.stream()
                .filter(o -> !longStayIds.contains(o.getStudentId()))
                .collect(Collectors.toList()));

        //两个集合的交集
        longStayStudents.stream()
                .filter(o -> nightAbsentIds.contains(o.getStudentId()))
                .forEach(o -> {
                    o.setLevel(o.getLevel() + 1);
                    o.setStatus(o.getStatus() + ":" + StudentStatus.NIGHT_ABSENCE.name());
                    o.getDoubtfulRecords().addAll(nightAbsentStudents.stream().filter(s -> s.getStudentId().equals(o.getStudentId())).findFirst().get().getDoubtfulRecords());
                    res.add(o);
                });

        //长时间停留且未夜不归宿的学生
        res.addAll(longStayStudents.stream().filter(o -> !nightAbsentIds.contains(o.getStudentId())).collect(Collectors.toList()));
        return res;

    }
}
