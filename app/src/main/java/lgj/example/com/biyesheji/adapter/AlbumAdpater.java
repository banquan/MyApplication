package lgj.example.com.biyesheji.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import lgj.example.com.biyesheji.model.ImageItemViewInfo;
import lgj.example.com.biyesheji.ui.activity.ShowPicActivity;
import lgj.example.com.biyesheji.widget.ImageItemView;


/**
 * Created by yu on 2017/10/9.
 */

public class AlbumAdpater extends RecyclerView.Adapter<AlbumAdpater.ImageItemViewHolder>{

    private Context mContext;
    private List<ImageItemViewInfo> mImageItemViewInfos;


    //public String objectId;//需要将此objectId传递过去用于更新数据（咱们每个条目的头像和照片数量）
    //public String title;//这个title作为每个相册的标题，也要作为每个相册跳转后的所有图片集合的表名。

    public AlbumAdpater(Context context, List<ImageItemViewInfo> imageItemViewInfos) {
        mContext = context;
        mImageItemViewInfos = imageItemViewInfos;
    }

    @Override
    public ImageItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageItemViewHolder(new ImageItemView(mContext));
    }

    @Override
    public void onBindViewHolder(final ImageItemViewHolder holder, int position) {
        holder.mImageItemView.bindView(mImageItemViewInfos.get(position));




        //设置点击事件（我需要把相册名字传递过去，同时我需要把条目对应的objectId传递过去才能更新数据）
        //存在的疑问是：我如何获取每个条目的objectId呢？
        holder.mImageItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int holderPositon = holder.getPosition();
                getObjectId(holderPositon);
                Toast.makeText(mContext, "mImageItemView", Toast.LENGTH_SHORT).show();
            }
        });

    }



    @Override
    public int getItemCount() {
        return mImageItemViewInfos.size();
    }

    public void refreshAlbumAdpater(List<ImageItemViewInfo> list) {
        mImageItemViewInfos.clear();
        mImageItemViewInfos = list;
        notifyDataSetChanged();
    }

    private void getObjectId(final int holderPositon) {

        BmobQuery<ImageItemViewInfo> bmobQuery = new BmobQuery<ImageItemViewInfo>();
        bmobQuery.findObjects(new FindListener<ImageItemViewInfo>() {
            @Override
            public void done(List<ImageItemViewInfo> object, BmobException e) {
                if(e==null){
                    String objectId = object.get(holderPositon).getObjectId();
                    String title = object.get(holderPositon).getTitle();
                    Log.d("objectId",objectId);
                    Log.d("objectId",title);
                    IntentToShowPicActivity(objectId,title);
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });

    }

    public void IntentToShowPicActivity(String objectId,String title){
        //跳转到相册界面
        Intent intent = new Intent(mContext, ShowPicActivity.class);
        intent.putExtra("objectId",objectId);
        intent.putExtra("title",title);
        mContext.startActivity(intent);
        Toast.makeText(mContext, "IntentToShowPicActivity", Toast.LENGTH_SHORT).show();
    }

    public List<ImageItemViewInfo> getDataList() {
        return mImageItemViewInfos;
    }


    public class ImageItemViewHolder extends RecyclerView.ViewHolder {

        private ImageItemView mImageItemView;

        public ImageItemViewHolder(ImageItemView itemView) {
            super(itemView);
            mImageItemView = itemView;
        }
    }
}
