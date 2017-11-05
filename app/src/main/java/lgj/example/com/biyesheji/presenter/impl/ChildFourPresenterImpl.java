package lgj.example.com.biyesheji.presenter.impl;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import lgj.example.com.biyesheji.model.Music;
import lgj.example.com.biyesheji.presenter.ChildFourPresenter;
import lgj.example.com.biyesheji.view.ChildFourView;


/**
 * Created by yu on 2017/10/19.
 */

public class ChildFourPresenterImpl implements ChildFourPresenter {
    private ChildFourView mChildFourView;
    private ArrayList<Music> mArrayList = new ArrayList<>();

    public ChildFourPresenterImpl(ChildFourView childFourView) {
        mChildFourView = childFourView;
    }

    @Override
    public void getMusicList() {
        mArrayList.clear();
        BmobQuery<Music> query = new BmobQuery<Music>();
        query.setLimit(50);
        query.findObjects(new FindListener<Music>() {
            @Override
            public void done(List<Music> object, BmobException e) {
                if(e==null){
                    for (Music gameScore : object) {
                        Music mMusic = new Music();
                        mMusic.setBmobFile(gameScore.getBmobFile());
                        mMusic.setMusicName(gameScore.getMusicName());
                        mMusic.setMusicSize(gameScore.getMusicSize());
                        mMusic.setMusicTime(gameScore.getUpdatedAt());
                        mMusic.setMusicType(gameScore.getMusicType());
                        mMusic.setOid(gameScore.getObjectId());
                        mArrayList.add(mMusic);
                    }
                    mChildFourView.getMusicListSuccess(mArrayList);
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                    mChildFourView.getMusicListFailed();
                }
            }
        });
    }
}
