package lgj.example.com.biyesheji.adapter;


import android.content.Context;
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
import lgj.example.com.biyesheji.app.MyApplication;
import lgj.example.com.biyesheji.model.ClassBean;
import lgj.example.com.biyesheji.ui.activity.AlbumActivity;

/**
 * Created by yu on 2017/11/5.
 */

public class PhotoClassAdapter extends RecyclerView.Adapter<PhotoClassAdapter.ViewHolder> {
    private List<ClassBean> mClassBeen = new ArrayList<>();
    private Context mContext;
    public PhotoClassAdapter(List<ClassBean> mClassBeen, Context context){
        this.mClassBeen = mClassBeen;
        this.mContext = context;
    }

    @Override
    public PhotoClassAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_item_view,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final PhotoClassAdapter.ViewHolder holder, int position) {
                final ClassBean classBean = mClassBeen.get(position);
        holder.className.setText(classBean.getClassName());
        Glide.with(MyApplication.getContext()).load(classBean.getClassImgUrl()).into(holder.classImg);
        holder.class_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyApplication.getContext(), AlbumActivity.class);
                intent.putExtra("classId",classBean.getObjectId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mClassBeen.size();
    }

    public void changeData(List<ClassBean> classBeen) {

        this.mClassBeen = classBeen;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView classImg;
        private TextView className;
        private CardView class_cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            classImg = (CircleImageView) itemView.findViewById(R.id.iv_classImg);
            className = (TextView) itemView.findViewById(R.id.tv_className);
            class_cardView = (CardView) itemView.findViewById(R.id.class_cardView);
        }
    }
}
