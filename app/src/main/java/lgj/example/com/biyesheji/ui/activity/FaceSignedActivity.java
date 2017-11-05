package lgj.example.com.biyesheji.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.baidu.aip.face.AipFace;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import lgj.example.com.biyesheji.R;
import lgj.example.com.biyesheji.model.FaceRecognizeTwoBean;
import lgj.example.com.biyesheji.model.MyUser;
import lgj.example.com.biyesheji.model.SignedBean;
import lgj.example.com.biyesheji.utils.BitmapCompressorUtil;
import lgj.example.com.biyesheji.utils.SharedpreferencesUtil;

import static cn.bmob.v3.BmobUser.getObjectByKey;
import static java.lang.System.out;

public class FaceSignedActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    SurfaceView mPreview;
    SurfaceHolder mHolder;
    Camera mcamera;
    int screenWidth, screenHeight;
    boolean isPreview = false; // 是否在浏览中
    private String ipname;
    int pic_name = 1;
    private Handler mHandler;
    private static final int REG_FACE = 1001;

    public static final String APP_ID = "9532759";
    public static final String API_KEY = "ItrqyhiVh5HCRLlKecrXB9SZ";
    public static final String SECRET_KEY = "0WtsdiSA3joN67YDALXH0YRaqMDv5lS3";
    private AipFace client;
    private Gson mGson = new Gson();
    private JSONObject mResponse;
    private boolean isStopGetFace = true;
    private String imgUrl = null;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_signed);

        initClient();
        getImgUrl();
        mPreview = (SurfaceView) findViewById(R.id.surface_view);
        screenHeight = 480;
        screenWidth = 640;
        mHolder = mPreview.getHolder();
        mHolder.addCallback(this);
        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == REG_FACE) {
                    //调用百度人脸识别
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            faceRecognize2(client);
                        }
                    }).start();

                }
                return true;
            }
        });

        //   mImageView = (ImageView) findViewById(R.id.iv_face);
    }

    private void getImgUrl() {
        Intent intent = getIntent();
        if (intent != null) {
            imgUrl = intent.getStringExtra("imgUrl");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mcamera == null) {
            mcamera = getMcamera();

            if (mHolder != null) {
                setStartPreview(mcamera, mHolder);
            }
        }
    }


    private void initClient() {
        // 初始化一个FaceClient
        client = new AipFace(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        new Thread(new Runnable() {
            @Override
            public void run() {
                client.addGroupUser("abc", "aa");
            }
        }).start();


    }


    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();

    }

    //在这里面添加了一些内容
    private Camera getMcamera() {
        Camera camera;
        try {
            camera = Camera.open(1);
            if (camera != null && !isPreview) {
                try {
                    Camera.Parameters parameters = camera.getParameters();
                    parameters.setPreviewSize(screenWidth, screenHeight); // 设置预览照片的大小
                    parameters.setPreviewFpsRange(20, 30); // 每秒显示20~30帧
                    parameters.setPictureFormat(ImageFormat.NV21); // 设置图片格式
                    parameters.setPictureSize(screenWidth, screenHeight); // 设置照片的大小
                    parameters.setPreviewFrameRate(2);// 每秒3帧 每秒从摄像头里面获得3个画面,
                    // camera.setParameters(parameters); // android2.3.3以后不需要此行代码
                    camera.setPreviewDisplay(mHolder); // 通过SurfaceView显示取景画面
                    camera.setPreviewCallback(new StreamIt(ipname)); // 设置回调的类
                    out.println("asasasasasasasasssssssssssssssssssssssssssss");
                    camera.startPreview(); // 开始预览
                    camera.autoFocus(null); // 自动对焦
                } catch (Exception e) {
                    e.printStackTrace();
                }
                isPreview = true;
            }
        } catch (Exception e) {
            camera = null;
            e.printStackTrace();
            Toast.makeText(this, "无法获取前置摄像头", Toast.LENGTH_LONG);
        }
        return camera;
    }

    //我新添加的关键类   保存图片名称为 pic_name +".jpg";
    class StreamIt implements Camera.PreviewCallback {
        private String ipname;

        public StreamIt(String ipname) {
            this.ipname = ipname;
        }

        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            Camera.Size size = camera.getParameters().getPreviewSize();
            try {
                // 调用image.compressToJpeg（）将YUV格式图像数据data转为jpg格式
                YuvImage image = new YuvImage(data, ImageFormat.NV21, size.width,
                        size.height, null);
                if (image != null) {

                    ByteArrayOutputStream outstream = new ByteArrayOutputStream();
                    image.compressToJpeg(new Rect(0, 0, size.width, size.height), 80, outstream);

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    image.compressToJpeg(new Rect(0, 0, size.width, size.height), 80, stream);
                    Bitmap bmp = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.size());
                    String picture_name = pic_name + ".jpg";
                    out.println(picture_name);

                    saveBitmap(bmp, picture_name);

                    pic_name = pic_name + 1;
                    out.println("aaaaaaaaaaaaaaaaaaaaaaaaassssssssssssssssssssssssssss");
                    outstream.flush();
                }
            } catch (Exception ex) {
                Log.e("Sys", "Error:" + ex.getMessage());
            }
        }
    }

    //新添加的保存到手机的方法
    private void saveBitmap(Bitmap bitmap, String bitName) throws IOException {
        File file = new File("/sdcard/DCIM/Camera/" + bitName);
        Log.e("AAA", "saveBitmap: " + "save sucess");

        Log.e("aaaaaaaaaaaaaaaaaaaaa", "saveBitmap: " + file.getAbsolutePath());
        if (file.exists()) {
            file.delete();
        }

        FileOutputStream out;
        try {
            if (isStopGetFace) {
                out = new FileOutputStream(file);
                if (bitmap.compress(Bitmap.CompressFormat.PNG, 90, out)) {
                    out.flush();
                    out.close();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //保存后调用百度人脸识别，检测是否属于同一个人，通过handler发起请求
        if (pic_name == 4) {
            isStopGetFace = false;
            progressDialog = new ProgressDialog(FaceSignedActivity.this);
            progressDialog.setTitle("提示");
            progressDialog.setMessage("识别中······");
            progressDialog.setCancelable(false);
            progressDialog.show();
            Log.e("aaaaaaaaaaa", "saveBitmap: " + "4444444444444444444444444444444");
            Message message = new Message();
            message.what = REG_FACE;
            mHandler.sendMessage(message);
        }

    }

    /*
       开始预览相机内容
        */
    private void setStartPreview(Camera camera, SurfaceHolder holder) {
        try {
            camera.setPreviewDisplay(holder);
            camera.setDisplayOrientation(90);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*
    释放相机资源
     */
    private void releaseCamera() {
        if (mcamera != null) {
            mcamera.setPreviewCallback(null);
            mcamera.stopPreview();
            mcamera.release();
            mcamera = null;
        }
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setStartPreview(mcamera, mHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        setStartPreview(mcamera, mHolder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseCamera();
    }


    //人脸比对
    public void faceRecognize2(final AipFace client) {
        // 参数为本地图片路径
        final ArrayList<String> pathArray = new ArrayList<String>();
        pathArray.add(imgUrl);
        pathArray.add("/sdcard/DCIM/Camera/4.jpg");

        Bitmap bit = BitmapFactory.decodeFile("/sdcard/DCIM/Camera/4.jpg");

        bit = BitmapCompressorUtil.compressBitmap(bit, 100);

        try {
            File file = new File("/sdcard/DCIM/Camera/4.jpg");
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream fos = new FileOutputStream(file);
            bit.compress(Bitmap.CompressFormat.JPEG, 90, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


//        Log.e("aaaaaaaaaaaa", "faceRecognize2: " + path1);


        mResponse = client.match(pathArray);


        Log.e("aaaaaaaaaa", "faceRecognize2: " + mResponse.toString());

        final FaceRecognizeTwoBean a = mGson.fromJson(mResponse.toString(), FaceRecognizeTwoBean.class);


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("aaaaaaaaaaa", "run: " + a);
                if (a != null) {
                    Log.e("aaaaaaaaaaaaaaaaa", "run: " + a.getResults().size());

                    if (a.getResults().get(0).getScore() >= 80) {
                        progressDialog.dismiss();

                        //上传学生信息到bmob
                        SignedBean signedBean = new SignedBean();
                        signedBean.setSignedPlace(SharedpreferencesUtil.getAddress(FaceSignedActivity.this, "address"));
                        signedBean.setSignedTime(new Date());
                        String stuId = (String) MyUser.getObjectByKey("objectId");
                        signedBean.setStuId(stuId);
                        String userName = (String) getObjectByKey("username");
                        signedBean.setStuName(userName);
                        signedBean.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
                                    Toast.makeText(FaceSignedActivity.this, "签到成功！！！", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(FaceSignedActivity.this, "签到失败！！！", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        });
//                        Intent intent = new Intent(FaceSignedActivity.this,ShowActivity.class);
//                        startActivity(intent);

                    } else {
                        Toast.makeText(FaceSignedActivity.this, "人脸识别失败！！！", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                } else {
                    Log.e("aaaaaaaaaaaaaa", "run: " + "nullnull-----------------------------------");
                }
            }
        });

        Log.i("dddddd", "faceRecognize2: " + mResponse.toString());
    }


}
