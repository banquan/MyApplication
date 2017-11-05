package lgj.example.com.biyesheji.model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by yu on 2017/10/19.
 */

public class Other extends BmobObject {

    private BmobFile mBmobFile;
    private String mOtherName;
    private String mOtherSize;
    private String mOtherTime;
    private String mOtherType;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getOtherType() {
        return mOtherType;
    }

    public void setOtherType(String otherType) {
        mOtherType = otherType;
    }

    public String getOtherTime() {
        return mOtherTime;
    }

    public void setOtherTime(String otherTime) {
        mOtherTime = otherTime;
    }

    public String getOtherSize() {
        return mOtherSize;
    }

    public void setOtherSize(String otherSize) {
        mOtherSize = otherSize;
    }

    public String getOtherName() {
        return mOtherName;
    }

    public void setOtherName(String otherName) {
        mOtherName = otherName;
    }

    public BmobFile getBmobFile() {
        return mBmobFile;
    }

    public void setBmobFile(BmobFile bmobFile) {
        mBmobFile = bmobFile;
    }

    private String oid;
}
