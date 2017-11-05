package lgj.example.com.biyesheji.presenter.impl;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import lgj.example.com.biyesheji.model.Document;
import lgj.example.com.biyesheji.model.Music;
import lgj.example.com.biyesheji.model.Other;
import lgj.example.com.biyesheji.model.Picture;
import lgj.example.com.biyesheji.model.Video;
import lgj.example.com.biyesheji.presenter.MainFilePresenter;
import lgj.example.com.biyesheji.view.MainFileView;

/**
 * Created by yu on 2017/10/19.
 */

public class MainFilePresenterImpl implements MainFilePresenter {
    private MainFileView mMainView;

    public MainFilePresenterImpl(MainFileView mainView) {
        mMainView = mainView;
    }

    @Override
    public void test() {
        mMainView.test();
    }

    @Override
    public void uploadDocument(String path, final String mName, final String fileSize, final String mType) {
        if(path == null){
            return;
        }
        final BmobFile bmobFile = new BmobFile(new File(path));
        bmobFile.uploadblock(new UploadFileListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){

                    saveDocument(bmobFile,mName,fileSize,mType);
                }else{

                }

            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
                mMainView.uploadblockSuccess(value);
            }
        });

    }

    @Override
    public void uploadPicture(String path, final String mName, final String fileSize, final String mType) {
        if(path == null){
            return;
        }
        final BmobFile bmobFile = new BmobFile(new File(path));
        bmobFile.uploadblock(new UploadFileListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){

                    savePicture(bmobFile,mName,fileSize,mType);
                }else{

                }

            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
                mMainView.uploadblockSuccess(value);
            }
        });
    }

    @Override
    public void uploadOther(String path, final String mName, final String fileSize, final String mType) {
        if(path == null){
            return;
        }
        final BmobFile bmobFile = new BmobFile(new File(path));
        bmobFile.uploadblock(new UploadFileListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){

                    saveOther(bmobFile,mName,fileSize,mType);
                }else{

                }

            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
                mMainView.uploadblockSuccess(value);
            }
        });
    }

    @Override
    public void uploadMusic(String path, final String mName, final String fileSize, final String mType) {
        if(path == null){
            return;
        }
        final BmobFile bmobFile = new BmobFile(new File(path));
        bmobFile.uploadblock(new UploadFileListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){

                    saveMusic(bmobFile,mName,fileSize,mType);
                }else{

                }

            }


            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
                mMainView.uploadblockSuccess(value);
            }
        });
    }

    @Override
    public void uploadVideo(String path, final String mName, final String fileSize, final String mType) {
        if(path == null){
            return;
        }
        final BmobFile bmobFile = new BmobFile(new File(path));
        bmobFile.uploadblock(new UploadFileListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){

                    saveVideo(bmobFile,mName,fileSize,mType);
                }else{

                }

            }


            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
                mMainView.uploadblockSuccess(value);
            }
        });
    }

    private void saveVideo(BmobFile bmobFile, String mName, String fileSize, String mType) {
        Video mVideo = new Video();
        //注意：不能调用gameScore.setObjectId("")方法
        mVideo.setBmobFile(bmobFile);
        mVideo.setVideoName(mName);
        mVideo.setVideoSize(fileSize);
        mVideo.setVideoType(mType);
        mVideo.save(new SaveListener<String>() {

            @Override
            public void done(String objectId, BmobException e) {
                if(e==null){
                    mMainView.saveVideoSuccess();
                }else{

                }
            }
        });
    }

    private void saveMusic(BmobFile bmobFile, String mName, String fileSize, String mType) {
        Music mMusic = new Music();
        //注意：不能调用gameScore.setObjectId("")方法
        mMusic.setBmobFile(bmobFile);
        mMusic.setMusicName(mName);
        mMusic.setMusicSize(fileSize);
        mMusic.setMusicType(mType);
        mMusic.save(new SaveListener<String>() {

            @Override
            public void done(String objectId, BmobException e) {
                if(e==null){
                    mMainView.saveMusicSuccess();
                }else{

                }
            }
        });
    }

    private void saveOther(BmobFile bmobFile, String mName, String fileSize, String mType) {
        Other mOther = new Other();
        //注意：不能调用gameScore.setObjectId("")方法
        mOther.setBmobFile(bmobFile);
        mOther.setOtherName(mName);
        mOther.setOtherSize(fileSize);
        mOther.setOtherType(mType);
        mOther.save(new SaveListener<String>() {

            @Override
            public void done(String objectId, BmobException e) {
                if(e==null){
                    mMainView.saveOtherSuccess();
                }else{

                }
            }
        });
    }

    private void savePicture(BmobFile bmobFile, String mName, String fileSize, String mType) {
        Picture mPicture = new Picture();
        //注意：不能调用gameScore.setObjectId("")方法
        mPicture.setBmobFile(bmobFile);
        mPicture.setPicName(mName);
        mPicture.setPicSize(fileSize);
        mPicture.setPicType(mType);
        mPicture.save(new SaveListener<String>() {

            @Override
            public void done(String objectId, BmobException e) {
                if(e==null){
                    mMainView.savePictureSuccess();
                }else{

                }
            }
        });
    }

    private void saveDocument(BmobFile bmobFile, String mName, String fileSize, String mType) {
        Document mDocument = new Document();
        //注意：不能调用gameScore.setObjectId("")方法
        mDocument.setBmobFile(bmobFile);
        mDocument.setDocumentName(mName);
        mDocument.setDocumentSize(fileSize);
        mDocument.setDocumentType(mType);
        mDocument.save(new SaveListener<String>() {

            @Override
            public void done(String objectId, BmobException e) {
                if(e==null){
                   mMainView.saveDocumentSuccess();
                }else{

                }
            }
        });
    }
}
