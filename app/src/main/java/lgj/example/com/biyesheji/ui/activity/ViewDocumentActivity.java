package lgj.example.com.biyesheji.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import lgj.example.com.biyesheji.R;
import lgj.example.com.biyesheji.tbs.SuperFileView2;


public class ViewDocumentActivity extends AppCompatActivity {

    private SuperFileView2 mSuperFileView2;
    private String documentPath = null;
    private String getFileUrl;
//    private String getUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_document);

//        //绑定控件
//        mSuperFileView2 = (SuperFileView2) findViewById(R.id.displayDocumentView);
//
        //获取从适配器拿里传送过来的文件下载的路径。
        Intent intent = getIntent();
        if (intent != null) {
            documentPath = intent.getStringExtra("documentPath");

            getFileUrl = intent.getStringExtra("getFileUrl");////////////////////////////////////
//            getUrl = intent.getStringExtra("getUrl");
            Log.e("ChildOneAdpater---",documentPath);
        }
//
//        Log.e("getFilePath" , documentPath);
//
//        //再将路径封装成File对象
//        File file = new File(documentPath);
//        mSuperFileView2.displayFile(file);



        WebView wv_office = (WebView) findViewById(R.id.wv_office);

        wv_office.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url);

            }
        });

        // webview必须设置支持Javascript才可打开
        wv_office.getSettings().setJavaScriptEnabled(true);

        // 设置此属性,可任意比例缩放
        wv_office.getSettings().setUseWideViewPort(true);

        //通过在线预览office文档的地址加载
        wv_office.loadUrl("https://view.officeapps.live.com/op/view.aspx?src="+getFileUrl);

    }

}
