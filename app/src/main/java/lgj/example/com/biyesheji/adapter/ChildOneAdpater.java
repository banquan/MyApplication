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
import lgj.example.com.biyesheji.model.Document;
import lgj.example.com.biyesheji.ui.activity.ViewDocumentActivity;
import lgj.example.com.biyesheji.widget.ChildOneItemView;


/**
 * Created by yu on 2017/10/9.
 */

public class ChildOneAdpater extends RecyclerView.Adapter<ChildOneAdpater.ChildOneItemViewHolder>{

    private Context mContext;
    private List<Document> mArrayList;
    private Intent mIntent;

    public ChildOneAdpater(Context context, List<Document> items) {
        mContext = context;
        this.mArrayList = items;
    }


    @Override
    public ChildOneItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChildOneItemViewHolder(new ChildOneItemView(mContext));
    }

    @Override
    public void onBindViewHolder(final ChildOneItemViewHolder holder, int position) {

        holder.mChildOneItemView.bindView(mArrayList.get(position));
        holder.mChildOneItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int holderPositon = holder.getPosition();
                Toast.makeText(mContext, "holderPositon" + holderPositon, Toast.LENGTH_SHORT).show();
                OnlyMethodToIntent(holderPositon);
            }
        });
    }


    private void OnlyMethodToIntent(final int holderPositon) {

        BmobQuery<Document> bmobQuery = new BmobQuery<Document>();
        bmobQuery.findObjects(new FindListener<Document>() {
            @Override
            public void done(List<Document> object, BmobException e) {
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
        //因为利用内核的时候它创建了TbsReaderTemp文件夹。就不用DownloadSeeXXX命名文件夹了。
        File saveFile = new File(Environment.getExternalStorageDirectory()+"/"+"TbsReaderTemp", file.getFilename());


        file.download(saveFile, new DownloadFileListener() {

            @Override
            public void onStart() {
                Toast.makeText(mContext, "开始下载", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void done(String savePath,BmobException e) {
                if(e==null){
                    Toast.makeText(mContext, "下载成功" +  savePath, Toast.LENGTH_SHORT).show();
                    Log.e("ChildOneAdpater+++",savePath);
                    mIntent = new Intent(mContext, ViewDocumentActivity.class);

                    mIntent.putExtra("documentPath",savePath);

                    mIntent.putExtra("getFileUrl",file.getFileUrl());

//                    mIntent.putExtra("getUrl",file.getUrl());

                    mContext.startActivity(mIntent);

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




    public class ChildOneItemViewHolder extends RecyclerView.ViewHolder {
        private ChildOneItemView mChildOneItemView;

        public ChildOneItemViewHolder(ChildOneItemView itemView) {
            super(itemView);
            mChildOneItemView = itemView;
        }
    }
}
