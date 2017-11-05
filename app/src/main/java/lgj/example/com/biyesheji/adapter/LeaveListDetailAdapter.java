package lgj.example.com.biyesheji.adapter;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import lgj.example.com.biyesheji.R;
import lgj.example.com.biyesheji.app.MyApplication;
import lgj.example.com.biyesheji.model.LeaveBean;

/**
 * Created by yhdj on 2017/11/4.
 */

public class LeaveListDetailAdapter extends RecyclerView.Adapter<LeaveListDetailAdapter.ViewHolder> {
    private List<LeaveBean> mLeaveBeanList = new ArrayList<>();

    public LeaveListDetailAdapter(List<LeaveBean> leaveBeen) {
        this.mLeaveBeanList = leaveBeen;
    }

    @Override
    public LeaveListDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leave_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(LeaveListDetailAdapter.ViewHolder holder, int position) {
        final LeaveBean leaveBean = mLeaveBeanList.get(position);
        holder.tvClassName.setText(leaveBean.getClassName());
        holder.tvClassStuName.setText(leaveBean.getStuName());

        //请假详情
        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //请假详情
                AlertDialog.Builder dialog = new AlertDialog.Builder(MyApplication.getContext());
                dialog.setTitle("请假详情");
                dialog.setMessage(leaveBean.getLeaveReason());
                dialog.setPositiveButton("批准", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            //更新请假
                        leaveBean.setPassed(true);
                        leaveBean.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null){
                                    Toast.makeText(MyApplication.getContext(), "审核成功！！！", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mLeaveBeanList.size();
    }

    public void changeData(List<LeaveBean> leaveBeanList) {
        this.mLeaveBeanList = leaveBeanList;
        Log.e("aaaaa", "changeData: " + mLeaveBeanList.get(0).getClassName());
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvClassName;
        private TextView tvClassStuName;
        private Button btnDetail;

        public ViewHolder(View itemView) {
            super(itemView);
            tvClassName = (TextView) itemView.findViewById(R.id.tvLeaveClassName);
            tvClassStuName = (TextView) itemView.findViewById(R.id.tvLeaveStuName);
            btnDetail = (Button) itemView.findViewById(R.id.leaveDetail);
        }
    }
}
