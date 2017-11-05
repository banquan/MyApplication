package lgj.example.com.biyesheji.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by yhdj on 2017/10/21.
 */

public class StudentInfoBean extends BmobObject {
    private  String stuInfoId;
    private String stuInfoName;
    private String phoneNum ;

    public String getStuInfoId() {
        return stuInfoId;
    }

    public void setStuInfoId(String stuInfoId) {
        this.stuInfoId = stuInfoId;
    }

    public String getStuInfoName() {
        return stuInfoName;
    }

    public void setStuInfoName(String stuInfoName) {
        this.stuInfoName = stuInfoName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
