package com.purchase.sls.energy.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.BuildConfig;
import com.purchase.sls.R;
import com.purchase.sls.address.ui.AddressListActivity;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.common.widget.Banner.Banner;
import com.purchase.sls.common.widget.Banner.BannerConfig;
import com.purchase.sls.common.widget.GradationScrollView;
import com.purchase.sls.common.widget.TearDownView;
import com.purchase.sls.data.entity.ActivityInfo;
import com.purchase.sls.data.entity.ActivityOrderDetailInfo;
import com.purchase.sls.data.entity.AddressInfo;
import com.purchase.sls.energy.DaggerEnergyComponent;
import com.purchase.sls.energy.EnergyContract;
import com.purchase.sls.energy.EnergyModule;
import com.purchase.sls.energy.presenter.ActivityDetailPresenter;
import com.purchase.sls.ordermanage.ui.ActivityOrderDetailActivity;
import com.purchase.sls.paypassword.ui.EcInputPayPwActivity;
import com.purchase.sls.paypassword.ui.RdSPpwActivity;
import com.purchase.sls.webview.unit.JSBridgeWebChromeClient;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/6/6.
 * 秒杀，兑换，抽奖详情页
 */

public class SkEcLtActivity extends BaseActivity implements EnergyContract.ActivityDetailView,TearDownView.TimeOutListener {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.need_energy)
    TextView needEnergy;
    @BindView(R.id.original_price)
    TextView originalPrice;
    @BindView(R.id.spike_explain)
    TextView spikeExplain;
    @BindView(R.id.count_down)
    TearDownView countDown;
    @BindView(R.id.surplus_number)
    TextView surplusNumber;
    @BindView(R.id.energy_ll)
    LinearLayout energyLl;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.spike_bt)
    Button spikeBt;
    @BindView(R.id.scrollview)
    GradationScrollView scrollview;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.choice_address)
    LinearLayout choiceAddress;
    @BindView(R.id.add_address)
    TextView addAddress;
    private ActivityInfo activityInfo;
    private AddressInfo addressInfo;

    private static final int REQUEST_ADDRESS = 11;
    @Inject
    ActivityDetailPresenter activityDetailPresenter;

    private static final int REQUEST_ACTIVITY= 24;

    private ActivityOrderDetailInfo activityOrderDetailInfo;

    public static void start(Context context, ActivityInfo activityInfo) {
        Intent intent = new Intent(context, SkEcLtActivity.class);
        intent.putExtra(StaticData.ACTIVITY_DETAIL, activityInfo);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skeclt);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initView();
    }

    private void initView() {
        activityInfo = (ActivityInfo) getIntent().getSerializableExtra(StaticData.ACTIVITY_DETAIL);
        title.setText(activityInfo.getpName());
        bannerInitialization();
        if (!TextUtils.isEmpty(activityInfo.getpPics())) {
            String[] bannerUrl = activityInfo.getpPics().split(",");
            List<String> bannerUrls = Arrays.asList(bannerUrl);
            banner.setImages(bannerUrls);
        }
        needEnergy.setText(activityInfo.getPrice() + "能量");
        originalPrice.setText("¥" + activityInfo.getpPrice());
        originalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        if(TextUtils.equals("1", activityInfo.getpType())){
            addAddress.setVisibility(View.GONE);
            choiceAddress.setEnabled(false);
        }else {
            addAddress.setVisibility(View.VISIBLE);
            choiceAddress.setEnabled(true);
        }
        if (TextUtils.equals("3", activityInfo.getType())) {
            countDown.setVisibility(View.GONE);
            surplusNumber.setVisibility(View.VISIBLE);
            spikeBt.setText("抽奖");
            spikeExplain.setText("剩余名额");
            surplusNumber.setVisibility(View.VISIBLE);
            surplusNumber.setText(activityInfo.getCount() + "份");
            spikeBt.setEnabled(true);
        } else {
            countDown.setVisibility(View.GONE);
            surplusNumber.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(activityInfo.getStartTime()) && !TextUtils.isEmpty(activityInfo.getEndTime())) {
                long distanceStart = Long.parseLong(activityInfo.getStartTime()) - System.currentTimeMillis() / 1000;
                long distanceEnd = Long.parseLong(activityInfo.getEndTime()) - System.currentTimeMillis() / 1000;
                if (distanceStart > 0) {
                    if (TextUtils.equals("1", activityInfo.getType())) {
                        spikeExplain.setText("距离秒杀开始");
                        spikeBt.setText("秒杀");
                        spikeBt.setEnabled(false);
                    } else {
                        spikeExplain.setText("距离兑换开始");
                        spikeBt.setText("兑换");
                        spikeBt.setEnabled(false);
                    }
                    countDown.setTimeOutListener(this);
                    countDown.setVisibility(View.VISIBLE);
                    countDown.startTearDown(Long.parseLong(activityInfo.getStartTime()), "0");
                } else if (distanceStart <= 0 && distanceEnd >= 0) {
                    if (TextUtils.equals("1", activityInfo.getType())) {
                        spikeExplain.setText("距离秒杀结束");
                        spikeBt.setText("秒杀");
                        spikeBt.setEnabled(true);
                    } else {
                        spikeExplain.setText("距离兑换结束");
                        spikeBt.setText("兑换");
                        spikeBt.setEnabled(true);
                    }
                    countDown.setTimeOutListener(this);
                    countDown.setVisibility(View.VISIBLE);
                    countDown.startTearDown(Long.parseLong(activityInfo.getEndTime()), "1");
                } else {
                    if (TextUtils.equals("1", activityInfo.getType())) {
                        spikeExplain.setText("秒杀结束");
                        spikeBt.setText("秒杀");
                        spikeBt.setEnabled(false);
                    } else {
                        spikeExplain.setText("兑换结束");
                        spikeBt.setText("兑换");
                        spikeBt.setEnabled(false);
                    }
                }
            }
        }
        initWeb(activityInfo.getpId());
    }

    @Override
    protected void onResume() {
        super.onResume();
        surplusNumber();
    }

    private void surplusNumber() {
        if (TextUtils.equals("3", activityInfo.getType())) {
            countDown.setVisibility(View.GONE);
            surplusNumber.setVisibility(View.VISIBLE);
            spikeBt.setText("抽奖");
            spikeExplain.setText("剩余名额");
            surplusNumber.setVisibility(View.VISIBLE);
            surplusNumber.setText(activityInfo.getCount() + "份");
        }
    }

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

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    protected void initializeInjector() {
        DaggerEnergyComponent.builder()
                .applicationComponent(getApplicationComponent())
                .energyModule(new EnergyModule(this))
                .build()
                .inject(this);
    }

    @OnClick({R.id.back, R.id.choice_address, R.id.spike_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.choice_address:
                choiceAddress();
                break;
            case R.id.spike_bt:
                activityDetailPresenter.isSetUpPayPw();
                break;
            default:
        }
    }

    private void choiceAddress() {
        Intent intent = new Intent(this, AddressListActivity.class);
        intent.putExtra(StaticData.BACK_ADDRESS, "1");
        startActivityForResult(intent, REQUEST_ADDRESS);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_ADDRESS:
                    if (data != null) {
                        Bundle bundle = data.getExtras();
                        addressInfo = (AddressInfo) bundle.getSerializable(StaticData.CHOICE_ADDRESS);
                        if (addressInfo != null) {
                            addAddress.setVisibility(View.GONE);
                            name.setVisibility(View.VISIBLE);
                            address.setVisibility(View.VISIBLE);
                            name.setText(addressInfo.getUsername() + " " + addressInfo.getTel());
                            address.setText(addressInfo.getProvince() + addressInfo.getCity() + addressInfo.getCountry() + addressInfo.getAddress());
                        }
                    }
                    break;
                case REQUEST_ACTIVITY:
                    if (data != null) {
                        Bundle bundle = data.getExtras();
                        activityOrderDetailInfo = (ActivityOrderDetailInfo) bundle.getSerializable(StaticData.ACTIVITY_DATA);
                        if (activityOrderDetailInfo != null) {
                            ActivityOrderDetailActivity.start(this, activityOrderDetailInfo);
                        }
                    }
                    break;
                default:
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDown != null) {
            countDown.cancel();
        }
    }

    @Override
    public void setPresenter(EnergyContract.ActivityDetailPresenter presenter) {

    }

    @Override
    public void submitSpikeSuccess(ActivityOrderDetailInfo activityOrderDetailInfo) {
        ActivityOrderDetailActivity.start(this, activityOrderDetailInfo);
    }

    @Override
    public void submitLotterySuccess(ActivityOrderDetailInfo activityOrderDetailInfo) {
        ActivityOrderDetailActivity.start(this, activityOrderDetailInfo);
    }

    @Override
    public void renderIsSetUpPayPw(String what) {
        if (TextUtils.equals("true", what)) {
            goWhere();
        } else {
            RdSPpwActivity.start(this);
        }
    }

    private void goWhere(){
        if(TextUtils.equals("1", activityInfo.getpType())){
            Intent intent = new Intent(this, EcInputPayPwActivity.class);
            intent.putExtra(StaticData.ACTIVITY_ID,activityInfo.getId());
            intent.putExtra(StaticData.ADDRESS_ID, "0");
            intent.putExtra(StaticData.WHERE_GO,activityInfo.getType());
            startActivityForResult(intent, REQUEST_ACTIVITY);
        }else {
            if (addressInfo == null) {
                choiceAddress();
            } else {
                Intent intent = new Intent(this, EcInputPayPwActivity.class);
                intent.putExtra(StaticData.ACTIVITY_ID,activityInfo.getId());
                intent.putExtra(StaticData.ADDRESS_ID, addressInfo.getId());
                intent.putExtra(StaticData.WHERE_GO,activityInfo.getType());
                startActivityForResult(intent, REQUEST_ACTIVITY);
            }
        }
    }

    @Override
    public void timeIn() {
        if (TextUtils.equals("1", activityInfo.getType())) {
            spikeExplain.setText("距离秒杀结束");
            spikeBt.setText("秒杀");
            spikeBt.setEnabled(true);
        } else {
            spikeExplain.setText("距离兑换结束");
            spikeBt.setText("兑换");
            spikeBt.setEnabled(true);
        }
        countDown.setVisibility(View.VISIBLE);
        countDown.startTearDown(Long.parseLong(activityInfo.getEndTime()), "1");
    }

    @Override
    public void timeOut() {
        if (TextUtils.equals("1", activityInfo.getType())) {
            spikeExplain.setText("秒杀结束");
            spikeBt.setText("秒杀");
            spikeBt.setEnabled(false);
        } else {
            spikeExplain.setText("兑换结束");
            spikeBt.setText("兑换");
            spikeBt.setEnabled(false);
        }
    }
}
