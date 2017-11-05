package lgj.example.com.biyesheji.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;

import lgj.example.com.biyesheji.R;
import lgj.example.com.biyesheji.model.VoteBean;

/**
 * Created by yu on 2017/11/5.
 */

public class VoteDetailAdapter extends RecyclerView.Adapter<VoteDetailAdapter.ViewHolder> {
    private VoteBean mVoteBeen = new VoteBean();
    private Context mContext;

    public VoteDetailAdapter(VoteBean mVoteBeen, Context mContext) {
        this.mVoteBeen = mVoteBeen;
        this.mContext = mContext;
    }

    @Override
    public VoteDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vote_detail_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(VoteDetailAdapter.ViewHolder holder, int position) {
        String voteContent = mVoteBeen.getVoteContent().get(position);
        int voteNum = mVoteBeen.getVoteNums().get(position);

        holder.voteContent.setText(voteContent);
        //判断单选，双选，还是无限制
        int voteSelect = mVoteBeen.getVoteSelect();

        holder.voteChoice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.e("aaaaaaaaa", "onCheckedChanged: " + isChecked);
            }
        });

        holder.voteChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("aaaaaaaaaa", "onClick: " + "check");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mVoteBeen.getVoteContent().size();
    }

    public void changeData(VoteBean voteBean) {
        this.mVoteBeen = voteBean;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView voteContent;
        private NumberProgressBar voteProgress;
        private TextView voteNum;
        private CheckBox voteChoice;

        public ViewHolder(View itemView) {
            super(itemView);

            voteContent = (TextView) itemView.findViewById(R.id.voteContent);
            voteProgress = (NumberProgressBar) itemView.findViewById(R.id.voteProgress);
            voteNum = (TextView) itemView.findViewById(R.id.voteNum);
            voteChoice = (CheckBox) itemView.findViewById(R.id.voteChoice);
        }
    }
}
