package lgj.example.com.biyesheji.view;

import java.util.ArrayList;

import lgj.example.com.biyesheji.model.Picture;


/**
 * Created by yu on 2017/10/19.
 */

public interface ChildTwoView {
    void getPictureListSuccess(ArrayList<Picture> arrayList);

    void getDocumentListFailed();
}
