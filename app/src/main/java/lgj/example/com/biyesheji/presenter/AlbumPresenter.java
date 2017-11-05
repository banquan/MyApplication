package lgj.example.com.biyesheji.presenter;

import java.util.List;

import lgj.example.com.biyesheji.model.AlbumBean;


/**
 * Created by yu on 2017/10/9.
 */

public interface AlbumPresenter {


    void UploadToBmob(String title,String classId);


    List<AlbumBean> getList(String classId);

    void isExistedAlbumName(String s);



}
