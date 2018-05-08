package com.purchase.sls.homepage.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.common.unit.TokenManager;
import com.purchase.sls.common.unit.UrlUtils;
import com.purchase.sls.common.widget.QrCodeScanView;
import com.purchase.sls.login.ui.RegisterFirstActivity;
import com.purchase.sls.shopdetailbuy.ui.PaymentOrderActivity;

import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.QRCodeView;


public class QrCodeScanActivity extends BaseActivity implements QRCodeView.Delegate {

    private static final String TAG = "QrCodeScanActivity";

    private static final boolean DEBUG = false;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.qr_code_scanner)
    QrCodeScanView qrCodeScanner;


    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_scan);
        ButterKnife.bind(this);

    }

    @Override
    protected void initializeInjector() {
        super.initializeInjector();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //scannerView.startCamera();
        qrCodeScanner.startSpot();

    }

    @Override
    protected void onPause() {
        super.onPause();
        qrCodeScanner.stopCamera();

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        if (DEBUG)
            Log.d(TAG, "onScanQRCodeSuccess: " + result);
        if (UrlUtils.isUrl(result)) {
            String[] results = result.split("&&");
            if (results[0].startsWith("ngapp::")) {
                String firstStr = results[0];
                String[] firstStrs = firstStr.split("::");
                if (!TextUtils.isEmpty(TokenManager.getToken()) && firstStrs.length > 1 && !TextUtils.isEmpty(firstStrs[1])) {
                    PaymentOrderActivity.start(this, results[1], results[2], firstStrs[1]);
                } else if (TextUtils.isEmpty(TokenManager.getToken()) && firstStrs.length > 1 && !TextUtils.isEmpty(firstStrs[1])) {
                    RegisterFirstActivity.start(this, StaticData.REGISTER, firstStrs[1]);
                }
            }
        }
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

}
