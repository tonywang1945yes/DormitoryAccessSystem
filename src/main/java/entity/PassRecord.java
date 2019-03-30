package entity;

import java.util.Calendar;

public class PassRecord {
    private Integer id;
    private String UserId;
    private Calendar passTime;
    private int sysId;
    private int PassStatus;
    private int Direction;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public Calendar getPassTime() {
        return passTime;
    }

    public void setPassTime(Calendar passTime) {
        this.passTime = passTime;
    }

    public int getSysId() {
        return sysId;
    }

    public void setSysId(int sysId) {
        this.sysId = sysId;
    }

    public int getPassStatus() {
        return PassStatus;
    }

    public void setPassStatus(int passStatus) {
        PassStatus = passStatus;
    }

    public int getDirection() {
        return Direction;
    }

    public void setDirection(int direction) {
        Direction = direction;
    }

    public PassRecord(Integer id, String userId, Calendar passTime, int sysId, int passStatus, int direction) {
        this.id = id;
        UserId = userId;
        this.passTime = passTime;
        this.sysId = sysId;
        PassStatus = passStatus;
        Direction = direction;
    }

    public PassRecord() {
    }
}
