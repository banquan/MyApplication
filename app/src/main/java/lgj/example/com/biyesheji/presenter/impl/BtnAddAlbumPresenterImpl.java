package lgj.example.com.biyesheji.presenter.impl;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import lgj.example.com.biyesheji.model.ImageItemViewInfo;
import lgj.example.com.biyesheji.presenter.BtnAddAlbumPresenter;
import lgj.example.com.biyesheji.view.BtnAddAlbumView;


/**
 * Created by yu on 2017/10/9.
 */

public class BtnAddAlbumPresenterImpl implements BtnAddAlbumPresenter {

    private BtnAddAlbumView mBtnAddAlbumView;
    private ArrayList<ImageItemViewInfo> mImageItemViewInfos;

    public BtnAddAlbumPresenterImpl(BtnAddAlbumView btnAddAlbumView) {
        mBtnAddAlbumView = btnAddAlbumView;
        mImageItemViewInfos = new ArrayList<>();
    }


    @Override
    public void UploadToBmob(String title) {
        ImageItemViewInfo photos = new ImageItemViewInfo();
        photos.setTitle(title);
        photos.save(mSaveListener);

    }

    @Override
    public List<ImageItemViewInfo> getList() {

        //mImageItemViewInfos.clear();

        BmobQuery<ImageItemViewInfo> query = new BmobQuery<ImageItemViewInfo>();
        //query.setLimit(50);
        query.findObjects(mFindListener);

        return mImageItemViewInfos;
    }

    @Override
    public void isExistedAlbumName(final String s) {
        BmobQuery<ImageItemViewInfo> query = new BmobQuery<>();
       query.findObjects(new FindListener<ImageItemViewInfo>() {
           @Override
           public void done(List<ImageItemViewInfo> list, BmobException e) {
               for (ImageItemViewInfo info : list){
                   if (info.getTitle().equals(s)){
                       mBtnAddAlbumView.showExistAlbumName();
                   }
               }
           }
       });
    }

    SaveListener<String> mSaveListener = new SaveListener<String>() {
        @Override
        public void done(String s, BmobException e) {
            if(e == null){
                if(e==null){
                    mBtnAddAlbumView.UploadToBmobSuccess();
                }else{
                    mBtnAddAlbumView.UploadToBmobFailed();
                }
            }
        }
    };

    FindListener<ImageItemViewInfo> mFindListener = new FindListener<ImageItemViewInfo>() {
        @Override
        public void done(List<ImageItemViewInfo> list, BmobException e) {
            if(e==null){
                for(ImageItemViewInfo i : list){
                    Log.d("ysw",i.getTitle());
                    ImageItemViewInfo mImageItemViewInfo = new ImageItemViewInfo();
                    mImageItemViewInfo.setShow_first_image(i.getShow_first_image());
                    mImageItemViewInfo.setTitle(i.getTitle());
                    mImageItemViewInfo.setQuantity(i.getQuantity());
                    mImageItemViewInfos.add(mImageItemViewInfo);
                }
                mBtnAddAlbumView.getListSuccess();
            }else{
                Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                mBtnAddAlbumView.getListFailed();
            }
        }
    };

}
