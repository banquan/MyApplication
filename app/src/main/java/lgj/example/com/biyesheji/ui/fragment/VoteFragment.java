package lgj.example.com.biyesheji.ui.fragment;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import lgj.example.com.biyesheji.R;
import lgj.example.com.biyesheji.adapter.VoteMainAdapter;
import lgj.example.com.biyesheji.app.MyApplication;
import lgj.example.com.biyesheji.model.MyUser;
import lgj.example.com.biyesheji.model.VoteBean;
import lgj.example.com.biyesheji.ui.activity.ClassVoteActivity;

/**
 * Created by yhdj on 2017/9/27.
 */

public class VoteFragment extends BaseFragment {
    @BindView(R.id.tv_lanuch_vote)
    TextView mTvLanuchVote;
    @BindView(R.id.vote_recycleView)
    RecyclerView mVoteRecycleView;
    @BindView(R.id.activity_main)
    LinearLayout mActivityMain;
    private List<String> classIds = new ArrayList<>();
    private List<VoteBean> mVoteBeen = new ArrayList<>();
    private VoteMainAdapter mVoteMainAdapter;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_vote;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this, mRootView);
        mVoteMainAdapter = new VoteMainAdapter(mVoteBeen,getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mVoteRecycleView.setAdapter(mVoteMainAdapter);
        mVoteRecycleView.setLayoutManager(layoutManager);
        mVoteMainAdapter.notifyDataSetChanged();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        //获取投票列表
            queryClass();
    }


    @OnClick(R.id.tv_lanuch_vote)
    public void onClick() {
        Intent intent = new Intent(MyApplication.getContext(), ClassVoteActivity.class);
        getActivity().startActivity(intent);
    }

    //解析班级id，将jsonArray组装成list
    private List<String> dealIds(JSONArray ids) {
        List<String> idList = new ArrayList<>();
        for (int i = 0; i < ids.length(); i++) {
            try {
                idList.add(ids.get(i).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return idList;
    }

    private void queryClass() {

        //查询当前用户所加入的班级
        JSONArray ids = (JSONArray) MyUser.getObjectByKey("classIds");
        if (ids == null){
            return;
        }
        classIds = dealIds(ids);

        BmobQuery<VoteBean> query = new BmobQuery<VoteBean>();
        query.addWhereContainedIn("classId", classIds);
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(1000);
        //执行查询方法
        query.findObjects(new FindListener<VoteBean>() {
            @Override
            public void done(List<VoteBean> object, BmobException e) {
                if (e == null) {
                    showToast("查询成功：共" + object.size() + "条数据。");
                    for (VoteBean voteBean : object) {
                        mVoteBeen.add(voteBean);
                    }
                    mVoteMainAdapter.changeData(mVoteBeen);

                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }
}
