package lgj.example.com.biyesheji.model;

import java.util.Date;

import cn.bmob.v3.BmobObject;

/**
 * Created by yhdj on 2017/10/14.
 */

public class LeaveBean extends BmobObject{
    private String stuId;
    private String leaveStartTime;
    private String leaveEndTime;
    private String leaveReason;
    private boolean isPassed;
    private String stuName;
    private Date leaveCreateTime;
    private String className;
    private String classId;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public Date getLeaveCreateTime() {
        return leaveCreateTime;
    }

    public void setLeaveCreateTime(Date leaveCreateTime) {
        this.leaveCreateTime = leaveCreateTime;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getLeaveStartTime() {
        return leaveStartTime;
    }

    public void setLeaveStartTime(String leaveStartTime) {
        this.leaveStartTime = leaveStartTime;
    }

    public String getLeaveEndTime() {
        return leaveEndTime;
    }

    public void setLeaveEndTime(String leaveEndTime) {
        this.leaveEndTime = leaveEndTime;
    }

    public String getLeaveReason() {
        return leaveReason;
    }

    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
    }

    public boolean isPassed() {
        return isPassed;
    }

    public void setPassed(boolean passed) {
        isPassed = passed;
    }
}
