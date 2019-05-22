package util.excel;

import entity.BlackStudent;
import entity.SuspectStudent;
import entity.TimePair;
import entity.WhiteStudent;
import enums.StudentStatus;
import exception.excelException.*;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.time.temporal.ChronoUnit.*;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExcelUtilTest {

    @Test
    public void readWhiteList() {
        try {
            List<WhiteStudent> whiteList = ExcelUtil.readWhiteList("白名单.xlsx", "Sheet1");
            assertThat(whiteList.size()).isEqualTo(2);
        } catch (FileNotFoundException | WrongFormatException | SheetNameException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void readBlackList() {
        try {
            List<BlackStudent> blackList = ExcelUtil.readBlackList("关注学生名单.xlsx", "Sheet1");
            assertThat(blackList.size()).isGreaterThan(0);
            BlackStudent bs = blackList.get(0);
            assertThat(bs.getStudentId()).isEqualTo("171250530");
            assertThat(bs.getOutReqDuration()).isEqualTo(Duration.of(1, DAYS));
            assertThat(bs.getLimitToNow()).isEqualTo(Duration.of(24, HOURS));
            assertThat(bs.getInReqDuration()).isEqualTo(Duration.of(36, HOURS));
            assertThat(bs.getMinBreak()).isEqualTo(Duration.of(15, MINUTES));
        } catch (FileNotFoundException | WrongFormatException | SheetNameException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void readTutorStudentMaps() {
    }

    @Test
    public void testBReadSuspectStudent() {

        try {
            List<SuspectStudent> students = ExcelUtil.readSuspectStudent("异常学生名单.xlsx", "Sheet1");
            assertThat(students.size()).isEqualTo(1);
        } catch (FileNotFoundException | WrongFormatException | SheetNameException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testAWriteSuspectStudent() {
        List<SuspectStudent> students = new ArrayList<>();
        SuspectStudent s = new SuspectStudent();
        s.setStudentId("171250530");
        s.setStatus(StudentStatus.LONG_OUT.getMsg());
        s.setLevel(2);
        List<TimePair> pairs = new ArrayList<>();
        pairs.add(new TimePair(new Timestamp(new Date().getTime()), new Timestamp(new Date().getTime())));
        pairs.add(new TimePair(new Timestamp(new Date().getTime()), new Timestamp(new Date().getTime())));
        s.setDoubtfulRecords(pairs);
        students.add(s);

        s = new SuspectStudent();
        s.setStudentId("171250549");
        s.setStatus(StudentStatus.LONG_OUT_WITH_CONFUSION.getMsg());
        s.setLevel(3);
        pairs = new ArrayList<>();
        pairs.add(new TimePair(new Timestamp(new Date().getTime()), new Timestamp(new Date().getTime())));
        pairs.add(new TimePair(new Timestamp(new Date().getTime()), new Timestamp(new Date().getTime())));
        pairs.add(new TimePair(new Timestamp(new Date().getTime()), new Timestamp(new Date().getTime())));
        s.setDoubtfulRecords(pairs);
        students.add(s);

        try {
            ExcelUtil.writeSuspectStudent(students, "F:\\Softwares\\GitHub\\Repository\\DormitoryAccessSystem\\异常学生名单.xlsx");
        } catch (FileNotWritable | FileNotClosable fileNotWritable) {
            fileNotWritable.printStackTrace();
        }
    }
}