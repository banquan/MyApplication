package lgj.example.com.biyesheji.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import lgj.example.com.biyesheji.R;
import lgj.example.com.biyesheji.adapter.PhotoClassAdapter;
import lgj.example.com.biyesheji.model.ClassBean;
import lgj.example.com.biyesheji.model.MyUser;


/**
 * Created by yhdj on 2017/9/27.
 */

public class PhotoFragment extends Fragment {

private RecyclerView mRecycleViewAlbumClass;
    private PhotoClassAdapter mPhotoClassAdapter;
    private List<ClassBean> mClassBeen = new ArrayList<>();
    private List<String> classIds = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        mRecycleViewAlbumClass = (RecyclerView) view.findViewById(R.id.recycle_photo);
        initData();
        mPhotoClassAdapter = new PhotoClassAdapter(mClassBeen,getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecycleViewAlbumClass.setLayoutManager(layoutManager);
        mRecycleViewAlbumClass.setAdapter(mPhotoClassAdapter);
        mPhotoClassAdapter.notifyDataSetChanged();
        return view;
    }



    public void initData() {
        //查询当前用户所加入的班级
        JSONArray ids = (JSONArray) MyUser.getObjectByKey("classIds");
        if (ids == null){
            return;
        }
        classIds = dealIds(ids);

        BmobQuery<ClassBean> query = new BmobQuery<ClassBean>();
        query.addWhereContainedIn("objectId",classIds);
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(1000);
        //执行查询方法
        query.findObjects(new FindListener<ClassBean>() {
            @Override
            public void done(List<ClassBean> object, BmobException e) {
                if (e == null) {

                    for (ClassBean classBean : object) {
                        mClassBeen.add(classBean);
                }
                   mPhotoClassAdapter.changeData(mClassBeen);
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    //解析班级id，将jsonArray组装成list
    private List<String> dealIds(JSONArray ids) {
        List<String> idList = new ArrayList<>();
        for (int i = 0; i < ids.length(); i++){
            try {
                idList.add(ids.get(i).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return idList;
    }

}
