package bl.strategy;

import entity.PassRecord;
import entity.SuspectStudent;
import entity.TimePair;
import enums.StudentStatus;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * 夜晚旷寝检查类
 *
 * @author Nosolution
 * @version 1.0
 * @since 2019/5/29
 */
public class NightAbsenceInspector {
    static final int IN = 0;
    static final int OUT = 1;
    Map<String, List<PassRecord>> recordMaps;

    public void setRecordMaps(Map<String, List<PassRecord>> recordMaps) {
        this.recordMaps = recordMaps;
    }

    /**
     * 应用策略，筛选出昨夜未归的学生
     *
     * @param requirement 以时间对作为条件，t1为归寝时间, t2为入寝时间
     * @return 被怀疑的学生列表
     */
    public List<SuspectStudent> apply(TimePair requirement) {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        //TODO 编写测试
        List<SuspectStudent> res = new ArrayList<>();

        recordMaps.entrySet().stream()
                .filter(e -> e.getValue().stream()
                        .anyMatch(o -> o.getPassTime().toLocalDateTime().toLocalDate().isEqual(yesterday)))
                .forEach(e -> {
                    Timestamp before = rightBefore(e.getValue(), requirement.getT1(), OUT);
                    Timestamp after = rightAfter(e.getValue(), requirement.getT2(), IN);
                    long count = e.getValue().stream().filter(r -> requirement.include(r.getPassTime())).count();

                    // 如果在时间对之内没有刷卡记录并且是先出后进，则判断为夜不归宿
                    if (before != null && after != null && count == 0) {
                        SuspectStudent s = new SuspectStudent();
                        s.setStudentId(e.getKey());
                        s.getDoubtfulRecords().add(new TimePair(before, after));
                        s.setLevel(2);
                        s.setStatus(StudentStatus.NIGHT_ABSENCE.name());
                        res.add(s);
                    }
                });

        return res;

    }

    /**
     * 找出时间点之前的最近一条记录
     *
     * @param records   记录集合
     * @param req       时间点
     * @param direction 要求方向
     * @return 该记录的时间，没有则返回null
     */
    private Timestamp rightBefore(List<PassRecord> records, Timestamp req, int direction) {
        PassRecord res = records.stream().filter(o -> o.getPassTime().before(req)).sorted(Comparator.comparingLong(o -> -o.getPassTime().getTime())).findFirst().get();
        if (res.getDirection() == direction)
            return res.getPassTime();
        return null;
    }

    /**
     * 找出时间点之后的最近一条记录
     *
     * @param records   记录集合
     * @param req       时间点
     * @param direction 要求方向
     * @return 该记录的时间，没有则返回null
     */
    private Timestamp rightAfter(List<PassRecord> records, Timestamp req, int direction) {
        PassRecord res = records.stream().filter(o -> o.getPassTime().after(req)).sorted(Comparator.comparingLong(o -> o.getPassTime().getTime())).findFirst().get();
        if (res.getDirection() == direction)
            return res.getPassTime();
        return null;
    }


}
