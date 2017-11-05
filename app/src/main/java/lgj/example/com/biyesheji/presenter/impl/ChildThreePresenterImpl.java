package lgj.example.com.biyesheji.presenter.impl;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import lgj.example.com.biyesheji.model.Video;
import lgj.example.com.biyesheji.presenter.ChildThreePresenter;
import lgj.example.com.biyesheji.view.ChildThreeView;


/**
 * Created by yu on 2017/10/19.
 */

public class ChildThreePresenterImpl implements ChildThreePresenter {
    private ChildThreeView mChildThreeView;
    private ArrayList<Video> mArrayList = new ArrayList<>();

    public ChildThreePresenterImpl(ChildThreeView childThreeView) {
        mChildThreeView = childThreeView;
    }

    @Override
    public void getVideoList() {
        mArrayList.clear();
        BmobQuery<Video> query = new BmobQuery<Video>();
        query.setLimit(50);
        query.findObjects(new FindListener<Video>() {
            @Override
            public void done(List<Video> object, BmobException e) {
                if(e==null){
                    for (Video gameScore : object) {
                        Video mVideo = new Video();
                        mVideo.setBmobFile(gameScore.getBmobFile());
                        mVideo.setVideoName(gameScore.getVideoName());
                        mVideo.setVideoSize(gameScore.getVideoSize());
                        mVideo.setVideoTime(gameScore.getUpdatedAt());
                        mVideo.setVideoType(gameScore.getVideoType());
                        mVideo.setOid(gameScore.getObjectId());
                        mArrayList.add(mVideo);
                    }
                    mChildThreeView.getVideoListSuccess(mArrayList);
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                    mChildThreeView.getVideoListFailed();
                }
            }
        });
    }
}
