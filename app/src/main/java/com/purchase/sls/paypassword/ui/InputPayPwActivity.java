package com.purchase.sls.paypassword.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.common.widget.paypw.PayPwdEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/8/21.
 */

public class InputPayPwActivity extends BaseActivity {
    @BindView(R.id.cancel)
    ImageView cancel;
    @BindView(R.id.pwd_et)
    PayPwdEditText pwdEt;
    @BindView(R.id.item_input_pw)
    RelativeLayout itemInputPw;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_paypw);
        ButterKnife.bind(this);
        initEt();
    }

    private void initEt() {
        pwdEt.initStyle(R.drawable.password_num_bg, 6, 0.33f, R.color.backGround19, R.color.backGround19, 20);
        pwdEt.setOnTextFinishListener(new PayPwdEditText.OnTextFinishListener() {
            @Override
            public void onFinish(String str) {//密码输入完后的回调
            }
        });

    }

    @OnClick({R.id.cancel, R.id.item_input_pw})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
            case R.id.item_input_pw:
                finish();
                break;
            default:
        }
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }
}
