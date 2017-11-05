package lgj.example.com.biyesheji.model;

import java.util.Date;

import cn.bmob.v3.BmobObject;

/**
 * Created by yhdj on 2017/11/4.
 * 发起签到
 */

public class LanuchSignedBean extends BmobObject{
    /**
     * 发起签到的用户id
     */
    private String creatorId;
    private String creatorName;
    private Date lanuchSignedTime;
    private String signedTime;
    private String lanuchSignedClassId;
    private String className;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getLanuchSignedClassId() {
        return lanuchSignedClassId;
    }

    public void setLanuchSignedClassId(String lanuchSignedClassId) {
        this.lanuchSignedClassId = lanuchSignedClassId;
    }

    public String getSignedTime() {
        return signedTime;
    }

    public void setSignedTime(String signedTime) {
        this.signedTime = signedTime;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getSignedTiem() {
        return signedTime;
    }

    public void setSignedTiem(String signedTiem) {
        this.signedTime = signedTiem;
    }

    public Date getLanuchSignedTime() {
        return lanuchSignedTime;
    }

    public void setLanuchSignedTime(Date lanuchSignedTime) {
        this.lanuchSignedTime = lanuchSignedTime;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }
}
