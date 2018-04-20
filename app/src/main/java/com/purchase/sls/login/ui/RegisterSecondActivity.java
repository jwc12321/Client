package com.purchase.sls.login.ui;

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

public class RegisterSecondActivity extends BaseActivity implements LoginContract.LoginView{

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
    @Inject
    LoginPresenter loginPresenter;

    public static void start(Context context, String type) {
        Intent intent = new Intent(context, RegisterSecondActivity.class);
        intent.putExtra(StaticData.TYPE, type);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_second);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        type = getIntent().getStringExtra(StaticData.TYPE);
        if (TextUtils.equals(StaticData.REGISTER, type)) {
            title.setText(getString(R.string.register));
        } else {
            title.setText(getString(R.string.forget_password));
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

    @OnClick({R.id.back,R.id.next})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.next:
                next();
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
        String passwordStr = setPasswordET.getText().toString();
        String passwordAgain=setPasswordAgainET.getText().toString();
        next.setEnabled(!(TextUtils.isEmpty(passwordStr) || TextUtils.isEmpty(passwordAgain)));
    }

    private void next(){
        String passwordStr = setPasswordET.getText().toString();
        String passwordAgain=setPasswordAgainET.getText().toString();
//        if (!NetUtils.isConnected()) {
//            showMessage(getString(R.string.check_network));
//            return;
//        }
        loginPresenter.registerPassword(passwordStr,passwordAgain,"",type);
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
    }
}
