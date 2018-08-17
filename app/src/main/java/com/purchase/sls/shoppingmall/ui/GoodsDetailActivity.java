package com.purchase.sls.shoppingmall.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
import com.purchase.sls.common.GlideHelper;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.common.widget.Banner.Banner;
import com.purchase.sls.common.widget.Banner.BannerConfig;
import com.purchase.sls.common.widget.GradationScrollView;
import com.purchase.sls.common.widget.TearDownView;
import com.purchase.sls.data.entity.GoodsDetailInfo;
import com.purchase.sls.data.entity.GoodsOrderList;
import com.purchase.sls.data.entity.GoodsUnitPrice;
import com.purchase.sls.data.request.PurchaseGoodsRequest;
import com.purchase.sls.shoppingmall.DaggerShoppingMallComponent;
import com.purchase.sls.shoppingmall.ShoppingMallContract;
import com.purchase.sls.shoppingmall.ShoppingMallModule;
import com.purchase.sls.shoppingmall.presenter.GoodsDetailPresenter;
import com.purchase.sls.webview.unit.JSBridgeWebChromeClient;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/7/4.
 * 商品详情
 */

public class GoodsDetailActivity extends BaseActivity implements ShoppingMallContract.GoodsDetailView, TearDownView.TimeOutListener {
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
    @BindView(R.id.add_to_cart)
    TextView addToCart;
    @BindView(R.id.purchase)
    TextView purchase;
    @BindView(R.id.count_down_rl)
    RelativeLayout countDownRl;
    @BindView(R.id.shop_iv)
    ImageView shopIv;
    @BindView(R.id.scrollview)
    GradationScrollView scrollview;

    private String goodsid;
    private List<String> bannerImages;
    private GoodsDetailInfo goodsDetailInfo;
    private GoodsUnitPrice goodsUnitPrice;
    private String goodsCount;
    private String addType = "0"; //0：加入购物车 1：购买
    private String taobaoid;
    private String quanPrice;

    @Inject
    GoodsDetailPresenter goodsDetailPresenter;

    private PurchaseGoodsRequest purchaseGoodsRequest;

    private static final int REQUEST_SPEC = 20;

    public static void start(Context context, String goodsid) {
        Intent intent = new Intent(context, GoodsDetailActivity.class);
        intent.putExtra(StaticData.GOODS_ID, goodsid);
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
        goodsid = getIntent().getStringExtra(StaticData.GOODS_ID);
        countDown.setTextColor("1");
//        bannerInitialization();
        initWeb(goodsid);
        goodsDetailPresenter.getGoodsDetail(goodsid);
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

        //设置是否支持缩放
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);

        //设置是否显示缩放按钮
        settings.setDisplayZoomControls(true);

        //设置自适应屏幕宽度
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);


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
        String url = BuildConfig.API_BASE_URL + "mall/index/goodsweb?goodsid=" + id;
        webView.loadUrl(url);
    }

    @OnClick({R.id.back, R.id.add_to_cart, R.id.purchase, R.id.shopping_cart})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.add_to_cart:
                addType = "0";
                selectSpec();
                break;
            case R.id.purchase:
                addType = "1";
                selectSpec();
                break;
            case R.id.shopping_cart:
                ShoppingCartActivity.start(this);
                break;
            default:
        }
    }

    private void selectSpec() {
        Intent intent = new Intent(this, SelectSpecActivity.class);
        intent.putExtra(StaticData.GOODS_DETAIL, goodsDetailInfo);
        startActivityForResult(intent, REQUEST_SPEC);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_SPEC:
                    if (data != null) {
                        Bundle bundle = data.getExtras();
                        goodsUnitPrice = (GoodsUnitPrice) bundle.getSerializable(StaticData.GOODS_UNIT_PRICE);
                        goodsCount = bundle.getString(StaticData.GOODS_COUNT);
                        if (goodsUnitPrice != null && !TextUtils.isEmpty(goodsCount)) {
                            if (TextUtils.equals("0", addType)) {
                                goodsDetailPresenter.addToCart(goodsid, taobaoid, goodsUnitPrice.getSkuId(), goodsCount, goodsUnitPrice.getName(), quanPrice, goodsUnitPrice.getPrice(), "");
                            } else {
                                purchaseGoodsRequest = new PurchaseGoodsRequest(goodsCount, goodsid, goodsUnitPrice.getSkuId(), goodsUnitPrice.getPrice(), goodsUnitPrice.getName(), taobaoid, quanPrice, "");
                                goodsDetailPresenter.purchaseGoods(purchaseGoodsRequest);
                            }
                        }
                    }
                    break;
                default:
            }
        }
    }

    @Override
    protected void initializeInjector() {
        DaggerShoppingMallComponent.builder()
                .applicationComponent(getApplicationComponent())
                .shoppingMallModule(new ShoppingMallModule(this))
                .build()
                .inject(this);
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
        goodsVoucher.setText("劵最高可减0/件");
    }

    @Override
    public void setPresenter(ShoppingMallContract.GoodsDetailPresenter presenter) {

    }

    @Override
    public void renderGoodsDetail(GoodsDetailInfo goodsDetailInfo) {
        this.goodsDetailInfo = goodsDetailInfo;
        if (goodsDetailInfo != null) {
            title.setText(goodsDetailInfo.getGoodsName());
            taobaoid = goodsDetailInfo.getTaobaoid();
            quanPrice = goodsDetailInfo.getQuanPrice();
            GlideHelper.load(this, goodsDetailInfo.getGoodsImg(), R.mipmap.no_url_icon, shopIv);
//            bannerImages = new ArrayList<>();
//            bannerImages.add(goodsDetailInfo.getGoodsImg());
//            banner.setImages(bannerImages);
            goodsPrice.setText("¥" + goodsDetailInfo.getPrice());
            goodsSold.setText("已售" + goodsDetailInfo.getSalenum());
            goodsVoucher.setText("劵最高可减" + goodsDetailInfo.getQuanPrice() + "/件");
            if (!TextUtils.isEmpty(goodsDetailInfo.getEndtime())) {
                countDown.setTimeOutListener(this);
                countDown.startTearDown(Long.parseLong(goodsDetailInfo.getEndtime()), "1");
            }
        }
    }

    @Override
    public void addToCartSuccess() {
        showMessage("加入购物车成功");
    }

    @Override
    public void purchaseGoodsSuccess(GoodsOrderList goodsOrderList) {
        FillInOrderActivity.start(this, goodsOrderList, "2", purchaseGoodsRequest, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDown != null) {
            countDown.cancel();
        }
    }
}
