package com.upholstery.share.battery.mvp.modle.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import cn.zcoder.xxp.base.net.BaseResponse;

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/9
 * Description : 商品訂單信息
 */
public class CommodityOrderListResponse extends BaseResponse {
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * actualMoney : 100
         * address : 沧泓科技打撒
         * area : 高新区
         * city : 成都市
         * name : 测试
         * point : 100
         * image : 123456.png
         * consignee : 李洋
         * createTime : 1525677767000
         * id : 1
         * linkPhone : 13980318235
         * money : 100
         * number : 1
         * orderNo : 123456
         * payWay : 3
         * productId : 10000
         * province : 四川省
         * sendWay : 0
         * status : 1
         * type : 1
         * userId : 3551
         */

        private int actualMoney;
        private String address;
        private String area;
        private String city;
        private String name;
        private String point;
        private String image;
        private String consignee;
        private long createTime;
        private int id;
        private String linkPhone;
        private int money;
        private int number;
        private String orderNo;
        private int payWay;
        private int productId;
        private String province;
        private int sendWay;
        @SerializedName("status")
        private int statusX;
        private int type;
        private int userId;

        public int getActualMoney() {
            return actualMoney;
        }

        public void setActualMoney(int actualMoney) {
            this.actualMoney = actualMoney;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPoint() {
            return point;
        }

        public void setPoint(String point) {
            this.point = point;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getConsignee() {
            return consignee;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee;
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

        public String getLinkPhone() {
            return linkPhone;
        }

        public void setLinkPhone(String linkPhone) {
            this.linkPhone = linkPhone;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public int getPayWay() {
            return payWay;
        }

        public void setPayWay(int payWay) {
            this.payWay = payWay;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public int getSendWay() {
            return sendWay;
        }

        public void setSendWay(int sendWay) {
            this.sendWay = sendWay;
        }

        public int getStatusX() {
            return statusX;
        }

        public void setStatusX(int statusX) {
            this.statusX = statusX;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
