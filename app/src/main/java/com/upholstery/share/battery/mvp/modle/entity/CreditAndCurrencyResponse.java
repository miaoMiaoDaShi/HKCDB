package com.upholstery.share.battery.mvp.modle.entity;

import cn.zcoder.xxp.base.net.BaseResponse;

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/7
 * Description : 积分和虚拟货币
 */
public class CreditAndCurrencyResponse extends BaseResponse {
    /**
     * data : {"point":0,"trustPoint":100}
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
         * point : 0
         * trustPoint : 100
         */

        private int point;
        private int trustPoint;

        public int getPoint() {
            return point;
        }

        public void setPoint(int point) {
            this.point = point;
        }

        public int getTrustPoint() {
            return trustPoint;
        }

        public void setTrustPoint(int trustPoint) {
            this.trustPoint = trustPoint;
        }
    }
}
