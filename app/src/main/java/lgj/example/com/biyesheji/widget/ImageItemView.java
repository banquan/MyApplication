package lgj.example.com.biyesheji.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.ButterKnife;
import lgj.example.com.biyesheji.R;
import lgj.example.com.biyesheji.model.ImageItemViewInfo;


/**
 * Created by yu on 2017/10/9.
 */

public class ImageItemView extends RelativeLayout {

    @BindView(R.id.first_image_show)
    ImageView mFirstImageShow;
    @BindView(R.id.photos_title)
    TextView mPhotosTitle;
    @BindView(R.id.photos_quantity)
    TextView mPhotosQuantity;

    public ImageItemView(Context context) {
        this(context, null);
    }

    public ImageItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_image, this);
        ButterKnife.bind(this,this);
    }


    public void bindView(ImageItemViewInfo imageItemViewInfo) {
        if(imageItemViewInfo.getShow_first_image() == null){

        }else {

            Glide.with(getContext()).load(imageItemViewInfo.getShow_first_image().getUrl())
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(mFirstImageShow);

        }
        mPhotosTitle.setText(imageItemViewInfo.getTitle());
        if(imageItemViewInfo.getQuantity() == null){
            mPhotosQuantity.setText("0");
        }else {
            mPhotosQuantity.setText(imageItemViewInfo.getQuantity());
        }
    }
}
