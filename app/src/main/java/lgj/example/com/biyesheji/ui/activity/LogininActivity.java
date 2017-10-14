package lgj.example.com.biyesheji.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chaychan.viewlib.PowerfulEditText;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import lgj.example.com.biyesheji.R;
import lgj.example.com.biyesheji.model.MyUser;


public class LogininActivity extends AppCompatActivity {

    private PowerfulEditText et_userName, et_psd;
    private Button loginin_btn;
    private TextView register_tv;
    String bundle_userName, bundle_psd;
    Boolean bundle_isMgn = null;
    String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_loginin);


//Bundle传递注册页面的数据
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            bundle_userName = bundle.getString("userName");
            bundle_psd = bundle.getString("psd");
        }

//初始化组件
        et_userName = (PowerfulEditText) findViewById(R.id.et_userName);
        et_psd = (PowerfulEditText) findViewById(R.id.et_psd);
        loginin_btn = (Button) findViewById(R.id.loginin_btn);
        register_tv = (TextView) findViewById(R.id.register_tv);
//把传递过来的用户名和密码填入输入框
        if (bundle_userName != null && bundle_psd != null) {
            et_userName.setText(bundle_userName);
            et_psd.setText(bundle_psd);
        }

//登录按钮点击事件
        loginin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MyUser bu2 = new MyUser();
                bu2.setUsername(String.valueOf(et_userName.getText()));
                bu2.setPassword(String.valueOf(et_psd.getText()));
                bu2.login(new SaveListener<MyUser>() {

                    @Override
                    public void done(MyUser bmobUser, BmobException e) {
                        if (e == null) {
                            name = bu2.getUsername();
                            Intent i = new Intent(LogininActivity.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            if (e.getErrorCode() == 101) {
                                toast("用户名或密码不正确,请重新检查后输入");
                            } else {
                                toast("未知错误");
                            }
                        }
                    }
                });

            }
        });

//新用户文字的点击事件
        register_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LogininActivity.this, RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });
    }


    //弹出吐司的方法
    private void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
