package com.purchase.sls.energy.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.common.UMStaticData;
import com.purchase.sls.common.unit.StaticHandler;
import com.purchase.sls.common.unit.UmengEventUtils;
import com.purchase.sls.energy.DaggerEnergyComponent;
import com.purchase.sls.energy.EnergyContract;
import com.purchase.sls.energy.EnergyModule;
import com.purchase.sls.energy.presenter.SignInPresenter;

import java.lang.reflect.Field;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/6/7.
 */

public class SignInActivity extends BaseActivity implements EnergyContract.SignInView{
    @BindView(R.id.red_iv)
    ImageView redIv;
    @BindView(R.id.energy_number)
    TextView energyNumber;
    @BindView(R.id.sign_bg)
    RelativeLayout signBg;

    private AnimationDrawable anim;
    private String energyNb;
    @Inject
    SignInPresenter signInPresenter;

    public static void start(Context context) {
        Intent intent = new Intent(context, SignInActivity.class);
        context.startActivity(intent);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        initSign();
    }

    @Override
    protected void initializeInjector() {
        DaggerEnergyComponent.builder()
                .applicationComponent(getApplicationComponent())
                .energyModule(new EnergyModule(this))
                .build()
                .inject(this);
    }

    @OnClick({R.id.red_iv, R.id.sign_bg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.red_iv:
                signInPresenter.signIn();
                break;
            case R.id.sign_bg:
                finish();
                break;
            default:
        }
    }


    private void initSign() {
        redIv.setBackgroundResource(R.drawable.red_envelope);
        anim = (AnimationDrawable) redIv.getBackground();
    }

    @Override
    public void setPresenter(EnergyContract.SignInPresenter presenter) {

    }

    @Override
    public void showError(Throwable e) {
        super.showError(e);
        finish();
    }

    @Override
    public void signInSuccess(String energy) {
        this.energyNb=energy;
        UmengEventUtils.statisticsClick(this, UMStaticData.ENG_QIAN_DAO);
        anim.setOneShot(true);
        anim.start();
        mHandler.sendEmptyMessageDelayed(SPIKE_WHAT, 1200);
    }


    public static class MyHandler extends StaticHandler<SignInActivity> {

        public MyHandler(SignInActivity target) {
            super(target);
        }

        @Override
        public void handle(SignInActivity target, Message msg) {
            switch (msg.what) {
                case SPIKE_WHAT:
                    target.showToast();
                    break;
            }
        }
    }

    private Handler mHandler = new MyHandler(this);
    private static final int SPIKE_WHAT = 1;

    public void showToast() {
        energyNumber.setText("获得"+energyNb+"能量");
    }
}
