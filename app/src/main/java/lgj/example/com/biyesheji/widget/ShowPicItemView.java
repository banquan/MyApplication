package lgj.example.com.biyesheji.widget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lgj.example.com.biyesheji.R;
import lgj.example.com.biyesheji.model.AllPicInfo;
import lgj.example.com.biyesheji.ui.activity.OnePicActivity;


/**
 * Created by yu on 2017/10/11.
 */

public class ShowPicItemView extends RelativeLayout {

    @BindView(R.id.show_pic)
    ImageView mShowPic;

    private String url;
    private String objectId;
    private List<AllPicInfo> mAllPicInfos;
    private String[] imgUrls;
    private int index = -1;

    public ShowPicItemView(Context context) {
        this(context, null);
    }

    public ShowPicItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_show_pic_item, this);
        ButterKnife.bind(this, this);
    }

    public void bindView(AllPicInfo allPicInfo) {

        url = allPicInfo.getPic().getUrl();
        objectId = allPicInfo.getObjectId();

        Glide.with(getContext()).load(allPicInfo.getPic().getUrl())
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(mShowPic);
    }

    @OnClick(R.id.show_pic)
    public void onClick() {
        Intent intent = new Intent(getContext(), OnePicActivity.class);
//        intent.putExtra("url",url);
//        intent.putExtra("objectId",objectId);
//        getContext().startActivity(intent);
        intent.putExtra("imgUrl", getImgUrls(mAllPicInfos));
        intent.putExtra("index", index);
        Toast.makeText(getContext(), "index = " + index, Toast.LENGTH_SHORT).show();
        getContext().startActivity(intent);
    }


    public String[] getImgUrls(List<AllPicInfo> mAllPicInfos) {
        for (int i = 0; i < mAllPicInfos.size(); i++) {
            imgUrls[i] = mAllPicInfos.get(i).getPic().getUrl();
        }

        return imgUrls;
    }

    //初始化全部图片信息，从showPicAdapter中获取来
    public void initPicInfo(List<AllPicInfo> mAllPicInfos) {
        this.mAllPicInfos = mAllPicInfos;
        Log.e("aaaaaaaaaaaaa", "initPicInfo: " + mAllPicInfos.size());
        imgUrls = new String[mAllPicInfos.size()];
    }

    //初始化图片的下标
    public void initImgUrlIndex(int index) {
        this.index = index;
    }

}
