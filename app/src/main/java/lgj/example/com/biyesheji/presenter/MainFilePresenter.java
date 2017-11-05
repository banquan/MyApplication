package lgj.example.com.biyesheji.presenter;

/**
 * Created by yu on 2017/10/19.
 */

public interface MainFilePresenter {
    void test();

    void uploadDocument(String path, String mName, String fileSize, String mType);

    void uploadPicture(String path, String name, String fileSize, String mType);

    void uploadOther(String path, String name, String fileSize, String mType);

    void uploadMusic(String path, String name, String fileSize, String mType);

    void uploadVideo(String path, String name, String fileSize, String mType);
}
