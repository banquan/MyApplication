package lgj.example.com.biyesheji.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lgj.example.com.biyesheji.R;

import static com.tencent.smtt.sdk.TbsReaderView.TAG;

/**
 * Created by yhdj on 2017/9/27.
 */

public class FileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("+++++++++", "MainFragment");

        View mView = inflater.inflate(R.layout.fragment_file, container, false);



        return mView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG, "onActivityResult: " );
        super.onActivityResult(requestCode, resultCode, data);
    }
}
