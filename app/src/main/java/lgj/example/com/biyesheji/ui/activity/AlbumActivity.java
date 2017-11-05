package lgj.example.com.biyesheji.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;
import lgj.example.com.biyesheji.R;
import lgj.example.com.biyesheji.adapter.AlbumAdpater;
import lgj.example.com.biyesheji.event.EventWithPhotoFragment;
import lgj.example.com.biyesheji.presenter.AlbumPresenter;
import lgj.example.com.biyesheji.presenter.impl.AlbumPresenterImpl;
import lgj.example.com.biyesheji.view.AlbumView;

import static lgj.example.com.biyesheji.app.MyApplication.getContext;

public class AlbumActivity extends BaseActivity implements AlbumView {
    @BindView(R.id.activity_main)
    LinearLayout mActivityMain;
    private RecyclerView mRecyclerView;
    private Button mBtnAddAlbum;
    private SwipeRefreshLayout mSwipeRefreshLayout;


    private AlbumAdpater mAlbumAdpater;
    private AlbumPresenter mBtnAddAlbumPresenter;
    private boolean isExistAlbumName = false;

    private boolean isEventBus = false;

    private FloatingActionButton mFloatingActionButton;
    private String classId;
    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null){
            classId = intent.getStringExtra("classId");
        }
        init();
        ininRecyclerView();
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void initView() {
        //注册EventBus
        EventBus.getDefault().register(this);
        Log.e("PhotoFragment","怎么老是那么多bug阿。。。");

        mBtnAddAlbum = (Button) findViewById(R.id.btn_add_album);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab_search);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlbumNameInputDialog(mBtnAddAlbumPresenter);
            }
        });


        initData();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_album;
    }

    private void ininRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        GridLayoutManager glm = new GridLayoutManager(AlbumActivity.this, 2);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setLayoutManager(glm);
        mAlbumAdpater = new AlbumAdpater(AlbumActivity.this,mBtnAddAlbumPresenter.getList(classId));
        mRecyclerView.setAdapter(mAlbumAdpater);

    }


    private void init() {
        mBtnAddAlbumPresenter = new AlbumPresenterImpl(this);
        initSwipeRefreshLayout();
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.qq_blue, R.color.colorAccent, R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
    }

    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            Toast.makeText(getContext(), "刷新啦~~~", Toast.LENGTH_SHORT).show();
            mAlbumAdpater.refreshAlbumAdpater(mBtnAddAlbumPresenter.getList(classId));
            mSwipeRefreshLayout.setRefreshing(false);
        }
    };

    @Override
    public void UploadToBmobSuccess() {
        Toast.makeText(getContext(), "数据添加成功", Toast.LENGTH_SHORT).show();
        //mAlbumAdpater.notifyDataSetChanged();
        //mAlbumAdpater.refreshAlbumAdpater(mBtnAddAlbumPresenter.getList(classId));
        mAlbumAdpater.notifyDataSetChanged();
    }

    @Override
    public void UploadToBmobFailed() {
        Toast.makeText(getContext(), "数据添加失败", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void getListFailed() {
        Toast.makeText(getContext(), "数据获取失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getListSuccess() {
        Toast.makeText(getContext(), "数据获取成功", Toast.LENGTH_SHORT).show();
        mAlbumAdpater.notifyDataSetChanged();
    }

    @Override
    public void showExistAlbumName() {
        Toast.makeText(getContext(), "您创建的相册名字已存在，请更改~~~", Toast.LENGTH_SHORT).show();
        isExistAlbumName = true;
    }



    @Override
    public void onStart() {
        super.onStart();
        Log.d("MainActivity", "onRestart");
        //mAlbumAdpater.refreshAlbumAdpater(mBtnAddAlbumPresenter.getList());
    }


    protected void showAlbumNameInputDialog(final AlbumPresenter mBtnAddAlbumPresenter) {
        final EditText et_name = new EditText(getContext());
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
        new AlertDialog.Builder(AlbumActivity.this)
                .setTitle("Write You AlbumName")
                .setPositiveButton("Sure", new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface dialog, int which) {
                        if (isExistAlbumName) {
                            isExistAlbumName = false;
                            return;
                        }
                        dialog.dismiss();
                        String title = et_name.getText().toString();
//                        Toast.makeText(MainActivity.this, name, Toast.LENGTH_LONG).show();
                        mBtnAddAlbumPresenter.UploadToBmob(title,classId);

                        mAlbumAdpater.refreshAlbumAdpater(mBtnAddAlbumPresenter.getList(classId));

                    }
                })
                .setView(et_name)//Set a custom view to be the contents of the Dialog
                .create()
                .show();

    }


    @OnClick(R.id.btn_add_album)
    public void onClick() {
//        showAlbumNameInputDialog(mBtnAddAlbumPresenter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("-=-=-=-=-=-=-=-", isEventBus+" ");


        if(isEventBus){
            isEventBus = false;
            mAlbumAdpater.refreshAlbumAdpater(mBtnAddAlbumPresenter.getList(classId));
        }

    }

    @Subscribe
    public void onEventMainThread(EventWithPhotoFragment event) {
        isEventBus = true;

        String msg =  "onEventMainThread收到了消息：" + event.getMsg();
        Log.d("harvic", msg);
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
        Log.e("harvic", msg);

    }
}
