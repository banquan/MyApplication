package lgj.example.com.biyesheji.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/10/12.
 */

public class Announcement extends BmobObject {
    private String title;
    private String content;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
