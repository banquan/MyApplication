package lgj.example.com.biyesheji.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import lgj.example.com.biyesheji.R;
import lgj.example.com.biyesheji.listener.VoteItemListListener;

/**
 * Created by yu on 2017/11/5.
 */

public class VoteListChoiceItemAdapter extends RecyclerView.Adapter<VoteListChoiceItemAdapter.ViewHolder> {
    private  List<Integer> num = new ArrayList<>();
    private List<String> voteList = new ArrayList<>();
    private VoteItemListListener mVoteItemListListener;
    private int index = 0;
    private int flag = 0;
    public VoteListChoiceItemAdapter(List<Integer> num,VoteItemListListener voteItemListListener){
        this.num = num;
        this.mVoteItemListListener = voteItemListListener;
    }
    @Override
    public VoteListChoiceItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vote_list_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final VoteListChoiceItemAdapter.ViewHolder holder, final int position) {

        holder.itemChoice.setHint("选项" + (position + 1));
        holder.itemChoice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                voteList.add(position,s.toString());
                Log.e("aaaaaaaaaaa", "afterTextChanged: " + voteList.get(position));
                mVoteItemListListener.getVoteItemList(voteList);
            }
        });
    }

    @Override
    public int getItemCount() {
        return num.size();
    }

    public void changeData(List<Integer> choiceItemNum) {
        this.num = choiceItemNum;
        notifyDataSetChanged();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        private EditText itemChoice;
        public ViewHolder(View itemView) {
            super(itemView);
            itemChoice = (EditText) itemView.findViewById(R.id.edt_choice_item);
        }
    }
}
