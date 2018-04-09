package com.upholstery.share.battery.mvp.modle.entity;

import java.util.List;

import cn.zcoder.xxp.base.net.BaseResponse;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/4/4
 * Description :网点列表
 */
public class NearTheSitesResponse extends BaseResponse {
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * address : 武侯区桂溪街道天香(天府五街)
         * contacts : 12344446666
         * costArge : 44
         * empty : 0
         * id : 209
         * lat : 30.536203
         * lng : 104.059952
         * logo : http://otvt0lhkm.bkt.clouddn.com/2017/09/03/21/14/56/111.jpg
         * name : 宜宾燃面
         * wall : http://otvt0lhkm.bkt.clouddn.com/2017/09/03/21/14/58/111.jpg
         */

        private String address;
        private String contacts;
        private int costArge;
        private int empty;
        private int id;
        private double lat;
        private double lng;
        private String logo;
        private String name;
        private String wall;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getContacts() {
            return contacts;
        }

        public void setContacts(String contacts) {
            this.contacts = contacts;
        }

        public int getCostArge() {
            return costArge;
        }

        public void setCostArge(int costArge) {
            this.costArge = costArge;
        }

        public int getEmpty() {
            return empty;
        }

        public void setEmpty(int empty) {
            this.empty = empty;
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

        public String getWall() {
            return wall;
        }

        public void setWall(String wall) {
            this.wall = wall;
        }
    }
}
