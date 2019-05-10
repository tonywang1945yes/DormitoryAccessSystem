package entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class PassRecord {
    private Integer id;
    private String userId;
    private Timestamp passTime;
    private Integer sysId;
    private Integer passStatus;
    private Integer direction;


}
