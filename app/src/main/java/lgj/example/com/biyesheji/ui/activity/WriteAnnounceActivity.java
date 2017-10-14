package lgj.example.com.biyesheji.ui.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.chaychan.viewlib.PowerfulEditText;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import lgj.example.com.biyesheji.R;
import lgj.example.com.biyesheji.model.Announcement;


/**
 * Created by Administrator on 2017/10/12.
 */

public class WriteAnnounceActivity extends BaseActivity {
    private PowerfulEditText title_write_announce, content_write_announce;
    private FloatingActionButton fab_announce;

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        fab_announce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final BmobUser bmobUser = BmobUser.getCurrentUser();

                //新建一个公告对象
                Announcement announcement = new Announcement();
                announcement.setTitle(String.valueOf(title_write_announce.getText()));
                announcement.setContent(String.valueOf(content_write_announce.getText()));

                //上传公告对象
                announcement.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {
                            BmobQuery<Announcement> bmobQuery = new BmobQuery<Announcement>();
                            bmobQuery.addQueryKeys("title,content,createdAt");
                            bmobQuery.findObjects(new FindListener<Announcement>() {
                                @Override
                                public void done(List<Announcement> list, BmobException e) {
                                    if(e==null){
                                        //如果创建公告成功，把前一个mainactivity结束掉，创建新的mainactivity
                                        MainActivity.sMainActivity.finish();

                                        showToast("创建公告成功");
                                        Intent i=new Intent(WriteAnnounceActivity.this,MainActivity.class);
                                        startActivity(i);
                                        finish();
                                    }
                                }
                            });

                        } else {
                            showToast("创建公告失败");
                        }
                    }
                });

            }

        });


    }

    @Override
    public void initView() {
        title_write_announce = (PowerfulEditText) findViewById(R.id.title_write_announce);
        content_write_announce = (PowerfulEditText) findViewById(R.id.content_write_announce);
        fab_announce = (FloatingActionButton) findViewById(R.id.fab_announce);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_write_announce;
    }


}
