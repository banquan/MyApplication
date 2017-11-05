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
import lgj.example.com.biyesheji.model.Music;
import lgj.example.com.biyesheji.ui.activity.ViewMusicActivity;
import lgj.example.com.biyesheji.widget.ChildFourItemView;


/**
 * Created by yu on 2017/10/9.
 */

public class ChildFourAdpater extends RecyclerView.Adapter<ChildFourAdpater.ChildFourItemViewHolder>{

    private Context mContext;
    private List<Music> mArrayList;

    public ChildFourAdpater(Context context, List<Music> items) {
        mContext = context;
        mArrayList = items;
    }


    @Override
    public ChildFourItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChildFourItemViewHolder(new ChildFourItemView(mContext));
    }

    @Override
    public void onBindViewHolder(final ChildFourItemViewHolder holder, int position) {
        holder.mChildFourItemView.bindView(mArrayList.get(position));

        holder.mChildFourItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int holderPositon = holder.getPosition();
                Toast.makeText(mContext, "holderPositon" + holderPositon, Toast.LENGTH_SHORT).show();
                OnlyMethodToIntent(holderPositon);
            }
        });
    }


    private void OnlyMethodToIntent(final int holderPositon) {

        BmobQuery<Music> bmobQuery = new BmobQuery<Music>();
        bmobQuery.findObjects(new FindListener<Music>() {
            @Override
            public void done(List<Music> object, BmobException e) {
                if(e==null){
                    BmobFile bmobFile = object.get(holderPositon).getBmobFile();
                    String mMusicName = object.get(holderPositon).getMusicName();
                    IntentToActivity(bmobFile,mMusicName);
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    private void IntentToActivity(BmobFile bmobFile, String mMusicName) {
        downloadFile(bmobFile,mMusicName);
    }


    private void downloadFile(BmobFile file, final String mMusicName){
        //允许设置下载文件的存储路径，默认下载文件的目录为：context.getApplicationContext().getCacheDir()+"/bmob/"
        File saveFile = new File(Environment.getExternalStorageDirectory()+"/"+"DownloadSeeMusic", file.getFilename());
        file.download(saveFile, new DownloadFileListener() {

            @Override
            public void onStart() {
                Toast.makeText(mContext, "开始下载", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void done(String savePath,BmobException e) {
                if(e==null){
                    Toast.makeText(mContext, "下载成功" +  savePath, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(mContext, ViewMusicActivity.class);
                    intent.putExtra("BmobFile",savePath);
                    intent.putExtra("mMusicName",mMusicName);
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


    public class ChildFourItemViewHolder extends RecyclerView.ViewHolder {
        private ChildFourItemView mChildFourItemView;

        public ChildFourItemViewHolder(ChildFourItemView itemView) {
            super(itemView);
            mChildFourItemView = itemView;
        }
    }
}
