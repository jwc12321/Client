package com.purchase.sls.webview.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.data.entity.WebViewDetailInfo;
import com.purchase.sls.webview.unit.BridgeImpl;
import com.purchase.sls.webview.unit.JSBridge;
import com.purchase.sls.webview.unit.JSBridgeWebChromeClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.KeyEvent.KEYCODE_BACK;

/**
 * Created by JWC on 2018/4/27.
 */

public class WebViewActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.collection)
    ImageView collection;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.webView)
    WebView webView;

    private BridgeImpl bridge;
    private WebViewDetailInfo webViewDetailInfo;

    public static void start(Context context, WebViewDetailInfo webViewDetailInfo) {
        Intent intent = new Intent(context, WebViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(StaticData.WEBVIEW_DETAILINFO, webViewDetailInfo);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        setHeight(back,title,collection);
        initView();
    }

    private void initView() {
        webViewDetailInfo = getIntent().getExtras().getParcelable(StaticData.WEBVIEW_DETAILINFO);
        title.setText(webViewDetailInfo.getTitle());
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setWebChromeClient(new JSBridgeWebChromeClient());
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                String titleStr = view.getTitle();
//                if (!TextUtils.isEmpty(titleStr)) {
//                    title.setText(titleStr);
//                }
            }
        });
        webView.loadUrl(webViewDetailInfo.getUrl());
        bridge = new BridgeImpl(this);
        JSBridge.register("bridge", BridgeImpl.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case StaticData.CALLBACK_DATA:

                    break;
                default:
            }
        }
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @OnClick({R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            default:
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
