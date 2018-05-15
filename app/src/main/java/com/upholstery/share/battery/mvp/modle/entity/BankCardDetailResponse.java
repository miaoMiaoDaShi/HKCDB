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
     * data : {"area":"香港(中國)","bankCvv2":"256","bankExpire":56000,"bankName":"恐龙","bankNo":"256888","bankType":0,"id":8,"is_Default":0}
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
         * area : 香港(中國)
         * bankCvv2 : 256
         * bankExpire : 56000
         * bankName : 恐龙
         * bankNo : 256888
         * bankType : 0
         * id : 8
         * is_Default : 0
         */

        private String area;
        private String bankCvv2;
        private long bankExpire;
        private String bankName;
        private String bankNo;
        private int bankType;
        private int id;
        private int is_Default;

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getBankCvv2() {
            return bankCvv2;
        }

        public void setBankCvv2(String bankCvv2) {
            this.bankCvv2 = bankCvv2;
        }

        public long getBankExpire() {
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

        public int getIs_Default() {
            return is_Default;
        }

        public void setIs_Default(int is_Default) {
            this.is_Default = is_Default;
        }
    }
}
