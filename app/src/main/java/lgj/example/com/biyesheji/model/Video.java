package lgj.example.com.biyesheji.model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by yu on 2017/10/19.
 */

public class Video extends BmobObject {
    private BmobFile mBmobFile;
    private String mVideoName;
    private String mVideoSize;
    private String mVideoTime;
    private String mVideoType;
    private String oid;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }


    public String getVideoTime() {
        return mVideoTime;
    }

    public void setVideoTime(String videoTime) {
        mVideoTime = videoTime;
    }

    public String getVideoType() {
        return mVideoType;
    }

    public void setVideoType(String videoType) {
        mVideoType = videoType;
    }



    public String getVideoSize() {
        return mVideoSize;
    }

    public void setVideoSize(String videoSize) {
        mVideoSize = videoSize;
    }

    public String getVideoName() {
        return mVideoName;
    }

    public void setVideoName(String videoName) {
        mVideoName = videoName;
    }

    public BmobFile getBmobFile() {
        return mBmobFile;
    }

    public void setBmobFile(BmobFile bmobFile) {
        mBmobFile = bmobFile;
    }


}
