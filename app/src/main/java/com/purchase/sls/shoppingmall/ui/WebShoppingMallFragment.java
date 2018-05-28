package com.purchase.sls.shoppingmall.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.purchase.sls.BaseFragment;
import com.purchase.sls.R;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.common.UMStaticData;
import com.purchase.sls.common.unit.PersionAppPreferences;
import com.purchase.sls.common.unit.UmengEventUtils;
import com.purchase.sls.login.ui.AccountLoginActivity;
import com.purchase.sls.webview.unit.BridgeImpl;
import com.purchase.sls.webview.unit.JSBridge;
import com.purchase.sls.webview.unit.JSBridgeWebChromeClient;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/4/19.
 */

public class WebShoppingMallFragment extends BaseFragment {
    @BindView(R.id.webView)
    WebView webView;

    private BridgeImpl bridge;
    private PersionAppPreferences persionAppPreferences;

    public WebShoppingMallFragment() {
    }

    public static WebShoppingMallFragment newInstance() {
        WebShoppingMallFragment webShoppingMallFragment = new WebShoppingMallFragment();
        return webShoppingMallFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_web_shopping_mall, container, false);
        ButterKnife.bind(this, rootview);
        return rootview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        persionAppPreferences = new PersionAppPreferences(getActivity());
        WebSettings settings = webView.getSettings();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(
                    WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        settings.setJavaScriptEnabled(true);
        webView.setWebChromeClient(new JSBridgeWebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showLoading();
            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                if (!TextUtils.equals(StaticData.HOMEURL, url)) {
                    if (TextUtils.isEmpty(persionAppPreferences.getShopMallId())) {
                        AccountLoginActivity.start(getActivity());
                        return true;
                    } else {
                        if (url.contains("?")) {
                            webView.loadUrl(url + "&appuserid=" + persionAppPreferences.getShopMallId());
                            return true;
                        } else {
                            webView.loadUrl(url + "?appuserid=" + persionAppPreferences.getShopMallId());
                            return true;
                        }
                    }
                }
                return false;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!TextUtils.equals(StaticData.HOMEURL, url)) {
                    if (TextUtils.isEmpty(persionAppPreferences.getShopMallId())) {
                        AccountLoginActivity.start(getActivity());
                        return true;
                    } else {
                        if (url.contains("?")) {
                            webView.loadUrl(url + "&appuserid=" + persionAppPreferences.getShopMallId());
                            return true;
                        } else {
                            webView.loadUrl(url + "?appuserid=" + persionAppPreferences.getShopMallId());
                            return true;
                        }
                    }
                }
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                dismissLoading();
            }

            @Override
            public void onReceivedSslError(WebView view,
                                           SslErrorHandler handler, SslError error) {
                // TODO Auto-generated method stub
                // handler.cancel();// Android默认的处理方式
                handler.proceed();// 接受所有网站的证书
                // handleMessage(Message msg);// 进行其他处理
            }
        });
//        webView.loadUrl("http://s.365neng.com/home");
        bridge = new BridgeImpl(getActivity());
        JSBridge.register("bridge", BridgeImpl.class);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            getActivity().onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    private boolean isFirstLoad = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isFirstLoad) {
            if (getUserVisibleHint()) {
                if (webView != null) {
                    UmengEventUtils.statisticsClick(getActivity(), UMStaticData.SHOPPING_MALL);
                    webView.loadUrl("https://s.365neng.com/home");
                }
                isFirstLoad = false;
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public boolean onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
            return true;
        } else {
            return super.onBackPressed();
        }
    }
}
