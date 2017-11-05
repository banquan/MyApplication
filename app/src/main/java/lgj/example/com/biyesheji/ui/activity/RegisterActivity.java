package lgj.example.com.biyesheji.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.chaychan.viewlib.PowerfulEditText;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import lgj.example.com.biyesheji.R;
import lgj.example.com.biyesheji.model.AcademicBean;
import lgj.example.com.biyesheji.model.MyUser;
import lgj.example.com.biyesheji.utils.ThreadUtils;


/**
 * Created by Administrator on 2017/10/7.
 */

public class RegisterActivity extends AppCompatActivity {
    private PowerfulEditText et_createUserName, et_createPsd, et_ensurePsd;
    private Button register_btn;
    private RadioGroup radioGroup_register;
    private RadioButton radioBtn_stu,radioBtn_mgn;
    private Boolean isMgn=false;
//    private NiceSpinner mAcademicSpinner;
//    private NiceSpinner mProfessionSpinner;
    private List<String> mAcademicInfoList = new ArrayList<>();
    private List<String> mProfessionInfoList = new ArrayList<>();
private LinkedList<String> mAcademicInfoLinkedList = new LinkedList<>();
    private LinkedList<String> mProfessionInfoLisr = new LinkedList<>();
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
//        mAcademicSpinner = (NiceSpinner) findViewById(R.id.academic_spinner);
//        mProfessionSpinner = (NiceSpinner) findViewById(R.id.profession_spinner);
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
                //注册到环信
                //注册失败会抛出HyphenateException
                ThreadUtils.runOnBackgroundThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.e("aaaaaaaaaa", "run: " +et_createUserName.getText().toString().trim()+ et_createPsd.getText().toString().trim() );
                            EMClient.getInstance().createAccount(et_createUserName.getText().toString().trim(), et_createPsd.getText().toString().trim());//同步方法
                        } catch (HyphenateException e) {
                            Log.e("aaaaaaaaaa", "run: " + "erroe==================");
                            e.printStackTrace();
                            Log.d("aaaaaaaa", String.valueOf(e));
                        }
                    }
                });

            }
        });



//        mNiceSpinner = (NiceSpinner) findViewById(R.id.nice_spinner);
//        List<String> dataset = new LinkedList<>(Arrays.asList("One", "Two", "Three", "Four", "Five"));
//        mNiceSpinner.attachDataSource(dataset);
        //初始化学院和专业的下拉列表
    //    mAcademicInfoList.add("学院");
//        mProfessionInfoList.add("专业");

//        mAcademicSpinner.setOnDragListener(new View.OnDragListener() {
//            @Override
//            public boolean onDrag(View v, DragEvent event) {
//                Toast.makeText(RegisterActivity.this, "11111", Toast.LENGTH_SHORT).show();
//                return true;
//            }
//        });
       // mAcademicSpinner.attachDataSource(mAcademicInfoList);
       // mProfessionSpinner.attachDataSource(mProfessionInfoList);

        new Thread(new Runnable() {
            @Override
            public void run() {
                findAcademicList();
            }
        }).start();

    }

    //弹出吐司的方法
    private void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    //查找学院列表
    private void findAcademicList(){
        //只返回Person表的academicName这列的值
        BmobQuery<AcademicBean> bmobQuery = new BmobQuery<AcademicBean>();
        bmobQuery.addQueryKeys("academicName");
        bmobQuery.findObjects(new FindListener<AcademicBean>() {
            @Override
            public void done(List<AcademicBean> object, BmobException e) {
                if(e==null){
                    toast("查询成功：共" + object.size() + "条数据。");
                    //注意：这里的AcademicBean对象中只有指定列的数据。
//                List<String> macademicinfoList = new LinkedList<>(Arrays.asList(dealAcademicList(object))) ;
//                    List<String> dataset = new LinkedList<>(Arrays.asList("One", "Two", "Three", "Four", "Five"));
//                    mAcademicSpinner.attachDataSource(dataset);

                    List<String> macademicinfoList = new LinkedList<>(Arrays.asList(dealAcademicList(object))) ;
           //         mAcademicSpinner.attachDataSource(macademicinfoList);
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });


    }

    private String[] dealAcademicList(List<AcademicBean> academic) {
        String[] academicInfos = new String[academic.size()+1];
        academicInfos[0] = "学院";
        for (int i = 1; i < academicInfos.length; i++){
            academicInfos[i] = (academic.get(i-1).getAcademicName());
        }
        return  academicInfos;
    }

}
