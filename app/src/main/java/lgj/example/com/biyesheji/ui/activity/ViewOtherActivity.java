package lgj.example.com.biyesheji.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import lgj.example.com.biyesheji.R;
import lgj.example.com.biyesheji.tbs.SuperFileView2;
import lgj.example.com.biyesheji.utils.ZipUtils;


public class ViewOtherActivity extends AppCompatActivity {

    private SuperFileView2 mSuperFileView2;
    private String documentPath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_other);


        //绑定控件
        mSuperFileView2 = (SuperFileView2) findViewById(R.id.displayDocumentView);

        //获取从适配器拿里传送过来的文件下载的路径。
        getFilePath();


        Toast.makeText(this, "getFilePath" + documentPath, Toast.LENGTH_SHORT).show();

        //再将路径封装成File对象
        File file = new File(documentPath);
//        mSuperFileView2.displayFile(file);




        //工具类调用的方法。
        ArrayList<String> names = null;
        try {
             names = ZipUtils.getEntriesNames(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String name : names){
            Log.e("aaaaaaaaaaaaa", "onCreate: " + name);
        }



    }

    public void getFilePath() {
        Intent intent = getIntent();
        if (intent != null) {
            documentPath = intent.getStringExtra("documentPath");
        }
    }
}

//10-21 22:43:52.348 31521-31521/cn.tablayouttest E/aaaaaaaaaaaaa: onCreate: fuck/
//10-21 22:43:52.348 31521-31521/cn.tablayouttest E/aaaaaaaaaaaaa: onCreate: fuck/two/
//10-21 22:43:52.348 31521-31521/cn.tablayouttest E/aaaaaaaaaaaaa: onCreate: fuck/two/c.txt
//10-21 22:43:52.348 31521-31521/cn.tablayouttest E/aaaaaaaaaaaaa: onCreate: fuck/two/d.txt
//10-21 22:43:52.348 31521-31521/cn.tablayouttest E/aaaaaaaaaaaaa: onCreate: fuck/one/
//10-21 22:43:52.348 31521-31521/cn.tablayouttest E/aaaaaaaaaaaaa: onCreate: fuck/one/a.txt
//10-21 22:43:52.348 31521-31521/cn.tablayouttest E/aaaaaaaaaaaaa: onCreate: fuck/one/b.txt

//E/aaaaaaaaaaaaa: onCreate: new 3.txt