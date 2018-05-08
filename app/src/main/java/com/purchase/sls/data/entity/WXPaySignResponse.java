package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2017/5/4.
 */

public class WXPaySignResponse {
    @SerializedName("sign")
    private WxpaySignBean wxpaySign;
    @SerializedName("orderno")
    private String orderno;

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public WxpaySignBean getWxpaySign() {
        return wxpaySign;
    }

    public void setWxpaySign(WxpaySignBean wxpaySign) {
        this.wxpaySign = wxpaySign;
    }

    public static class WxpaySignBean {
        @SerializedName("appid")
        private String appid;
        @SerializedName("partnerid")
        private String partnerid;
        @SerializedName("prepayid")
        private String prepayid;
        @SerializedName("package")
        private String packageX;
        @SerializedName("noncestr")
        private String noncestr;
        @SerializedName("timestamp")
        private String timestamp;
        @SerializedName("sign")
        private String sign;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}
