package lgj.example.com.biyesheji.ui.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import lgj.example.com.biyesheji.R;
import lgj.example.com.biyesheji.utils.SharedpreferencesUtil;

public class AttendanceCheckingActivity extends BaseActivity {

    private MaterialCalendarView mMaterialCalendarView;
    private TextView tv_signed;
    private TextView tv_leave;
    private boolean isMgn;
    private String imgUrl;
    //声明AMapLocationClient类对象
    private AMapLocationClient mLocationClient = null;
    //声明定位回调监听器

    //声明AMapLocationClientOption对象
    private AMapLocationClientOption mLocationOption = null;

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            isMgn = intent.getBooleanExtra("isMgn", false);
            imgUrl = intent.getStringExtra("imgUrl");
        }

        if (isMgn) {
            tv_signed.setText("发起签到");
            tv_leave.setText("请假列表");
        }
    }

    @Override
    protected void initListener() {
        tv_signed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //是管理员则发起签到，否则跳转到签到页面进行人脸识别
                if (isMgn) {
                    Intent intent = new Intent(AttendanceCheckingActivity.this, AttendanceCheckingForManagerActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(AttendanceCheckingActivity.this, FaceSignedActivity.class);
                    intent.putExtra("imgUrl", imgUrl);
                    startActivity(intent);
                    finish();
                }
            }
        });

        tv_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果是管理员则跳转到请假列表，否则跳到填写请假单
                if (isMgn){
                    Intent intent = new Intent(AttendanceCheckingActivity.this,LeaveListActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(AttendanceCheckingActivity.this,AttendanceCheckingForStudentActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void initView() {
        mMaterialCalendarView = (MaterialCalendarView) findViewById(R.id.calendarView);
        tv_signed = (TextView) findViewById(R.id.tv_signed);
        tv_leave = (TextView) findViewById(R.id.tv_leave);
        initLocation();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_attendance_checking;
    }

    private void initLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
//设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.setInterval(1000);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);


//        mLocationListener = new AMapLocationListener() {
//            @Override
//            public void onLocationChanged(AMapLocation aMapLocation) {
//
//            }
//        };
//        //启动定位
        mLocationClient.startLocation();
        //   mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
        //  mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
    }

    AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    Log.i("kkkkkkkkkkk", "aMapLocation.getAdCode() : =  " + aMapLocation.getAdCode() + "\n"
                            + " aMapLocation.getAddress() = " + aMapLocation.getAddress() + "\n"
                            + "aMapLocation.getAoiName()  = " + aMapLocation.getAoiName() + "\n"
                            + "aMapLocation.getBuildingId() = " + aMapLocation.getBuildingId() + "\n"
                            + "aMapLocation.getCity() = " + aMapLocation.getCity() + "\n"
                            + "aMapLocation.getProvince() = " + aMapLocation.getProvince() + "\n"
                            + "aMapLocation.getGpsAccuracyStatus() = " + aMapLocation.getGpsAccuracyStatus() + "\n"
                            + "aMapLocation.getLocationDetail() = " + aMapLocation.getLocationDetail() + "\n"
                            + "aMapLocation.getLatitude() = " + aMapLocation.getLatitude() + "\n"
                            + "aMapLocation.getLongitude() = " + aMapLocation.getLongitude() + "\n"
                    );
                    SharedpreferencesUtil.saveAddress(AttendanceCheckingActivity.this,"address",aMapLocation.getAddress());
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
            }


        }
    };
}
