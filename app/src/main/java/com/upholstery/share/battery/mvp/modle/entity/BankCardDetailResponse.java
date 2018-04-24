package com.upholstery.share.battery.mvp.modle.entity;

import java.util.List;

import cn.zcoder.xxp.base.net.BaseResponse;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/4/4
 * Description :  银行卡的详细信息
 */
public class BankCardDetailResponse extends BaseResponse {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * area : 香港(中國)
         * bankCvv : 325
         * bankExpire : 1000
         * bankName : 哈哈
         * bankNo : 56666666
         * bankType : 0
         * id : 7
         * isDefault : 0
         * userName : 喵喵
         */

        private String area;
        private String bankCvv;
        private int bankExpire;
        private String bankName;
        private String bankNo;
        private int bankType;
        private int id;
        private int isDefault;
        private String userName;

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getBankCvv() {
            return bankCvv;
        }

        public void setBankCvv(String bankCvv) {
            this.bankCvv = bankCvv;
        }

        public int getBankExpire() {
            return bankExpire;
        }

        public void setBankExpire(int bankExpire) {
            this.bankExpire = bankExpire;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getBankNo() {
            return bankNo;
        }

        public void setBankNo(String bankNo) {
            this.bankNo = bankNo;
        }

        public int getBankType() {
            return bankType;
        }

        public void setBankType(int bankType) {
            this.bankType = bankType;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIsDefault() {
            return isDefault;
        }

        public void setIsDefault(int isDefault) {
            this.isDefault = isDefault;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
