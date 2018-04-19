package com.upholstery.share.battery.mvp.modle.entity;

import cn.zcoder.xxp.base.net.BaseResponse;

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/4/19
 * Description : 支付寶支付
 */
public class PayByAlipayResponse extends BaseResponse {
    /**
     * data : {"orderno":"XXXX"}
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
         * orderno : XXXX
         */

        private String orderno;

        public String getOrderno() {
            return orderno;
        }

        public void setOrderno(String orderno) {
            this.orderno = orderno;
        }
    }
}
