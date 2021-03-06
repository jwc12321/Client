package com.purchase.sls.login.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.common.unit.PermissionUtil;
import com.purchase.sls.common.unit.PersionAppPreferences;
import com.purchase.sls.common.unit.TokenManager;
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
import butterknife.OnTextChanged;
import cn.jpush.android.api.JPushInterface;

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
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.icon)
    ImageView icon;
    @BindView(R.id.input_number_rel)
    RelativeLayout inputNumberRel;
    @BindView(R.id.input_password_rel)
    RelativeLayout inputPasswordRel;
    @BindView(R.id.login_type)
    TextView loginType;
    @BindView(R.id.other_rel)
    RelativeLayout otherRel;
    @BindView(R.id.login_cl)
    ConstraintLayout loginCl;

    //登录按钮是否可点击
    private boolean loginEnable = false;
    //密码是否显示
    private boolean isPassWordVisible = true;
    private PersionAppPreferences persionAppPreferences;

    private String accountNumber;
    private String loginPassword;

    @Inject
    LoginPresenter loginPresenter;

    private static final int CODE_SMS_LOGIN = 1;
    private String goType;
    private String storeId;

    @Override
    public View getSnackBarHolderView() {
        return loginCl;
    }


    public static void start(Context context) {
        Intent intent = new Intent(context, AccountLoginActivity.class);
        context.startActivity(intent);
    }

    public static void start(Context context, String goType,String storeid) {
        Intent intent = new Intent(context, AccountLoginActivity.class);
        intent.putExtra(StaticData.GO_TYPE, goType);
        intent.putExtra(StaticData.BUSINESS_STOREID, storeid);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_login);
        ButterKnife.bind(this);
        persionAppPreferences = new PersionAppPreferences(this);
        goType = getIntent().getStringExtra(StaticData.GO_TYPE);
        storeId=getIntent().getStringExtra(StaticData.BUSINESS_STOREID);
        if (TextUtils.equals("1", goType)) {
            RegisterFirstActivity.start(this, StaticData.REGISTER, storeId);
        }
    }

    @Override
    protected void initializeInjector() {
        DaggerLoginComponent.builder()
                .applicationComponent(getApplicationComponent())
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    @OnClick({R.id.clean_account_number, R.id.clean_password, R.id.hidden_password, R.id.login_in, R.id.sms_login, R.id.forget_password, R.id.immediate_registration, R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
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
                Intent intent = new Intent(this, SmsLoginActivity.class);
                startActivityForResult(intent, CODE_SMS_LOGIN);
                break;
            case R.id.forget_password:
                RegisterFirstActivity.start(this, StaticData.CHANGEPWD, "");
                break;
            case R.id.immediate_registration:
                RegisterFirstActivity.start(this, StaticData.REGISTER, "");
                break;
            case R.id.back:
                finish();
                break;
            default:
        }
    }

    /**
     * 隐藏和显示密码
     */
    private void passwordDisplay() {
        if (isPassWordVisible) {
            hiddenPassword.setImageResource(R.mipmap.ic_invisible_hig);
            loginPasswordEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            loginPassword = loginPasswordEt.getText().toString().trim();
            loginPasswordEt.setSelection(loginPassword.toCharArray().length);
            isPassWordVisible = false;
        } else {
            //隐藏密码
            isPassWordVisible = true;
            loginPassword = loginPasswordEt.getText().toString().trim();
            loginPasswordEt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            loginPasswordEt.setSelection(loginPassword.toCharArray().length);
            hiddenPassword.setImageResource(R.mipmap.si_yan);
        }
    }

    /**
     * 监听输入框
     */
    @OnTextChanged({R.id.login_account_number_et, R.id.login_password_et})
    public void checkLoginEnable() {
        loginIn.setEnabled(true);
        accountNumber = loginAccountNumberEt.getText().toString().trim();
        loginPassword = loginPasswordEt.getText().toString().trim();
        loginIn.setEnabled(!(TextUtils.isEmpty(accountNumber) || TextUtils.isEmpty(loginPassword)));
    }

    /**
     * 登录
     */
    private void login() {
        loginPresenter.accountLogin(accountNumber, loginPassword, "");
    }

    @Override
    public void setPresenter(LoginContract.LoginPresenter presenter) {

    }


    @Override
    public void accountLoginSuccess(PersionInfoResponse persionInfoResponse) {
        TokenManager.saveToken(persionInfoResponse.getToken());
        Gson gson = new Gson();
        String persionInfoResponseStr = gson.toJson(persionInfoResponse);
        persionAppPreferences.setPersionInfo(persionInfoResponseStr);
        persionAppPreferences.setShopMallId(persionInfoResponse.getId());
        JPushInterface.setAliasAndTags(getApplicationContext(),
                persionInfoResponse.getTel(),
                null,
                null);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CODE_SMS_LOGIN:
                    this.finish();
                    break;
                default:
            }
        }
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
        inputMethodManager.hideSoftInputFromWindow(loginAccountNumberEt.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(loginPasswordEt.getWindowToken(), 0);
    }


    @Override
    protected void onPause() {
        super.onPause();
        hideKeyboard();
    }
}
