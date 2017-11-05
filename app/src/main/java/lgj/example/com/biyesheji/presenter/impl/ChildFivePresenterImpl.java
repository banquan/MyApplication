package lgj.example.com.biyesheji.presenter.impl;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import lgj.example.com.biyesheji.model.Other;
import lgj.example.com.biyesheji.presenter.ChildFivePresenter;
import lgj.example.com.biyesheji.view.ChildFiveView;


/**
 * Created by yu on 2017/10/19.
 */

public class ChildFivePresenterImpl implements ChildFivePresenter {
    private ChildFiveView mChildFiveView;
    private ArrayList<Other> mArrayList = new ArrayList<>();

    public ChildFivePresenterImpl(ChildFiveView childFiveView) {
        mChildFiveView = childFiveView;
    }

    @Override
    public void getOtherList() {
        mArrayList.clear();
        BmobQuery<Other> query = new BmobQuery<Other>();
        query.setLimit(50);
        query.findObjects(new FindListener<Other>() {
            @Override
            public void done(List<Other> object, BmobException e) {
                if(e==null){
                    for (Other gameScore : object) {
                        Other mOther = new Other();
                        mOther.setBmobFile(gameScore.getBmobFile());
                        mOther.setOtherName(gameScore.getOtherName());
                        mOther.setOtherSize(gameScore.getOtherSize());
                        mOther.setOtherTime(gameScore.getUpdatedAt());
                        mOther.setOtherType(gameScore.getOtherType());
                        mOther.setOid(gameScore.getObjectId());
                        mArrayList.add(mOther);
                    }
                    mChildFiveView.getOtherListSuccess(mArrayList);
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                    mChildFiveView.getOtherListFailed();
                }
            }
        });
    }
}
