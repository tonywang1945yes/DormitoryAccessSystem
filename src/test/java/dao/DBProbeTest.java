package dao;

import entity.PassRecord;
import entity.TimeRequirement;
import org.junit.Test;

import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.Calendar;
import java.util.List;

public class DBProbeTest {

    @Test
    public void test() {
        DBProbe probe = new DBProbe(
                DaoConfig.url,
                DaoConfig.username,
                DaoConfig.password);
        try {
            Calendar c = Calendar.getInstance();
            c.set(2019, 1, 22);
            TimeRequirement requirement = new TimeRequirement();
            requirement.startTime = c;

            probe.buildConnection();
            probe.queryOneBuffer(requirement);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testSpecificUser() {
        DBProbe probe = new DBProbe(
                DaoConfig.url,
                DaoConfig.username,
                DaoConfig.password);
        try {
            Calendar c = Calendar.getInstance();
            c.set(2019, 1, 22);
            TimeRequirement requirement = new TimeRequirement();
            requirement.startTime = c;

            probe.buildConnection();
//            probe.queryOneBuffer(requirement);
            Instant st = Instant.now();
            List<PassRecord> list = probe.loadPersonalRecord("186361");
            Instant mt = Instant.now();
            Duration duration = Duration.between(st, mt);
            System.out.println("装填id对应的通过信息需要时间: " + duration);

            probe.judge(list, requirement);
            Instant et = Instant.now();
            Duration d2 = Duration.between(mt, et);
            System.out.println("判断是否符合条件需要时间: " + d2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void loop() {
        for (int i = 0; i < 10; i++)
            testSpecificUser();
    }
}