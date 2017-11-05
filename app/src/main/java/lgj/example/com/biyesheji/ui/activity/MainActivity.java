package lgj.example.com.biyesheji.ui.activity;

import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.client.android.Intents;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.QueryListener;
import lgj.example.com.biyesheji.R;
import lgj.example.com.biyesheji.app.Constant;
import lgj.example.com.biyesheji.model.ClassBean;
import lgj.example.com.biyesheji.model.FaceImgBean;
import lgj.example.com.biyesheji.ui.fragment.FileFragment;
import lgj.example.com.biyesheji.ui.fragment.MainFragment;
import lgj.example.com.biyesheji.ui.fragment.PhotoFragment;
import lgj.example.com.biyesheji.ui.fragment.VoteFragment;
import lgj.example.com.biyesheji.utils.ThreadUtils;

import static cn.bmob.v3.BmobUser.getObjectByKey;

public class MainActivity extends BaseActivity {

    private RadioGroup mRadioGroup;
    private ViewPager mViewPager;
    private String userName;
    private Boolean isMgn;
    //创建mainactivity的实例
    public static MainActivity sMainActivity = null;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private Toolbar mToolbar;
    private String imgUrl = null;
    private BmobFile imgFile = null;
    private Gson mGson = new Gson();
    private BmobFile bmobFile = null;

    @Override
    protected void initData() {
        //获得mainactivity
        sMainActivity = this;
        final BmobUser bmobUser = BmobUser.getCurrentUser();
        //本地无bmob的数据跳到登录页面
        if (bmobUser == null) {
            Intent i = new Intent(MainActivity.this, LogininActivity.class);
            startActivity(i);
            finish();

        }

        //接受从登陆页面成功的登陆的用户名并toast
        if (bmobUser != null) {
            userName = (String) getObjectByKey("username");
            isMgn = (Boolean) getObjectByKey("isMgn");
            Object url = getObjectByKey("imgUrl");
            FaceImgBean faceImgBean = null;
            if (url != null){
                 faceImgBean = mGson.fromJson(url.toString(), FaceImgBean.class);
                Log.e("aaaaaaa", "initData: " + url + "classname" + getObjectByKey("className"));
                int index = faceImgBean.getUrl().lastIndexOf(".");
                String imgName = faceImgBean.getUrl().substring(index + 1);
                Log.e("aaaaaa", "initData: " + imgName);
                bmobFile = new BmobFile(userName + "." + imgName, "", faceImgBean.getUrl());
            }

            if (isMgn == true) {
                showToast("登陆成功,欢迎管理员:" + userName);
            } else {
                showToast("登陆成功,欢迎学生:" + userName);
            }
        }

        ThreadUtils.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                if (bmobFile != null) {
                    downloadFile(bmobFile);
                }
            }
        });

    }

    @Override
    protected void initListener() {
//1.点击单选时，viewpager显示对应的子界面
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_main:
                        mViewPager.setCurrentItem(0);
                        break;

                    case R.id.rb_photo:
                        mViewPager.setCurrentItem(1);
                        break;

                    case R.id.rb_vote:
                        mViewPager.setCurrentItem(2);
                        break;

                    case R.id.rb_file:
                        mViewPager.setCurrentItem(3);
                        break;
                }
            }
        });

        //2.当viewpager子界面发生改变时，选中并高亮对应的单选按钮
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mViewPager.setCurrentItem(0);
                        mRadioGroup.check(R.id.rb_main);
                        break;

                    case 1:
                        mViewPager.setCurrentItem(1);
                        mRadioGroup.check(R.id.rb_photo);
                        break;

                    case 2:
                        mViewPager.setCurrentItem(2);
                        mRadioGroup.check(R.id.rb_vote);
                        break;

                    case 3:
                        mViewPager.setCurrentItem(3);
                        mRadioGroup.check(R.id.rb_file);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_call:

                        Intent intent2 = new Intent(MainActivity.this, FaceSignedActivity.class);
                        intent2.putExtra("imgUrl", imgUrl);
                        startActivity(intent2);
                        showToast(item.getTitle() + "");
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_friends:
                        showToast(item.getTitle() + "");
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_location:
                        showToast(item.getTitle() + "");
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_attendanceChecking:
                        Intent intent = new Intent(MainActivity.this, AttendanceCheckingActivity.class);
                        intent.putExtra("isMgn", isMgn);
                        intent.putExtra("imgUrl", imgUrl);
                        startActivity(intent);
                        showToast(item.getTitle() + "");
                        mDrawerLayout.closeDrawers();
                        break;

                    case R.id.logout:
                        //退出登录
                        logout();
                        mDrawerLayout.closeDrawers();
                        break;
                }

                return true;
            }
        });


        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.scan_code) {
                    //扫描二维码
                    Intent intent = new Intent(MainActivity.this, MyCaptureActivity.class);
                    startActivityForResult(intent, 1001);
                }
                return true;
            }
        });
    }

    private void logout() {
        BmobUser.logOut();   //清除缓存用户对象
        BmobUser currentUser = BmobUser.getCurrentUser(); // 现在的currentUser是null了
        showToast("退出登录成功");
        Intent intent = new Intent(MainActivity.this, LogininActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void initView() {
        mRadioGroup = (RadioGroup) findViewById(R.id.rg_01);
        initViewPager();
        initNavigationView();
        initToolBar();
        initDrawerLayout();
    }


    private void initToolBar() {
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mToolbar.inflateMenu(R.menu.toolbar_menu);
        //setSupportActionBar(mToolbar);
        // mToolbar.setTitle("班圈");

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initDrawerLayout() {
        mActionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, mDrawerLayout, mToolbar, 0, 0);
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();
    }

    private void initNavigationView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
    }


    private void initViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setOffscreenPageLimit(4);
        final List<Fragment> fragments = new ArrayList<>();
        fragments.add(new MainFragment());
        fragments.add(new PhotoFragment());
        fragments.add(new VoteFragment());
        fragments.add(new FileFragment());
        //设置适配器
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
    }

    //下载文件
    private void downloadFile(BmobFile file) {
        //允许设置下载文件的存储路径，默认下载文件的目录为：context.getApplicationContext().getCacheDir()+"/bmob/"
        File saveFile = new File(Environment.getExternalStorageDirectory() + "/" + "faceimgs", file.getFilename());
        file.download(saveFile, new DownloadFileListener() {

            @Override
            public void onStart() {
                toast("开始下载...");
            }

            @Override
            public void done(String savePath, BmobException e) {
                if (e == null) {
                    toast("下载成功,保存路径:" + savePath);
                    imgUrl = savePath;
                } else {
                    toast("下载失败：" + e.getErrorCode() + "," + e.getMessage());
                }
            }

            @Override
            public void onProgress(Integer value, long newworkSpeed) {
                Log.i("bmob", "下载进度：" + value + "," + newworkSpeed);
            }

        });
    }

    private void toast(String s) {
        Toast.makeText(sMainActivity, s, Toast.LENGTH_SHORT).show();
    }


    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == 1001) {
            final String content = data.getStringExtra(Intents.Scan.RESULT);
//            tvContent.setText(content);

            BmobQuery<ClassBean> query = new BmobQuery<ClassBean>();
            query.getObject(content, new QueryListener<ClassBean>() {

                @Override
                public void done(ClassBean classBean, BmobException e) {
                    if (e == null) {

                        String classImgUrl = classBean.getClassImgUrl();
                        String className = classBean.getClassName();
                        int stuNum = classBean.getStuNum();
                        Intent intent = new Intent(MainActivity.this, ClassDetailActivity.class);
                        intent.putExtra(Constant.INTENT_CLASS_IMG, classImgUrl);
                        intent.putExtra(Constant.INTNET_CLASS_NAME, className);
                        intent.putExtra(Constant.INTENT_CLASS_STU_NUM, stuNum);
                        intent.putExtra(Constant.INTENT_IS_JOIN_CLASS, true);
                        intent.putExtra(Constant.INTENT_CLASS_ID,content);
                        startActivity(intent);
                    } else {
                        Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                    }
                }
            });
        }

    }



}
