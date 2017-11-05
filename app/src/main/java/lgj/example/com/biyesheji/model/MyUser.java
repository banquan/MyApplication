package lgj.example.com.biyesheji.model;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2017/10/7.
 */

public class MyUser extends BmobUser {

    private Boolean isMgn;
    private List<String> classIds;
    private String stuId;
    private String stuName;
    private String stuImgUrl;
    private String className;
    private BmobFile imgUrl;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public BmobFile getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(BmobFile imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getStuImgUrl() {
        return stuImgUrl;
    }

    public void setStuImgUrl(String stuImgUrl) {
        this.stuImgUrl = stuImgUrl;
    }

    public List<String> getClassIds() {
        return classIds;
    }

    public void setClassIds(List<String> classIds) {
        this.classIds = classIds;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public Boolean getMgn() {
        return isMgn;
    }

    public void setMgn(Boolean mgn) {
        isMgn = mgn;
    }

}
