package com.purchase.sls.homepage.ui;

import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.common.UMStaticData;
import com.purchase.sls.common.unit.TokenManager;
import com.purchase.sls.common.unit.UmengEventUtils;
import com.purchase.sls.data.entity.ShopDetailsInfo;
import com.purchase.sls.data.entity.WebViewDetailInfo;
import com.purchase.sls.homepage.DaggerHomePageComponent;
import com.purchase.sls.homepage.HomePageContract;
import com.purchase.sls.homepage.HomePageModule;
import com.purchase.sls.homepage.presenter.QrCodePresenter;
import com.purchase.sls.login.ui.AccountLoginActivity;
import com.purchase.sls.login.ui.RegisterFirstActivity;
import com.purchase.sls.shopdetailbuy.ui.PaymentOrderActivity;
import com.purchase.sls.webview.ui.WebViewActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;


public class QrCodeScanActivity extends BaseActivity implements QRCodeView.Delegate,HomePageContract.QrCodeView {

    private static final String TAG = "QrCodeScanActivity";
    private WebViewDetailInfo webViewDetailInfo;

    private static final boolean DEBUG = false;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.zxingview)
    ZXingView mQRCodeView;

    @Inject
    QrCodePresenter qrCodePresenter;

    private String storeId;


    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_scan);
        ButterKnife.bind(this);
        setHeight(back,title,null);
        mQRCodeView.setDelegate(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
        mQRCodeView.showScanRect();
        mQRCodeView.startSpot();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        DaggerHomePageComponent.builder()
                .applicationComponent(getApplicationComponent())
                .homePageModule(new HomePageModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mQRCodeView.stopCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mQRCodeView.onDestroy();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Log.d(TAG, "onScanQRCodeSuccess: " + result);
        UmengEventUtils.statisticsClick(this, UMStaticData.SCAN_CODE);
        if (!TextUtils.isEmpty(result)) {
            if(TextUtils.isEmpty(TokenManager.getToken())){
                AccountLoginActivity.start(this, "1");
                this.finish();
            }else {
                if (result.contains("ngapp")) {
                    String[] results = result.split("&&");
                    if (results[0].startsWith("ngapp")) {
                        String firstStr = results[0];
                        String[] firstStrs = firstStr.split("::");
                        if (firstStrs.length > 1 && !TextUtils.isEmpty(firstStrs[1])) {
                            PaymentOrderActivity.start(this, results[1], results[2], firstStrs[1]);
                            this.finish();
                        }
                    }
                }else if(result.contains("home/register/adduser")){
                    webViewDetailInfo = new WebViewDetailInfo();
                    webViewDetailInfo.setTitle("推荐注册");
                    webViewDetailInfo.setUrl(result);
                    WebViewActivity.start(this, webViewDetailInfo);
                    this.finish();
                }
                else {
                    String[] results = result.split("[?]");
                    if (results[1] != null && results[1].startsWith("storeid")) {
                        String[] ids = results[1].split("=");
                        if (ids[1] != null && !TextUtils.isEmpty(ids[1])) {
                            storeId = ids[1];
                            qrCodePresenter.getShopDetail(storeId);
                        }
                    }
                }
            }
        }
        vibrate();
        mQRCodeView.startSpot();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
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
    public void showError(Throwable e) {
        super.showError(e);
        finish();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "onScanQRCodeOpenCameraError: ");
    }

    @Override
    public void setPresenter(HomePageContract.QrCodePresenter presenter) {

    }

    @Override
    public void shopDetailInfo(ShopDetailsInfo shopDetailsInfo) {
        if(shopDetailsInfo!=null&&shopDetailsInfo.getStoreInfo()!=null){
            PaymentOrderActivity.start(this, shopDetailsInfo.getStoreInfo().getTitle(), shopDetailsInfo.getStoreInfo().getzPics(),storeId );
            this.finish();
        }
    }
}
