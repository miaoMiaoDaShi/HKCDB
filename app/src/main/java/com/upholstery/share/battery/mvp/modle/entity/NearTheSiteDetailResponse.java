package com.upholstery.share.battery.mvp.modle.entity;

import java.util.List;

import cn.zcoder.xxp.base.net.BaseResponse;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/4/4
 * Description : 网点的详细信息
 */
public class NearTheSiteDetailResponse extends BaseResponse {
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * address : 武侯区桂溪街道菁蓉国际广场5A天府软件园F区
         * canUseCount : 18
         * city : 成都市
         * contacts : 13658028547
         * freeNumCount : 12
         * id : 201
         * lat : 30.573095
         * lng : 104.066143
         * logo : http://otvt0lhkm.bkt.clouddn.com/2017/09/02/09/58/43/704-160Q01R032.png
         * name : 阿里巴巴
         * openTime : 周1/周5 10:00-22:00
         * prov : 四川省
         * wall : http://otvt0lhkm.bkt.clouddn.com/2017/09/02/09/58/59/704-160Q01R032.png
         */

        private String address;
        private int canUseCount;
        private String city;
        private String contacts;
        private String freeNumCount;
        private int id;
        private double lat;
        private double lng;
        private String logo;
        private String name;
        private String openTime;
        private String prov;
        private String wall;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getCanUseCount() {
            return canUseCount;
        }

        public void setCanUseCount(int canUseCount) {
            this.canUseCount = canUseCount;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getContacts() {
            return contacts;
        }

        public void setContacts(String contacts) {
            this.contacts = contacts;
        }

        public String getFreeNumCount() {
            return freeNumCount;
        }

        public void setFreeNumCount(String freeNumCount) {
            this.freeNumCount = freeNumCount;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOpenTime() {
            return openTime;
        }

        public void setOpenTime(String openTime) {
            this.openTime = openTime;
        }

        public String getProv() {
            return prov;
        }

        public void setProv(String prov) {
            this.prov = prov;
        }

        public String getWall() {
            return wall;
        }

        public void setWall(String wall) {
            this.wall = wall;
        }
    }
}
