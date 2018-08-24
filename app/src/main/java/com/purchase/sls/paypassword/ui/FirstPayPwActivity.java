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
import com.purchase.sls.common.widget.paypw.PayPwdEditText;

import java.util.regex.Pattern;

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

    private String whereGo; //0:新密码(第一次设置支付密码)1:忘记支付密码短信修改2:记得支付密码修改
    private String password;
    private String ppwOldData;

    public static void start(Context context,String whereGo, String ppwOldData) {
        Intent intent = new Intent(context, FirstPayPwActivity.class);
        intent.putExtra(StaticData.WHERE_GO,whereGo);
        intent.putExtra(StaticData.PPW_OLD_DATA,ppwOldData);
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
        ppwOldData=getIntent().getStringExtra(StaticData.PPW_OLD_DATA);
        if(TextUtils.equals("0",whereGo)){
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
                if(!isNumber(password)){
                    showMessage("请输入数字支付密码");
                    return;
                }
                if(repeatNumber(password)){
                    showMessage("支付密码不能是重复、连续的数字");
                    return;
                }
                SecondPayPwActivity.start(this,whereGo,password,ppwOldData);
                this.finish();
                break;
            default:
        }
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    private boolean isNumber(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    //判断是不是重复数字
    private boolean repeatNumber(String str){
        if(TextUtils.equals(str,"012345")
                ||TextUtils.equals(str,"123456")
                ||TextUtils.equals(str,"234567")
                ||TextUtils.equals(str,"345678")
                ||TextUtils.equals(str,"456789")
                ||TextUtils.equals(str,"543210")
                ||TextUtils.equals(str,"654321")
                ||TextUtils.equals(str,"765432")
                ||TextUtils.equals(str,"876543")
                ||TextUtils.equals(str,"987654")
                ||TextUtils.equals(str,"000000")
                ||TextUtils.equals(str,"111111")
                ||TextUtils.equals(str,"222222")
                ||TextUtils.equals(str,"333333")
                ||TextUtils.equals(str,"444444")
                ||TextUtils.equals(str,"555555")
                ||TextUtils.equals(str,"666666")
                ||TextUtils.equals(str,"777777")
                ||TextUtils.equals(str,"888888")
                ||TextUtils.equals(str,"999999")){
            return true;
        }
        return false;
    }
}
