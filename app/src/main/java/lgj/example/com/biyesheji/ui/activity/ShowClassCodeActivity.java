package lgj.example.com.biyesheji.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.android.Intents;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import lgj.example.com.biyesheji.R;
import lgj.example.com.biyesheji.app.Constant;

public class ShowClassCodeActivity extends BaseActivity {

    private ImageView mIvClassCode;
    private String mClassId;

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent !=null){
            mClassId = intent.getStringExtra(Constant.INTENT_CLASS_ID);
        }
        Bitmap bp = encodeAsBitmap(mClassId);
        mIvClassCode.setImageBitmap(bp);
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void initView() {
        mIvClassCode = (ImageView) findViewById(R.id.iv_class_code);

    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_show_class_code;
    }

    private Bitmap encodeAsBitmap(String str) {
        Bitmap bitmap = null;
        BitMatrix result = null;
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            result = multiFormatWriter.encode(str, BarcodeFormat.QR_CODE, 200, 200);
            // 使用 ZXing Android Embedded 要写的代码
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.createBitmap(result);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException iae) { // ?
            return null;
        }

        // 如果不使用 ZXing Android Embedded 的话，要写的代码

//        int w = result.getWidth();
//        int h = result.getHeight();
//        int[] pixels = new int[w * h];
//        for (int y = 0; y < h; y++) {
//            int offset = y * w;
//            for (int x = 0; x < w; x++) {
//                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
//            }
//        }
//        bitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
//        bitmap.setPixels(pixels,0,100,0,0,w,h);

        return bitmap;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1001) {
            String content = data.getStringExtra(Intents.Scan.RESULT);
//            tvContent.setText(content);
            Toast.makeText(this, "aaaaaaaaaaaaaaaaaa" + content, Toast.LENGTH_SHORT).show();

            Toast.makeText(this, "bbbbbbbbbbbbbbbbbbbbbbbb", Toast.LENGTH_SHORT).show();

        }
    }
}
