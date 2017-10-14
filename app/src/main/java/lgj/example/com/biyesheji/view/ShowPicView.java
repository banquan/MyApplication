package lgj.example.com.biyesheji.view;

import java.util.ArrayList;

import lgj.example.com.biyesheji.model.AllPicInfo;


/**
 * Created by yu on 2017/10/11.
 */

public interface ShowPicView {

    void uploadPicSuccess();

    void saveAllPicInfoSuccess();


    void getAllPicInfoListSuccess(ArrayList<AllPicInfo> arr);

    void getListSuccess(ArrayList<AllPicInfo> arr);

    void uploadPicProgress(Integer value);

    void showProgressBar();
}
