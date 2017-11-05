package lgj.example.com.biyesheji.model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by yu on 2017/10/19.
 */

public class Document extends BmobObject {

    private BmobFile mBmobFile;
    private String mDocumentName;
    private String mDocumentSize;
    private String mDocumentType;
    private String oid;
//    private String mDownLoadPath;


//    public String getDownLoadPath() {
//        return mDownLoadPath;
//    }
//
//    public void setDownLoadPath(String downLoadPath) {
//        mDownLoadPath = downLoadPath;
//    }



    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }


    public String getDocumentTime() {
        return mDocumentTime;
    }

    public void setDocumentTime(String documentTime) {
        mDocumentTime = documentTime;
    }

    private String mDocumentTime;

    public BmobFile getBmobFile() {
        return mBmobFile;
    }

    public void setBmobFile(BmobFile bmobFile) {
        mBmobFile = bmobFile;
    }

    public String getDocumentName() {
        return mDocumentName;
    }

    public void setDocumentName(String documentName) {
        mDocumentName = documentName;
    }

    public String getDocumentSize() {
        return mDocumentSize;
    }

    public void setDocumentSize(String documentSize) {
        mDocumentSize = documentSize;
    }

    public String getDocumentType() {
        return mDocumentType;
    }

    public void setDocumentType(String documentType) {
        mDocumentType = documentType;
    }
}
