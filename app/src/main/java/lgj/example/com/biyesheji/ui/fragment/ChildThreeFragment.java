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
import lgj.example.com.biyesheji.adapter.ChildThreeAdpater;
import lgj.example.com.biyesheji.event.Event;
import lgj.example.com.biyesheji.model.Video;
import lgj.example.com.biyesheji.presenter.ChildThreePresenter;
import lgj.example.com.biyesheji.presenter.impl.ChildThreePresenterImpl;
import lgj.example.com.biyesheji.view.ChildThreeView;


/**
 * Created by yu on 2017/10/18.
 */

public class ChildThreeFragment extends Fragment implements ChildThreeView {

    private RecyclerView mRecyclerView;
    private ChildThreePresenter mChildThreePresenter;
    private ArrayList<Video> mArrayList = new ArrayList<>();
    private ChildThreeAdpater mChildThreeAdpater;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_child_three,container,false);

        //mTextView = (TextView) view.findViewById(R.id.child_text);
        //String str =getArguments().getString("key");
        //mTextView.setText(str+"-=-=");

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        initSwipeRefreshLayout();

        Log.e("+++++++++","ChildThreeFragment   onCreateView");

        mChildThreePresenter.getVideoList();//展示数据

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
            mChildThreePresenter.getVideoList();
            mSwipeRefreshLayout.setRefreshing(false);
        }
    };

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mChildThreeAdpater = new ChildThreeAdpater(getContext(), mArrayList);
        mRecyclerView.setAdapter(mChildThreeAdpater);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e("+++++++++","ChildThreeFragment   onCreate");
        mChildThreePresenter = new ChildThreePresenterImpl(this);



        //注册EventBus
        EventBus.getDefault().register(this);
    }

    @Override
    public void getVideoListSuccess(ArrayList<Video> arrayList) {
        Toast.makeText(getContext(), "获取Video成功", Toast.LENGTH_SHORT).show();
        mArrayList = arrayList;
        initRecyclerView();
    }

    @Override
    public void getVideoListFailed() {
        Toast.makeText(getContext(), "获取Video失败", Toast.LENGTH_SHORT).show();
    }

    public void onDestroy() {
        super.onDestroy();
        Log.e("ChildThreeFragment","onDestroy");
        EventBus.getDefault().unregister(this);//反注册EventBus
    }

    @Subscribe
    public void onEvent(Event event) {
        String msg = "onEventMainThread收到了消息：" + event.getMsg();
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        mChildThreePresenter.getVideoList();
    }
}
