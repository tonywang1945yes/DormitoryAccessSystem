package entity;

import java.util.Calendar;

/**
筛选时使用的时间条件，由使用者提供
目前的逻辑是当两次刷卡时间间隔(第一次为出去，第二次为进来)大于所提供的时间间隔的学生会被怀疑
间隔使用"天数-小时-分钟"的格式，如"1-3-30"表明使用的间隔为1天3小时又30分钟
 */
public class TimeRequirement {

    public Calendar startTime;//该时间之后刷第二次卡的学生会被筛选出来
    public int day;//天数间隔
    public int hour;//小时间隔
    public int minute;//分钟间隔

    public int turn2Hours() {
        return day * 24 + hour;
    }

    public TimeRequirement() {
    }

    public TimeRequirement(Calendar startTime, int day, int hour, int minute) {
        this.startTime = startTime;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }
}
