package com.purchase.sls.login.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.common.unit.NetUtils;
import com.purchase.sls.common.unit.PermissionUtil;
import com.purchase.sls.data.entity.PersionInfoResponse;
import com.purchase.sls.login.DaggerLoginComponent;
import com.purchase.sls.login.LoginContract;
import com.purchase.sls.login.LoginModule;
import com.purchase.sls.login.presenter.LoginPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.purchase.sls.common.unit.AccountUtils.isAccountValid;

/**
 * Created by JWC on 2018/4/17.
 * 密码登录
 */

public class AccountLoginActivity extends BaseActivity implements LoginContract.LoginView {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.login_account_number_et)
    EditText loginAccountNumberEt;
    @BindView(R.id.clean_account_number)
    ImageView cleanAccountNumber;
    @BindView(R.id.login_password_et)
    EditText loginPasswordEt;
    @BindView(R.id.clean_password)
    ImageView cleanPassword;
    @BindView(R.id.hidden_password)
    ImageView hiddenPassword;
    @BindView(R.id.login_in)
    Button loginIn;
    @BindView(R.id.forget_password)
    TextView forgetPassword;
    @BindView(R.id.immediate_registration)
    TextView immediateRegistration;
    @BindView(R.id.sms_login)
    ImageView smsLogin;

    //登录按钮是否可点击
    private boolean loginEnable = false;
    //密码是否显示
    private boolean isPassWordVisible = false;
    private static final int REQUEST_PHONE_STATE = 0x01;

    @Inject
    LoginPresenter loginPresenter;

    @Override
    public View getSnackBarHolderView() {
        return null;
    }


    public static void start(Context context) {
        Intent intent = new Intent(context, AccountLoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_login);
        ButterKnife.bind(this);
    }

    @Override
    protected void initializeInjector() {
        DaggerLoginComponent.builder()
                .applicationComponent(getApplicationComponent())
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    @OnClick({R.id.clean_account_number,R.id.clean_password,R.id.hidden_password,R.id.login_in,R.id.sms_login,R.id.forget_password,R.id.immediate_registration})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.clean_account_number:
                loginAccountNumberEt.setText("");
                break;
            case R.id.clean_password:
                loginPasswordEt.setText("");
                break;
            case R.id.hidden_password:
                passwordDisplay();
                break;
            case R.id.login_in:
                login();
                break;
            case R.id.sms_login:
                SmsLoginActivity.start(this);
                break;
            case R.id.forget_password:
                RegisterFirstActivity.start(this, StaticData.CHANGEPWD);
                break;
            case R.id.immediate_registration:
                RegisterFirstActivity.start(this, StaticData.REGISTER);
                break;
                default:
        }
    }

    /**
     * 隐藏和显示密码
     */
    private void passwordDisplay(){
        if (isPassWordVisible) {
            hiddenPassword.setImageResource(R.mipmap.ic_invisible_hig);
            loginPasswordEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            String loginPassword =loginPasswordEt.getText().toString().trim();
            loginPasswordEt.setSelection(loginPassword.toCharArray().length);
            isPassWordVisible = false;
        } else {
            //隐藏密码
            isPassWordVisible = true;
            String loginPassword = loginPasswordEt.getText().toString().trim();
            loginPasswordEt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            loginPasswordEt.setSelection(loginPassword.toCharArray().length);
            hiddenPassword.setImageResource(R.mipmap.si_yan);
        }
    }

    /**
     * 登录
     */
    private void login(){
        String accountNumber=loginAccountNumberEt.getText().toString();
        String passWord=loginPasswordEt.getText().toString();
//        if (!NetUtils.isConnected()) {
//            showMessage(getString(R.string.check_network));
//            return;
//        }
        if (!isAccountValid(accountNumber)) {
            showError(getString(R.string.invalid_account_input));
            return;
        } else if (TextUtils.isEmpty(accountNumber)) {
            showMessage(getString(R.string.empty_account));
            return;
        } else if (TextUtils.isEmpty(passWord)) {
            showMessage(getString(R.string.empty_password));
            return;
        }
        List<String> groups = new ArrayList<>();
        groups.add(Manifest.permission_group.PHONE);
        if (requestRuntimePermissions(PermissionUtil.permissionGroup(groups, null), REQUEST_PHONE_STATE)) {
            loginPresenter.accountLogin(accountNumber,passWord, "");
        }
    }

    @Override
    public void setPresenter(LoginContract.LoginPresenter presenter) {

    }


    @Override
    public void accountLoginSuccess(PersionInfoResponse persionInfoResponse) {
        finish();
    }

    @Override
    public void codeSuccess() {

    }

    @Override
    public void checkCodeSuccess() {

    }

    @Override
    public void setPasswordSuccess() {

    }
}
