package com.upholstery.share.battery.mvp.modle.entity;

import cn.zcoder.xxp.base.net.BaseResponse;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/3/31
 * Description :
 */
public class UserResponse extends BaseResponse {
    /**
     *  status  : 0
     * data : {"head":"213","name":"asd","point":0,"status":0,"token":"7469943894a4889b2836f1aa369ce8a5","type":0,"uid":1}
     */
    private DataBean data;

    public static class DataBean {
        /**
         * head : 213
         * name : asd
         * point : 0
         * status : 0
         * token : 7469943894a4889b2836f1aa369ce8a5
         * type : 0
         * uid : 1
         */

        private String head;
        private String name;
        private int point;
        @com.google.gson.annotations.SerializedName("status")
        private int statusX;
        private String token;
        private int type;
        private int uid;

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPoint() {
            return point;
        }

        public void setPoint(int point) {
            this.point = point;
        }

        public int getStatusX() {
            return statusX;
        }

        public void setStatusX(int statusX) {
            this.statusX = statusX;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }
}
