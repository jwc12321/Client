package com.purchase.sls.applyvip.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.applyvip.ApplyVipContract;
import com.purchase.sls.applyvip.ApplyVipModule;
import com.purchase.sls.applyvip.DaggerApplyVipComponent;
import com.purchase.sls.applyvip.presenter.ApplyVipPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/11/27.
 */

public class ApplyVipActivity extends BaseActivity implements ApplyVipContract.ApplyVipView{
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.complete)
    TextView complete;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.id_et)
    EditText idEt;
    @BindView(R.id.real_name_et)
    EditText realNameEt;

    private String idNumberStr;
    private String realNameStr;

    @Inject
    ApplyVipPresenter applyVipPresenter;

    public static void start(Context context) {
        Intent intent = new Intent(context, ApplyVipActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applyvip);
        ButterKnife.bind(this);
        setHeight(back,title,complete);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    protected void initializeInjector() {
        DaggerApplyVipComponent.builder()
                .applicationComponent(getApplicationComponent())
                .applyVipModule(new ApplyVipModule(this))
                .build()
                .inject(this);
    }

    @OnClick({R.id.back,R.id.complete})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.complete:
                complete();
                break;
                default:
        }
    }

    public void complete(){
        idNumberStr=idEt.getText().toString();
        realNameStr=realNameEt.getText().toString();
        if(TextUtils.isEmpty(idNumberStr)){
            showMessage("请输入身份证号");
            return;
        }
        if(TextUtils.isEmpty(realNameStr)){
            showMessage("请输入真实姓名");
            return;
        }
        applyVipPresenter.applyVip(idNumberStr,realNameStr);
    }

    @Override
    public void setPresenter(ApplyVipContract.ApplyVipPresenter presenter) {

    }

    @Override
    public void applyVipSuccess() {
        showMessage("已申请，等待审核");
        finish();
    }
}
