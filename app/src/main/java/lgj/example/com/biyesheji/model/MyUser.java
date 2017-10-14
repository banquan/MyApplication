package lgj.example.com.biyesheji.model;

import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2017/10/7.
 */

public class MyUser extends BmobUser {

    private Boolean isMgn;

    public Boolean getMgn() {
        return isMgn;
    }

    public void setMgn(Boolean mgn) {
        isMgn = mgn;
    }

}
