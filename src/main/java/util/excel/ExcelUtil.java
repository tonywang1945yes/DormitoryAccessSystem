package util.excel;

import entity.*;
import exception.excelException.FileNotFoundException;
import exception.excelException.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MINUTES;

/**
 * Excel文件的读写类
 *
 * @author Nosolution
 * @version 1.0
 * @since 2019/5/14
 */
public class ExcelUtil {

    /**
     * 读取白名单，格式参照说明
     *
     * @param filePath 白名单所在路径
     * @return 白名单列表
     * @throws FileNotFoundException 不存在指定文件时抛出该异常
     * @throws WrongFormatException  文件格式解析错误时抛出该异常
     */
    public static List<WhiteStudent> readWhiteList(String filePath, String sheetName) throws FileNotFoundException, WrongFormatException, SheetNameException {
        Sheet sheet = openSheet(filePath, sheetName);

        List<WhiteStudent> whiteList = new LinkedList<>();
        int rowNum = sheet.getLastRowNum();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        try {
            for (int i = 1; i <= rowNum; i++) {
                Row row = sheet.getRow(i);
                String studentId = row.getCell(0).toString();
                Timestamp startTime = new Timestamp(format.parse(row.getCell(1).toString()).getTime());
                Timestamp endTime = new Timestamp(format.parse(row.getCell(2).toString()).getTime());

                WhiteStudent ws = new WhiteStudent();
                ws.setStudentId(studentId);
                ws.setValidTime(new TimePair(startTime, endTime));
                whiteList.add(ws);

            }
        } catch (NullPointerException | ParseException e) {
            e.printStackTrace();
            throw new WrongFormatException("格式不匹配");
        }

        return whiteList;
    }

    /**
     * 读取黑名单，格式参照说明
     *
     * @param filePath 黑名单所在路径
     * @return 黑名单列表
     * @throws FileNotFoundException 不存在指定文件时抛出该异常
     * @throws WrongFormatException  文件格式解析错误时抛出该异常
     */
    public static List<BlackStudent> readBlackList(String filePath, String sheetName) throws FileNotFoundException, WrongFormatException, SheetNameException {
        Sheet sheet = openSheet(filePath, sheetName);

        List<BlackStudent> blackList = new ArrayList<>();
        int rowNum = sheet.getLastRowNum();

        try {
            for (int i = 1; i <= rowNum; i++) {
                Row row = sheet.getRow(i);
                row.getCell(0).setCellType(CellType.STRING);
                String studentId = row.getCell(0).toString();
                Duration outDuration = parseHours(row.getCell(1).toString());
                Duration limitationToNow = parseHours(row.getCell(2).toString());
                Duration inDuration = parseHours(row.getCell(3).toString());
                Duration minBreak = parseMinutes(row.getCell(4).toString());

                BlackStudent bs = new BlackStudent();
                bs.setStudentId(studentId);
                bs.setOutReqDuration(outDuration);
                bs.setLimitToNow(limitationToNow);
                bs.setInReqDuration(inDuration);
                bs.setMinBreak(minBreak);
                blackList.add(bs);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw new WrongFormatException("格式不匹配");
        }

        return blackList;
    }

    /**
     * 读取辅导员学号对应名单，格式参照说明
     *
     * @param filePath 名单所在路径
     * @return Map包含辅导员信息与对应的学号信息
     * @throws FileNotFoundException 不存在指定文件时抛出该异常
     * @throws WrongFormatException  文件格式解析错误时抛出该异常
     */
    public static Map<Tutor, String> readTutorStudentMaps(String filePath, String sheetName) throws FileNotFoundException, WrongFormatException, SheetNameException {
        Sheet sheet = openSheet(filePath, sheetName);
        Map<Tutor, String> res = new HashMap<>();
        int rowNum = sheet.getLastRowNum();
        try {
            for (int i = 1; i <= rowNum; i++) {
                Row row = sheet.getRow(i);
                String idPrefix = row.getCell(0).toString();
                String emailAddress = row.getCell(1).toString();
                String name = row.getCell(2).toString();
                String institution = row.getCell(3).toString();
                String grade = row.getCell(4).toString();

                Tutor t = new Tutor();
                t.setEmailAddress(emailAddress);
                t.setName(name);
                t.setInstitute(institution);
                t.setGrade(grade);

                res.put(t, idPrefix);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw new WrongFormatException("格式不匹配");
        }

        return res;
    }

    /**
     * 读取之前程序输出的异常学生名单
     *
     * @param filePath 名单所在路径
     * @return 异常学生列表
     * @throws FileNotFoundException 不存在指定文件时抛出该异常
     * @throws WrongFormatException  文件格式解析错误时抛出该异常
     */
    public static List<SuspectStudent> readSuspectStudent(String filePath, String sheetName) throws FileNotFoundException, WrongFormatException, SheetNameException {
        Sheet sheet = openSheet(filePath, sheetName);

        List<SuspectStudent> res = new ArrayList<>();
        int rowNum = sheet.getLastRowNum();
        boolean uncompleted = false;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");

        try {
            SuspectStudent s = null;
            List<TimePair> records = new ArrayList<>();
            for (int i = 1; i <= rowNum; i++) {
                Row row = sheet.getRow(i);
                if (row.getFirstCellNum() == 0) {
                    s = new SuspectStudent();
                    records = new ArrayList<>();

                    String studentId = row.getCell(0).toString();
                    s.setStudentId(studentId);
                    String status = row.getCell(1).toString();
                    row.getCell(2).setCellType(CellType.STRING);
                    Integer level = Double.valueOf(row.getCell(2).toString()).intValue();

                    s.setStudentId(studentId);
                    s.setStatus(status);
                    s.setLevel(level);
                    s.setDoubtfulRecords(records);

                    res.add(s);

                }

                Timestamp startTime = new Timestamp(format.parse(row.getCell(3).toString()).getTime());
                Timestamp endTime = new Timestamp(format.parse(row.getCell(4).toString()).getTime());
                records.add(new TimePair(startTime, endTime));

            }

        } catch (NullPointerException | ParseException e) {
            e.printStackTrace();
            throw new WrongFormatException("格式不匹配");
        }

        return res;

    }

    /**
     * 将异常学生列表输出为excel表格
     *
     * @param students 异常学生列表
     * @param filePath 待输出的文件路径
     * @throws FileNotWritable 文件不可被写入时抛出该异常
     * @throws FileNotClosable 文件无法正常关闭时抛出该异常
     */
    public static void writeSuspectStudent(List<SuspectStudent> students, String filePath) throws FileNotWritable, FileNotClosable {
        Workbook workbook;
        if (filePath.endsWith(".xls")) {
            workbook = new HSSFWorkbook();
        } else {
            workbook = new XSSFWorkbook();
        }

        Sheet sheet = workbook.createSheet("异常学生名单");
        Row title = sheet.createRow(0);
        Cell cell0 = title.createCell(0);
        cell0.setCellValue("学号");
        Cell cell1 = title.createCell(1);
        cell1.setCellValue("状态");
        Cell cell2 = title.createCell(2);
        cell2.setCellValue("严重等级");
        Cell cell3 = title.createCell(3);
        cell3.setCellValue("开始时间");
        Cell cell4 = title.createCell(4);
        cell4.setCellValue("结束时间");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (students != null && students.size() != 0) {
            int total = 1;
            for (int i = 0; i < students.size(); i++, total++) {

                SuspectStudent s = students.get(i);
                Row row = sheet.createRow(total);

                Cell studentId = row.createCell(0);
                studentId.setCellValue(s.getStudentId());
                Cell status = row.createCell(1);
                status.setCellValue(s.getStatus());
                Cell level = row.createCell(2);
                level.setCellType(CellType.STRING);
                level.setCellValue(s.getLevel());

                List<TimePair> pairs = s.getDoubtfulRecords();
                TimePair first = pairs.get(0);

                Cell startTime = row.createCell(3);
                startTime.setCellValue(format.format(new Date(first.getT1().getTime())));
                Cell endTime = row.createCell(4);
                endTime.setCellValue(format.format(new Date(first.getT2().getTime())));

                for (int j = 1; j < pairs.size(); j++) {
                    total++;
                    Row recordRow = sheet.createRow(total);
                    TimePair next = pairs.get(j);
                    Cell st = recordRow.createCell(3);
                    st.setCellValue(format.format(new Date(next.getT1().getTime())));
                    Cell et = recordRow.createCell(4);
                    et.setCellValue(format.format(new Date(next.getT2().getTime())));
                }
            }
        }

        //        新建文件。找不到文件夹。
        FileOutputStream out = null;
        try {
            File file = new File(filePath).getParentFile();
            if (!file.exists()) {
                file.mkdirs();
            }
            out = new FileOutputStream(filePath);
            workbook.write(out);
        } catch (IOException e) {
            throw new FileNotWritable("The file cannot be written to.");
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                throw new FileNotClosable("The file cannot be closed normally.");
            }
        }


    }

    /**
     * 根据文件路径打开excel工作表
     *
     * @param filePath  excel文件路径
     * @param sheetName 待打开的表名
     * @return 成功打开的sheet对象
     * @throws FileNotFoundException 找不到文件时抛出该异常
     */
    private static Sheet openSheet(String filePath, String sheetName) throws FileNotFoundException, SheetNameException {
        Workbook workbook = null;

//        由路径获取文件并解析
        if (filePath == null) {
            return null;
        }
        String type = filePath.substring(filePath.lastIndexOf("."));
        try {
            InputStream inputStream = new FileInputStream(filePath);
            if (type.equals(".xls")) {
                workbook = new HSSFWorkbook(inputStream);
            } else if (type.equals(".xlsx")) {
                workbook = new XSSFWorkbook(inputStream);

            }
        } catch (IOException e) {
            throw new FileNotFoundException("The file cannot be found.");
        }

//        读取文件内容
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null)
            throw new SheetNameException("未找到sheet");
        return sheet;
    }

    /**
     * 从字符串中解析时间长度
     * 支持格式：xx天，xx小时，xx天xx小时
     *
     * @param raw 待解析字符串
     * @return 时间长度
     */
    private static Duration parseHours(String raw) throws WrongFormatException {
        int days = 0;
        int hours = 0;
        if (raw.contains("天")) {
            days = Integer.parseInt(raw.substring(0, raw.indexOf("天")));
        }
        if (raw.contains("小时")) {
            hours = Integer.parseInt(raw.substring(raw.indexOf("天") + 1, raw.indexOf("小时")));
        }
        if (!raw.contains("天") && !raw.contains("小时"))
            throw new WrongFormatException("时间长度格式不匹配");
        return Duration.of(days * 24 + hours, HOURS);
    }

    private static Duration parseMinutes(String raw) throws WrongFormatException {
        int minutes = 0;
        if (raw.contains("分钟"))
            minutes = Integer.parseInt(raw.substring(0, raw.indexOf("分钟")));
        else
            minutes = Integer.parseInt(raw);
        return Duration.of(minutes, MINUTES);
    }

}
