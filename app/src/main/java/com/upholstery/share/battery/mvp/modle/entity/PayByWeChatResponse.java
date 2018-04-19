package com.upholstery.share.battery.mvp.modle.entity;

import cn.zcoder.xxp.base.net.BaseResponse;

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/4/19
 * Description : 微信支付
 */
public class PayByWeChatResponse extends BaseResponse {
    /**
     * data : {"str":""}
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
         * str :
         */

        private String str;

        public String getStr() {
            return str;
        }

        public void setStr(String str) {
            this.str = str;
        }
    }
}
