package com.purchase.sls.mine.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.common.unit.QrCodeUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/5/26.
 * 二维码
 */

public class RdCodeActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.qr_code)
    ImageView qrCode;

    private String qrCodeUrl;

    public static void start(Context context,String qrCodeUrl) {
        Intent intent = new Intent(context, RdCodeActivity.class);
        intent.putExtra(StaticData.RD_CODE,qrCodeUrl);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rdcode);
        ButterKnife.bind(this);
        setHeight(back,title,null);
        initView();
    }

    private void initView(){
        qrCodeUrl=getIntent().getStringExtra(StaticData.RD_CODE);
        if(!TextUtils.isEmpty(qrCodeUrl)) {
            int size = 200;
            Bitmap bitmap = QrCodeUtil.createQRCode(qrCodeUrl, size, size);
            qrCode.setImageBitmap(bitmap);
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
}
