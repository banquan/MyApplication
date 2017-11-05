package lgj.example.com.biyesheji.model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by yu on 2017/10/19.
 */

public class Music extends BmobObject {
    private BmobFile mBmobFile;
    private String mMusicName;
    private String mMusicSize;
    private String mMusicTime;
    private String mMusicType;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getMusicType() {
        return mMusicType;
    }

    public void setMusicType(String musicType) {
        mMusicType = musicType;
    }

    public String getMusicTime() {
        return mMusicTime;
    }

    public void setMusicTime(String musicTime) {
        mMusicTime = musicTime;
    }

    public String getMusicSize() {
        return mMusicSize;
    }

    public void setMusicSize(String musicSize) {
        mMusicSize = musicSize;
    }

    public String getMusicName() {
        return mMusicName;
    }

    public void setMusicName(String musicName) {
        mMusicName = musicName;
    }

    public BmobFile getBmobFile() {
        return mBmobFile;
    }

    public void setBmobFile(BmobFile bmobFile) {
        mBmobFile = bmobFile;
    }

    private String oid;
}
