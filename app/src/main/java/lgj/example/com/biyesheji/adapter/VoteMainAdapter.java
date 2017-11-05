package lgj.example.com.biyesheji.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lgj.example.com.biyesheji.R;
import lgj.example.com.biyesheji.model.VoteBean;
import lgj.example.com.biyesheji.ui.activity.VoteDetailActivity;

/**
 * Created by yu on 2017/11/5.
 */

public class VoteMainAdapter extends RecyclerView.Adapter<VoteMainAdapter.ViewHolder> {
    private List<VoteBean> mVoteBeen = new ArrayList<>();
    private Context mContext;
    private String[] voteContent;
    private int[] voteNum;

    public VoteMainAdapter(List<VoteBean> mVoteBeen,Context mContext){
        this.mVoteBeen = mVoteBeen;
        this.mContext = mContext;
    }
    @Override
    public VoteMainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vote_item_view,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(VoteMainAdapter.ViewHolder holder, int position) {
            final VoteBean voteBean = mVoteBeen.get(position);
        holder.tvVoteTitle.setText(voteBean.getVoteTitle());
        //判断投票是否进行中
        String time = voteBean.getEndTime();
        //获取现在的时间，与投票结束时间进行对比
        Date now = new Date();
        Date vote = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
             vote = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (vote.getTime() > now.getTime()){
            holder.voteIsDoing.setText("进行中");
        }else{
            holder.voteIsDoing.setText("已结束");
        }
        Log.e("aaaaaaaaaa", "onBindViewHolder: " + time + "===" + vote );
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入投票页面
                Intent intent  = new Intent(mContext, VoteDetailActivity.class);
                dealData(voteBean);
                intent.putExtra("voteContent",voteContent);
                intent.putExtra("voteNum",voteNum);
                intent.putExtra("title",voteBean.getVoteTitle());
                mContext.startActivity(intent);
            }
        });
    }

    private void dealData(VoteBean voteBean) {
        List<String> content = voteBean.getVoteContent();
        List<Integer> num = voteBean.getVoteNums();
        voteContent = new String[content.size()];
        voteNum = new int[num.size()];
        for (int i = 0; i < voteContent.length; i++){
            voteContent[i] = content.get(i);
        }

        for (int y = 0; y < voteNum.length; y++){
            voteNum[y] = num.get(y);
        }
    }

    @Override
    public int getItemCount() {
        return mVoteBeen.size();
    }

    public void changeData(List<VoteBean> voteBeen) {
        this.mVoteBeen = voteBeen;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView mCardView;
        private TextView tvVoteTitle;
        private TextView voteIsDoing;
        public ViewHolder(View itemView) {
            super(itemView);

            mCardView = (CardView) itemView.findViewById(R.id.class_cardView);
            tvVoteTitle = (TextView) itemView.findViewById(R.id.vote_title);
            voteIsDoing = (TextView) itemView.findViewById(R.id.vote_doing);
        }
    }
}
