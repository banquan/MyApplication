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
import lgj.example.com.biyesheji.adapter.ChildTwoAdpater;
import lgj.example.com.biyesheji.event.Event;
import lgj.example.com.biyesheji.model.Picture;
import lgj.example.com.biyesheji.presenter.ChildTwoPresenter;
import lgj.example.com.biyesheji.presenter.impl.ChildTwoPresenterImpl;
import lgj.example.com.biyesheji.view.ChildTwoView;


/**
 * Created by yu on 2017/10/18.
 */

public class ChildTwoFragment extends Fragment implements ChildTwoView {
    private RecyclerView mRecyclerView;
    private ChildTwoPresenter mChildTwoPresenter;
    private ArrayList<Picture> mArrayList = new ArrayList<>();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_child_two,container,false);

        //mTextView = (TextView) view.findViewById(R.id.child_text);
        //String str =getArguments().getString("key");
        //mTextView.setText(str+"-=-=");
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        initSwipeRefreshLayout();

        Log.e("+++++++++","ChildTwoFragment   onCreateView");

        mChildTwoPresenter.getPictureList();//展示数据

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
            mChildTwoPresenter.getPictureList();
            mSwipeRefreshLayout.setRefreshing(false);
        }
    };

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ChildTwoAdpater mChildTwoAdpater = new ChildTwoAdpater(getContext(), mArrayList);
        mRecyclerView.setAdapter(mChildTwoAdpater);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e("+++++++++","ChildTwoFragment   onCreate");
        mChildTwoPresenter = new ChildTwoPresenterImpl(this);


        //注册EventBus
        EventBus.getDefault().register(this);
    }

    @Override
    public void getPictureListSuccess(ArrayList<Picture> arrayList) {
        Toast.makeText(getContext(), "获取Picture成功", Toast.LENGTH_SHORT).show();
        mArrayList = arrayList;
        initRecyclerView();
    }

    @Override
    public void getDocumentListFailed() {
        Toast.makeText(getContext(), "获取Picture失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("ChildTwoFragment","onDestroy");
        EventBus.getDefault().unregister(this);//反注册EventBus
    }

    @Subscribe
    public void onEvent(Event event) {
        String msg = "onEventMainThread收到了消息：" + event.getMsg();
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        mChildTwoPresenter.getPictureList();
    }
}
