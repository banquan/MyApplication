package lgj.example.com.biyesheji.presenter.impl;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import lgj.example.com.biyesheji.model.PicInAlbumBean;
import lgj.example.com.biyesheji.presenter.PicInAlbumPresenter;
import lgj.example.com.biyesheji.view.PicInAlbumView;


/**
 * Created by yu on 2017/10/11.
 */

public class PicInAlbumPresenterImpl implements PicInAlbumPresenter {
    private PicInAlbumView mShowPicView;
    private BmobFile mBmobFile;
    private ArrayList<PicInAlbumBean> arr = new ArrayList<>();
    private String albumName = null;

    public PicInAlbumPresenterImpl(PicInAlbumView showPicView) {
        mShowPicView = showPicView;
    }


    @Override
    public void uploadPic(String path, final String title) {
        if (path == null) {
            return;
        }
        albumName = title;
        mBmobFile = new BmobFile(new File(path));
        mBmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {

                    mShowPicView.uploadPicSuccess();

                    //创建方法传递参数。
                    saveAlbumPic(mBmobFile, title);

                } else {

                }

            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
                mShowPicView.showProgressBar();
                mShowPicView.uploadPicProgress(value);
            }
        });

    }

    @Override
    public void getAllPicInfoList(final String s) {

        BmobQuery<PicInAlbumBean> query = new BmobQuery<PicInAlbumBean>();

        query.addWhereEqualTo("albumName", s);

        query.findObjects(mFindListener);

    }

    @Override
    public void getList(String s) {
        BmobQuery<PicInAlbumBean> query = new BmobQuery<PicInAlbumBean>();

        query.addWhereEqualTo("albumName", s);

        query.findObjects(mRefreshListener);
    }

    FindListener<PicInAlbumBean> mRefreshListener = new FindListener<PicInAlbumBean>() {
        @Override
        public void done(List<PicInAlbumBean> object, BmobException e) {
            if (e == null) {
                Log.d("+++++++++++++++++++++++", "start：");
                arr.clear();
                for (PicInAlbumBean list : object) {
                    //获得playerName的信息
                    BmobFile pic = list.getPic();
                    String albumName = list.getAlbumName();
                    PicInAlbumBean api = new PicInAlbumBean();
                    api.setPic(pic);
                    api.setAlbumName(albumName);
                    arr.add(api);
                    Log.d("+++++++++++++++++++++++", "for");
                }
                Log.d("+++++++++++++++++++++++", "end：");
                mShowPicView.getListSuccess(arr);
            } else {
                Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());

            }
        }
    };


    FindListener<PicInAlbumBean> mFindListener = new FindListener<PicInAlbumBean>() {
        @Override
        public void done(List<PicInAlbumBean> object, BmobException e) {
            if (e == null) {
                arr.clear();
                Log.d("55555555555555555", "成功start");
                for (PicInAlbumBean list : object) {
                    //获得playerName的信息
                    BmobFile pic = list.getPic();
                    String albumName = list.getAlbumName();
                    PicInAlbumBean api = new PicInAlbumBean();
                    api.setPic(pic);
                    api.setAlbumName(albumName);
                    arr.add(api);
                }
                Log.d("55555555555555555", String.valueOf(arr.size()));
                Log.d("55555555555555555", "成功end");
                mShowPicView.getAllPicInfoListSuccess(arr);

            } else {
                Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                Log.d("55555555555555555", "失败：");
            }
        }
    };


    private void saveAlbumPic(BmobFile bmobFile, String title) {

        PicInAlbumBean api = new PicInAlbumBean();
        //注意：不能调用gameScore.setObjectId("")方法
        api.setPic(bmobFile);
        api.setAlbumName(title);
        api.save(new SaveListener<String>() {

            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    //通知view层AllPicInfo保存成功。
                    mShowPicView.saveAllPicInfoSuccess();

                    //在这里刷新，相册里的照片
                    getList(albumName);


                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });

    }


}
