package com.upholstery.share.battery.mvp.modle.entity;

import cn.zcoder.xxp.base.net.BaseResponse;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/4/3
 * Description : 用户的详细信息
 */
public class UserDetailResponse extends BaseResponse {

    /**
     * data : {"birth":28800000,"deposit":100000,"head":"http://wx.qlogo.cn/mmopen/CepOt2kn0MCkv4Pm5xqKLQacIM100afxviaIKGy7HMZp1Ybg33O9ItuyKUUamoYZx1ybEKZ7SynxOMibSUhtJSbqkqIXaGVp6M/0","money":99900,"name":"测试专用(勿动)","phone":13980318235,"point":0,"realname":"洋","sex":0,"surname":"李","type":1,"uid":212}
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
         * birth : 28800000
         * deposit : 100000
         * head : http://wx.qlogo.cn/mmopen/CepOt2kn0MCkv4Pm5xqKLQacIM100afxviaIKGy7HMZp1Ybg33O9ItuyKUUamoYZx1ybEKZ7SynxOMibSUhtJSbqkqIXaGVp6M/0
         * money : 99900
         * name : 测试专用(勿动)
         * phone : 13980318235
         * point : 0
         * realname : 洋
         * sex : 0
         * surname : 李
         * type : 1
         * uid : 212
         */

        private long birth ;
        private int deposit;
        private String head= "";
        private int money;
        private String name= "";
        private long phone;
        private int point;
        private String realname = "";
        private int sex;
        private String surname = "";
        private int type;
        private int uid;
        private String city = "";
        private String email= "";

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public long getBirth() {
            return birth;
        }

        public void setBirth(long birth) {
            this.birth = birth;
        }

        public int getDeposit() {
            return deposit;
        }

        public void setDeposit(int deposit) {
            this.deposit = deposit;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getPhone() {
            return phone;
        }

        public void setPhone(long phone) {
            this.phone = phone;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public int getPoint() {
            return point;
        }

        public void setPoint(int point) {
            this.point = point;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }
    }
}
