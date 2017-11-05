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
import lgj.example.com.biyesheji.adapter.ChildFourAdpater;
import lgj.example.com.biyesheji.event.Event;
import lgj.example.com.biyesheji.model.Music;
import lgj.example.com.biyesheji.presenter.ChildFourPresenter;
import lgj.example.com.biyesheji.presenter.impl.ChildFourPresenterImpl;
import lgj.example.com.biyesheji.view.ChildFourView;


/**
 * Created by yu on 2017/10/18.
 */

public class ChildFourFragment extends Fragment implements ChildFourView {

    private RecyclerView mRecyclerView;
    private ChildFourPresenter mChildFourPresenter;
    private ArrayList<Music> mArrayList = new ArrayList<>();
    private ChildFourAdpater mChildFourAdpater;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_child_four,container,false);

        //mTextView = (TextView) view.findViewById(R.id.child_text);
        //String str =getArguments().getString("key");
        //mTextView.setText(str+"-=-=");

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        initSwipeRefreshLayout();


        Log.e("+++++++++","ChildFourFragment   onCreateView");

        mChildFourPresenter.getMusicList();//展示数据


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
            mChildFourPresenter.getMusicList();
            mSwipeRefreshLayout.setRefreshing(false);
        }
    };

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mChildFourAdpater = new ChildFourAdpater(getContext(), mArrayList);
        mRecyclerView.setAdapter(mChildFourAdpater);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e("+++++++++","ChildFourFragment   onCreate");
        mChildFourPresenter = new ChildFourPresenterImpl(this);

        //注册EventBus
        EventBus.getDefault().register(this);


    }

    @Override
    public void getMusicListSuccess(ArrayList<Music> arrayList) {
        Toast.makeText(getContext(), "获取Music成功", Toast.LENGTH_SHORT).show();
        mArrayList = arrayList;
        initRecyclerView();
    }

    @Override
    public void getMusicListFailed() {
        Toast.makeText(getContext(), "获取Music失败", Toast.LENGTH_SHORT).show();
    }




    public void onDestroy() {
        super.onDestroy();
        Log.e("ChildFourFragment","onDestroy");
        EventBus.getDefault().unregister(this);//反注册EventBus
    }

    @Subscribe
    public void onEvent(Event event) {
        String msg = "onEventMainThread收到了消息：" + event.getMsg();
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        mChildFourPresenter.getMusicList();
    }
}
