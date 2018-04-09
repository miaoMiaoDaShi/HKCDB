package com.upholstery.share.battery.mvp.modle.entity;

import cn.zcoder.xxp.base.net.BaseResponse;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/3/31
 * Description :
 */
public class WalletResponse extends BaseResponse {
    /**
     * data : {"deposit":100000,"money":99900,"point":500,"zmxyStatus":1}
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
         * deposit : 100000
         * money : 99900
         * point : 500
         * zmxyStatus : 1
         */

        private int deposit;
        private int money;
        private int point;
        private int zmxyStatus;

        public int getDeposit() {
            return deposit;
        }

        public void setDeposit(int deposit) {
            this.deposit = deposit;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public int getPoint() {
            return point;
        }

        public void setPoint(int point) {
            this.point = point;
        }

        public int getZmxyStatus() {
            return zmxyStatus;
        }

        public void setZmxyStatus(int zmxyStatus) {
            this.zmxyStatus = zmxyStatus;
        }
    }
}
