package lgj.example.com.biyesheji.view;

import java.util.ArrayList;

import lgj.example.com.biyesheji.model.PicInAlbumBean;


/**
 * Created by yu on 2017/10/11.
 */

public interface PicInAlbumView {

    void uploadPicSuccess();

    void saveAllPicInfoSuccess();


    void getAllPicInfoListSuccess(ArrayList<PicInAlbumBean> arr);

    void getListSuccess(ArrayList<PicInAlbumBean> arr);

    void uploadPicProgress(Integer value);

    void showProgressBar();
}
