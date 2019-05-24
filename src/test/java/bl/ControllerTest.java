package bl;

import bl.strategy.LongInInspector;
import bl.strategy.LongOutInspector;
import entity.SuspectStudent;
import entity.TimePair;
import entity.TimeRequirement;
import enums.CheckResult;
import exception.excelException.FileNotFoundException;
import exception.excelException.SheetNameException;
import exception.excelException.WrongFormatException;
import org.junit.Test;
import util.excel.ExcelUtil;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MINUTES;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class ControllerTest {

    @Test
    public void intergrationTest1() {

        Instant i1 = Clock.systemDefaultZone().instant();

        Controller controller = new Controller();
        controller.testDatabase("ddas");

        controller.setWhiteListPath("F:/Docs/DormitoryAssessSystem/白名单.xlsx");
        controller.setBlackListPath("F:/Docs/DormitoryAssessSystem/关注名单.xlsx");
        controller.setTutorMapListPath("F:/Docs/DormitoryAssessSystem/辅导员名单.xlsx");
        controller.setHolidayExcelPath("F:/Docs/DormitoryAssessSystem/节假日信息.xlsx");
        controller.setOutputExcelPath("F:/Docs/DormitoryAssessSystem");
        controller.setInspector(new LongOutInspector());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        TimeRequirement requirement = new TimeRequirement();
        try {
            requirement.setInterval(new TimePair(new Timestamp(dateFormat.parse("2018-09-10").getTime()), new Timestamp(dateFormat.parse("2018-09-20").getTime())));
            requirement.setReqDuration(Duration.of(1, DAYS));
            requirement.setLimitationToNow(Duration.of(15, MINUTES));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        CheckResult result = controller.generateStudentList(requirement);

        Instant i2 = Clock.systemDefaultZone().instant();
        System.out.println("用时: " + Duration.between(i1, i2));
        assertThat(result).isEqualTo(CheckResult.SUCCESS);

//        MailResult r2 = controller.sendMail("...", "...");
//        assertThat(r2).isEqualTo(MailResult.OK);
    }

    @Test
    public void excelTest() {
        try {
            List<SuspectStudent> students = ExcelUtil.readSuspectStudent("F:\\Docs\\DormitoryAssessSystem\\2019-05-20异常学生名单.xlsx", "异常学生名单");
            System.out.println(students.size());
        } catch (FileNotFoundException | SheetNameException | WrongFormatException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void intergrationTest2() {
        Instant i1 = Clock.systemDefaultZone().instant();

        Controller controller = new Controller();
        controller.testDatabase("ddas");

        controller.setWhiteListPath("F:/Docs/DormitoryAssessSystem/白名单.xlsx");
        controller.setBlackListPath("F:/Docs/DormitoryAssessSystem/关注名单.xlsx");
        controller.setTutorMapListPath("F:/Docs/DormitoryAssessSystem/辅导员名单.xlsx");
        controller.setOutputExcelPath("F:/Docs/DormitoryAssessSystem");
        controller.setInspector(new LongInInspector());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        TimeRequirement requirement = new TimeRequirement();
        try {
            requirement.setInterval(new TimePair(new Timestamp(dateFormat.parse("2018-09-01").getTime()), new Timestamp(dateFormat.parse("2019-01-09").getTime())));
            requirement.setReqDuration(Duration.of(1, DAYS));
            requirement.setMinBreak(Duration.of(15, MINUTES));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        CheckResult result = controller.generateStudentList(requirement);

        Instant i2 = Clock.systemDefaultZone().instant();
        System.out.println("用时: " + Duration.between(i1, i2));
        assertThat(result).isEqualTo(CheckResult.SUCCESS);
    }

}