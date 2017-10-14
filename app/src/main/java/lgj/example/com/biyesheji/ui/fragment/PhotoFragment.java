package lgj.example.com.biyesheji.ui.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.ButterKnife;
import lgj.example.com.biyesheji.R;
import lgj.example.com.biyesheji.adapter.AlbumAdpater;
import lgj.example.com.biyesheji.app.MyApplication;
import lgj.example.com.biyesheji.presenter.BtnAddAlbumPresenter;
import lgj.example.com.biyesheji.presenter.impl.BtnAddAlbumPresenterImpl;
import lgj.example.com.biyesheji.view.BtnAddAlbumView;

/**
 * Created by yhdj on 2017/9/27.
 */

public class PhotoFragment extends BaseFragment implements BtnAddAlbumView {
//    @BindView(R.id.recycler_view)
//    RecyclerView mRecyclerView;
//    @BindView(R.id.swipe_refresh_layout)
//    SwipeRefreshLayout mSwipeRefreshLayout;
//    @BindView(R.id.btn_add_album)
//    Button mBtnAddAlbum;
//    @BindView(R.id.activity_main)
//    LinearLayout mActivityMain;
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Button mBtnAddAlbum;

//    @BindView(R.id.recycler_view)
//    RecyclerView mRecyclerView;
//    @BindView(R.id.btn_add_album)
//    Button mBtnAddAlbum;
//    @BindView(R.id.swipe_refresh_layout)
//    SwipeRefreshLayout mSwipeRefreshLayout;

    private AlbumAdpater mAlbumAdpater;
    private BtnAddAlbumPresenter mBtnAddAlbumPresenter;
    private boolean isExistAlbumName = false;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_photo;
    }

    @Override
    public void initView() {
//        ButterKnife.bind(getActivity(),getActivity());
        mRecyclerView = (RecyclerView) super.mRootView.findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) super.mRootView.findViewById(R.id.swipe_refresh_layout);
        mBtnAddAlbum = (Button) super.mRootView.findViewById(R.id.btn_add_album);
        mBtnAddAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showAlbumNameInputDialog(mBtnAddAlbumPresenter);
//                EditNameDialogFragment editNameDialog = new EditNameDialogFragment();
//                editNameDialog.show(getFragmentManager(), "EditNameDialog");
                Toast.makeText(getContext(), "dialog", Toast.LENGTH_SHORT).show();
            }
        });

        init();
        ininRecyclerView();
    }

    private void showAlbumNameInputDialog(BtnAddAlbumPresenter btnAddAlbumPresenter) {
        final EditText et_name = new EditText(getActivity());
        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.e("aaaaaaaa", "beforeTextChanged: " + s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e("aaaaaaaa", "onTextChanged: " + s);
                //监听输入框的文字变化，然后判断是否与已在相册同名
                mBtnAddAlbumPresenter.isExistedAlbumName(String.valueOf(s));

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.e("aaaaaaaa", "afterTextChanged: " + s);

            }
        });
        new AlertDialog.Builder(getActivity())
                .setTitle("Write You AlbumName")
                .setPositiveButton("Sure", new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface dialog, int which) {
                        if (isExistAlbumName){
                            isExistAlbumName = false;
                            return;
                        }
                        dialog.dismiss();
                        String title = et_name.getText().toString();
//                        Toast.makeText(MainActivity.this, name, Toast.LENGTH_LONG).show();
                        mBtnAddAlbumPresenter.UploadToBmob(title);

                        mAlbumAdpater.refreshAlbumAdpater(mBtnAddAlbumPresenter.getList());

                    }
                })
                .setView(et_name)//Set a custom view to be the contents of the Dialog
                .create()
                .show();


    }

    private void ininRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MyApplication.getContext()));
        mAlbumAdpater = new AlbumAdpater(MyApplication.getContext(), mBtnAddAlbumPresenter.getList());
        mRecyclerView.setAdapter(mAlbumAdpater);
    }

    private void init() {
        mBtnAddAlbumPresenter = new BtnAddAlbumPresenterImpl(PhotoFragment.this);
        initSwipeRefreshLayout();


        //侧滑删除
        //initSlideDelete();
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.qq_blue, R.color.colorAccent, R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
    }

    @Override
    public void initListener() {
        mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showToast("刷新了··········");
                mAlbumAdpater.refreshAlbumAdpater(mBtnAddAlbumPresenter.getList());
                mSwipeRefreshLayout.setRefreshing(false);
            }
        };
    }

    @Override
    public void initData() {

    }

    @Override
    public void UploadToBmobSuccess() {
        showToast("数据添加成功");
    }

    @Override
    public void UploadToBmobFailed() {
        showToast("数据添加失败");
    }

    @Override
    public void getListFailed() {
        showToast("数据获取失败");
    }

    @Override
    public void getListSuccess() {
        showToast("数据获取成功");
    }

    @Override
    public void showExistAlbumName() {
        showToast("您创建的相册名字已存在，请更改~~~");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("MainActivity", "onStart");

    }

//    @Override
//    public void onRestart() {
//
//        Log.d("MainActivity", "onRestart");
//        mAlbumAdpater.refreshAlbumAdpater(mBtnAddAlbumPresenter.getList());
//    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d("MainActivity", "onResume");


    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("MainActivity", "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("MainActivity", "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MainActivity", "onDestroy");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }


}
