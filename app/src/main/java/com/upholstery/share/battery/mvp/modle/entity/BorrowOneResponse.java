package com.upholstery.share.battery.mvp.modle.entity;

import cn.zcoder.xxp.base.net.BaseResponse;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/4/13
 * Description :
 */
public class BorrowOneResponse extends BaseResponse {
    /**
     *  total : 1
     * data : {"orderno":32323}
     * msg : SUCCESS
     */

    private int total;
    private DataBean data;
    private String msg;

    public static class DataBean {
        /**
         * orderno : 32323
         */

        private int orderno;

        public int getOrderno() {
            return orderno;
        }

        public void setOrderno(int orderno) {
            this.orderno = orderno;
        }
    }
}
