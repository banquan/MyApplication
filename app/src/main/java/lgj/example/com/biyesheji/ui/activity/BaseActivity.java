package lgj.example.com.biyesheji.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity {

    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        initView();
        initListener();
        initData();
    }

    protected abstract void initData();

    protected abstract void initListener();

    public abstract void initView();

    public abstract int getLayoutRes() ;

    public void showToast(String msg){
        if(mToast == null){
            mToast = Toast.makeText(this,"",Toast.LENGTH_LONG);
        }
        mToast.setText(msg);
        mToast.show();
    }

}
