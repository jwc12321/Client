package com.purchase.sls.login.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.common.unit.AccountUtils;
import com.purchase.sls.common.widget.ColdDownButton;
import com.purchase.sls.data.entity.PersionInfoResponse;
import com.purchase.sls.login.DaggerLoginComponent;
import com.purchase.sls.login.LoginContract;
import com.purchase.sls.login.LoginModule;
import com.purchase.sls.login.presenter.LoginPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import static com.purchase.sls.common.unit.AccountUtils.isAccountValid;

/**
 * Created by JWC on 2018/4/19.
 */

public class RegisterFirstActivity extends BaseActivity implements LoginContract.LoginView, ColdDownButton.OnResetListener {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.login_phone_number_et)
    EditText loginPhoneNumberEt;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.send_auth_code)
    ColdDownButton sendAuthCode;
    @BindView(R.id.phone_code_et)
    EditText phoneCodeEt;
    @BindView(R.id.first)
    LinearLayout first;
    @BindView(R.id.next)
    Button next;
    @BindView(R.id.agreement_check)
    CheckBox agreementCheck;
    @BindView(R.id.agreement)
    TextView agreement;
    @BindView(R.id.registration_agreement_ll)
    LinearLayout registrationAgreementLl;

    private String type;
    private String phoneNumberStr;
    private String phoneCodeStr;
    @Inject
    LoginPresenter loginPresenter;

    public static void start(Context context, String type) {
        Intent intent = new Intent(context, RegisterFirstActivity.class);
        intent.putExtra(StaticData.TYPE, type);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_first);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void initializeInjector() {
        DaggerLoginComponent.builder()
                .applicationComponent(getApplicationComponent())
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    private void initView() {
        type = getIntent().getStringExtra(StaticData.TYPE);
        if (TextUtils.equals(StaticData.REGISTER, type)) {
            title.setText(getString(R.string.register));
            registrationAgreementLl.setVisibility(View.VISIBLE);
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.appText3));
            SpannableString spannableString = new SpannableString(getString(R.string.registration_agreement));
            spannableString.setSpan(foregroundColorSpan, 7, 15, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            spannableString.setSpan(protocolSpan, 7, 15, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            agreement.setMovementMethod(LinkMovementMethod.getInstance());
            agreement.setLinksClickable(true);
            agreement.setText(spannableString);
        } else {
            title.setText(getString(R.string.forget_password));
            registrationAgreementLl.setVisibility(View.GONE);
        }
    }

    private ClickableSpan protocolSpan = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
        }
    };

    /**
     * 监听输入框
     */
    @OnTextChanged({R.id.login_phone_number_et, R.id.phone_code_et})
    public void checkLoginEnable() {
        next.setEnabled(true);
        phoneNumberStr = loginPhoneNumberEt.getText().toString().trim();
        phoneCodeStr = phoneCodeEt.getText().toString().trim();
        sendAuthCode.setEnabled(!TextUtils.isEmpty(phoneNumberStr) && AccountUtils.isAccountValid(phoneNumberStr));
        loginPhoneNumberEt.setFocusable(!sendAuthCode.isCounting());
        if (TextUtils.equals(StaticData.REGISTER, type)) {
            next.setEnabled(!(TextUtils.isEmpty(phoneNumberStr) || TextUtils.isEmpty(phoneCodeStr) || !agreementCheck.isChecked()));
        } else {
            next.setEnabled(!(TextUtils.isEmpty(phoneNumberStr) || TextUtils.isEmpty(phoneCodeStr)));
        }
    }

    @OnCheckedChanged({R.id.agreement_check})
    public void agreementCheck(boolean checked) {
        String phomeNumber = loginPhoneNumberEt.getText().toString().trim();
        String phoneCode = phoneCodeEt.getText().toString().trim();
        if (TextUtils.equals(StaticData.REGISTER, type)) {
            next.setEnabled(!(TextUtils.isEmpty(phomeNumber) || TextUtils.isEmpty(phoneCode) || !agreementCheck.isChecked()));
        }
    }


    @OnClick({R.id.back, R.id.send_auth_code,R.id.next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.send_auth_code:
                sendCode();
                break;
            case R.id.next:
                loginPresenter.checkCode(phoneNumberStr, phoneCodeStr, type);
                break;

        }
    }

    /**
     * 发送验证码
     */
    private void sendCode() {
        if (!isAccountValid(phoneNumberStr)) {
            showError(getString(R.string.invalid_account_input));
            return;
        } else if (TextUtils.isEmpty(phoneNumberStr)) {
            showMessage(getString(R.string.empty_account));
            return;
        }
        sendAuthCode.startCold();
        loginPresenter.sendCode(phoneNumberStr, type);
        sendAuthCode.setOnResetListener(this);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void setPresenter(LoginContract.LoginPresenter presenter) {

    }

    @Override
    public void onReset() {

    }

    @Override
    public void accountLoginSuccess(PersionInfoResponse persionInfoResponse) {

    }

    @Override
    public void codeSuccess() {
        showMessage(getString(R.string.login_auth_code_sent));
    }

    @Override
    public void checkCodeSuccess() {
        RegisterSecondActivity.start(this,type,phoneNumberStr);
    }

    @Override
    public void setPasswordSuccess() {

    }
}
