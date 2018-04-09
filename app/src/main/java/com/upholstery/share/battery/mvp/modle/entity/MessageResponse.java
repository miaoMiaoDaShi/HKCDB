package com.upholstery.share.battery.mvp.modle.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import cn.zcoder.xxp.base.net.BaseResponse;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/4/3
 * Description : 消息
 */
public class MessageResponse extends BaseResponse {
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * content : 测试
         * creaTime : 1500967080000
         * from : 1
         * id : 2
         * msgId : 1
         * status : 1
         * title : 文字消息
         * type : 0
         */

        private String content;
        private long creaTime;
        private int from;
        private int id;
        private int msgId;
        @SerializedName("status")
        private int statusX;
        private String title;
        private int type;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public long getCreaTime() {
            return creaTime;
        }

        public void setCreaTime(long creaTime) {
            this.creaTime = creaTime;
        }

        public int getFrom() {
            return from;
        }

        public void setFrom(int from) {
            this.from = from;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMsgId() {
            return msgId;
        }

        public void setMsgId(int msgId) {
            this.msgId = msgId;
        }

        public int getStatusX() {
            return statusX;
        }

        public void setStatusX(int statusX) {
            this.statusX = statusX;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
