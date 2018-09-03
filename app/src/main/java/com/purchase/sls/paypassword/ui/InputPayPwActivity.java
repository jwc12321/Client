package com.purchase.sls.paypassword.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.common.widget.paypw.PayPwdEditText;
import com.purchase.sls.paypassword.DaggerPayPasswordComponent;
import com.purchase.sls.paypassword.PayPasswordContract;
import com.purchase.sls.paypassword.PayPasswordModule;
import com.purchase.sls.paypassword.presenter.PayPwPowerPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/8/21.
 */

public class InputPayPwActivity extends BaseActivity implements PayPasswordContract.PayPwPowerView{
    @BindView(R.id.cancel)
    ImageView cancel;
    @BindView(R.id.pwd_et)
    PayPwdEditText pwdEt;
    @BindView(R.id.item_input_pw)
    RelativeLayout itemInputPw;

    @Inject
    PayPwPowerPresenter payPwPowerPresenter;

    private String password;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ec_input_paypw);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        initEt();
    }

    private void initEt() {
        pwdEt.initStyle(R.drawable.password_num_bg, 6, 0.33f, R.color.backGround19, R.color.appText5, 20);
        pwdEt.setOnTextFinishListener(new PayPwdEditText.OnTextFinishListener() {
            @Override
            public void onFinish(String str) {//密码输入完后的回调
                password=str;
                payPwPowerPresenter.verifyPayPassword(str);
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
    protected void initializeInjector() {
        DaggerPayPasswordComponent.builder()
                .applicationComponent(getApplicationComponent())
                .payPasswordModule(new PayPasswordModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void setPresenter(PayPasswordContract.PayPwPowerPresenter presenter) {

    }

    @Override
    public void verifySuccess() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(StaticData.PAY_PASSWORD, password);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }
}
