package lgj.example.com.biyesheji.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import lgj.example.com.biyesheji.R;
import lgj.example.com.biyesheji.ui.fragment.PhotoViewFragment;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class OnePicActivity extends BaseActivity {

    private String[] imgUrl;
    private PhotoView mPhotoView;
    private PhotoViewAttacher mPhotoViewAttacher;
    private ViewPager mViewPager;
    private List<Fragment> mFragments = new ArrayList<>();
    private int index = -1;

    @Override
    protected void initData() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void initView() {
        initViewPager();
    }

    private void initViewPager() {
        imgUrl = getIntent().getStringArrayExtra("imgUrl");
        index = getIntent().getIntExtra("index",-1);
        mViewPager = (ViewPager) findViewById(R.id.img_pager);

        // Picasso.with(this).load(imgUrl).into(mImageView);
        for (int i = 0; i < imgUrl.length; i++) {
            PhotoViewFragment photoViewFragment = new PhotoViewFragment();
            photoViewFragment.setImgUrl(imgUrl[i]);
            mFragments.add(photoViewFragment);
        }

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return imgUrl.length;
            }
        });
        mViewPager.setCurrentItem(index);

    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_one_pic;
    }
//
//    @BindView(R.id.one_pic_objectId)
//    TextView mOnePicObjectId;
//    @BindView(R.id.one_pic_show)
//    PhotoView mOnePicShow;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_one_pic);
//        ButterKnife.bind(this);
//
//        init();
//    }
//
//    private void init() {
//        getIntentFromShowPicAdater();
//    }
//
//    private void getIntentFromShowPicAdater() {
//        Intent intent = getIntent();
//        String url = intent.getStringExtra("url");
//        //String objectId = intent.getStringExtra("objectId");
//
//        Glide.with(this).load(url).into(mOnePicShow);
//        //mOnePicObjectId.setText(objectId);
//
//
//    }
//



}
