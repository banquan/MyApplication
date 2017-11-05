package lgj.example.com.biyesheji.presenter.impl;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import lgj.example.com.biyesheji.model.Picture;
import lgj.example.com.biyesheji.presenter.ChildTwoPresenter;
import lgj.example.com.biyesheji.view.ChildTwoView;


/**
 * Created by yu on 2017/10/19.
 */

public class ChildTwoPresenterImpl implements ChildTwoPresenter {
    private ChildTwoView mChildTwoView;
    private ArrayList<Picture> mArrayList = new ArrayList<>();

    public ChildTwoPresenterImpl(ChildTwoView childTwoView) {
        mChildTwoView = childTwoView;
    }

    @Override
    public void getPictureList() {
        mArrayList.clear();
        BmobQuery<Picture> query = new BmobQuery<Picture>();
        query.setLimit(50);
        query.findObjects(new FindListener<Picture>() {
            @Override
            public void done(List<Picture> object, BmobException e) {
                if(e==null){
                    for (Picture gameScore : object) {
                        Picture mPicture = new Picture();
                        mPicture.setBmobFile(gameScore.getBmobFile());
                        mPicture.setPicName(gameScore.getPicName());
                        mPicture.setPicSize(gameScore.getPicSize());

                        Log.e("getPictureList",gameScore.getUpdatedAt() + " ");

                        mPicture.setPicTime(gameScore.getUpdatedAt());
                        mPicture.setPicType(gameScore.getPicType());
                        mPicture.setOid(gameScore.getObjectId());

                        mArrayList.add(mPicture);
                    }
                    mChildTwoView.getPictureListSuccess(mArrayList);
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                    mChildTwoView.getDocumentListFailed();
                }
            }
        });
    }
}
