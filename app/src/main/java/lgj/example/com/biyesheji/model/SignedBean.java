package lgj.example.com.biyesheji.model;

import java.util.Date;

import cn.bmob.v3.BmobObject;

/**
 * Created by yhdj on 2017/10/14.
 */

public class SignedBean extends BmobObject {
    private String stuId;
    private Date signedTime;
    private String signedPlace;
    private boolean isSignedLate;

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    private String stuName;

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public Date getSignedTime() {
        return signedTime;
    }

    public void setSignedTime(Date signedTime) {
        this.signedTime = signedTime;
    }

    public String getSignedPlace() {
        return signedPlace;
    }

    public void setSignedPlace(String signedPlace) {
        this.signedPlace = signedPlace;
    }

    public boolean isSignedLate() {
        return isSignedLate;
    }

    public void setSignedLate(boolean signedLate) {
        isSignedLate = signedLate;
    }
}
