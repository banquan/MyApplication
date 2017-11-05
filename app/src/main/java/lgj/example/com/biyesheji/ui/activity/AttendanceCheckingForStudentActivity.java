package lgj.example.com.biyesheji.ui.activity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yarolegovich.lovelydialog.LovelyChoiceDialog;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.qqtheme.framework.picker.DatePicker;
import lgj.example.com.biyesheji.R;
import lgj.example.com.biyesheji.model.ClassBean;
import lgj.example.com.biyesheji.model.LeaveBean;
import lgj.example.com.biyesheji.model.MyUser;

import static cn.bmob.v3.BmobUser.getObjectByKey;

public class AttendanceCheckingForStudentActivity extends BaseActivity {


    @BindView(R.id.tv_schedule_title)
    TextView mTvScheduleTitle;
    @BindView(R.id.tv_leave)
    TextView mTvLeave;
    @BindView(R.id.edt_leave_name)
    EditText mEdtLeaveName;
    @BindView(R.id.edt_leave_num)
    EditText mEdtLeaveNum;
    @BindView(R.id.edt_leave_class)
    EditText mEdtLeaveClass;
    @BindView(R.id.edt_leave_date)
    EditText mEdtLeaveDate;
    @BindView(R.id.edt_leave_reason)
    EditText mEdtLeaveReason;
    @BindView(R.id.btn_leave)
    Button mBtnLeave;
    @BindView(R.id.linearLayout_leave)
    LinearLayout mLinearLayoutLeave;
    @BindView(R.id.activity_attendance_checking)
    LinearLayout mActivityAttendanceChecking;
    @BindView(R.id.tv_leave_beginTime)
    TextView mTvLeaveBeginTime;
    @BindView(R.id.tv_leave_endTime)
    TextView mTvLeaveEndTime;
    @BindView(R.id.btn_choice_class)
    Button mBtnChoiceClass;

    private String beginTime;
    private String endTime;
    private String name;
    private String num;
    private String className;
    private String reason;
    private DatePicker beginPicker;
    private DatePicker endPicker;
    private String stuId;
    private String stuName;
    private String classId;
    private String[] classNames;


    @Override
    protected void initData() {
        stuId = (String) MyUser.getObjectByKey("objectId");
        stuName = (String) getObjectByKey("username");
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        beginPicker = new DatePicker(this, DatePicker.MONTH_DAY);
        endPicker = new DatePicker(this, DatePicker.MONTH_DAY);

        beginPicker.setOnDatePickListener(new DatePicker.OnMonthDayPickListener() {
            @Override
            public void onDatePicked(String month, String day) {
                beginTime = month + "月" + day + "日";
                mTvLeaveBeginTime.setText(beginTime);
            }
        });

        endPicker.setOnDatePickListener(new DatePicker.OnMonthDayPickListener() {
            @Override
            public void onDatePicked(String month, String day) {
                endTime = month + "月" + day + "日";
                mTvLeaveEndTime.setText(endTime);
            }
        });

    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_attendance_checking_for_student;
    }


    @OnClick(R.id.btn_leave)
    public void onClick() {
        if (getLeaveStuMsg()) {
            if (beginTime == null || endTime == null) {
                showToast("日期不能为空");
            } else {
                //保存请假理由
                LeaveBean leaveBean = new LeaveBean();
                leaveBean.setLeaveCreateTime(new Date());
                leaveBean.setStuId(stuId);
                leaveBean.setStuName(stuName);
                leaveBean.setLeaveReason(reason);
                leaveBean.setLeaveStartTime(beginTime);
                leaveBean.setLeaveEndTime(endTime);
                leaveBean.setClassName(className);
                leaveBean.setPassed(false);
                leaveBean.setClassId(classId);
                leaveBean.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {
                            showToast("提交成功，等待管理员审核通过！！！");
                        } else {
                            showToast("提交失败！！！");
                        }
                    }
                });
            }
        }
    }

    private boolean getLeaveStuMsg() {
        name = mEdtLeaveName.getText().toString();
        num = mEdtLeaveNum.getText().toString();
        className = mEdtLeaveClass.getText().toString();
        reason = mEdtLeaveReason.getText().toString();
        if (name == null || name.trim().equals("")) {
            showToast("姓名不能为空！！！");
            return false;
        } else if (num == null || num.trim().equals("")) {
            showToast("学号不能为空！！！");
            return false;
        } else if (className == null || className.trim().equals("")) {
            showToast("班级名称不能为空！！！");
            return false;
        } else if (reason == null || reason.trim().equals("")) {
            showToast("理由不能为空！！！");
            return false;
        }
        return true;
    }


    @OnClick({R.id.tv_leave_beginTime, R.id.tv_leave_endTime,R.id.btn_choice_class})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_leave_beginTime:
                beginPicker.show();
                break;
            case R.id.tv_leave_endTime:
                endPicker.show();
                break;
            case R.id.btn_choice_class:
                choiceClass();
                break;
        }
    }

    private void choiceClass() {
        //查询当前用户所加入的班级
        JSONArray ids = (JSONArray) getObjectByKey("classIds");
        final List<String> classIds = dealIds(ids);

        String[] items = new String[classIds.size()];
        for (int i = 0; i < items.length; i++){
            items[i] = classIds.get(i);
        }

        BmobQuery<ClassBean> query = new BmobQuery<ClassBean>();
        query.addWhereContainedIn("objectId",classIds);
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(1000);
        //执行查询方法
        query.findObjects(new FindListener<ClassBean>() {
            @Override
            public void done(List<ClassBean> object, BmobException e) {
                if (e == null) {
                    showToast("查询成功：共" + object.size() + "条数据。");
                    classNames = new String[object.size()];
                    for (int i = 0; i < object.size(); i++) {
                       classNames[i] = object.get(i).getClassName();
                    }

                    //弹出班级选择框
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            new LovelyChoiceDialog(AttendanceCheckingForStudentActivity.this, R.style.CheckBoxTintTheme)
                                    .setTopColorRes(R.color.qq_blue)
                                    .setTitle("选择班级")
                                    .setIcon(R.drawable.alerter_ic_face)
                                    .setItemsMultiChoice(classNames, new LovelyChoiceDialog.OnItemsSelectedListener<String>() {
                                        @Override
                                        public void onItemsSelected(List<Integer> positions, List<String> items) {
                                            classId = classIds.get(positions.get(0));
                                            mEdtLeaveClass.setText(items.get(positions.get(0)));
                                        }
                                    })
                                    .show();

                        }
                    });

                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });


    }

    //解析班级id，将jsonArray组装成list
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

}
