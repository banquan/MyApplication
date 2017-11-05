package lgj.example.com.biyesheji.view;

import java.util.ArrayList;

import lgj.example.com.biyesheji.model.Other;


/**
 * Created by yu on 2017/10/19.
 */

public interface ChildFiveView {
    void test();

    void getOtherListSuccess(ArrayList<Other> arrayList);

    void getOtherListFailed();
}
