package lgj.example.com.biyesheji.model;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by yhdj on 2017/10/25.
 * 投票类
 */

public class VoteBean extends BmobObject{
    private String voteId;

    /**
     * 创建人id
     */
    private String createrId;

    /**
     * 创建人名称
     */
    private String creater;

    /**
     * 投票创建时间
     */
    private String createTime;

    /**
     * 投票结束时间
     */
    private String endTime;

    /**
     * 投票标题
     */
    private String voteTitle;

    /**
     * 投票内容
     */
    private List<String> voteContent;

    /**
     * 投票最多选多少项 1:1项 2:2项。。。
     */
    private int voteSelect;

    private List<Integer> voteNums;

    public List<Integer> getVoteNums() {
        return voteNums;
    }

    public void setVoteNums(List<Integer> voteNums) {
        this.voteNums = voteNums;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    /**
     * 发起人所在的班级id

     */
    private String classId;

    public String getVoteImgUrl() {
        return voteImgUrl;
    }



    public void setVoteImgUrl(String voteImgUrl) {
        this.voteImgUrl = voteImgUrl;
    }

    public String getVoteId() {
        return voteId;
    }

    public void setVoteId(String voteId) {
        this.voteId = voteId;
    }

    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getVoteTitle() {
        return voteTitle;
    }

    public void setVoteTitle(String voteTitle) {
        this.voteTitle = voteTitle;
    }

    public List<String> getVoteContent() {
        return voteContent;
    }

    public void setVoteContent(List<String> voteContent) {
        this.voteContent = voteContent;
    }

    public int getVoteSelect() {
        return voteSelect;
    }

    public void setVoteSelect(int voteSelect) {
        this.voteSelect = voteSelect;
    }

    /**
     * 投票相关图片URL
     */
    private String voteImgUrl;


}
