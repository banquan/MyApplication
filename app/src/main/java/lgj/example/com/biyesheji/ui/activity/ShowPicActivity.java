package lgj.example.com.biyesheji.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.numberprogressbar.NumberProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import lgj.example.com.biyesheji.R;
import lgj.example.com.biyesheji.adapter.ShowPicAdapter;
import lgj.example.com.biyesheji.model.AllPicInfo;
import lgj.example.com.biyesheji.model.ImageItemViewInfo;
import lgj.example.com.biyesheji.presenter.ShowPicPresenter;
import lgj.example.com.biyesheji.presenter.impl.ShowPicPresenterImpl;
import lgj.example.com.biyesheji.view.ShowPicView;

public class ShowPicActivity extends AppCompatActivity implements ShowPicView {
    @BindView(R.id.ablum_name)
    TextView mAblumName;
    @BindView(R.id.upload_pic)
    Button mUploadPic;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.number_progress_bar)
    NumberProgressBar mProgressBar;
    private String objectId;
    private String title;

    private ShowPicPresenter mShowPicPresenter;

    protected static final int GET_HEAD_IMG = 1001;
    private static final int CROP_HEAD = 1002;
    private String path;//图片在手机的路径。

    private ShowPicAdapter mShowPicAdapter;

    private List<AllPicInfo> mAllPicInfos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pic);
        ButterKnife.bind(this);

        init();

    }


    private void init() {
        getIntentFromAlbumAdapter();
        mShowPicPresenter = new ShowPicPresenterImpl(this);

        mShowPicPresenter.getList(mAblumName.getText().toString());
        initSwipeRefreshLayout();
        initProgressBar();
    }

    private void initProgressBar() {
        mProgressBar.setProgress(0);
    }


    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.qq_blue, R.color.colorAccent, R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
    }


    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {

            Toast.makeText(ShowPicActivity.this, "刷新啦~~~", Toast.LENGTH_SHORT).show();
            mShowPicPresenter.getList(mAblumName.getText().toString());

        }
    };

    private void ininRecyclerView() {

        mRecyclerView.setHasFixedSize(true);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        Log.d("pppppppppppppppppppp", String.valueOf(mAllPicInfos.size()));
        mShowPicAdapter = new ShowPicAdapter(this, mAllPicInfos);
        //        mShowPicAdapter = new ShowPicAdapter(this,null);
        mRecyclerView.setAdapter(mShowPicAdapter);


    }

    private void getIntentFromAlbumAdapter() {
        Intent intent = getIntent();
        objectId = intent.getStringExtra("objectId");
        title = intent.getStringExtra("title");
        mAblumName.setText(title);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d("ShowPicActivity", "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("ShowPicActivity", "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("ShowPicActivity", "onResume");
        ////////////////////////////////////////////////////////////////
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("ShowPicActivity", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("ShowPicActivity", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("ShowPicActivity", "onDestroy");
    }


    @OnClick(R.id.upload_pic)
    public void onClick() {
        getpic();
    }

    private void getpic() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, GET_HEAD_IMG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GET_HEAD_IMG) {
            Uri imgUri = data.getData();


            //获取图片路径
            String proj[] = {MediaStore.Images.Media.DATA};
            Cursor cursor = managedQuery(imgUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            path = cursor.getString(column_index);
            Toast.makeText(this, "" + path, Toast.LENGTH_SHORT).show();

            //拿到图片后先裁剪
            Intent intent = new Intent();
            intent.setAction("com.android.camera.action.CROP");
            intent.setDataAndType(imgUri, "image/*");
            intent.putExtra("crop", true);
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", 200);
            intent.putExtra("outputY", 200);
            intent.putExtra("return-data", true);

            startActivityForResult(intent, CROP_HEAD);

        }

        if (requestCode == CROP_HEAD) {
            //Bundle bundle = data.getExtras();
            //Bitmap bmp = bundle.getParcelable("data");
            //这里注意是  ImageView 组件用 setImageBitmap 方法。
            //iv.setImageBitmap(bmp);
            mShowPicPresenter.uploadPic(path, mAblumName.getText().toString());
        }

    }

    @Override
    public void uploadPicSuccess() {
        Toast.makeText(this, "上传图片成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void saveAllPicInfoSuccess() {
        Toast.makeText(this, "保存AllPicInfo成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getAllPicInfoListSuccess(ArrayList<AllPicInfo> arr) {

        updateAlbum(objectId, arr);

        Toast.makeText(this, "获取AllPicInfo成功-->" + arr.size(), Toast.LENGTH_SHORT).show();
        mAllPicInfos = arr;
        // ininRecyclerView();
        mShowPicAdapter.refreshAlbumAdpater(mAllPicInfos);
    }


    @Override
    public void getListSuccess(ArrayList<AllPicInfo> arr) {

        updateAlbum(objectId, arr);

        Toast.makeText(this, "刷新获取数据" + arr.size(), Toast.LENGTH_SHORT).show();
        mAllPicInfos = arr;
        ininRecyclerView();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void uploadPicProgress(Integer value) {
        if (value == 100) {
            mProgressBar.setVisibility(View.GONE);
        }
        mProgressBar.setProgress(value);
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }


    private void updateAlbum(String objectId, ArrayList<AllPicInfo> arr) {
        //        ImageItemViewInfo

        ImageItemViewInfo mImageItemViewInfo = new ImageItemViewInfo();

        mImageItemViewInfo.setShow_first_image(arr.get(0).getPic());
        mImageItemViewInfo.setQuantity(String.valueOf(arr.size()));

        mImageItemViewInfo.update(objectId, new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.i("bmob", "更新成功");
                } else {
                    Log.i("bmob", "更新失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }
}
