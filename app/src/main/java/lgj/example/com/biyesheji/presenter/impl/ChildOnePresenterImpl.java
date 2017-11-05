package lgj.example.com.biyesheji.presenter.impl;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import lgj.example.com.biyesheji.model.Document;
import lgj.example.com.biyesheji.presenter.ChildOnePresenter;
import lgj.example.com.biyesheji.view.ChildOneView;


/**
 * Created by yu on 2017/10/19.
 */

public class ChildOnePresenterImpl implements ChildOnePresenter {
    private ChildOneView mChildOneView;
    private ArrayList<Document> mArrayList = new ArrayList<>();

    public ChildOnePresenterImpl(ChildOneView childOneView) {
        mChildOneView = childOneView;
    }

    @Override
    public void getDocumentList() {
        mArrayList.clear();
        BmobQuery<Document> query = new BmobQuery<Document>();
        query.setLimit(50);
        query.findObjects(new FindListener<Document>() {
            @Override
            public void done(List<Document> object, BmobException e) {
                if(e==null){
                    for (Document gameScore : object) {
                        Document mDocument = new Document();
                        mDocument.setBmobFile(gameScore.getBmobFile());
                        mDocument.setDocumentName(gameScore.getDocumentName());
                        mDocument.setDocumentSize(gameScore.getDocumentSize());
                        mDocument.setDocumentTime(gameScore.getUpdatedAt());
                        mDocument.setDocumentType(gameScore.getDocumentType());
                        mDocument.setOid(gameScore.getObjectId());
                        mArrayList.add(mDocument);
                    }
                    mChildOneView.getDocumentListSuccess(mArrayList);
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                    mChildOneView.getDocumentListFailed();
                }
            }
        });
    }

}
