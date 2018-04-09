package com.upholstery.share.battery.mvp.modle.entity;

import java.util.List;

import cn.zcoder.xxp.base.net.BaseResponse;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/3/31
 * Description : 優惠卷
 */
public class CouponResponse extends BaseResponse {
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * beginTime : 1504747276000
         * brief :
         * endTime : 1506734478000
         * id : 2
         * image : http://otvt0lhkm.bkt.clouddn.com/2017/09/18/13/35/02/u=4060019533,4286365943&fm=27&gp=0.jpg
         * url : http://www.baidu.com
         * worthMoney : 1
         * worthTime : 0
         */

        private long beginTime;
        private String brief;
        private long endTime;
        private int id;
        private String image;
        private String url;
        private int worthMoney;
        private int worthTime;

        public long getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(long beginTime) {
            this.beginTime = beginTime;
        }

        public String getBrief() {
            return brief;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getWorthMoney() {
            return worthMoney;
        }

        public void setWorthMoney(int worthMoney) {
            this.worthMoney = worthMoney;
        }

        public int getWorthTime() {
            return worthTime;
        }

        public void setWorthTime(int worthTime) {
            this.worthTime = worthTime;
        }
    }
}
