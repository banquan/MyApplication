package lgj.example.com.biyesheji.model;

import java.util.Date;

import cn.bmob.v3.BmobObject;

/**
 * Created by yhdj on 2017/10/14.
 */

public class ClassBean extends BmobObject {
    private String classId;
    private String className;
    private Date classCreateDate;
    private String classCreater;
    private int stuNum;
    private String administrator;
    private String professionId;
    private String classImgUrl;

    public String getClassImgUrl() {
        return classImgUrl;
    }

    public void setClassImgUrl(String classImgUrl) {
        this.classImgUrl = classImgUrl;
    }

    public String getProfessionId() {
        return professionId;
    }

    public void setProfessionId(String professionId) {
        this.professionId = professionId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Date getClassCreateDate() {
        return classCreateDate;
    }

    public void setClassCreateDate(Date classCreateDate) {
        this.classCreateDate = classCreateDate;
    }

    public String getClassCreater() {
        return classCreater;
    }

    public void setClassCreater(String classCreater) {
        this.classCreater = classCreater;
    }

    public int getStuNum() {
        return stuNum;
    }

    public void setStuNum(int stuNum) {
        this.stuNum = stuNum;
    }

    public String getAdministrator() {
        return administrator;
    }

    public void setAdministrator(String administrator) {
        this.administrator = administrator;
    }
}
