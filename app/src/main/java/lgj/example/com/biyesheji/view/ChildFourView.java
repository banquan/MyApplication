package lgj.example.com.biyesheji.view;

import java.util.ArrayList;

import lgj.example.com.biyesheji.model.Music;


/**
 * Created by yu on 2017/10/19.
 */

public interface ChildFourView {
    void getMusicListSuccess(ArrayList<Music> arrayList);

    void getMusicListFailed();
}
