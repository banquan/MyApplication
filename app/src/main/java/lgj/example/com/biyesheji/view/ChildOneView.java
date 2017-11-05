package lgj.example.com.biyesheji.view;

import java.util.ArrayList;

import lgj.example.com.biyesheji.model.Document;


/**
 * Created by yu on 2017/10/19.
 */

public interface ChildOneView {
    void getDocumentListSuccess(ArrayList<Document> arrayList);

    void getDocumentListFailed();
}
