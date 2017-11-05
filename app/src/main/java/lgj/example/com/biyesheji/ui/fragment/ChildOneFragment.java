package lgj.example.com.biyesheji.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import lgj.example.com.biyesheji.R;
import lgj.example.com.biyesheji.adapter.ChildOneAdpater;
import lgj.example.com.biyesheji.event.Event;
import lgj.example.com.biyesheji.model.Document;
import lgj.example.com.biyesheji.presenter.ChildOnePresenter;
import lgj.example.com.biyesheji.presenter.impl.ChildOnePresenterImpl;
import lgj.example.com.biyesheji.view.ChildOneView;


/**
 * Created by yu on 2017/10/18.
 */

public class ChildOneFragment extends Fragment implements ChildOneView {

    private RecyclerView mRecyclerView;
    private ChildOnePresenter mChildOnePresenter;
    private ArrayList<Document> mArrayList = new ArrayList<>();
    private ChildOneAdpater mChildOneAdpater;
    private SwipeRefreshLayout mSwipeRefreshLayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_child_one, container, false);

        //mTextView = (TextView) view.findViewById(R.id.child_text);
        //String str =getArguments().getString("key");
        //mTextView.setText(str+"成功阿~~~~");

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        initSwipeRefreshLayout();

        Log.e("+++++++++", "ChildOneFragment  onCreateView");
        mChildOnePresenter.getDocumentList();//展示数据

        return view;
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.qq_color, R.color.colorAccent, R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
    }

    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            Toast.makeText(getContext(), "刷新啦~~~", Toast.LENGTH_SHORT).show();
            mChildOnePresenter.getDocumentList();
            mSwipeRefreshLayout.setRefreshing(false);
        }
    };

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mChildOneAdpater = new ChildOneAdpater(getContext(), mArrayList);
        mRecyclerView.setAdapter(mChildOneAdpater);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("+++++++++", "ChildOneFragment  onCreate");

        //初始化ChildOnePresenter层
        mChildOnePresenter = new ChildOnePresenterImpl(this);


        //注册EventBus
        EventBus.getDefault().register(this);

    }

    @Override
    public void getDocumentListSuccess(ArrayList<Document> arrayList) {
        Toast.makeText(getContext(), "获取Document成功", Toast.LENGTH_SHORT).show();
        mArrayList = arrayList;
        initRecyclerView();
    }

    @Override
    public void getDocumentListFailed() {
        Toast.makeText(getContext(), "获取Document失败", Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("ChildOneFragment","onAttach");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("ChildOneFragment","onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("ChildOneFragment","onStart");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("ChildOneFragment","onResume");

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("ChildOneFragment","onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("ChildOneFragment","onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("ChildOneFragment","onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("ChildOneFragment","onDestroy");
        EventBus.getDefault().unregister(this);//反注册EventBus
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("ChildOneFragment","onDetach");
    }

    @Subscribe
    public void onEvent(Event event) {
        String msg = "onEventMainThread收到了消息：" + event.getMsg();
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        mChildOnePresenter.getDocumentList();
    }

//    10-19 13:56:32.766 14857-14857/cn.tablayouttest E/ChildOneFragment: onAttach
//    10-19 13:56:32.766 14857-14857/cn.tablayouttest E/ChildOneFragment: onCreate
//    10-19 13:56:32.826 14857-14857/cn.tablayouttest E/ChildOneFragment: onCreateView
//    10-19 13:56:32.826 14857-14857/cn.tablayouttest E/ChildOneFragment: onActivityCreated
//    10-19 13:56:32.826 14857-14857/cn.tablayouttest E/ChildOneFragment: onStart
//    10-19 13:56:32.826 14857-14857/cn.tablayouttest E/ChildOneFragment: onResume
//
//    10-19 13:56:45.301 14857-14857/cn.tablayouttest E/ChildOneFragment: onPause
//    10-19 13:56:46.076 14857-14857/cn.tablayouttest E/ChildOneFragment: onStop
//
//    10-19 13:57:20.931 14857-14857/cn.tablayouttest E/ChildOneFragment: onStart
//    10-19 13:57:20.931 14857-14857/cn.tablayouttest E/ChildOneFragment: onResume

}
