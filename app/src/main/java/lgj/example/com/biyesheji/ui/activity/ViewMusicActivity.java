package lgj.example.com.biyesheji.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import lgj.example.com.biyesheji.R;


public class ViewMusicActivity extends AppCompatActivity {

    private JCVideoPlayerStandard mJCVideoPlayerStandard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_music);

        Intent intent = getIntent();
        String downloadpath = intent.getStringExtra("BmobFile");
        String mMusicName = intent.getStringExtra("mMusicName");

        mJCVideoPlayerStandard = (JCVideoPlayerStandard) findViewById(R.id.jiecao_Player);
        mJCVideoPlayerStandard.setUp(
                downloadpath, JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL,
                mMusicName);
        mJCVideoPlayerStandard.startVideo();
    }
    
    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()){
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
