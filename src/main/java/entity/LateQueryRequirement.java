package entity;

import java.util.Calendar;

/**
 * 判断学生晚归时需要的参数
 * 由使用者指定在哪个日期段内进行筛选，区间为[startTime, endTime]
 */
public class LateQueryRequirement {
    Calendar startTime;//开始日期，默认为yyyy-MM-dd 00:00:00
    Calendar endTime;//结束日期, 默认为yyyy-MM-dd 00:00:00
    Calendar timeLimit; //假设timeLimit为跟在startTime之后的期限

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        Calendar c = (Calendar) startTime.clone();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        this.startTime = c;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        Calendar c = (Calendar) endTime.clone();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        this.endTime = c;
    }

    public Calendar getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Calendar timeLimit) {
        this.timeLimit = timeLimit;
    }

    public LateQueryRequirement(Calendar startTime, Calendar endTime, Calendar timeLimit) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeLimit = timeLimit;
    }

    public LateQueryRequirement() {
    }
}
