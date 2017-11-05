package lgj.example.com.biyesheji.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import lgj.example.com.biyesheji.R;
import lgj.example.com.biyesheji.adapter.LeaveListDetailAdapter;
import lgj.example.com.biyesheji.model.LeaveBean;
import lgj.example.com.biyesheji.model.MyUser;

public class LeaveListActivity extends BaseActivity {


    @BindView(R.id.leaveList_recycleView)
    RecyclerView mLeaveListRecycleView;
    @BindView(R.id.activity_leave_list)
    RelativeLayout mActivityLeaveList;
    private LeaveListDetailAdapter mLeaveListDetailAdapter;
    private List<String> classIds;
    private List<LeaveBean> mLeaveBeanList = new ArrayList<>();

    @Override
    protected void initData() {
        //查询当前用户所加入的班级
        JSONArray ids = (JSONArray) MyUser.getObjectByKey("classIds");
        classIds = dealIds(ids);

        BmobQuery<LeaveBean> query = new BmobQuery<LeaveBean>();
        query.addWhereContainedIn("classId",classIds);
        query.addWhereEqualTo("isPassed",false);
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(1000);
        //执行查询方法
        query.findObjects(new FindListener<LeaveBean>() {
            @Override
            public void done(List<LeaveBean> object, BmobException e) {
                if (e == null) {
                    showToast("查询成功：共" + object.size() + "条数据。");
                    for (LeaveBean leaveBean : object) {
                       mLeaveBeanList.add(leaveBean);
                    }
                    mLeaveListDetailAdapter.changeData(mLeaveBeanList);

                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(LeaveListActivity.this);
        mLeaveListRecycleView.setLayoutManager(linearLayoutManager);
        mLeaveListDetailAdapter = new LeaveListDetailAdapter(mLeaveBeanList);
        mLeaveListRecycleView.setAdapter(mLeaveListDetailAdapter);
        mLeaveListDetailAdapter.notifyDataSetChanged();

    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_leave_list;
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
