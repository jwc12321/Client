package com.purchase.sls.push;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by JWC on 2017/5/2.
 */

public class PushInfo implements Serializable {
    @SerializedName("Type")
    String jumpType;
    @SerializedName("SpId")
    String id;
    @SerializedName("SpType")
    String type;
    @SerializedName("AppViewId")
    String appViewId;
    @SerializedName("ServiceProvider")
    ServiceProvider serviceProvider;
    @SerializedName("Order")
    Order order;
    @SerializedName("Message")
    Message message;
    @SerializedName("Order2Pay")
    OrderPay orderToPay;
    @SerializedName("Worker")
    Worker worker;
    @SerializedName("Merchant")
    Merchant merchant;

    /*webview 需需要字段*/
    @SerializedName("Title")
    String title;
    @SerializedName("Url")
    String url;
    @SerializedName("IsShare")
    String isShare;
    @SerializedName("ShareUrl")
    String shareUrl;
    @SerializedName("ShareTitle")
    String shareTitle;
    @SerializedName("SharePic")
    String sharePic;
    @SerializedName("ShareDescription")
    String shareDescription;
    @SerializedName("Voice")
    String voice;
    @SerializedName("Sound")
    String sound;

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    private int sharePicResources;

    public String getShareDescription() {
        return shareDescription;
    }

    public void setShareDescription(String shareDescription) {
        this.shareDescription = shareDescription;
    }

    public String getJumpType() {
        return jumpType;
    }

    public void setJumpType(String jumpType) {
        this.jumpType = jumpType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIsShare() {
        return isShare;
    }

    public void setIsShare(String isShare) {
        this.isShare = isShare;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getSharePic() {
        return sharePic;
    }

    public void setSharePic(String sharePic) {
        this.sharePic = sharePic;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAppViewId() {
        return appViewId;
    }

    public void setAppViewId(String appViewId) {
        this.appViewId = appViewId;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public OrderPay getOrderToPay() {
        return orderToPay;
    }

    public void setOrderToPay(OrderPay orderToPay) {
        this.orderToPay = orderToPay;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public void setSharePicResources(int sharePicResources) {
        this.sharePicResources = sharePicResources;
    }

    public int getSharePicResources() {
        return sharePicResources;
    }

    public static class ServiceProvider implements Serializable {
        @SerializedName("SpId")
        String id;
        @SerializedName("SpType")
        String type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class Order implements Serializable {

        @SerializedName("OrderId")
        String orderId;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }
    }

    public static class Message implements Serializable {
        @SerializedName("Id")
        String id;
        @SerializedName("MessageType")
        String messageType;
        @SerializedName("Title")
        String title;
        @SerializedName("Content")
        String content;
        @SerializedName("Date")
        String date;
        @SerializedName("JumpCode")
        String jumpCode;
        @SerializedName("JumpParam")
        String jumpParam;
        @SerializedName("IsRead")
        String isRead;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMessageType() {
            return messageType;
        }

        public void setMessageType(String messageType) {
            this.messageType = messageType;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getJumpCode() {
            return jumpCode;
        }

        public void setJumpCode(String jumpCode) {
            this.jumpCode = jumpCode;
        }

        public String getJumpParam() {
            return jumpParam;
        }

        public void setJumpParam(String jumpParam) {
            this.jumpParam = jumpParam;
        }

        public String getIsRead() {
            return isRead;
        }

        public void setIsRead(String isRead) {
            this.isRead = isRead;
        }
    }

    public static class OrderPay {//Order2Pay
        @SerializedName("OrderId")
        String orderId;
        @SerializedName("SpName")
        String serviceProviderName;
        @SerializedName("Price")
        String price;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getServiceProviderName() {
            return serviceProviderName;
        }

        public void setServiceProviderName(String serviceProviderName) {
            this.serviceProviderName = serviceProviderName;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }

    public static class Worker {
        @SerializedName("WorkerId")
        String workerId;

        public String getWorkerId() {
            return workerId;
        }

        public void setWorkerId(String workerId) {
            this.workerId = workerId;
        }
    }

    public static class Merchant {
        String merchant;
    }
}
