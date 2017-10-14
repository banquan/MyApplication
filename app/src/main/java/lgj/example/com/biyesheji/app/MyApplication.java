package lgj.example.com.biyesheji.app;

import android.app.Application;
import android.content.Context;

import cn.bmob.v3.Bmob;

/**
 * Created by yls on 2016/12/29.
 */

public class MyApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        //初始化bmob
        Bmob.initialize(getApplicationContext(), "cc6b20f0a8ad63b7a930cb97bdffd204");

    }

    public static Context getContext() {
        return mContext;
    }
}



