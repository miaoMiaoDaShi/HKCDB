package com.upholstery.share.battery.mvp.modle.entity;

import com.google.gson.annotations.SerializedName;

import cn.zcoder.xxp.base.net.BaseResponse;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/4/4
 * Description : 正在使用的
 */
public class UsingOrderResponse extends BaseResponse {
    /**
     * data : {"createTime":1520405681000,"id":12499,"orderNo":"8374220162","sno":"2E000000","status":1}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * createTime : 1520405681000
         * id : 12499
         * orderNo : 8374220162
         * sno : 2E000000
         * status : 1
         */

        private long createTime;
        private int id;
        private String orderNo;
        private String sno;
        @SerializedName("status")
        private int statusX;

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

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
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
    }
}
