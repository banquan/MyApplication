package lgj.example.com.biyesheji.adapter;


import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import lgj.example.com.biyesheji.R;
import lgj.example.com.biyesheji.app.Constant;
import lgj.example.com.biyesheji.app.MyApplication;
import lgj.example.com.biyesheji.model.ClassBean;
import lgj.example.com.biyesheji.ui.activity.ClassDetailActivity;

/**
 * Created by yhdj on 2017/10/21.
 */

public class ClassRecycleviewAdapter extends RecyclerView.Adapter<ClassRecycleviewAdapter.MyAdapter> {

    private List<ClassBean> mClassBeenList = new ArrayList<>();


    public ClassRecycleviewAdapter(List<ClassBean> mClassBeenList) {
        this.mClassBeenList = mClassBeenList;

    }


    @Override
    public MyAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_item_view, parent, false);
        MyAdapter viewHolder = new MyAdapter(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyAdapter holder, int position) {
        final ClassBean classBean = mClassBeenList.get(position);
        holder.className.setText(classBean.getClassName());
        Glide.with(MyApplication.getContext()).load(classBean.getClassImgUrl()).into(holder.classImg);
        holder.classCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳到班级详情页面
                Intent intent = new Intent(MyApplication.getContext(), ClassDetailActivity.class);
                intent.putExtra(Constant.INTNET_CLASS_NAME, classBean.getClassName());
                intent.putExtra(Constant.INTENT_CLASS_IMG, classBean.getClassImgUrl());
                intent.putExtra(Constant.INTENT_CLASS_STU_NUM, classBean.getStuNum());
                intent.putExtra(Constant.INTENT_CLASS_ID,classBean.getObjectId());
                holder.classCardView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mClassBeenList.size();
    }

    public void changeList(List<ClassBean> classBeanList) {
        this.mClassBeenList = classBeanList;
        notifyDataSetChanged();
    }

    public class MyAdapter extends RecyclerView.ViewHolder {
        private CircleImageView classImg;
        private TextView className;
        private CardView classCardView;

        public MyAdapter(View itemView) {
            super(itemView);
            classImg = (CircleImageView) itemView.findViewById(R.id.iv_classImg);
            className = (TextView) itemView.findViewById(R.id.tv_className);
            classCardView = (CardView) itemView.findViewById(R.id.class_cardView);
        }
    }
}
