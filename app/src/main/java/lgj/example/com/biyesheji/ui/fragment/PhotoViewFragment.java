package lgj.example.com.biyesheji.ui.fragment;

import android.util.Log;

import com.squareup.picasso.Picasso;

import lgj.example.com.biyesheji.R;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;


/**
 * Created by yu on 2017/10/14.
 */

public class PhotoViewFragment extends BaseFragment {
    private String mImgUrl;
    private PhotoView mPhotoView;
    private PhotoViewAttacher mPhotoViewAttacher;

    public void setImgUrl(String url) {
        this.mImgUrl = url;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_one_pic;
    }

    @Override
    public void initView() {
        mPhotoView = (PhotoView) super.mRootView.findViewById(R.id.one_pic_show);
        mPhotoViewAttacher = new PhotoViewAttacher(mPhotoView);
        Picasso.with(getActivity()).load(mImgUrl).into(mPhotoView);
        Log.e("aaaaaaaaaaa", "initView: " + mImgUrl + "         ");
    }


    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }
}
