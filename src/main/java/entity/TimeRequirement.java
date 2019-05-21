package entity;

import lombok.Data;

import java.sql.Timestamp;
import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

/**
 * 筛选时使用的时间条件，由使用者提供
 * 目前的逻辑是当两次刷卡时间间隔(第一次为出去，第二次为进来)大于所提供的时间间隔的学生会被怀疑
 * 间隔使用"天数-小时-分钟"的格式，如"1-3-30"表明使用的间隔为1天3小时又30分钟
 */
@Data
public class TimeRequirement {

    TimePair interval;//起始+终止时间
    Duration reqDuration;//终止-起始
    Duration limitationToNow; //到当前的限制，LongOutStrategy需要的。使用者指定的需用于判断距使用时的最大时间间隔，以便找出到目前仍未回到宿舍的学生
    Duration minBreak; //最小出进宿舍时间，LongInStrategy用于过滤出进宿舍拿外卖的情况


    public Duration getIntervalDuration() {
        return interval.getDuration();
    }

    public Duration getReqDuration() {
        return Duration.of(reqDuration.getSeconds(), SECONDS);
    }

    // 判断时间点是否在时间段之中
    public boolean include(Timestamp time) {
        return interval.include(time);
    }

    //判断目标时间段是否完全被包含于该时间段中
    public boolean cover(TimePair tp) {
        return include(tp.getT1()) && include(tp.getT2());
    }

    public Timestamp getStartTime() {
        return new Timestamp(interval.getT1().getTime());
    }

    public Timestamp getEndTime() {
        return new Timestamp(interval.getT2().getTime());
    }

    public TimeRequirement copy() {
        TimeRequirement tr = new TimeRequirement();
        TimePair i = new TimePair();
        i.setT1((Timestamp) this.interval.getT1().clone());
        i.setT2((Timestamp) this.interval.getT2().clone());
        tr.setInterval(i);
        tr.setReqDuration(Duration.ofMillis(this.reqDuration.toMillis()));
        tr.setLimitationToNow(Duration.ofMillis(this.limitationToNow.toMillis()));
        tr.setMinBreak(Duration.ofMillis(this.minBreak.toMillis()));
        return tr;
    }

    public TimeRequirement() {

    }

    public TimeRequirement(TimePair interval, Duration reqDuration, Duration limitationToNow, boolean isOut) {
        this.interval = interval;
        this.reqDuration = reqDuration;
        if (isOut) {
            this.limitationToNow = limitationToNow;
            this.minBreak = Duration.ZERO;
        } else
            this.minBreak = limitationToNow;
    }
}
