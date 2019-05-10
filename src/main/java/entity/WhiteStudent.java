package entity;

import lombok.Data;

/**
 * 白名单记录
 *
 * @author Nosolution
 * @version 1.0
 * @since 2019/4/26
 */
@Data
public class WhiteStudent {
    private String studentId;
    private TimePair validTime; //白名单有效时间段
}
