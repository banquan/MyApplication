package lgj.example.com.biyesheji.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import lgj.example.com.biyesheji.R;
import lgj.example.com.biyesheji.app.MyApplication;
import lgj.example.com.biyesheji.model.ClassBean;

/**
 * Created by yhdj on 2017/11/1.
 */

public class AttendanceCheckingAdapter extends RecyclerView.Adapter<AttendanceCheckingAdapter.MyHolder> {

    private List<ClassBean> mClassBeenList = new ArrayList<>();


    public AttendanceCheckingAdapter(List<ClassBean> mClassBeenList) {
        this.mClassBeenList = mClassBeenList;

    }

    @Override
    public AttendanceCheckingAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_item_view, parent, false);
        MyHolder viewHolder = new MyHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AttendanceCheckingAdapter.MyHolder holder, int position) {
        final ClassBean classBean = mClassBeenList.get(position);
        holder.tvClassName.setText(classBean.getClassName());
        Glide.with(MyApplication.getContext()).load(classBean.getClassImgUrl()).into(holder.classImg);
    }

    @Override
    public int getItemCount() {
        return mClassBeenList.size();
    }

    public void changeList(List<ClassBean> classBeanList) {
        this.mClassBeenList = classBeanList;
        notifyDataSetChanged();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private ImageView classImg;
        private TextView tvClassName;

        public MyHolder(View itemView) {
            super(itemView);
            classImg = (ImageView) itemView.findViewById(R.id.iv_classImg);
            tvClassName = (TextView) itemView.findViewById(R.id.tv_className);
        }
    }
}
