package lgj.example.com.biyesheji.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import lgj.example.com.biyesheji.R;
import uk.co.senab.photoview.PhotoView;

public class ViewPictureActivity extends AppCompatActivity {

    private PhotoView mPhotoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_picture);

        mPhotoView = (PhotoView) findViewById(R.id.one_pic_show);

        Intent intent = getIntent();
        String url = intent.getStringExtra("BmobFile");
        Picasso.with(this).load(url).into(mPhotoView);

    }
}
