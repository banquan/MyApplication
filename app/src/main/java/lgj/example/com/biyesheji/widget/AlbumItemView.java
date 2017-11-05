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
import lgj.example.com.biyesheji.model.AlbumBean;


/**
 * Created by yu on 2017/10/9.
 */

public class AlbumItemView extends RelativeLayout {

    @BindView(R.id.first_image_show)
    ImageView mFirstImageShow;
    @BindView(R.id.photos_title)
    TextView mPhotosTitle;
    @BindView(R.id.photos_quantity)
    TextView mPhotosQuantity;

    public AlbumItemView(Context context) {
        this(context, null);
    }

    public AlbumItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),
                getDefaultSize(0, heightMeasureSpec));

        int childWidthSize = getMeasuredWidth();
        // 高度和宽度一样
        heightMeasureSpec = widthMeasureSpec = MeasureSpec.makeMeasureSpec(
                childWidthSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_album, this);
        ButterKnife.bind(this,this);
    }


    public void bindView(AlbumBean imageItemViewInfo) {
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
