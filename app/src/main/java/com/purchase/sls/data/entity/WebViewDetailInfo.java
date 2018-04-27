package com.purchase.sls.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/4/27.
 */

public class WebViewDetailInfo implements Parcelable{
    /**
     * 透传消息类型	2
     */
    @SerializedName("Type")
    private String type;

    /**
     * 标题
     */
    @SerializedName("Title")
    private String title;

    /**
     * Webview内容所指向的Url
     */
    @SerializedName("Url")
    private String url;

    /**
     *是否分享	0:不分享,1:分享
     */
    @SerializedName("IsShare")
    private String isShare;

    /**
     * 分享链接
     */
    @SerializedName("ShareUrl")
    private String shareUrl;

    /**
     * 分享标题
     */
    @SerializedName("ShareTitle")
    private String shareTitle;

    /**
     *分享图片,是一串Url
     */
    @SerializedName("SharePic")
    private String sharePic;

    /**
     *分享描述
     */
    @SerializedName("ShareDescription")
    private String shareDescription;

    public WebViewDetailInfo() {
    }

    public WebViewDetailInfo(Parcel in) {
        type = in.readString();
        title = in.readString();
        url = in.readString();
        isShare = in.readString();
        shareUrl = in.readString();
        shareTitle = in.readString();
        sharePic = in.readString();
        shareDescription = in.readString();
    }

    public static final Creator<WebViewDetailInfo> CREATOR = new Creator<WebViewDetailInfo>() {
        @Override
        public WebViewDetailInfo createFromParcel(Parcel in) {
            return new WebViewDetailInfo(in);
        }

        @Override
        public WebViewDetailInfo[] newArray(int size) {
            return new WebViewDetailInfo[size];
        }
    };

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getShareDescription() {
        return shareDescription;
    }

    public void setShareDescription(String shareDescription) {
        this.shareDescription = shareDescription;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(title);
        dest.writeString(url);
        dest.writeString(isShare);
        dest.writeString(shareUrl);
        dest.writeString(shareTitle);
        dest.writeString(sharePic);
        dest.writeString(shareDescription);

    }

}
