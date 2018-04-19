package com.upholstery.share.battery.mvp.modle.entity;

import cn.zcoder.xxp.base.net.BaseResponse;

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/4/19
 * Description : 餘額支付
 */
public class PayByWalletResponse extends BaseResponse {
    /**
     * data : {"money":8,"orderno":"1234567890"}
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
         * money : 8
         * orderno : 1234567890
         */

        private int money;
        private String orderno;

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public String getOrderno() {
            return orderno;
        }

        public void setOrderno(String orderno) {
            this.orderno = orderno;
        }
    }
}
