package bean;

import java.util.List;

public class UserList {

    private List<ResultsBean> results;

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * collections : {"__type":"Relation","className":"CollectionSong"}
         * contribution : 8
         * createdAt : 2017-05-26 03:21:42
         * email : 863253820@qq.com
         * headImg : http://bmob-cdn-6553.b0.upaiyun.com/2017/05/04/af2010244002047a80bccc5e1d1a4e81.jpg
         * lastSignInDate : 2017.05.29 15:16
         * nickName : 闵超强
         * objectId : 00181a3ec9
         * updatedAt : 2017-05-29 15:16:47
         * username : 863253820@qq.com
         * signature : 这个人没有签名哦~
         * versionFlag : 5.6
         */

        private CollectionsBean collections;
        private int contribution;
        private String createdAt;
        private String email;
        private String headImg;
        private String lastSignInDate;
        private String nickName;
        private String objectId;
        private String updatedAt;
        private String username;
        private String signature;
        private String versionFlag;

        public CollectionsBean getCollections() {
            return collections;
        }

        public void setCollections(CollectionsBean collections) {
            this.collections = collections;
        }

        public int getContribution() {
            return contribution;
        }

        public void setContribution(int contribution) {
            this.contribution = contribution;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getLastSignInDate() {
            return lastSignInDate;
        }

        public void setLastSignInDate(String lastSignInDate) {
            this.lastSignInDate = lastSignInDate;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
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

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public String getVersionFlag() {
            return versionFlag;
        }

        public void setVersionFlag(String versionFlag) {
            this.versionFlag = versionFlag;
        }

        public static class CollectionsBean {
            /**
             * __type : Relation
             * className : CollectionSong
             */

            private String __type;
            private String className;

            public String get__type() {
                return __type;
            }

            public void set__type(String __type) {
                this.__type = __type;
            }

            public String getClassName() {
                return className;
            }

            public void setClassName(String className) {
                this.className = className;
            }
        }
    }
}
