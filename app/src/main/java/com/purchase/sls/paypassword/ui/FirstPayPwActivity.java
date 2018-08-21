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
import android.widget.Toast;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.address.ui.AddAddressActivity;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.common.widget.paypw.PayPwdEditText;
import com.purchase.sls.ordermanage.ui.ActivityOrderActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/8/20.
 * 第一次设置支付密码
 */

public class FirstPayPwActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.pwd_et)
    PayPwdEditText pwdEt;
    @BindView(R.id.next)
    Button next;
    @BindView(R.id.setting_item)
    LinearLayout settingItem;

    private String whereGo; //1:设置密码2:修改密码
    private String password;

    public static void start(Context context,String whereGo) {
        Intent intent = new Intent(context, FirstPayPwActivity.class);
        intent.putExtra(StaticData.WHERE_GO,whereGo);
        context.startActivity(intent);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_pay_pw);
        ButterKnife.bind(this);
        setHeight(back,title,null);
        initView();
    }

    private void initView(){
        whereGo=getIntent().getStringExtra(StaticData.WHERE_GO);
        if(TextUtils.equals("1",whereGo)){
            title.setText("支付密码");
        }else {
            title.setText("修改支付密码");
        }
        initEt();
    }

    private void initEt(){
        pwdEt.initStyle(R.drawable.password_num_bg, 6, 0.33f, R.color.backGround19, R.color.backGround19, 20);
        pwdEt.setOnTextFinishListener(new PayPwdEditText.OnTextFinishListener() {
            @Override
            public void onFinish(String str) {//密码输入完后的回调
                password=str;
                next.setEnabled(true);
            }
        });

    }


    @OnClick({R.id.back, R.id.next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.next:
                SecondPayPwActivity.start(this,whereGo,password);
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
