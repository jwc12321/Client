package com.purchase.sls.mine.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.common.unit.AccountUtils;
import com.purchase.sls.common.unit.CommonAppPreferences;
import com.purchase.sls.common.widget.ColdDownButton;
import com.purchase.sls.data.entity.PersionInfoResponse;
import com.purchase.sls.login.ui.AccountLoginActivity;
import com.purchase.sls.mine.DaggerPersonalCenterComponent;
import com.purchase.sls.mine.PersonalCenterContract;
import com.purchase.sls.mine.PersonalCenterModule;
import com.purchase.sls.mine.presenter.ShiftHandsetPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import static com.purchase.sls.common.unit.AccountUtils.isAccountValid;

/**
 * Created by JWC on 2018/5/4.
 */

public class ShiftHandsetActivity extends BaseActivity implements PersonalCenterContract.ShiftHandsetView, ColdDownButton.OnResetListener {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.photo_number_et)
    EditText photoNumberEt;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.send_auth_code)
    ColdDownButton sendAuthCode;
    @BindView(R.id.phone_code_et)
    EditText phoneCodeEt;
    @BindView(R.id.first)
    LinearLayout first;
    @BindView(R.id.ok_button)
    Button okButton;

    private CommonAppPreferences commonAppPreferences;
    private String persionInfoStr;
    private PersionInfoResponse persionInfoResponse;
    private Gson gson;
    private String typeWhat;
    private String phoneNumberStr;
    private String phoneCodeStr;

    @Inject
    ShiftHandsetPresenter shiftHandsetPresenter;

    public static void start(Context context) {
        Intent intent = new Intent(context, ShiftHandsetActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_handset);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        typeWhat = "1";
        commonAppPreferences = new CommonAppPreferences(this);
        persionInfoStr = commonAppPreferences.getPersionInfo();
        gson = new Gson();
        if (persionInfoStr != null && !TextUtils.isEmpty(persionInfoStr)) {
            persionInfoResponse = gson.fromJson(persionInfoStr, PersionInfoResponse.class);
            phoneNumberStr = persionInfoResponse.getTel();
            photoNumberEt.setText(phoneNumberStr);
            sendAuthCode.startCold();
            shiftHandsetPresenter.sendOldVcode(phoneNumberStr, "changetel");
            sendAuthCode.setOnResetListener(this);
        }
    }


    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    protected void initializeInjector() {
        DaggerPersonalCenterComponent.builder()
                .applicationComponent(getApplicationComponent())
                .personalCenterModule(new PersonalCenterModule(this))
                .build().inject(this);
    }

    @Override
    public void setPresenter(PersonalCenterContract.ShiftHandsetPresenter presenter) {

    }

    @Override
    public void oldVcodeSuccess() {
        showMessage("验证码发送成功");
    }

    @Override
    public void checkOldCodeSuccess() {
        showMessage("验证成功");
        sendAuthCode.reset();
        typeWhat = "2";
        photoNumberEt.setText("");
        phoneCodeEt.setText("");
        photoNumberEt.setHint("请输入新手机号");
        phoneCodeEt.setHint("请输入手机验证码");
        photoNumberEt.setFocusable(true);
        photoNumberEt.setFocusableInTouchMode(true);
        photoNumberEt.requestFocus();
    }

    @Override
    public void newVcodeSuccess() {
        showMessage("验证码发送成功");
    }

    @Override
    public void checkNewCodeSuccess() {
        AccountLoginActivity.start(this);
        finish();
    }

    @Override
    public void onReset() {

    }

    @OnClick({R.id.back, R.id.ok_button,R.id.send_auth_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.ok_button:
                okButton();
                break;
            case R.id.send_auth_code:
                sendCode();
                break;
            default:
        }
    }

    /**
     * 发送验证码
     */
    private void sendCode() {
        phoneNumberStr = photoNumberEt.getText().toString();
        if (!isAccountValid(phoneNumberStr)) {
            showError(getString(R.string.invalid_account_input));
            return;
        } else if (TextUtils.isEmpty(phoneNumberStr)) {
            showMessage(getString(R.string.empty_account));
            return;
        }
        sendAuthCode.startCold();
        if (TextUtils.equals("1", typeWhat)) {
            shiftHandsetPresenter.sendOldVcode(phoneNumberStr, "changetel");
        } else {
            shiftHandsetPresenter.sendNewVCode(phoneNumberStr);
        }
        sendAuthCode.setOnResetListener(this);
    }

    private void okButton() {
        if (TextUtils.isEmpty(phoneCodeEt.getText().toString())) {
            showMessage("请输入验证码");
        } else {
            if (TextUtils.equals("1", typeWhat)) {
                shiftHandsetPresenter.checkOldCode(phoneNumberStr, phoneCodeEt.getText().toString(), "changetel");
            } else {
                shiftHandsetPresenter.checkNewCode(phoneNumberStr, phoneCodeEt.getText().toString());
            }
        }
    }


    /**
     * 监听输入框
     */
    @OnTextChanged({R.id.photo_number_et, R.id.phone_code_et})
    public void checkLoginEnable() {
        okButton.setEnabled(true);
        phoneNumberStr = photoNumberEt.getText().toString().trim();
        phoneCodeStr = phoneCodeEt.getText().toString().trim();
        sendAuthCode.setEnabled(!TextUtils.isEmpty(phoneNumberStr) && AccountUtils.isAccountValid(phoneNumberStr));
//        photoNumberEt.setFocusable(!sendAuthCode.isCounting());
        okButton.setEnabled(!(TextUtils.isEmpty(phoneNumberStr) || TextUtils.isEmpty(phoneCodeStr)));
    }
}

