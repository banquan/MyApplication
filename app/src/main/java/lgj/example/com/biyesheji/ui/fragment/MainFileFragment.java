package lgj.example.com.biyesheji.ui.fragment;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import lgj.example.com.biyesheji.R;
import lgj.example.com.biyesheji.adapter.FragmentAdapter;
import lgj.example.com.biyesheji.event.Event;
import lgj.example.com.biyesheji.presenter.MainFilePresenter;
import lgj.example.com.biyesheji.presenter.impl.MainFilePresenterImpl;
import lgj.example.com.biyesheji.utils.FileUtils;
import lgj.example.com.biyesheji.view.MainFileView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by yu on 2017/10/18.
 */

public class MainFileFragment extends Fragment implements MainFileView {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FragmentAdapter mAdapter;
    private List<Fragment> mChildFragments = new ArrayList<>();
    private List<String> mStrings = new ArrayList<>();
    private Button mButton;
    private MainFilePresenter mMainPresenter;
    private View mView;
    private static final int FILE_SELECT_CODE = 0;
    private static final String TAG = "ChooseFile";
    private String mPath;
    private String fileSize = null;
    private String mName;
//    private NumberProgressBar mNumberProgressBar;

    private ProgressDialog mProgressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("+++++++++","MainFileFragment");

        mView = inflater.inflate(R.layout.fragment_mainfile,container,false);
        mTabLayout = (TabLayout) mView.findViewById(R.id.tablayout);
        mViewPager = (ViewPager) mView.findViewById(R.id.viewpager);
        mAdapter = new FragmentAdapter(getChildFragmentManager(),mChildFragments,mStrings);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);


        //设置进入条
//        mNumberProgressBar = (NumberProgressBar) mView.findViewById(R.id.number_progress_bar);
//        mNumberProgressBar.setProgress(0);

        //点击事件上传文件
        mButton = (Button) mView.findViewById(R.id.btn_upload);
        initViews();

        //初始化对话框
        initProgressBar();

        return mView;
    }

    private void initProgressBar() {
        //mProgressBar.setProgress(0);
        mProgressBar = new ProgressDialog(getContext());
        mProgressBar.setTitle("This is ProgressDialog");
        mProgressBar.setMessage("Loading...");
        mProgressBar.setCancelable(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        Log.e("+++++++++","MainFileFragment");
        mMainPresenter = new MainFilePresenterImpl(this);
    }

    private void initViews() {
        //初始化按钮控件（上传文件）

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
    }

    private void initData() {

        ChildOneFragment c1 = new ChildOneFragment();
        //Bundle b1 =new Bundle();
        //b1.putString("key","ChildOneFragment");
        //c1.setArguments(b1);
        mChildFragments.add(c1);
        mStrings.add("文档");

        ChildTwoFragment c2 = new ChildTwoFragment();
        //Bundle b2 =new Bundle();
        //b2.putString("key","ChildTwoFragment");
        //c2.setArguments(b2);
        mChildFragments.add(c2);
        mStrings.add("图片");

        ChildThreeFragment c3 = new ChildThreeFragment();
        //Bundle b3 =new Bundle();
        //b3.putString("key","ChildThreeFragment");
        //c3.setArguments(b3);
        mChildFragments.add(c3);
        mStrings.add("视频");

        ChildFourFragment c4 = new ChildFourFragment();
        //Bundle b4 =new Bundle();
        //b4.putString("key","ChildFourFragment");
        //c4.setArguments(b4);
        mChildFragments.add(c4);
        mStrings.add("音乐");


        ChildFiveFragment c5 = new ChildFiveFragment();
        //Bundle b5 =new Bundle();
        //b5.putString("key","ChildFiveFragment");
        //c5.setArguments(b5);
        mChildFragments.add(c5);
        mStrings.add("其他");


    }

    @Override
    public void test() {
        Toast.makeText(getContext(), "测试mvp", Toast.LENGTH_SHORT).show();
    }



    @Override
    public void uploadblockSuccess(Integer value) {
//        mNumberProgressBar.setVisibility(View.VISIBLE);
        if (value == 100) {
//            mNumberProgressBar.setVisibility(View.GONE);
            Toast.makeText(getContext(), "uploadblockSuccess()", Toast.LENGTH_SHORT).show();
        }
//        mNumberProgressBar.setProgress(value);

        mProgressBar.show();
    }


    @Override
    public void saveDocumentSuccess() {
        Toast.makeText(getContext(), "saveDocumentSuccess()", Toast.LENGTH_SHORT).show();

        EventBus.getDefault().post(
                new Event("refreshDoucument"));
        mProgressBar.dismiss();
    }

    @Override
    public void saveVideoSuccess() {
        Toast.makeText(getContext(), "saveVideoSuccess()", Toast.LENGTH_SHORT).show();
        EventBus.getDefault().post(
                new Event("refreshVideo"));
        mProgressBar.dismiss();
    }



    @Override
    public void savePictureSuccess() {
        Toast.makeText(getContext(), "savePictureSuccess()", Toast.LENGTH_SHORT).show();
        EventBus.getDefault().post(
                new Event("refreshPicture"));
        mProgressBar.dismiss();
    }

    @Override
    public void saveMusicSuccess() {
        Toast.makeText(getContext(), "saveMusicSuccess()", Toast.LENGTH_SHORT).show();
        EventBus.getDefault().post(
                new Event("refreshMusic"));
        mProgressBar.dismiss();
    }



    @Override
    public void saveOtherSuccess() {
        Toast.makeText(getContext(), "saveOtherSuccess()", Toast.LENGTH_SHORT).show();
        EventBus.getDefault().post(
                new Event("refreshSuccess"));
        mProgressBar.dismiss();
    }



    //1.请求选择文件
    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(intent, FILE_SELECT_CODE);
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(getContext(), "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }
    }

    //处理选中文件的回调
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult: ");
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {

                    Uri uri = data.getData();

                    Log.d(TAG, "File Uri: " + uri.toString());

                    mPath = null;

                    try {
                        mPath = FileUtils.getPath(getContext(), uri);
                        Toast.makeText(getContext(), "文件大小 = " + FileUtils.getFileSize(mPath), Toast.LENGTH_SHORT).show();
                        fileSize = FileUtils.getFileSize(mPath);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, "File Path: " + mPath);

                    //这里执行操作。
                    //1.首先判断文件类型（用一个变量存储），获取文件类型以后上传
                    //(1)常见的文档类型：txt doc hlp wps rtf html pdf
                    //(2)常见的图片类型：bmp gif jpg pic png tif
                    //(3)常见的视频类型：。。。
                    //(4)常见的音乐类型：mp3
                    //(5)其他的类型：。。。

                    //获取文件类型
                    int indexOfType = mPath.lastIndexOf(".");
                    indexOfType = indexOfType + 1;
                    String mType = mPath.substring(indexOfType);
                    Log.e("-------------", "《-》" + mType);

                    //获取文件名字
                    int indexOfNmae = mPath.lastIndexOf("/");
                    indexOfNmae = indexOfNmae + 1;
                    mName = mPath.substring(indexOfNmae);
                    Log.e("-------------", "《-》" + mName);


                    classifyType(mType);


                }
                break;
        }

    }



    private void classifyType(String mType) {
        switch (mType) {
            case "docx":
            case "doc":
            case "pptx":
            case "ppt":
            case "xlsx":
            case "xls":
                System.out.println("文档类型");
                mMainPresenter.uploadDocument(mPath,mName,fileSize,mType);

                break;
            case "gif":
            case "jpeg":
            case "jpg":
            case "png":
                System.out.println("图片类型");
                mMainPresenter.uploadPicture(mPath,mName,fileSize,mType);
                break;
            case "rm":
            case "rmvb":
            case "wmv":
            case "avi":
            case "mp4":
            case "3gp":
            case "mkv":
                System.out.println("视频类型");
                mMainPresenter.uploadVideo(mPath,mName,fileSize,mType);
                break;
            case "m4a":
            case "wav":
            case "mp3":
            case "wma":
            case "ogg":
            case "ape":
            case "acc":
                System.out.println("音乐类型");
                mMainPresenter.uploadMusic(mPath,mName,fileSize,mType);
                break;
            case "zip":
            case "rar":
                System.out.println("其他类型");
                mMainPresenter.uploadOther(mPath,mName,fileSize,mType);
                break;
            default:

                break;
        }
    }

}
