package com.purchase.sls.paypassword.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.common.widget.paypw.PayPwdEditText;
import com.purchase.sls.paypassword.DaggerPayPasswordComponent;
import com.purchase.sls.paypassword.PayPasswordContract;
import com.purchase.sls.paypassword.PayPasswordModule;
import com.purchase.sls.paypassword.presenter.AuthenticationPresenter;
import com.purchase.sls.paypassword.presenter.SmsAuthenticationPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/8/22.
 * 验证身份
 */

public class SmsAuthenticationActivity extends BaseActivity implements PayPasswordContract.SmsAuthenticationView{
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.pwd_et)
    PayPwdEditText pwdEt;
    @BindView(R.id.confirm)
    Button confirm;
    @BindView(R.id.setting_item)
    LinearLayout settingItem;

    private String password;
    private String phoneNumber;

    @Inject
    SmsAuthenticationPresenter smsAuthenticationPresenter;

    public static void start(Context context, String phoneNumber) {
        Intent intent = new Intent(context, SmsAuthenticationActivity.class);
        intent.putExtra(StaticData.PHONE_NUMBER, phoneNumber);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_authentication);
        ButterKnife.bind(this);
        setHeight(back,title,null);
        initView();
    }

    private void initView(){
        phoneNumber = getIntent().getStringExtra(StaticData.PHONE_NUMBER);
        initEt();
    }

    private void initEt(){
        pwdEt.initStyle(R.drawable.password_num_bg, 6, 0.33f, R.color.backGround19, R.color.backGround19, 20);
        pwdEt.setOnTextFinishListener(new PayPwdEditText.OnTextFinishListener() {
            @Override
            public void onFinish(String str) {//密码输入完后的回调
                password=str;
                confirm.setText("确定");
            }
        });

    }

    @OnClick({R.id.back, R.id.confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.confirm:
                if(TextUtils.equals("获取验证码",confirm.getText().toString())){
                    smsAuthenticationPresenter.sendCode(phoneNumber,"paypassword");
                }else {
                    smsAuthenticationPresenter.checkCode(phoneNumber,password,"paypassword");
                }
                break;
            default:
        }
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
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
    public void setPresenter(PayPasswordContract.SmsAuthenticationPresenter presenter) {

    }

    @Override
    public void sendCodeSuccess() {
        showMessage("验证码发送成功");
        confirm.setText("确定");
    }

    @Override
    public void checkCodeSuccess() {
        FirstPayPwActivity.start(this,"1",password);
        this.finish();
    }
}
