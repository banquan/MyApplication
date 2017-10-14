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
import lgj.example.com.biyesheji.model.AllPicInfo;
import lgj.example.com.biyesheji.presenter.ShowPicPresenter;
import lgj.example.com.biyesheji.view.ShowPicView;


/**
 * Created by yu on 2017/10/11.
 */

public class ShowPicPresenterImpl implements ShowPicPresenter {
    private ShowPicView mShowPicView;
    private BmobFile mBmobFile;
    private ArrayList<AllPicInfo> arr = new ArrayList<>();
    private String albumName = null;

    public ShowPicPresenterImpl(ShowPicView showPicView) {
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

        BmobQuery<AllPicInfo> query = new BmobQuery<AllPicInfo>();

        query.addWhereEqualTo("albumName", s);

        query.findObjects(mFindListener);

    }

    @Override
    public void getList(String s) {
        BmobQuery<AllPicInfo> query = new BmobQuery<AllPicInfo>();

        query.addWhereEqualTo("albumName", s);

        query.findObjects(mRefreshListener);
    }

    FindListener<AllPicInfo> mRefreshListener = new FindListener<AllPicInfo>() {
        @Override
        public void done(List<AllPicInfo> object, BmobException e) {
            if (e == null) {
                Log.d("+++++++++++++++++++++++", "start：");
                arr.clear();
                for (AllPicInfo list : object) {
                    //获得playerName的信息
                    BmobFile pic = list.getPic();
                    String albumName = list.getAlbumName();
                    AllPicInfo api = new AllPicInfo();
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


    FindListener<AllPicInfo> mFindListener = new FindListener<AllPicInfo>() {
        @Override
        public void done(List<AllPicInfo> object, BmobException e) {
            if (e == null) {
                arr.clear();
                Log.d("55555555555555555", "成功start");
                for (AllPicInfo list : object) {
                    //获得playerName的信息
                    BmobFile pic = list.getPic();
                    String albumName = list.getAlbumName();
                    AllPicInfo api = new AllPicInfo();
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

        AllPicInfo api = new AllPicInfo();
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
