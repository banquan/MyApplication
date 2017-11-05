package lgj.example.com.biyesheji.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import lgj.example.com.biyesheji.R;
import lgj.example.com.biyesheji.app.Constant;
import lgj.example.com.biyesheji.model.MyUser;

public class ClassDetailActivity extends BaseActivity {

    private ImageView mIvClassImg;
    private ImageView mIvClassCode;
    private TextView mTvClassName;
    private TextView mTvClassStuNum;
    private Button mBtnSendClassMsg;
    private String mClassImg;
    private String mClassName;
    private int mClassStuNum;
    private String mClassId;
    private boolean isJoinClass;

    @Override
    protected void initData() {
        //从adapter中获取数据
        Intent intent = getIntent();
        if (intent != null) {
            mClassImg = intent.getStringExtra(Constant.INTENT_CLASS_IMG);
            mClassName = intent.getStringExtra(Constant.INTNET_CLASS_NAME);
            mClassStuNum = intent.getIntExtra(Constant.INTENT_CLASS_STU_NUM, 0);
            mClassId = intent.getStringExtra(Constant.INTENT_CLASS_ID);
            isJoinClass = intent.getBooleanExtra(Constant.INTENT_IS_JOIN_CLASS, false);
        }

        //渲染数据
        if (mClassImg != null) {
            Glide.with(ClassDetailActivity.this).load(mClassImg).into(mIvClassImg);
        }
        mTvClassName.setText(mClassName);
        mTvClassStuNum.setText(mClassStuNum + "人");
        if (isJoinClass) {
            mBtnSendClassMsg.setText("加入班级");
        } else {
            mBtnSendClassMsg.setText("发消息");
        }

    }

    @Override
    protected void initListener() {
        mBtnSendClassMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isJoinClass) {
                    //进入班级聊天
                } else {
                    //加入班级
                    //学生加入班级
                    String objectId = (String) MyUser.getObjectByKey("objectId");
                    MyUser myUser = new MyUser();

                    JSONArray ids = (JSONArray) MyUser.getObjectByKey("classIds");
                    List<String> classIds = dealIds(ids);

                    BmobQuery<MyUser> query = new BmobQuery<MyUser>();
                    query.addWhereEqualTo("objectId", BmobUser.getCurrentUser().getObjectId());
                    query.findObjects(new FindListener<MyUser>() {
                        @Override
                        public void done(List<MyUser> object,BmobException e) {
                            if(e==null){
                             showToast(object.size()+"666");
                            }else{
                                showToast("erroe"+"666");
                            }
                        }
                    });




                    //判断是否已经加入了改班级
                    for (String classId : classIds) {

                        if (classId.equals(mClassId)) {
                            Toast.makeText(ClassDetailActivity.this, "您已经加入了改班级", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    classIds.add(mClassId);
                    Toast.makeText(ClassDetailActivity.this, "size:" + classIds.size(), Toast.LENGTH_SHORT).show();
                    myUser.setClassIds(classIds);
                    myUser.update(objectId, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                showToast("加入班级成功");
                                Intent intent = new Intent(ClassDetailActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                showToast("更新用户信息失败:" + e.getMessage());
                            }
                        }
                    });
                }

            }
        });


        mIvClassCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入班级二维码界面
                Intent intent = new Intent(ClassDetailActivity.this, ShowClassCodeActivity.class);
                intent.putExtra(Constant.INTENT_CLASS_ID, mClassId);
                startActivity(intent);
            }
        });
    }

    private List<String> dealIds(JSONArray ids) {
        List<String> idList = new ArrayList<>();
        for (int i = 0; i < ids.length(); i++){
            try {
                idList.add(ids.get(i).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return idList;
    }

    @Override
    public void initView() {
        mIvClassImg = (ImageView) findViewById(R.id.iv_class_img);
        mIvClassCode = (ImageView) findViewById(R.id.iv_class_code);
        mTvClassName = (TextView) findViewById(R.id.tv_className);
        mTvClassStuNum = (TextView) findViewById(R.id.tv_class_num);
        mBtnSendClassMsg = (Button) findViewById(R.id.btn_class_sendMsg);

    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_class_detail;
    }
}
