package com.purchase.sls.paypassword.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.common.widget.paypw.PayPwdEditText;
import com.purchase.sls.data.entity.ActivityOrderDetailInfo;
import com.purchase.sls.paypassword.DaggerPayPasswordComponent;
import com.purchase.sls.paypassword.PayPasswordContract;
import com.purchase.sls.paypassword.PayPasswordModule;
import com.purchase.sls.paypassword.presenter.EcPayPwPowerPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/8/21.
 *  能量中心的秒杀输入支付密码
 */

public class EcInputPayPwActivity extends BaseActivity implements PayPasswordContract.EcPayPwPowerView{
    @BindView(R.id.cancel)
    ImageView cancel;
    @BindView(R.id.pwd_et)
    PayPwdEditText pwdEt;
    @BindView(R.id.item_input_pw)
    RelativeLayout itemInputPw;

    @Inject
    EcPayPwPowerPresenter ecPayPwPowerPresenter;

    private String id;
    private String aid;
    private String whereGo;//1:秒杀3：抽奖


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_paypw);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        id=getIntent().getStringExtra(StaticData.ACTIVITY_ID);
        aid=getIntent().getStringExtra(StaticData.ADDRESS_ID);
        whereGo=getIntent().getStringExtra(StaticData.WHERE_GO);
        initEt();
    }

    private void initEt() {
        pwdEt.initStyle(R.drawable.password_num_bg, 6, 0.33f, R.color.backGround19, R.color.backGround19, 20);
        pwdEt.setOnTextFinishListener(new PayPwdEditText.OnTextFinishListener() {
            @Override
            public void onFinish(String str) {//密码输入完后的回调
                if(TextUtils.equals("1",whereGo)){
                    ecPayPwPowerPresenter.paysecKill(str,id,aid);
                }else {
                    ecPayPwPowerPresenter.paydrawOrder(str,id,aid);
                }
            }
        });

    }

    @OnClick({R.id.cancel, R.id.item_input_pw})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
            case R.id.item_input_pw:
                finish();
                break;
            default:
        }
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
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void setPresenter(PayPasswordContract.EcPayPwPowerPresenter presenter) {

    }

    @Override
    public void paySuccess(ActivityOrderDetailInfo activityOrderDetailInfo) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(StaticData.ACTIVITY_DATA, activityOrderDetailInfo);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }
}
