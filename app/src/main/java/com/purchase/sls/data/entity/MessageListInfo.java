package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2018/5/8.
 */

public class MessageListInfo {
    @SerializedName("current_page")
    private String currentPage;
    @SerializedName("data")
    private List<MessageItem> messageItems;

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public List<MessageItem> getMessageItems() {
        return messageItems;
    }

    public void setMessageItems(List<MessageItem> messageItems) {
        this.messageItems = messageItems;
    }

    public static class MessageItem{
        @SerializedName("id")
        private String id;
        @SerializedName("sendtime")
        private String sendtime;
        @SerializedName("type")
        private String type;
        @SerializedName("number")
        private String number;
        @SerializedName("title")
        private String title;
        @SerializedName("messageid")
        private String messageId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSendtime() {
            return sendtime;
        }

        public void setSendtime(String sendtime) {
            this.sendtime = sendtime;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMessageId() {
            return messageId;
        }

        public void setMessageId(String messageId) {
            this.messageId = messageId;
        }
    }
}
