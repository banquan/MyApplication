package lgj.example.com.biyesheji.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RadioGroup;
import java.util.ArrayList;
import java.util.List;
import lgj.example.com.biyesheji.R;
import lgj.example.com.biyesheji.ui.fragment.FileFragment;
import lgj.example.com.biyesheji.ui.fragment.MainFragment;
import lgj.example.com.biyesheji.ui.fragment.PhotoFragment;
import lgj.example.com.biyesheji.ui.fragment.VoteFragment;

public class MainActivity extends BaseActivity {

    private RadioGroup mRadioGroup;
    private ViewPager mViewPager;

    @Override
    protected void initData() {

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
    }

    @Override
    public void initView() {
        mRadioGroup = (RadioGroup) findViewById(R.id.rg_01);
        initViewPager();
    }

    private void initViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
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

    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }
}
