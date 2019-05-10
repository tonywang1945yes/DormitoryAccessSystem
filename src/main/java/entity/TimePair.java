package entity;

import lombok.Data;

import java.sql.Timestamp;
import java.time.Duration;

/**
 * 时间段，简化操作
 *
 * @author Nosolution
 * @version 1.0
 * @since 2019/4/26
 */
@Data
public class TimePair {
    String status;
    Timestamp t1;
    Timestamp t2;

    public Duration getDuration() {
        if (t1 == null || t2 == null)
            return Duration.ofMinutes(0);
        return Duration.between(t1.toInstant(), t2.toInstant());
    }

    // 判断时间点是否在时间段之中
    public boolean include(Timestamp time) {
        return time.after(t1) && time.before(t2);
    }

    //判断两时间段是否相交
    public boolean intersect(TimePair tp) {
        return tp.include(t1);
    }

    public TimePair copy() {
        TimePair r = new TimePair();
        r.setStatus(this.status);
        r.setT1(new Timestamp(this.t1.getTime()));
        r.setT2(new Timestamp(this.t2.getTime()));
        return r;
    }


}
