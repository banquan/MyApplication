package lgj.example.com.biyesheji.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tapadoo.alerter.Alerter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.ValueEventListener;
import lgj.example.com.biyesheji.R;
import lgj.example.com.biyesheji.adapter.ClassRecycleviewAdapter;
import lgj.example.com.biyesheji.app.MyApplication;
import lgj.example.com.biyesheji.model.ClassBean;
import lgj.example.com.biyesheji.model.LaunchSignedRealTimeData;
import lgj.example.com.biyesheji.model.MyUser;

/**
 * Created by yhdj on 2017/9/27.
 */

public class MainFragment extends BaseFragment {
    @BindView(R.id.attendanceChecking)
    TextView mAttendanceChecking;
    @BindView(R.id.activity_main)
    LinearLayout mActivityMain;

    private String className;
    private List<ClassBean> mClassBeanList = new ArrayList<>();
    private ClassRecycleviewAdapter mClassRecycleviewAdapter;
    private FloatingActionButton addClassButton;
    private RecyclerView mRecycleviewClass;
    private  List<String> classIds = new ArrayList<>();
    private Gson mGson = new Gson();
    private String lanuchSignedClassName;
    private String lanuchSignedCreator;
    private String lanuchSignedClassId;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_main;
    }

    @Override
    public void initView() {
        addClassButton = (FloatingActionButton) mRootView.findViewById(R.id.fab_add_class);
        mRecycleviewClass = (RecyclerView) mRootView.findViewById(R.id.recycleview_class);
        boolean isMgn = (boolean) MyUser.getObjectByKey("isMgn");
        showToast(isMgn + "");
        if (!isMgn) {
            addClassButton.setVisibility(View.GONE);
        } else {
            addClassButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initListener() {
//监听表
        final BmobRealTimeData rtd = new BmobRealTimeData();
        rtd.start(new ValueEventListener() {
            @Override
            public void onDataChange(JSONObject data) {
                Log.e("bmob", "(" + data.optString("action") + ")" + "数据：" + data);
                //解析实时更新的数据
               LaunchSignedRealTimeData classBean = mGson.fromJson(data.toString(), LaunchSignedRealTimeData.class);
                lanuchSignedClassName = classBean.getData().getClassName();
                lanuchSignedCreator = classBean.getData().getCreatorName();
                lanuchSignedClassId = classBean.getData().getLanuchSignedClassId();

                //判断要签到的班级
                for (String id : classIds){
                    if (lanuchSignedClassId.equals(id)){
                        //弹出提示框
                        Alerter.create(getActivity())
                                .setTitle(lanuchSignedClassName + "的签到提醒")
                                .setText(lanuchSignedCreator + "发起签到，请于十分钟内完成签到！！！")
                                .enableSwipeToDismiss()
                                .setDuration(30000)
                                .setIcon(R.drawable.alerter_ic_face)
                                .show();
                    }
                }


            }

            @Override
            public void onConnectCompleted(Exception ex) {
                Log.d("bmob", "连接成功:" + rtd.isConnected());
                if (rtd.isConnected()) {
                    // 监听表更新
                    rtd.subTableUpdate("LanuchSignedBean");
                }
            }
        });

        addClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = new EditText(getContext());
                new AlertDialog.Builder(getContext())
                        .setTitle("请输入班级名称")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                className = editText.getText().toString();
                                if (className == null || className.equals("")) {
                                    showToast("请输入班级名称");
                                    return;
                                }
                                //管理员创建班级
                                createClass();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setView(editText)
                        .show();

            }
        });
    }

    //创建班级
    private void createClass() {
        ClassBean classBean = new ClassBean();
        Date classCreateDate = new Date();
        String classCreater = (String) MyUser.getObjectByKey("username");
        String administrator = classCreater;
        String classImgUrl = "http://f2.topitme.com/2/44/48/11321074391a748442o.jpg";
        classBean.setAdministrator(administrator);
        classBean.setClassCreateDate(classCreateDate);
        classBean.setClassCreater(classCreater);
        classBean.setClassImgUrl(classImgUrl);
        classBean.setClassId(UUID.randomUUID().toString());
        classBean.setClassName(className);
        classBean.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    showToast("创建成功！！！");
                    queryClass();
                } else {
                    showToast("创建失败！！！" + e.toString());
                }
            }
        });
    }

    @Override
    public void initData() {


        //从数据库里取出班级列表
        queryClass();


        mClassRecycleviewAdapter = new ClassRecycleviewAdapter(mClassBeanList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyApplication.getContext());
        mRecycleviewClass.setLayoutManager(linearLayoutManager);
        mRecycleviewClass.setAdapter(mClassRecycleviewAdapter);
        mClassRecycleviewAdapter.notifyDataSetChanged();
    }

    private void queryClass() {

        //查询当前用户所加入的班级
        JSONArray ids = (JSONArray) MyUser.getObjectByKey("classIds");
        if (ids == null){
            return;
        }
         classIds = dealIds(ids);

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
                    mClassRecycleviewAdapter.changeList(mClassBeanList);
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
}
