package entity;

import lombok.Data;

import java.time.Duration;

/**
 * 关注学生
 *
 * @author Nosolution
 * @version 1.0
 * @since 2019/4/26
 */
@Data
public class BlackStudent {
    private String studentId;
    private TimeRequirement outRequirement; //特别指定的用于LongOutInspector的时间要求
    private TimeRequirement inRequirement; //特别指定的用于LongInInspector的时间要求

    private Duration outReqDuration;
    private Duration inReqDuration;
    private Duration limitToNow;
    private Duration minBreak;

}
