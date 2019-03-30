package entity;

import java.util.Calendar;

public class PassRecord {
    private Integer id;
    private Integer userId;
    private Calendar passTime;
    private Integer sysId;
    private Integer passStatus;
    private Integer direction;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Calendar getPassTime() {
        return passTime;
    }

    public void setPassTime(Calendar passTime) {
        this.passTime = passTime;
    }

    public Integer getSysId() {
        return sysId;
    }

    public void setSysId(Integer sysId) {
        this.sysId = sysId;
    }

    public Integer getPassStatus() {
        return passStatus;
    }

    public void setPassStatus(Integer passStatus) {
        this.passStatus = passStatus;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public PassRecord(Integer id, Integer userId, Calendar passTime, Integer sysId, Integer passStatus, Integer direction) {
        this.id = id;
        this.userId = userId;
        this.passTime = passTime;
        this.sysId = sysId;
        this.passStatus = passStatus;
        this.direction = direction;
    }

    public PassRecord() {
    }
}
