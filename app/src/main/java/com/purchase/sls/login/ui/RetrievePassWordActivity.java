package com.purchase.sls.login.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.login.DaggerLoginComponent;
import com.purchase.sls.login.LoginContract;
import com.purchase.sls.login.LoginModule;
import com.purchase.sls.login.presenter.RetrievePassWordPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/4/13.
 */

public class RetrievePassWordActivity extends BaseActivity implements LoginContract.RetrievePassWordView {
    @Inject
    RetrievePassWordPresenter retrievePresenter;
    @BindView(R.id.button)
    Button button;

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_password);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                retrievePresenter.sendCaptcha("18758302924");
                break;
            default:
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

    @Override
    public void setPresenter(LoginContract.RetrievePassWordPresenter presenter) {

    }

    @Override
    public void onCaptchaSend() {
        Toast toast = Toast.makeText(getApplicationContext(), "验证码发送到了", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void notRegister() {

    }
}

