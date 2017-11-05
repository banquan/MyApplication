package lgj.example.com.biyesheji.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by yhdj on 2017/10/21.
 */

public class Manager extends BmobObject {
    private String mgnId;
    private String mgnName;
    private String classId;
    private boolean isMgn;
    private String password;

    public String getMgnId() {
        return mgnId;
    }

    public void setMgnId(String mgnId) {
        this.mgnId = mgnId;
    }

    public String getMgnName() {
        return mgnName;
    }

    public void setMgnName(String mgnName) {
        this.mgnName = mgnName;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public boolean isMgn() {
        return isMgn;
    }

    public void setMgn(boolean mgn) {
        isMgn = mgn;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
