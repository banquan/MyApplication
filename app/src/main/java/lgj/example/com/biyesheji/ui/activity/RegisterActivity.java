package lgj.example.com.biyesheji.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.chaychan.viewlib.PowerfulEditText;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import lgj.example.com.biyesheji.R;
import lgj.example.com.biyesheji.model.MyUser;


/**
 * Created by Administrator on 2017/10/7.
 */

public class RegisterActivity extends AppCompatActivity {
    private PowerfulEditText et_createUserName, et_createPsd, et_ensurePsd;
    private Button register_btn;
    private RadioGroup radioGroup_register;
    private RadioButton radioBtn_stu,radioBtn_mgn;
    private Boolean isMgn=false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//初始化组件
        et_createUserName = (PowerfulEditText) findViewById(R.id.et_createUserName);
        et_createPsd = (PowerfulEditText) findViewById(R.id.et_createPsd);
        et_ensurePsd = (PowerfulEditText) findViewById(R.id.et_ensurePsd);
        register_btn = (Button) findViewById(R.id.register_btn);
        radioGroup_register= (RadioGroup) findViewById(R.id.radioGroup_register);
        radioBtn_stu= (RadioButton) findViewById(R.id.radioBtn_stu);
        radioBtn_mgn= (RadioButton) findViewById(R.id.radioBtn_mgn);
        radioGroup_register.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
               if(radioBtn_mgn.getId()==checkedId){
                   isMgn=true;
                }else{
                   isMgn=false;
               }
            }
        });

//注册的点击事件
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (String.valueOf(et_createPsd.getText()).equals(String.valueOf(et_ensurePsd.getText()))) {
                    MyUser bu = new MyUser();
                    bu.setUsername(String.valueOf(et_createUserName.getText()).trim());
                    bu.setPassword(String.valueOf(et_ensurePsd.getText()).trim());
                    bu.setMgn(isMgn);
                    bu.signUp(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser s, BmobException e) {
                            if (e == null) {
                                toast("注册成功:"+s.getUsername());
                                Intent i = new Intent(RegisterActivity.this, LogininActivity.class);
                                Bundle bundle=new Bundle();
                                bundle.putString("userName", s.getUsername());
                                bundle.putString("psd", String.valueOf(et_createPsd.getText()));
                                i.putExtras(bundle);
                                startActivity(i);
                                finish();
                            } else {
                                if (e.getErrorCode() == 202) {
                                    toast("用户名已存在,请尝试其他用户名");
                                } else {
                                    toast("用户名或密码不能为空");
                                }
                            }
                        }
                    });
                } else {
                    toast("请保证两次密码一致");
                }

            }
        });
    }

    //弹出吐司的方法
    private void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
