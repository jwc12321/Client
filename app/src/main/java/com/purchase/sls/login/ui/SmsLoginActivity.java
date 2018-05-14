package com.purchase.sls.login.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.common.unit.CommonAppPreferences;
import com.purchase.sls.common.unit.PersionAppPreferences;
import com.purchase.sls.common.unit.TokenManager;
import com.purchase.sls.common.widget.ColdDownButton;
import com.purchase.sls.data.entity.PersionInfoResponse;
import com.purchase.sls.login.DaggerLoginComponent;
import com.purchase.sls.login.LoginContract;
import com.purchase.sls.login.LoginModule;
import com.purchase.sls.login.presenter.LoginPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;

import static com.purchase.sls.common.unit.AccountUtils.isAccountValid;

/**
 * Created by JWC on 2018/4/18.
 * 短信发送登录
 */

public class SmsLoginActivity extends BaseActivity implements LoginContract.LoginView, ColdDownButton.OnResetListener {

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

    @Inject
    LoginPresenter loginPresenter;

    private String phoneNumberStr;
    private String phoneCodeStr;
    private PersionAppPreferences persionAppPreferences;

    public static void start(Context context) {
        Intent intent = new Intent(context, SmsLoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_login);
        ButterKnife.bind(this);
        persionAppPreferences=new PersionAppPreferences(this);
        sendAuthCode.setOnResetListener(this);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @OnClick({R.id.back, R.id.clean_phone_number, R.id.send_auth_code, R.id.clean_verificationcode, R.id.hidden_verificationcode, R.id.login_in, R.id.account_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.clean_phone_number:
                loginPhoneNumberEt.setText("");
                break;
            case R.id.send_auth_code:
                sendCode();
                break;
            case R.id.clean_verificationcode:
                break;
            case R.id.hidden_verificationcode:
                break;
            case R.id.login_in:
                loginPresenter.phoneLogin(phoneNumberStr, phoneCodeStr);
                break;
            case R.id.account_login:
                AccountLoginActivity.start(this);
                break;
            default:
        }
    }

    private void sendCode() {
        if (!isAccountValid(phoneNumberStr)) {
            showError(getString(R.string.invalid_account_input));
            return;
        } else if (TextUtils.isEmpty(phoneNumberStr)) {
            showMessage(getString(R.string.empty_account));
            return;
        }
        loginPresenter.sendCode(phoneNumberStr, "login");
        sendAuthCode.startCold();
    }

    /**
     * 监听输入框
     */
    @OnTextChanged({R.id.login_phone_number_et, R.id.login_verificationcode_et})
    public void checkLoginEnable() {
        loginIn.setEnabled(true);
        phoneNumberStr = loginPhoneNumberEt.getText().toString();
        phoneCodeStr = loginVerificationcodeEt.getText().toString();
        loginIn.setEnabled(!(TextUtils.isEmpty(phoneNumberStr) || TextUtils.isEmpty(phoneCodeStr)));
        loginPhoneNumberEt.setFocusable(!sendAuthCode.isCounting());
    }

    @OnFocusChange({R.id.login_phone_number_et, R.id.login_verificationcode_et})
    public void changeFocus(View view) {
        loginPhoneNumberEt.setFocusable(!sendAuthCode.isCounting());
    }

    @Override
    protected void initializeInjector() {
        DaggerLoginComponent.builder()
                .applicationComponent(getApplicationComponent())
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void setPresenter(LoginContract.LoginPresenter presenter) {

    }


    @Override
    public void onReset() {
    }

    @Override
    public void accountLoginSuccess(PersionInfoResponse persionInfoResponse) {
        TokenManager.saveToken(persionInfoResponse.getToken());
        Gson gson=new Gson();
        String persionInfoResponseStr=gson.toJson(persionInfoResponse);
        persionAppPreferences.setPersionInfo(persionInfoResponseStr);
        Intent intent = new Intent();
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void codeSuccess() {
        showMessage(getString(R.string.login_auth_code_sent));
    }

    @Override
    public void checkCodeSuccess() {

    }

    @Override
    public void setPasswordSuccess() {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    assert v != null;
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        return getWindow().superDispatchTouchEvent(ev) || onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(loginPhoneNumberEt.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(loginVerificationcodeEt.getWindowToken(), 0);
    }


    @Override
    protected void onPause() {
        super.onPause();
        hideKeyboard();
    }
}
