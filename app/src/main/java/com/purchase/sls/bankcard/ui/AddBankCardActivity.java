package com.purchase.sls.bankcard.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.bankcard.BankCardContract;
import com.purchase.sls.bankcard.BankCardModule;
import com.purchase.sls.bankcard.DaggerBankCardComponent;
import com.purchase.sls.bankcard.presenter.AddBankCardPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/11/27.
 */

public class AddBankCardActivity extends BaseActivity implements BankCardContract.AddBankCardView {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.complete)
    TextView complete;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.bank_number_et)
    EditText bankNumberEt;
    @BindView(R.id.bank_name_et)
    EditText bankNameEt;
    @BindView(R.id.bank_subbranch_name_et)
    EditText bankSubbranchNameEt;
    @BindView(R.id.mobile_et)
    EditText mobileEt;
    @BindView(R.id.owner_name_et)
    EditText ownerNameEt;
    private String bankNumber;
    private String bankName;
    private String bankSubbranchName;
    private String mobile;
    private String ownerName;

    @Inject
    AddBankCardPresenter addBankCardPresenter;


    public static void start(Context context) {
        Intent intent = new Intent(context, AddBankCardActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank_card);
        ButterKnife.bind(this);
        setHeight(back, title, complete);
    }

    @OnClick({R.id.back, R.id.complete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.complete:
                complete();
                break;
            default:
        }
    }

    private void complete() {
        bankNumber=bankNumberEt.getText().toString();
        bankName=bankNameEt.getText().toString();
        bankSubbranchName=bankSubbranchNameEt.getText().toString();
        mobile=mobileEt.getText().toString();
        ownerName=ownerNameEt.getText().toString();
        if(TextUtils.isEmpty(bankNumber)){
            showMessage("请填写银行卡号");
            return;
        }
        if(TextUtils.isEmpty(bankName)){
            showMessage("请填写所属银行");
            return;
        }
        if(TextUtils.isEmpty(bankSubbranchName)){
            showMessage("请输入开户支行");
            return;
        }
        if(TextUtils.isEmpty(mobile)){
            showMessage("请输入银行预留手机号");
            return;
        }
        if(TextUtils.isEmpty(ownerName)){
            showMessage("请输入银行预留姓名");
            return;
        }
        addBankCardPresenter.addBankCard(bankNumber,bankName,bankSubbranchName,mobile,ownerName);
    }

    @Override
    protected void initializeInjector() {
        DaggerBankCardComponent.builder()
                .applicationComponent(getApplicationComponent())
                .bankCardModule(new BankCardModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void setPresenter(BankCardContract.AddBankCardPresenter presenter) {

    }

    @Override
    public void addSuccess() {
        showMessage("添加成功");
        finish();
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
}
