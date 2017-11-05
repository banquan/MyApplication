package lgj.example.com.biyesheji.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by yhdj on 2017/10/14.
 * 学院bean类
 */

public class AcademicBean extends BmobObject{
    /**
     * academicId
     * 学院id
     */
    private String academicId;

    /**
     * academicName
     * 学院名字
     */
    private String academicName;

    public String getAcademicId() {
        return academicId;
    }

    public void setAcademicId(String academicId) {
        this.academicId = academicId;
    }

    public String getAcademicName() {
        return academicName;
    }

    public void setAcademicName(String academicName) {
        this.academicName = academicName;
    }
}
