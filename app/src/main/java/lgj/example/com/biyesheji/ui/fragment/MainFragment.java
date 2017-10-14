package lgj.example.com.biyesheji.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lgj.example.com.biyesheji.R;
import lgj.example.com.biyesheji.app.MyApplication;
import lgj.example.com.biyesheji.ui.activity.AttendanceCheckingActivity;

/**
 * Created by yhdj on 2017/9/27.
 */

public class MainFragment extends BaseFragment {
    @BindView(R.id.attendanceChecking)
    TextView mAttendanceChecking;
    @BindView(R.id.activity_main)
    LinearLayout mActivityMain;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_main;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick(R.id.attendanceChecking)
    public void onClick() {
        Intent intent = new Intent(MyApplication.getContext(), AttendanceCheckingActivity.class);
        getContext().startActivity(intent);
    }
}
