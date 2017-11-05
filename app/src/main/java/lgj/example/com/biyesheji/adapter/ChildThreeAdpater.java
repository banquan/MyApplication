package lgj.example.com.biyesheji.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;
import lgj.example.com.biyesheji.model.Video;
import lgj.example.com.biyesheji.ui.activity.ViewVideoActivity;
import lgj.example.com.biyesheji.widget.ChildThreeItemView;


/**
 * Created by yu on 2017/10/9.
 */

public class ChildThreeAdpater extends RecyclerView.Adapter<ChildThreeAdpater.ChildThreeItemViewHolder>{

    private Context mContext;
    private List<Video> mArrayList;

    public ChildThreeAdpater(Context context, List<Video> items) {
        mContext = context;
        mArrayList = items;
    }


    @Override
    public ChildThreeItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChildThreeItemViewHolder(new ChildThreeItemView(mContext));
    }

    @Override
    public void onBindViewHolder(final ChildThreeItemViewHolder holder, int position) {
        holder.mChildThreeItemView.bindView(mArrayList.get(position));
        holder.mChildThreeItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int holderPositon = holder.getPosition();
                Toast.makeText(mContext, "holderPositon" + holderPositon, Toast.LENGTH_SHORT).show();
                OnlyMethodToIntent(holderPositon);
            }
        });
    }


    private void OnlyMethodToIntent(final int holderPositon) {

        BmobQuery<Video> bmobQuery = new BmobQuery<Video>();
        bmobQuery.findObjects(new FindListener<Video>() {
            @Override
            public void done(List<Video> object, BmobException e) {
                if(e==null){
                    BmobFile bmobFile = object.get(holderPositon).getBmobFile();
                    String mVideoName = object.get(holderPositon).getVideoName();
                    IntentToActivity(bmobFile,mVideoName);
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    private void IntentToActivity(BmobFile bmobFile, String mVideoName ) {
        downloadFile(bmobFile,mVideoName);
    }

    private void downloadFile(BmobFile file, final String mVideoName){
        //允许设置下载文件的存储路径，默认下载文件的目录为：context.getApplicationContext().getCacheDir()+"/bmob/"
        File saveFile = new File(Environment.getExternalStorageDirectory()+"/"+"DownloadSeeVideo", file.getFilename());
        file.download(saveFile, new DownloadFileListener() {

            @Override
            public void onStart() {
                Toast.makeText(mContext, "开始下载", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void done(String savePath,BmobException e) {
                if(e==null){
                    Toast.makeText(mContext, "下载成功" +  savePath, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(mContext, ViewVideoActivity.class);
                    intent.putExtra("BmobFile",savePath);
                    intent.putExtra("mVideoName",mVideoName);
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


    public class ChildThreeItemViewHolder extends RecyclerView.ViewHolder {
            private ChildThreeItemView mChildThreeItemView;
        public ChildThreeItemViewHolder(ChildThreeItemView itemView) {
            super(itemView);
            mChildThreeItemView = itemView;
        }
    }
}
