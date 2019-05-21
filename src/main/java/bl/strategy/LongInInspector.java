package bl.strategy;

import entity.*;
import enums.PairStatus;
import enums.StudentStatus;

import java.time.Duration;
import java.util.List;

/**
 * simple introduction
 *
 * @author Nosolution
 * @version 1.0
 * @since 2019/5/8
 */
public class LongInInspector extends LongStayInspector {


    @Override
    Duration getBlackReqDuration(BlackStudent blackInfo, TimeRequirement req) {

        return blackInfo.getInReqDuration();
    }

    @Override
    List<TimePair> getTargetPairs(List<PassRecord> records, TimeRequirement req) {
        return getLongStayPairs(records, IN, req.getMinBreak(), req);
    }

    @Override
    void preProcess(SuspectStudent student, List<PassRecord> records, TimeRequirement req) {

    }

    @Override
    void postProcess(SuspectStudent student, List<PassRecord> records, TimeRequirement req) {

    }

    @Override
    void handleEvidence(SuspectStudent student, List<TimePair> evidence, TimeRequirement req) {
        //如果有长时间未出宿舍情况
        if (evidence.size() != 0) {
            student.getDoubtfulRecords().addAll(evidence);
            if (student.getLevel() <= 2) {
                student.setLevel(2);
            }
            student.setStatus(student.getStatus() + ":" + StudentStatus.LONG_IN.name());
            if (evidence.stream().map(TimePair::getStatus).noneMatch(o -> o.contains(PairStatus.NORMAL.name()))) {
                student.setStatus(student.getStatus() + ":" + StudentStatus.WITH_CONFUSION);
            }
            if (evidence.stream().map(TimePair::getStatus).allMatch(o -> o.contains(PairStatus.ABOUTHOLIDAY.name()))) {
                student.setStatus(student.getStatus() + ":" + StudentStatus.ABOUT_HOLIDAY);
            }
        }
    }

}
