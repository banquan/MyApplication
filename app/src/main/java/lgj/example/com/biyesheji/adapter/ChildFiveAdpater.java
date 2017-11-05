package lgj.example.com.biyesheji.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;
import lgj.example.com.biyesheji.model.Other;
import lgj.example.com.biyesheji.ui.activity.ViewOtherActivity;
import lgj.example.com.biyesheji.widget.ChildFiveItemView;


/**
 * Created by yu on 2017/10/9.
 */

public class ChildFiveAdpater extends RecyclerView.Adapter<ChildFiveAdpater.ChildFiveItemViewHolder>{

    private Context mContext;
    private List<Other> mArrayList;

    public ChildFiveAdpater(Context context, List<Other> items) {
        mContext = context;
        mArrayList = items;
    }


    @Override
    public ChildFiveItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChildFiveItemViewHolder(new ChildFiveItemView(mContext));
    }

    @Override
    public void onBindViewHolder(final ChildFiveItemViewHolder holder, int position) {
        holder.mChildFiveItemView.bindView(mArrayList.get(position));


//        holder.mChildFiveItemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int holderPositon = holder.getPosition();
//                Toast.makeText(mContext, "holderPositon" + holderPositon, Toast.LENGTH_SHORT).show();
//                OnlyMethodToIntent(holderPositon);
//            }
//        });

    }


    private void OnlyMethodToIntent(final int holderPositon) {

        BmobQuery<Other> bmobQuery = new BmobQuery<Other>();
        bmobQuery.findObjects(new FindListener<Other>() {
            @Override
            public void done(List<Other> object, BmobException e) {
                if(e==null){
                    BmobFile bmobFile = object.get(holderPositon).getBmobFile();
                    IntentToActivity(bmobFile);
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    private void IntentToActivity(BmobFile bmobFile) {
        downloadFile(bmobFile);
    }


    private void downloadFile(BmobFile file){
        //允许设置下载文件的存储路径，默认下载文件的目录为：context.getApplicationContext().getCacheDir()+"/bmob/"
        File saveFile = new File(Environment.getExternalStorageDirectory()+"/"+"DownloadSeeOther", file.getFilename());
        file.download(saveFile, new DownloadFileListener() {

            @Override
            public void onStart() {
                Toast.makeText(mContext, "开始下载", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void done(String savePath,BmobException e) {
                if(e==null){
                    Toast.makeText(mContext, "下载成功" +  savePath, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(mContext, ViewOtherActivity.class);
                    intent.putExtra("documentPath",savePath);
                    mContext.startActivity(intent);

                }else{
                    Toast.makeText(mContext, "下载失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onProgress(Integer value, long newworkSpeed) {
                Log.i("bmob","下载进度："+value+","+newworkSpeed);
            }

        });

    }




    @Override
    public int getItemCount() {
        return mArrayList.size();
    }


    public class ChildFiveItemViewHolder extends RecyclerView.ViewHolder {
        private ChildFiveItemView mChildFiveItemView;
        public ChildFiveItemViewHolder(ChildFiveItemView itemView) {
            super(itemView);
            mChildFiveItemView = itemView;
        }
    }


}
