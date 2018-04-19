package com.purchase.sls.login.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.common.widget.ColdDownButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/4/18.
 * 短信发送登录
 */

public class SmsLoginActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.login_phone_number_et)
    EditText loginPhoneNumberEt;
    @BindView(R.id.clean_phone_number)
    ImageView cleanPhoneNumber;
    @BindView(R.id.send_auth_code)
    ColdDownButton sendAuthCode;
    @BindView(R.id.login_verificationcode_et)
    EditText loginVerificationcodeEt;
    @BindView(R.id.clean_verificationcode)
    ImageView cleanVerificationcode;
    @BindView(R.id.hidden_verificationcode)
    ImageView hiddenVerificationcode;
    @BindView(R.id.login_in)
    Button loginIn;
    @BindView(R.id.account_login)
    ImageView accountLogin;

    public static void start(Context context) {
        Intent intent = new Intent(context, SmsLoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_login);
        ButterKnife.bind(this);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @OnClick({R.id.back, R.id.clean_phone_number, R.id.send_auth_code, R.id.clean_verificationcode,R.id.hidden_verificationcode,R.id.login_in,R.id.account_login})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.clean_phone_number:
                loginPhoneNumberEt.setText("");
                break;
            case R.id.send_auth_code:
                break;
            case R.id.clean_verificationcode:
                break;
            case R.id.hidden_verificationcode:
                break;
            case R.id.login_in:
                break;
            case R.id.account_login:
                AccountLoginActivity.start(this);
                break;
                default:
        }
    }
}
