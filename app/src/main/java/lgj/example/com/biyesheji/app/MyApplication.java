package lgj.example.com.biyesheji.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import com.hyphenate.chat.BuildConfig;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

import java.util.Iterator;
import java.util.List;

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
        Bmob.initialize(getApplicationContext(), "d659d0fdc9ec4ed34f83721af8d83327");

//        EMOptions options = new EMOptions();
//// 默认添加好友时，是不需要验证的，改成需要验证
//        options.setAcceptInvitationAlways(false);
//// 是否自动将消息附件上传到环信服务器，默认为True是使用环信服务器上传下载，如果设为 false，需要开发者自己处理附件消息的上传和下载
////        options.setAutoTransferMessageAttachments(true);
////// 是否自动下载附件类消息的缩略图等，默认为 true 这里和上边这个参数相关联
////        options.setAutoDownloadThumbnail(true);
//
////初始化
//        EMClient.getInstance().init(mContext, options);
////在做打包混淆时，关闭debug模式，避免消耗不必要的资源
//        EMClient.getInstance().setDebugMode(true);

        initEaseMob();


    }

    public static Context getContext() {
        return mContext;
    }

    /**
     * 初始化环信SDK
     */
    private void initEaseMob() {
//        appContext = this;
        int pid = android.os.Process.myPid();//拿到当前进程的id
        String processAppName = getAppName(pid);//获取到当前进程的名字
        //默认进程的app的进程名是包名



// 如果APP启用了远程的service，此application:onCreate会被调用2次
// 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
// 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回

        if (processAppName == null || !processAppName.equalsIgnoreCase(getPackageName())) {
            Log.e("aaaaaa", "enter the service process!");

            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }



        EMOptions options = new EMOptions();
// 当接收到好友请求，默认添加好友
        options.setAcceptInvitationAlways(true);

//初始化，只在默认进程初始化一次
        EMClient.getInstance().init(getApplicationContext(), options);
//在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        if(BuildConfig.DEBUG){
            EMClient.getInstance().setDebugMode(true);
        }
    }

    /**
     * 根据当前进程的pID,获取进程名
     * @param pID
     * @return
     */
    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();//获取到正在运行app的进程
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            //遍历所有进程的信息
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }
}



