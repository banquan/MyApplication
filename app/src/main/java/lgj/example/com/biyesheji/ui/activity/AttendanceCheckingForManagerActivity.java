package lgj.example.com.biyesheji.ui.activity;


import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.DPoint;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.SupportMapFragment;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.MyLocationStyle;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.qqtheme.framework.picker.TimePicker;
import lgj.example.com.biyesheji.R;
import lgj.example.com.biyesheji.adapter.AttendanceCheckingAdapter;
import lgj.example.com.biyesheji.app.MyApplication;
import lgj.example.com.biyesheji.map.AMapGeoFence;
import lgj.example.com.biyesheji.model.ClassBean;
import lgj.example.com.biyesheji.model.LanuchSignedBean;
import lgj.example.com.biyesheji.model.MyUser;
import lgj.example.com.biyesheji.utils.SharedpreferencesUtil;

import static cn.bmob.v3.BmobUser.getObjectByKey;

public class AttendanceCheckingForManagerActivity extends BaseActivity implements LocationSource, AMapLocationListener {

    private TimePicker mTime_attendanceChecking;
    private SwipeMenuRecyclerView mClass_swipRecycleView;
    private List<ClassBean> mClassBeanList = new ArrayList<>();
    private AttendanceCheckingAdapter mAttendanceCheckingAdapter;
   // private TimePicker picker;
    private AMap mAMap;
    private AMapGeoFence mAMapGeoFence;
    private TextView mFenceResult;
    /**
     * 用于显示当前的位置
     * <p>
     * 示例中是为了显示当前的位置，在实际使用中，单独的地理围栏可以不使用定位接口
     * </p>
     */
    private AMapLocationClient mlocationClient;
    private OnLocationChangedListener mListener;
    private AMapLocationClientOption mLocationOption;
    private DPoint mPoint = new DPoint();
    @Override
    protected void initData() {
        //从数据库里取出班级列表
        queryClass();
    }

    @Override
    protected void initListener() {
//        picker.setTopLineVisible(false);
//        picker.setOnTimePickListener(new TimePicker.OnTimePickListener() {
//            @Override
//            public void onTimePicked(String hour, String minute) {
//                showToast(hour + ":" + minute);
//            }
//        });
//        picker.show();
    }

    @Override
    public void initView() {
         //picker = new TimePicker(this, TimePicker.HOUR_OF_DAY);
        mClass_swipRecycleView = (SwipeMenuRecyclerView) findViewById(R.id.class_swipRecycleView);
        mClass_swipRecycleView.setLayoutManager(new LinearLayoutManager(AttendanceCheckingForManagerActivity.this));
        mAttendanceCheckingAdapter = new AttendanceCheckingAdapter(mClassBeanList);
        mClass_swipRecycleView.setSwipeMenuCreator(mSwipeMenuCreator);
        mClass_swipRecycleView.setSwipeMenuItemClickListener(mMenuItemClickListener);
        mClass_swipRecycleView.setAdapter(mAttendanceCheckingAdapter);
        mAttendanceCheckingAdapter.notifyDataSetChanged();
        DefaultItemDecoration defaultItemDecoration = new DefaultItemDecoration(0x0000ff, 10, 10);
        mClass_swipRecycleView.addItemDecoration(defaultItemDecoration);
        setUpMapIfNeeded();
        mFenceResult = (TextView) findViewById(R.id.tv_fenceList);
        mAMapGeoFence = new AMapGeoFence(this.getApplicationContext(), mAMap, handler,mPoint);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_attendance_checking_for_manager;
    }


    private void queryClass() {
        //查询当前用户所加入的班级
        JSONArray ids = (JSONArray) getObjectByKey("classIds");
        List<String> classIds = dealIds(ids);
        BmobQuery<ClassBean> query = new BmobQuery<ClassBean>();
        query.addWhereContainedIn("objectId",classIds);
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(1000);
        //执行查询方法
        query.findObjects(new FindListener<ClassBean>() {
            @Override
            public void done(List<ClassBean> object, BmobException e) {
                if (e == null) {
                    showToast("查询成功：共" + object.size() + "条数据。");
                    for (ClassBean classBean : object) {
                        mClassBeanList.add(classBean);
                    }
                    mAttendanceCheckingAdapter.changeList(mClassBeanList);
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    // 创建菜单：
    SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int viewType) {

            SwipeMenuItem deleteItem = new SwipeMenuItem(MyApplication.getContext());
            deleteItem.setBackground(R.color.colorAccent);
            deleteItem.setText("发起签到");
            deleteItem.setHeight(120);
            rightMenu.addMenuItem(deleteItem); // 在Item右侧添加一个菜单。

        }
    };


    SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();

            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
            String creater = (String) getObjectByKey("username");
            String id = (String) MyUser.getObjectByKey("objectId");
            LanuchSignedBean lanuchSignedBean = new LanuchSignedBean();
            lanuchSignedBean.setCreatorId(id);
            lanuchSignedBean.setCreatorName(creater);
            lanuchSignedBean.setLanuchSignedTime(new Date());
            lanuchSignedBean.setLanuchSignedClassId(mClassBeanList.get(adapterPosition).getObjectId());
            lanuchSignedBean.setClassName(mClassBeanList.get(adapterPosition).getClassName());
            lanuchSignedBean.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null){
                        showToast("发起签到成功！！！");
                    }else{
                        showToast("发起签到失败！！！");
                    }
                }
            });
//            Intent intent = new Intent(AttendanceCheckingForManagerActivity.this,MainActivity.class);
//            startActivity(intent);
            finish();
        }
    };


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
    }
    //解析班级id，将jsonArray组装成list
    private List<String> dealIds(JSONArray ids) {
        List<String> idList = new ArrayList<>();
        for (int i = 0; i < ids.length(); i++){
            try {
                idList.add(ids.get(i).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return idList;
    }
    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAMapGeoFence.removeAll();

    }

    private void setUpMapIfNeeded() {
        if (mAMap == null) {
            mAMap = ((SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map)).getMap();
            UiSettings uiSettings = mAMap.getUiSettings();
            if (uiSettings != null) {
                uiSettings.setRotateGesturesEnabled(false);
                uiSettings.setMyLocationButtonEnabled(true); // 设置默认定位按钮是否显示
            }
            mAMap.setLocationSource(this);// 设置定位监听
            mAMap.setMyLocationStyle(
                    new MyLocationStyle().radiusFillColor(Color.argb(0, 0, 0, 0))
                            .strokeColor(Color.argb(0, 0, 0, 0)).myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.navi_map_gps_locked)));
            mAMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
            // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
            mAMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
            mAMap.showIndoorMap(true);    //true：显示室内地图；false：不显示；
            mAMap.moveCamera(CameraUpdateFactory.zoomTo(18));
            mAMap.setMaxZoomLevel(20);
            mAMap.setMinZoomLevel(18);
        }
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
//                    Toast.makeText(getApplicationContext(), "添加围栏成功",
//                            Toast.LENGTH_SHORT).show();
                    mAMapGeoFence.drawFenceToMap();
                    break;
                case 1:
                    int errorCode = msg.arg1;
                    Toast.makeText(getApplicationContext(),
                            "添加围栏失败 " + errorCode, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    String statusStr = (String) msg.obj;
                    Toast.makeText(getApplicationContext(), statusStr,
                            Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                Log.e("aaaaaaaa", "onLocationChanged: " + amapLocation.getAddress() + amapLocation.getDescription() );
                SharedpreferencesUtil.saveAddress(AttendanceCheckingForManagerActivity.this,"address",amapLocation.getAddress());
                mPoint = new DPoint();
                mPoint.setLatitude(amapLocation.getLatitude());
                mPoint.setLongitude(amapLocation.getLongitude());
                mAMapGeoFence = new AMapGeoFence(this.getApplicationContext(), mAMap, handler,mPoint);

            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": "
                        + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
                mFenceResult.setVisibility(View.VISIBLE);
                mFenceResult.setText(errText);
            }
        }/**/
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
            mLocationOption.setInterval(1000);
            //设置是否返回地址信息（默认返回地址信息）
            mLocationOption.setNeedAddress(true);
            // 设置定位监听
            mlocationClient.setLocationListener(this);
            // 设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

            // 设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            mlocationClient.startLocation();
        }

    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }
}
