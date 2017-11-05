package lgj.example.com.biyesheji.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.white.progressview.CircleProgressView;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemMoveListener;
import com.yarolegovich.lovelydialog.LovelyChoiceDialog;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.TimePicker;
import lgj.example.com.biyesheji.R;
import lgj.example.com.biyesheji.adapter.VoteListChoiceItemAdapter;
import lgj.example.com.biyesheji.listener.VoteItemListListener;
import lgj.example.com.biyesheji.model.ClassBean;
import lgj.example.com.biyesheji.model.VoteBean;

import static android.view.View.GONE;
import static cn.bmob.v3.BmobUser.getObjectByKey;

public class ClassVoteActivity extends BaseActivity implements VoteItemListListener{

    private EditText mClassVoteTitle;
    private SwipeMenuRecyclerView mVoteLanuchRecycleview;
    private Spinner mVoteClassKind;
    private ImageView mVoteClassAddImg;
    private LinearLayout mActivityClassVote;
    private Button mBtnLanuchVote;
    private String voteKind;//投票选择的类型
    private LinearLayout mVoteListAdd;
    private VoteListChoiceItemAdapter mVoteListChoiceItemAdapter;
    private List<Integer> mChoiceItemNum = new ArrayList<>();
    private ScrollView mScrollView;
    private TextView voteEndDate;
    private TextView voteEndTime;
    private TextView voteChoiceClass;
    private String EndTime;
    private String[] classNames;
    private String classId;
    private List<String> voteList = new ArrayList<>();
    private List<Integer> voteNumList = new ArrayList<>();
    private DatePicker endVoteDatePicker;
    private TimePicker endVoteTimePicker;
    private String endDate;
    private String endTime;
    private int voteSelect;
    private String voteTitle;
    private String creator;
    protected static final int GET_HEAD_IMG = 1001;
    private static final int CROP_HEAD = 1002;
    private String path;//图片在手机的路径。
    private String voteImg;
    private CircleProgressView mCircleProgressView;

    @Override
    protected void initData() {
        creator =(String) getObjectByKey("username");
    }

    @Override
    protected void initListener() {
        //选择投票类型，单选，双选，或无限制
        mVoteClassKind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] voteKinds = getResources().getStringArray(R.array.vote_kind);
                voteKind = voteKinds[position];
                voteSelect = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //点击发起投票
        mBtnLanuchVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击发起投票

                //初始化选票
                initVoteNums();

                //获取投票信息
                getVoteMsg();

                //保存投票信息到bmob
                VoteBean voteBean = new VoteBean();
                voteBean.setClassId(classId);
                voteBean.setCreater(creator);
                voteBean.setCreateTime(new Date().toString());
                voteBean.setEndTime(EndTime);
                voteBean.setVoteContent(voteList);
                voteBean.setVoteNums(voteNumList);
                voteBean.setVoteId(UUID.randomUUID().toString());
                voteBean.setVoteSelect(voteSelect);
                voteBean.setVoteTitle(voteTitle);
                if (voteImg != null){
                    voteBean.setVoteImgUrl(voteImg);
                }

                voteBean.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null){
                            showToast("发布投票成功！！！");
                        }else{
                            showToast("发布投票失败！！！");
                        }
                    }
                });
            }
        });


        //添加选项
        mVoteListAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加选项
                if (mChoiceItemNum.size() >= 15){
                    showToast("最多添加15项");
                    return;
                }
                mChoiceItemNum.add(1);
                mVoteListChoiceItemAdapter.changeData(mChoiceItemNum);
            }
        });

        //投票结束时间
        voteEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endVoteTimePicker.show();
            }
        });
        //投票结束日期
        voteEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endVoteDatePicker.show();
            }
        });

        //选择班级
        voteChoiceClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choiceClass();
            }
        });
    }

    private void getVoteMsg() {
         voteTitle = mClassVoteTitle.getText().toString();
        if (voteTitle == null || voteTitle.trim().equals("")){
            showToast("投票主题不能为空！！！");
            return;
        }

        voteTitle = voteTitle.trim();
        EndTime = endDate + " " + endTime;
    }

    private void initVoteNums() {
        for (int i = 0; i < voteList.size(); i++){
            voteNumList.add(i,0);
        }
    }

    @Override
    public void initView() {

        mChoiceItemNum.add(0);
        mChoiceItemNum.add(1);
        voteEndDate = (TextView) findViewById(R.id.voteEndDate);
        voteEndTime = (TextView) findViewById(R.id.voteEndTime);
        voteChoiceClass = (TextView) findViewById(R.id.vote_choice_class);

        mScrollView = (ScrollView) findViewById(R.id.scollview);
        mClassVoteTitle = (EditText) findViewById(R.id.class_vote_title);
        mVoteLanuchRecycleview = (SwipeMenuRecyclerView) findViewById(R.id.vote_lanuch_recycleview);
        mVoteClassKind = (Spinner) findViewById(R.id.vote_class_kind);
        mVoteClassAddImg = (ImageView) findViewById(R.id.vote_class_add_img);
        mBtnLanuchVote = (Button) findViewById(R.id.btn_lanuch_vote);
        mVoteLanuchRecycleview.setItemViewSwipeEnabled(true);
        mVoteListAdd = (LinearLayout) findViewById(R.id.vote_class_add_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ClassVoteActivity.this);
        mVoteListChoiceItemAdapter = new VoteListChoiceItemAdapter(mChoiceItemNum,ClassVoteActivity.this);
        mVoteLanuchRecycleview.setAdapter(mVoteListChoiceItemAdapter);
        mVoteLanuchRecycleview.setLayoutManager(layoutManager);
        mVoteListChoiceItemAdapter.notifyDataSetChanged();

        endVoteDatePicker = new DatePicker(ClassVoteActivity.this, DatePicker.YEAR_MONTH_DAY);
        endVoteTimePicker = new TimePicker(ClassVoteActivity.this,TimePicker.HOUR_OF_DAY);

        //日期监听
        endVoteDatePicker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                endDate = year + "-" + month + "-" + day;
                voteEndDate.setText(endDate);
            }
        });

        //时间监听
        endVoteTimePicker.setOnTimePickListener(new TimePicker.OnTimePickListener() {
            @Override
            public void onTimePicked(String hour, String minute) {
                endTime = hour + ":" + minute;
                voteEndTime.setText(endTime);
            }
        });

        //选择图片
        mVoteClassAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getpic();
            }
        });

        mCircleProgressView = (CircleProgressView) findViewById(R.id.circle_progress_normal);

    }

    @Override
    public int getLayoutRes() {

        return R.layout.activity_class_vote;
    }


    //监听侧滑删除，更新UI
    OnItemMoveListener mItemMoveListener = new OnItemMoveListener() {

        @Override
        public boolean onItemMove(RecyclerView.ViewHolder srcHolder, RecyclerView.ViewHolder targetHolder) {
            return false;
        }

        @Override
        public void onItemDismiss(RecyclerView.ViewHolder srcHolder) {
            //删除选项
            int position = srcHolder.getAdapterPosition();
            mChoiceItemNum.remove(position);
            mVoteListChoiceItemAdapter.changeData(mChoiceItemNum);
        }
    };


    private void choiceClass() {
        //查询当前用户所加入的班级
        JSONArray ids = (JSONArray) getObjectByKey("classIds");
        final List<String> classIds = dealIds(ids);

        String[] items = new String[classIds.size()];
        for (int i = 0; i < items.length; i++) {
            items[i] = classIds.get(i);
        }

        BmobQuery<ClassBean> query = new BmobQuery<ClassBean>();
        query.addWhereContainedIn("objectId", classIds);
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

                            new LovelyChoiceDialog(ClassVoteActivity.this, R.style.CheckBoxTintTheme)
                                    .setTopColorRes(R.color.qq_blue)
                                    .setTitle("选择班级")
                                    .setIcon(R.drawable.alerter_ic_face)
                                    .setItemsMultiChoice(classNames, new LovelyChoiceDialog.OnItemsSelectedListener<String>() {
                                        @Override
                                        public void onItemsSelected(List<Integer> positions, List<String> items) {
                                            classId = classIds.get(positions.get(0));
                                            voteChoiceClass.setText(items.get(positions.get(0)));
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

    @Override
    public void getVoteItemList(List<String> voteList) {
        this.voteList = voteList;
        Log.e("aaaaaaaaaaaaaa", "getVoteItemList: " + voteList.size());
    }

    private void getpic() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, GET_HEAD_IMG);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

//        if(data.getData() == null){
//            Toast.makeText(this, "你还没选中图片", Toast.LENGTH_SHORT).show();
//            //存在如果没有选择图片就会崩溃的bug。
//            return;
//        }


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
            mCircleProgressView.setVisibility(View.VISIBLE);
            uploadPic(path);
        }
    }

    public void uploadPic(final String path) {
        if (path == null) {
            return;
        }
       final BmobFile bmobFile = new BmobFile(new File(path));
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {

                    voteImg = bmobFile.getFileUrl();
                } else {
                }
            }
            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
                mCircleProgressView.setProgress(value);
                if(value == 100){
                    mCircleProgressView.setVisibility(GONE);
                }
            }
        });
    }
}
