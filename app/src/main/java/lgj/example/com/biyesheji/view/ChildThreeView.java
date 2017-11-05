package lgj.example.com.biyesheji.view;

import java.util.ArrayList;

import lgj.example.com.biyesheji.model.Video;


/**
 * Created by yu on 2017/10/19.
 */

public interface ChildThreeView {
    void getVideoListSuccess(ArrayList<Video> arrayList);

    void getVideoListFailed();
}
