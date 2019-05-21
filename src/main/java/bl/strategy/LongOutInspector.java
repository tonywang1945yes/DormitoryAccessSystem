package bl.strategy;

import entity.*;
import enums.PairStatus;
import enums.StudentStatus;

import java.time.Clock;
import java.time.Duration;
import java.util.List;

/**
 * 判断学生长时间未归的策略
 *
 * @author Nosolution
 * @version 1.0
 * @since 2019/4/26
 */
public class LongOutInspector extends LongStayInspector {

    @Override
    Duration getBlackReqDuration(BlackStudent blackInfo, TimeRequirement req) {

        return blackInfo.getOutReqDuration();
    }

    @Override
    List<TimePair> getTargetPairs(List<PassRecord> records, TimeRequirement req) {
        return getLongStayPairs(records, OUT, Duration.ZERO, req);
    }

    @Override
    void preProcess(SuspectStudent student, List<PassRecord> records, TimeRequirement req) {
        //判断是否到现在未归并且时间超出要求
        TimePair stillOut = judgeStillOut(records, req);
        if (stillOut != null) {
            student.getDoubtfulRecords().add(stillOut);
            student.setStatus(StudentStatus.STILL_OUT.name());
            student.setLevel(3);
        }
    }

    @Override
    void postProcess(SuspectStudent student, List<PassRecord> records, TimeRequirement req) {

    }

    @Override
    void handleEvidence(SuspectStudent student, List<TimePair> evidence, TimeRequirement req) {
        //如果有长时间未归情况
        if (evidence.size() != 0) {
            student.getDoubtfulRecords().addAll(evidence);
            if (student.getLevel() <= 2) {
                student.setLevel(2);
            }
            student.setStatus(student.getStatus() + ":" + StudentStatus.LONG_OUT);
            if (evidence.stream().map(TimePair::getStatus).noneMatch(o -> o.contains(PairStatus.NORMAL.name()))) {
                student.setStatus(student.getStatus() + ":" + StudentStatus.WITH_CONFUSION);
            }
            if (evidence.stream().map(TimePair::getStatus).allMatch(o -> o.contains(PairStatus.ABOUTHOLIDAY.name()))) {
                student.setStatus(student.getStatus() + ":" + StudentStatus.ABOUT_HOLIDAY);
            }
        }
    }

    /**
     * 判断现在在宿舍外的学生离开时间超过正常值
     *
     * @param records 刷卡记录
     * @param req     时间要求
     * @return 相关时间对(起始时间为离开宿舍的时间)
     */
    private TimePair judgeStillOut(List<PassRecord> records, TimeRequirement req) {
        PassRecord latest = records.get(records.size() - 1);
        if (latest.getDirection() == OUT &&
                Duration.between(
                        latest.getPassTime().toInstant(),
                        Clock.systemDefaultZone().instant()
                )
                        .compareTo(req.getLimitationToNow()) > 0) {
            TimePair stillOut = new TimePair();
            stillOut.setT1(latest.getPassTime());
            stillOut.setT2(null);
            return stillOut;
        }
        return null;
    }


}
