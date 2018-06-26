package com.purchase.sls.login.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.common.unit.AccountUtils;
import com.purchase.sls.data.entity.PersionInfoResponse;
import com.purchase.sls.login.DaggerLoginComponent;
import com.purchase.sls.login.LoginContract;
import com.purchase.sls.login.LoginModule;
import com.purchase.sls.login.presenter.LoginPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import static com.purchase.sls.common.unit.AccountUtils.isAccountValid;

/**
 * Created by Administrator on 2018/4/18.
 * 注册页面和修改密码
 */

public class RegisterSecondActivity extends BaseActivity implements LoginContract.LoginView {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.complete)
    TextView complete;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.set_password_et)
    EditText setPasswordET;
    @BindView(R.id.set_password_again_et)
    EditText setPasswordAgainET;
    @BindView(R.id.password_ll)
    LinearLayout passwordLl;
    @BindView(R.id.next)
    Button next;
    @BindView(R.id.over_iv)
    ImageView overIv;

    private String type;
    private String phoneNumber;
    private String storeid;
    private String passwordStr;
    private String passwordAgain;
    @Inject
    LoginPresenter loginPresenter;
    private String phoneCode;

    public static void start(Context context, String type, String phoneNumber, String storeid,String code) {
        Intent intent = new Intent(context, RegisterSecondActivity.class);
        intent.putExtra(StaticData.PHONE_NUMBER, phoneNumber);
        intent.putExtra(StaticData.TYPE, type);
        intent.putExtra(StaticData.BUSINESS_STOREID, storeid);
        intent.putExtra(StaticData.PHONE_CODE,code);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_second);
        ButterKnife.bind(this);
        setHeight(back,title,complete);
        initView();
    }

    private void initView() {
        type = getIntent().getStringExtra(StaticData.TYPE);
        phoneCode=getIntent().getStringExtra(StaticData.PHONE_CODE);
        phoneNumber = getIntent().getStringExtra(StaticData.PHONE_NUMBER);
        storeid = getIntent().getStringExtra(StaticData.BUSINESS_STOREID);
        title.setText(getString(R.string.set_passwrod));
        setPasswordET.setFocusable(true);
        setPasswordET.setFocusableInTouchMode(true);
        setPasswordET.requestFocus();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(setPasswordET, InputMethodManager.SHOW_FORCED);
            }
        }, 500);
    }

    @Override
    protected void initializeInjector() {
        DaggerLoginComponent.builder()
                .applicationComponent(getApplicationComponent())
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    @OnClick({R.id.back, R.id.next, R.id.complete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.next:
                if (TextUtils.equals(passwordStr, passwordAgain)) {
                    if (!TextUtils.isEmpty(phoneCode)) {
                        loginPresenter.registerPassword(phoneNumber, passwordAgain, "", type, storeid,phoneCode);
                    } else {
                        loginPresenter.changepwd(phoneNumber, passwordAgain, type);
                    }
                } else {
                    showMessage("两次输入得密码不一样");
                }
                break;
            case R.id.complete:
                finish();
                break;
            default:
        }
    }

    /**
     * 监听输入框
     */
    @OnTextChanged({R.id.set_password_et, R.id.set_password_again_et})
    public void checkLoginEnable() {
        next.setEnabled(true);
        passwordStr = setPasswordET.getText().toString();
        passwordAgain = setPasswordAgainET.getText().toString();
        next.setEnabled(!(TextUtils.isEmpty(passwordStr) || TextUtils.isEmpty(passwordAgain)));
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void setPresenter(LoginContract.LoginPresenter presenter) {

    }

    @Override
    public void accountLoginSuccess(PersionInfoResponse persionInfoResponse) {

    }

    @Override
    public void codeSuccess() {

    }

    @Override
    public void checkCodeSuccess() {

    }

    @Override
    public void setPasswordSuccess() {
        passwordLl.setVisibility(View.GONE);
        next.setVisibility(View.GONE);
        overIv.setVisibility(View.VISIBLE);
        complete.setVisibility(View.VISIBLE);
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

    @Override
    protected void onPause() {
        super.onPause();
        hideKeyboard();
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(setPasswordET.getWindowToken(),0);
        inputMethodManager.hideSoftInputFromWindow(setPasswordAgainET.getWindowToken(),0);
    }
}
