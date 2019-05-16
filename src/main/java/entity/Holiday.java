package entity;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 用于节假日筛选
 *
 * @author Nosolution
 * @version 1.0
 * @since 2019/5/7
 */
@Data
public class Holiday {
    String name;
    TimePair interval;

    public boolean inHoliday(Timestamp time) {
        return interval.include(time);
    }
}
