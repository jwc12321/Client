package com.purchase.sls.shoppingmall.ui;

import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.BuildConfig;
import com.purchase.sls.R;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.common.widget.Banner.Banner;
import com.purchase.sls.common.widget.Banner.BannerConfig;
import com.purchase.sls.common.widget.GradationScrollView;
import com.purchase.sls.common.widget.TearDownView;
import com.purchase.sls.webview.unit.JSBridgeWebChromeClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/7/4.
 * 商品详情
 */

public class GoodsDetailActivity extends BaseActivity implements TearDownView.TimeOutListener{
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.shopping_cart)
    ImageView shoppingCart;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.goods_price)
    TextView goodsPrice;
    @BindView(R.id.goods_sold)
    TextView goodsSold;
    @BindView(R.id.goods_voucher)
    TextView goodsVoucher;
    @BindView(R.id.count_down)
    TearDownView countDown;
    @BindView(R.id.goods_detail)
    TextView goodsDetail;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.scrollview)
    GradationScrollView scrollview;
    @BindView(R.id.add_to_cart)
    TextView addToCart;
    @BindView(R.id.purchase)
    TextView purchase;

    private String goodsid;

    public static void start(Context context,String goodsid) {
        Intent intent = new Intent(context, GoodsDetailActivity.class);
        intent.putExtra(StaticData.GOODS_ID,goodsid);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);
        setHeight(back, title, shoppingCart);
        initView();
    }

    private void initView() {
        goodsid=getIntent().getStringExtra(StaticData.GOODS_ID);
        bannerInitialization();
        initWeb("");

    }

    //初始化banner
    private void bannerInitialization() {
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.isAutoPlay(true);
        banner.setOnBannerClickListener(new Banner.OnBannerClickListener() {
            @Override
            public void OnBannerClick(View view, int position) {
            }
        });
    }

    //初始化web
    private void initWeb(String id) {
        WebSettings settings = webView.getSettings();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(
                    WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        settings.setJavaScriptEnabled(true);
        webView.setWebChromeClient(new JSBridgeWebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
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
        String url = BuildConfig.API_BASE_URL + "home/product/productdetail?id=" + id;
        webView.loadUrl(url);
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
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void timeIn() {

    }

    @Override
    public void timeOut() {

    }
}
