package com.upholstery.share.battery.mvp.modle.entity;

import cn.zcoder.xxp.base.net.BaseResponse;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/4/4
 * Description :  银行卡的详细信息
 */
public class BankCardDetailResponse extends BaseResponse {
    /**
     * data : {"bankExpire":1520590000,"bankNo":"6228480402564890018","bankType":0}
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
         * bankExpire : 1520590000
         * bankNo : 6228480402564890018
         * bankType : 0
         */

        private int bankExpire;
        private String bankNo;
        private int bankType;
        private String area;

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public int getBankExpire() {
            return bankExpire;
        }

        public void setBankExpire(int bankExpire) {
            this.bankExpire = bankExpire;
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
    }
}
