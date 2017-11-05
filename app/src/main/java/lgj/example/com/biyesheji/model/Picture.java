package lgj.example.com.biyesheji.model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by yu on 2017/10/19.
 */

public class Picture extends BmobObject {

    private BmobFile mBmobFile;
    private String mPicName;
    private String mPicSize;
    private String mPicTime;
    private String mPicType;
    private String oid;


    public String getPicType() {
        return mPicType;
    }

    public void setPicType(String picType) {
        mPicType = picType;
    }

    public BmobFile getBmobFile() {
        return mBmobFile;
    }

    public void setBmobFile(BmobFile bmobFile) {
        mBmobFile = bmobFile;
    }

    public String getPicName() {
        return mPicName;
    }

    public void setPicName(String picName) {
        mPicName = picName;
    }

    public String getPicSize() {
        return mPicSize;
    }

    public void setPicSize(String picSize) {
        mPicSize = picSize;
    }

    public String getPicTime() {
        return mPicTime;
    }

    public void setPicTime(String picTime) {
        mPicTime = picTime;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }
}
