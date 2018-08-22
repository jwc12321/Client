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
import com.purchase.sls.common.StaticData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/8/22.
 */

public class RememberPswActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.do_not_remember_bt)
    Button doNotRememberBt;
    @BindView(R.id.remember_bt)
    Button rememberBt;
    @BindView(R.id.setting_item)
    LinearLayout settingItem;

    private String phoneNumber;

    public static void start(Context context, String phoneNumber) {
        Intent intent = new Intent(context, RememberPswActivity.class);
        intent.putExtra(StaticData.PHONE_NUMBER, phoneNumber);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remember_psw);
        ButterKnife.bind(this);
        setHeight(back,title,null);
        initView();
    }

    private void initView(){
        phoneNumber = getIntent().getStringExtra(StaticData.PHONE_NUMBER);
        if (!TextUtils.isEmpty(phoneNumber)) {
            userName.setText("您是否记得账号*" + phoneNumber.substring(phoneNumber.length() - 4, phoneNumber.length()));
        }
    }


    @OnClick({R.id.back, R.id.do_not_remember_bt,R.id.remember_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.do_not_remember_bt:
                SmsAuthenticationActivity.start(this,phoneNumber);
                this.finish();
                break;
            case R.id.remember_bt:
                AuthenticationActivity.start(this);
                this.finish();
                break;
            default:
        }
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }
}
