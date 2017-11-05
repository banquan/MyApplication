package lgj.example.com.biyesheji.model;

/**
 * Created by yhdj on 2017/11/4.
 */

public class LaunchSignedRealTimeData {

    /**
     * appKey : d659d0fdc9ec4ed34f83721af8d83327
     * tableName : LanuchSignedBean
     * objectId :
     * action : updateTable
     * data : {"className":"15软件2班","createdAt":"2017-11-04 17:43:16","creatorId":"fmQNttt8","creatorName":"admin","lanuchSignedClassId":"bf7eb7d1c2","lanuchSignedTime":"Nov 4, 2017 5:43:17 PM","objectId":"db55d5c967","updatedAt":"2017-11-04 17:43:16"}
     */

    private String appKey;
    private String tableName;
    private String objectId;
    private String action;
    /**
     * className : 15软件2班
     * createdAt : 2017-11-04 17:43:16
     * creatorId : fmQNttt8
     * creatorName : admin
     * lanuchSignedClassId : bf7eb7d1c2
     * lanuchSignedTime : Nov 4, 2017 5:43:17 PM
     * objectId : db55d5c967
     * updatedAt : 2017-11-04 17:43:16
     */

    private DataBean data;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String className;
        private String createdAt;
        private String creatorId;
        private String creatorName;
        private String lanuchSignedClassId;
        private String lanuchSignedTime;
        private String objectId;
        private String updatedAt;

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getCreatorId() {
            return creatorId;
        }

        public void setCreatorId(String creatorId) {
            this.creatorId = creatorId;
        }

        public String getCreatorName() {
            return creatorName;
        }

        public void setCreatorName(String creatorName) {
            this.creatorName = creatorName;
        }

        public String getLanuchSignedClassId() {
            return lanuchSignedClassId;
        }

        public void setLanuchSignedClassId(String lanuchSignedClassId) {
            this.lanuchSignedClassId = lanuchSignedClassId;
        }

        public String getLanuchSignedTime() {
            return lanuchSignedTime;
        }

        public void setLanuchSignedTime(String lanuchSignedTime) {
            this.lanuchSignedTime = lanuchSignedTime;
        }

        public String getObjectId() {
            return objectId;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }
    }
}
