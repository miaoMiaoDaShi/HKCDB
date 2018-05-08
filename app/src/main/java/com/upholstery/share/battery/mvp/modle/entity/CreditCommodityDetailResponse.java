package com.upholstery.share.battery.mvp.modle.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import cn.zcoder.xxp.base.net.BaseResponse;

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/7
 * Description : 积分兑换商品详情
 */
public class CreditCommodityDetailResponse extends BaseResponse {
    /**
     * data : {"brief":"爱仕达多撒","createTime":1525489263000,"details":"爱仕达多多多多多","id":10000,"image":"123456.png","name":"测试","number":1000,"point":50,"status":0,"taked":"500"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * brief : 爱仕达多撒
         * createTime : 1525489263000
         * details : 爱仕达多多多多多
         * id : 10000
         * image : 123456.png
         * name : 测试
         * number : 1000
         * point : 50
         * status : 0
         * taked : 500
         */

        private String brief;
        private long createTime;
        private String details;
        private int id;
        private String image;
        private String name;
        private int number;
        private int point;
        @SerializedName("status")
        private int statusX;
        private String taked;

        public String getBrief() {
            return brief;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
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

        public String getTaked() {
            return taked;
        }

        public void setTaked(String taked) {
            this.taked = taked;
        }
    }
}
