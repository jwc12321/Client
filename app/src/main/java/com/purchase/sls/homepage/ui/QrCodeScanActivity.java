package com.purchase.sls.homepage.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.common.unit.UrlUtils;
import com.purchase.sls.common.widget.QrCodeScanView;

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
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(result));
//        startActivity(intent);
//        finish();

        //扫描工人/商户二维码
        boolean isUpload = true;
        if (UrlUtils.isUrl(result)) {
            String[] results = result.split("//");

            if (results[1].startsWith(UrlUtils.baseUrl())) {
                String[] strings = results[1].split("\\?");

                Map<String, String> codesMap = new TreeMap<>();
                String[] codes = strings[1].split("&");
                for (String string : codes) {
                    String[] code = string.split("=");
                    codesMap.put(code[0], code[1]);
                }

                if (codesMap.get("fn").equals("1")) {
                    //type=1表示工人或者type=2表示商户,id=xxx表示工人或者商户id
                    switch (codesMap.get("type")) {
                        case "1":
                            break;
                        case "2":
                            break;
                    }
                    isUpload = false;
                    finish();
                } else {
                    isUpload = true;
                }
            }
        }

    }

    @OnClick({R.id.back})
    public void onClick(View view){
        switch (view.getId()){
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
