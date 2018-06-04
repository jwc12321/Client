package com.purchase.sls.common.widget.dialog;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.purchase.sls.BuildConfig;
import com.purchase.sls.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * 分享Dialog
 */
public class ShareDialog extends BottomSheetDialog {

    private static final String TAG = "ShareDialog";
    private static final boolean DEBUG = BuildConfig.DEBUG;

    private String text = "分享";

    private String url;

    private UMImage image;

    private Activity activity;

    private String title;

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ShareAction shareAction = new ShareAction(activity).setCallback(umShareListener);
            shareAction.withText(text);
            switch (v.getId()) {
                case R.id.wechat:
                    shareAction.setPlatform(SHARE_MEDIA.WEIXIN);
                    break;
                case R.id.wechat_circle:
                    shareAction.setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE);
                    break;
                case R.id.qq_friend:
                    shareAction.setPlatform(SHARE_MEDIA.QQ);
                    break;
                case R.id.qq_zone:
                    shareAction.setPlatform(SHARE_MEDIA.QZONE);
                    break;
                case R.id.sms:
                    shareAction.setPlatform(SHARE_MEDIA.SMS);
                    break;
                case R.id.cancel:
                    dismiss();
                    break;
                default:
                    shareAction.setPlatform(SHARE_MEDIA.SINA);
                    break;
            }

            if (url != null) {
                if (DEBUG)
                    Log.d(TAG, "url: " + url);
                UMWeb web = new UMWeb(url);//连接地址
                web.setTitle("能购");
                shareAction.withMedia(web);

            }
            if (image != null) {
                if (DEBUG)
                    Log.d(TAG, "image: " + image);
                shareAction.withMedia(image);
            }
            shareAction.share();
        }
    };

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(getContext(), " 分享成功啦", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onResult: " + platform);
            dismiss();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onError: ");
            dismiss();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(getContext(), " 分享取消了", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onCancel: ");
            dismiss();
        }
    };

    @SuppressWarnings("ConstantConditions")
    public ShareDialog(@NonNull Activity activity) {
        super(activity);
        this.activity = activity;
        setContentView(R.layout.dialog_share_bottom_sheet);
        findViewById(R.id.wechat).setOnClickListener(clickListener);
        findViewById(R.id.wechat_circle).setOnClickListener(clickListener);
        findViewById(R.id.qq_zone).setOnClickListener(clickListener);
        findViewById(R.id.qq_friend).setOnClickListener(clickListener);
        findViewById(R.id.sms).setOnClickListener(clickListener);
        findViewById(R.id.cancel).setOnClickListener(clickListener);
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(UMImage image) {
        this.image = image;
    }

    public void setUmShareListener(UMShareListener listener) {
        umShareListener = listener;
    }

}
