package lgj.example.com.biyesheji.presenter.impl;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import lgj.example.com.biyesheji.model.AlbumBean;
import lgj.example.com.biyesheji.presenter.AlbumPresenter;
import lgj.example.com.biyesheji.view.AlbumView;


/**
 * Created by yu on 2017/10/9.
 */

public class AlbumPresenterImpl implements AlbumPresenter {

    private AlbumView mBtnAddAlbumView;
    private ArrayList<AlbumBean> mImageItemViewInfos;

    public AlbumPresenterImpl(AlbumView btnAddAlbumView) {
        mBtnAddAlbumView = btnAddAlbumView;
        mImageItemViewInfos = new ArrayList<>();
    }


    @Override
    public void UploadToBmob(String title,String classId) {
        AlbumBean photos = new AlbumBean();
        photos.setTitle(title);
        photos.setClassId(classId);
        photos.save(mSaveListener);

    }

    @Override
    public List<AlbumBean> getList(String classId) {

        //mImageItemViewInfos.clear();

        BmobQuery<AlbumBean> query = new BmobQuery<AlbumBean>();
        //query.setLimit(50);
        query.addWhereEqualTo("classId",classId);
        query.findObjects(mFindListener);

        return mImageItemViewInfos;
    }

    @Override
    public void isExistedAlbumName(final String s) {
        BmobQuery<AlbumBean> query = new BmobQuery<>();
       query.findObjects(new FindListener<AlbumBean>() {
           @Override
           public void done(List<AlbumBean> list, BmobException e) {
               for (AlbumBean info : list){
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


    FindListener<AlbumBean> mFindListener = new FindListener<AlbumBean>() {
        @Override
        public void done(List<AlbumBean> list, BmobException e) {
            if(e==null){
                for(AlbumBean i : list){
                    Log.d("ysw",i.getTitle());
                    AlbumBean mImageItemViewInfo = new AlbumBean();
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
