package com.upholstery.share.battery.mvp.modle.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import cn.zcoder.xxp.base.net.BaseResponse;

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/7
 * Description : 信用积分记录列表
 */
public class CreditRecordListResponse extends BaseResponse {
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * createTime : 1525507558000
         * id : 1
         * point : 5
         * pointAfter : 105
         * status : 1
         * usedTo : 用户3551邀请新用户注册，获得信誉积分+5
         * userId : 3551
         */

        private long createTime;
        private int id;
        private int point;
        private int pointAfter;
        @SerializedName("status")
        private int statusX;
        private String usedTo;
        private int userId;

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPoint() {
            return point;
        }

        public void setPoint(int point) {
            this.point = point;
        }

        public int getPointAfter() {
            return pointAfter;
        }

        public void setPointAfter(int pointAfter) {
            this.pointAfter = pointAfter;
        }

        public int getStatusX() {
            return statusX;
        }

        public void setStatusX(int statusX) {
            this.statusX = statusX;
        }

        public String getUsedTo() {
            return usedTo;
        }

        public void setUsedTo(String usedTo) {
            this.usedTo = usedTo;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
