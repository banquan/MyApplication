package lgj.example.com.biyesheji.model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by yu on 2017/10/9.
 */

public class AlbumBean extends BmobObject {



    private BmobFile show_first_image;
    private String title;
    private String quantity;
    private String classId;

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public BmobFile getShow_first_image() {
        return show_first_image;
    }
    public void setShow_first_image(BmobFile show_first_image) {
        this.show_first_image = show_first_image;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
