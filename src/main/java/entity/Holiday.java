package entity;

import lombok.Data;

import java.sql.Timestamp;

/**
 * simple introduction
 *
 * @author Nosolution
 * @version 1.0
 * @since 2019/5/7
 */
@Data
public class Holiday {
    TimePair interval;

    public boolean inHoliday(Timestamp time) {
        return interval.include(time);
    }
}
