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
import lgj.example.com.biyesheji.model.Picture;
import lgj.example.com.biyesheji.ui.activity.ViewPictureActivity;
import lgj.example.com.biyesheji.widget.ChildTwoItemView;


/**
 * Created by yu on 2017/10/9.
 */

public class ChildTwoAdpater extends RecyclerView.Adapter<ChildTwoAdpater.ChildTwoItemViewHolder>{

    private List<Picture> mArrayList;
    private Context mContext;


    public ChildTwoAdpater(Context context, List<Picture> items) {
        mContext = context;
        mArrayList = items;
    }


    @Override
    public ChildTwoItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChildTwoItemViewHolder(new ChildTwoItemView(mContext));
    }

    @Override
    public void onBindViewHolder(final ChildTwoItemViewHolder holder, int position) {
        holder.mChildTwoItemView.bindView(mArrayList.get(position));

        holder.mChildTwoItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int holderPositon = holder.getPosition();
                Toast.makeText(mContext, "holderPositon" + holderPositon, Toast.LENGTH_SHORT).show();
                OnlyMethodToIntent(holderPositon);
            }
        });

    }


    private void OnlyMethodToIntent(final int holderPositon) {

        BmobQuery<Picture> bmobQuery = new BmobQuery<Picture>();
        bmobQuery.findObjects(new FindListener<Picture>() {
            @Override
            public void done(List<Picture> object, BmobException e) {
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


    private void downloadFile(final BmobFile file){
        //允许设置下载文件的存储路径，默认下载文件的目录为：context.getApplicationContext().getCacheDir()+"/bmob/"
        //+"/"+"TbsReaderTemp"让他统一下载到这个文件夹中

        File saveFile = new File(Environment.getExternalStorageDirectory()+"/"+"DownloadSeePicture", file.getFilename());
        file.download(saveFile, new DownloadFileListener() {

            @Override
            public void onStart() {
                Toast.makeText(mContext, "开始下载", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void done(String savePath,BmobException e) {
                if(e==null){
                    Toast.makeText(mContext, "下载成功" +  savePath, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(mContext, ViewPictureActivity.class);
                    intent.putExtra("BmobFile",file.getUrl());
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


    public class ChildTwoItemViewHolder extends RecyclerView.ViewHolder {
        private ChildTwoItemView mChildTwoItemView;
        public ChildTwoItemViewHolder(ChildTwoItemView itemView) {
            super(itemView);
            mChildTwoItemView = itemView;
        }
    }
}
