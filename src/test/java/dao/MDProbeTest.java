package dao;

import entity.IdMap;
import entity.PassRecord;
import org.junit.Test;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;

public class MDProbeTest {

    @Test
    public void build() {
        try {
            MDProbe probe = MDProbe.build("njuacdbtest");
            List<PassRecord> records = probe.getRecordWithLimit(5);
            System.out.println(records);
//            probe.getRecordWithLimit(5);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void getRecordsGrouped() {
        try {
            MDProbe probe = MDProbe.build("njuacdbtest");

            Instant i1 = Clock.systemDefaultZone().instant();
            Map<String, List<PassRecord>> records = probe.getRecordsGrouped();
            Instant i2 = Clock.systemDefaultZone().instant();
            System.out.println("The size of result is " + records.size() + " .");
            System.out.println("用时" + Duration.between(i1, i2));
//            List<PassRecord> records = probe.getRecordWithLimit(5);
//            System.out.println(records);
//            probe.getRecordWithLimit(5);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getUserIdMaps() {
        try {
            MDProbe probe = MDProbe.build("njuacdbtest");
            List<IdMap> maps = probe.getIdMap();
            System.out.println(maps);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkDatabaseErrorTest() {
        try {
            MDProbe probe = MDProbe.build("njuacdbtest");
            List<PassRecord> records = probe.getRecordByDate("2019-01-27");
            System.out.println(probe.lostData(records));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}