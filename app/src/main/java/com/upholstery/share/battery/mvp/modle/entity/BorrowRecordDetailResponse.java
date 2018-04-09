package com.upholstery.share.battery.mvp.modle.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import cn.zcoder.xxp.base.net.BaseResponse;


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/4/8
 * Description :
 */

public class BorrowRecordDetailResponse extends BaseResponse {
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * address : 天府三街
         * cost : 100
         * createTime : 1520407841000
         * id : 12499
         * orderno : 8374220162
         * sno : 29000000
         * status : 3
         * used : 120
         */

        private String address;
        private int cost;
        private long createTime;
        private int id;
        private String orderno;
        private String sno;
        @SerializedName("status")
        private int statusX;
        private int used;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getCost() {
            return cost;
        }

        public void setCost(int cost) {
            this.cost = cost;
        }

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

        public String getOrderno() {
            return orderno;
        }

        public void setOrderno(String orderno) {
            this.orderno = orderno;
        }

        public String getSno() {
            return sno;
        }

        public void setSno(String sno) {
            this.sno = sno;
        }

        public int getStatusX() {
            return statusX;
        }

        public void setStatusX(int statusX) {
            this.statusX = statusX;
        }

        public int getUsed() {
            return used;
        }

        public void setUsed(int used) {
            this.used = used;
        }
    }
}
