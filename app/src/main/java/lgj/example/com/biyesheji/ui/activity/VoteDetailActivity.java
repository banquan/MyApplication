package lgj.example.com.biyesheji.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lgj.example.com.biyesheji.R;
import lgj.example.com.biyesheji.adapter.VoteDetailAdapter;
import lgj.example.com.biyesheji.model.VoteBean;

public class VoteDetailActivity extends BaseActivity {

    @BindView(R.id.tv_voteTitle)
    TextView mTvVoteTitle;
    @BindView(R.id.voteDetailRecycleview)
    RecyclerView mVoteDetailRecycleview;
    @BindView(R.id.activity_vote_detail)
    LinearLayout mActivityVoteDetail;
    private VoteBean mVoteBean = new VoteBean();
    private VoteDetailAdapter mVoteDetailAdapter;
    private List<String> voteContent = new ArrayList<>();
    private List<Integer> voteNum = new ArrayList<>();

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null){
            String[] content = intent.getStringArrayExtra("voteContent");
            int[] num = intent.getIntArrayExtra("voteNum");
            String title = intent.getStringExtra("title");
            mTvVoteTitle.setText(title);
            mVoteBean.setVoteTitle(title);
            dealData(content,num);
        }
    }

    private void dealData(String[] content, int[] num) {
        for (int i = 0; i < content.length; i++){
            voteContent.add(content[i]);
            voteNum.add(num[i]);
        }
        mVoteBean.setVoteContent(voteContent);
        mVoteBean.setVoteNums(voteNum);
        mVoteDetailAdapter.changeData(mVoteBean);
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        mVoteDetailAdapter = new VoteDetailAdapter(mVoteBean,VoteDetailActivity.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(VoteDetailActivity.this);
        mVoteDetailRecycleview.setAdapter(mVoteDetailAdapter);
        mVoteDetailRecycleview.setLayoutManager(layoutManager);
        mVoteDetailAdapter.notifyDataSetChanged();

    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_vote_detail;
    }


}
