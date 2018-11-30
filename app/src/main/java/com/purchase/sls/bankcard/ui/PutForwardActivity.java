package com.purchase.sls.bankcard.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.bankcard.BankCardContract;
import com.purchase.sls.bankcard.BankCardModule;
import com.purchase.sls.bankcard.DaggerBankCardComponent;
import com.purchase.sls.bankcard.presenter.PutForwardPresenter;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.data.entity.BankCardInfo;
import com.purchase.sls.data.entity.CommissionInfo;

import java.math.BigDecimal;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/11/27.
 */

public class PutForwardActivity extends BaseActivity implements BankCardContract.PutForwardView{
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.present_record)
    TextView presentRecord;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.bank_name)
    TextView bankName;
    @BindView(R.id.bank_number)
    TextView bankNumber;
    @BindView(R.id.choice_bankcard)
    TextView choiceBankcard;
    @BindView(R.id.item_bank)
    RelativeLayout itemBank;
    @BindView(R.id.input_cash_et)
    EditText inputCashEt;
    @BindView(R.id.can_putword)
    TextView canPutword;
    @BindView(R.id.balance_tv)
    TextView balanceTv;
    @BindView(R.id.all_put_forward)
    TextView allPutForward;
    @BindView(R.id.comfirm)
    TextView comfirm;

    private static final int CHOICE_BANK = 102;

    private BankCardInfo bankCardInfo;
    private String bankId;
    private String balance;
    private String inputCash;
    boolean moneyDouble = true;
    private int digits = 2;
    private BigDecimal inputCashBd;
    private BigDecimal balanceBd;

    @Inject
    PutForwardPresenter putForwardPresenter;

    public static void start(Context context) {
        Intent intent = new Intent(context, PutForwardActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_forward);
        ButterKnife.bind(this);
        setHeight(back,title,presentRecord);
        initView();
    }


    private void initView(){
        editListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        putForwardPresenter.getCommissionInfo();
    }

    /**
     * edittext监听
     */
    private void editListener() {
        //只是为了显示光标
        inputCashEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputCashEt.setCursorVisible(true);
            }
        });
        inputCashEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(inputCashEt.getText().toString())) {
                    moneyDouble = true;
                } else {
                    try {
                        Double db = Double.valueOf(inputCashEt.getText().toString());
                        moneyDouble = true;
                    } catch (Exception e) {
                        moneyDouble = false;
                        Toast.makeText(PutForwardActivity.this, "请正确填写", Toast.LENGTH_SHORT).show();
                    }
                    if (moneyDouble) {
                        if (!TextUtils.isEmpty(inputCashEt.getText().toString())) {
                            limitedDecimal(inputCashEt.getText().toString(), inputCashEt);
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void limitedDecimal(String s, EditText editText) {
        //删除“.”后面超过2位后的数据
        if (s.toString().contains(".")) {
            if (s.length() - 1 - s.toString().indexOf(".") > digits) {
                s = (String) s.toString().subSequence(0, s.toString().indexOf(".") + digits + 1);
                editText.setText(s);
                editText.setSelection(s.length()); //光标移到最后
            }
        }
        //如果"."在起始位置,则起始位置自动补0
        if (s.toString().trim().substring(0).equals(".")) {
            s = "0" + s;
            editText.setText(s);
            editText.setSelection(2);
        }

        //如果起始位置为0,且第二位跟的不是".",则无法后续输入
        if (s.toString().startsWith("0")
                && s.toString().trim().length() > 1) {
            if (!s.toString().substring(1, 2).equals(".")) {
                editText.setText(s.subSequence(0, 1));
                editText.setSelection(1);
                return;
            }
        }
    }



    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void setPresenter(BankCardContract.PutForwardPresenter presenter) {

    }

    @Override
    public void putForwardSuccess() {
        inputCashEt.setText("");
        PutForwardRecordActivity.start(this);
    }

    @Override
    public void renderCommissionInfo(CommissionInfo commissionInfo) {
        if(commissionInfo!=null){
            balance=commissionInfo.getBalanceMoney();
            balanceTv.setText(balance);
        }
    }

    @OnClick({R.id.back, R.id.present_record,R.id.item_bank,R.id.all_put_forward,R.id.comfirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.present_record:
                PutForwardRecordActivity.start(this);
                break;
            case R.id.item_bank:
                Intent intent = new Intent(this, BankCardListActivity.class);
                intent.putExtra(StaticData.IS_BANK_INTO,"1");
                startActivityForResult(intent, CHOICE_BANK);
                break;
            case R.id.all_put_forward:
                inputCashEt.setText(balance);
                break;
            case R.id.comfirm:
                comfirm();
                break;
            default:
        }
    }

    private void comfirm(){
        inputCash=inputCashEt.getText().toString();
        if(TextUtils.isEmpty(bankId)){
            showMessage("请选择银行卡");
            return;
        }
        if(TextUtils.isEmpty(inputCash)){
            showMessage("请填写提现金额");
            return;
        }
        inputCashBd = new BigDecimal(inputCash).setScale(2, BigDecimal.ROUND_DOWN);
        balanceBd = new BigDecimal(balance).setScale(2, BigDecimal.ROUND_DOWN);
        if (inputCashBd.compareTo(balanceBd) > 0) {
            showMessage("输入价格大于可提现金额，请重新输入");
            inputCashEt.setText("");
            return;
        }
        putForwardPresenter.putForward(bankId,inputCash);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            switch (requestCode) {
                case CHOICE_BANK:
                    bankCardInfo = (BankCardInfo) data.getSerializableExtra(StaticData.BANK_INFO);
                    if (bankCardInfo != null) {
                        bankId = bankCardInfo.getId();
                        bankName.setVisibility(View.VISIBLE);
                        bankNumber.setVisibility(View.VISIBLE);
                        choiceBankcard.setVisibility(View.GONE);
                        String cardNo = bankCardInfo.getBankNumber();
                        bankName.setText(bankCardInfo.getBankName());
                        if (!TextUtils.isEmpty(bankCardInfo.getBankNumber())) {
                            bankNumber.setText("尾号"+cardNo.substring(cardNo.length() - 4, cardNo.length()));
                        }
                    }
                    break;
                default:
            }
        }
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
