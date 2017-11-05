package lgj.example.com.biyesheji.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by yhdj on 2017/10/14.
 * 专业bean类
 */

public class ProfessionBean extends BmobObject{

    /**
     * professionId
     * 专业id
     * 主键
     */
    private String professionId;

    /**
     * professionName
     * 专业名称
     */
    private String professionName;

    /**
     * academicId
     * 学院id
     * 外键
     */
    private String academicId;

    public String getProfessionId() {
        return professionId;
    }

    public void setProfessionId(String professionId) {
        this.professionId = professionId;
    }

    public String getProfessionName() {
        return professionName;
    }

    public void setProfessionName(String professionName) {
        this.professionName = professionName;
    }

    public String getAcademicId() {
        return academicId;
    }

    public void setAcademicId(String academicId) {
        this.academicId = academicId;
    }
}
