package entity;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 伪id与真实id的map，用于刷卡记录分组
 *
 * @author Nosolution
 * @version 1.0
 * @since 2019/5/10
 */
@Data
public class IdMap {
    private String userId; //伪id
    private String credentialId; //真实id
    private Timestamp updateTime; //该信息更新时间
}
