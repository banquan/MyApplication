package lgj.example.com.biyesheji.ui.fragment;

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
import lgj.example.com.biyesheji.adapter.ChildFiveAdpater;
import lgj.example.com.biyesheji.event.Event;
import lgj.example.com.biyesheji.model.Other;
import lgj.example.com.biyesheji.presenter.ChildFivePresenter;
import lgj.example.com.biyesheji.presenter.impl.ChildFivePresenterImpl;
import lgj.example.com.biyesheji.view.ChildFiveView;


/**
 * Created by yu on 2017/10/18.
 */

public class ChildFiveFragment extends Fragment implements ChildFiveView {
    private ChildFivePresenter mChildFivePresenter;
    private RecyclerView mRecyclerView;
    private ArrayList<Other> mArrayList = new ArrayList<>();
    private ChildFiveAdpater mChildFiveAdpater;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_child_five, container, false);

        //mTextView = (TextView) view.findViewById(R.id.child_text);
        //String str =getArguments().getString("key");
        //mTextView.setText(str+"-=-=");

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        initSwipeRefreshLayout();



        Log.e("+++++++++", "ChildFiveFragment   onCreateView");
        mChildFivePresenter.getOtherList();//展示数据

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
            mChildFivePresenter.getOtherList();
            mSwipeRefreshLayout.setRefreshing(false);
        }
    };

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mChildFiveAdpater = new ChildFiveAdpater(getContext(), mArrayList);
        mRecyclerView.setAdapter(mChildFiveAdpater);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("+++++++++", "ChildFiveFragment   onCreate");

        mChildFivePresenter = new ChildFivePresenterImpl(this);

        //注册EventBus
        EventBus.getDefault().register(this);
    }

    @Override
    public void test() {
        Toast.makeText(getContext(), "测试MVP", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getOtherListSuccess(ArrayList<Other> arrayList) {
        Toast.makeText(getContext(), "获取Other成功", Toast.LENGTH_SHORT).show();
        mArrayList = arrayList;
        initRecyclerView();
    }

    @Override
    public void getOtherListFailed() {
        Toast.makeText(getContext(), "获取Other失败", Toast.LENGTH_SHORT).show();
    }


    public void onDestroy() {
        super.onDestroy();
        Log.e("ChildFiveFragment","onDestroy");
        EventBus.getDefault().unregister(this);//反注册EventBus
    }

    @Subscribe
    public void onEvent(Event event) {
        String msg = "onEventMainThread收到了消息：" + event.getMsg();
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        mChildFivePresenter.getOtherList();
    }
}
