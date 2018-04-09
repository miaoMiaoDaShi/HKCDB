package com.upholstery.share.battery.mvp.modle.entity;

import java.util.List;

import cn.zcoder.xxp.base.net.BaseResponse;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/3/31
 * Description :
 */
public class InvitesResponse extends BaseResponse {
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * createTime : 1520306084000
         * invitationPoint : 100
         * name : One day、
         * point : 500
         * uid : 215
         */

        private long createTime;
        private int invitationPoint;
        private String name;
        private int point;
        private int uid;

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getInvitationPoint() {
            return invitationPoint;
        }

        public void setInvitationPoint(int invitationPoint) {
            this.invitationPoint = invitationPoint;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPoint() {
            return point;
        }

        public void setPoint(int point) {
            this.point = point;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }
    }
}
