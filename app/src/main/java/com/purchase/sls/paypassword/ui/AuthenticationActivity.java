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
import com.purchase.sls.common.widget.paypw.PayPwdEditText;
import com.purchase.sls.paypassword.DaggerPayPasswordComponent;
import com.purchase.sls.paypassword.PayPasswordContract;
import com.purchase.sls.paypassword.PayPasswordModule;
import com.purchase.sls.paypassword.presenter.AuthenticationPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/8/22.
 * 验证身份
 */

public class AuthenticationActivity extends BaseActivity implements PayPasswordContract.AuthenticationView{
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

    @Inject
    AuthenticationPresenter authenticationPresenter;

    public static void start(Context context) {
        Intent intent = new Intent(context, AuthenticationActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        ButterKnife.bind(this);
        setHeight(back,title,null);
        initEt();
    }

    private void initEt(){
        pwdEt.initStyle(R.drawable.password_num_bg, 6, 0.33f, R.color.backGround19, R.color.backGround19, 20);
        pwdEt.setOnTextFinishListener(new PayPwdEditText.OnTextFinishListener() {
            @Override
            public void onFinish(String str) {//密码输入完后的回调
                password=str;
                confirm.setEnabled(true);
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
                if(!TextUtils.isEmpty(password)){
                    authenticationPresenter.verifyPayPassword(password);
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
    public void setPresenter(PayPasswordContract.AuthenticationPresenter presenter) {

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
    public void verifySuccess() {
        FirstPayPwActivity.start(this,"2",password);
        this.finish();
    }
}
