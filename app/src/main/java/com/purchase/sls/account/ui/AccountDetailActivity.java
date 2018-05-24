package com.purchase.sls.account.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.account.AccountContract;
import com.purchase.sls.account.AccountModule;
import com.purchase.sls.account.DaggerAccountComponent;
import com.purchase.sls.account.presenter.AccountDetailPresenter;
import com.purchase.sls.common.GlideHelper;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.common.unit.FormatUtil;
import com.purchase.sls.data.entity.AccountDetailInfo;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/5/2.
 * 账单详情
 */

public class AccountDetailActivity extends BaseActivity implements AccountContract.AccountDetailView {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.photo)
    RoundedImageView photo;
    @BindView(R.id.business_name)
    TextView businessName;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.trading_state)
    TextView tradingState;
    @BindView(R.id.payment_method)
    TextView paymentMethod;
    @BindView(R.id.commodity_description)
    TextView commodityDescription;
    @BindView(R.id.created_at)
    TextView createdAt;
    @BindView(R.id.order_number)
    TextView orderNumber;
    @BindView(R.id.merchant_order_number)
    TextView merchantOrderNumber;
    @BindView(R.id.go_recode_rl)
    RelativeLayout goRecodeRl;
    private String billid;
    private String storeid;
    private String titleStr;
    @Inject
    AccountDetailPresenter accountDetailPresenter;

    public static void start(Context context, String billid) {
        Intent intent = new Intent(context, AccountDetailActivity.class);
        intent.putExtra(StaticData.BILLID, billid);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_detail);
        ButterKnife.bind(this);
        setHeight(back,title,null);
        initView();
    }

    private void initView() {
        billid = getIntent().getStringExtra(StaticData.BILLID);
        accountDetailPresenter.getAccountDetail(billid);
    }

    @Override
    protected void initializeInjector() {
        DaggerAccountComponent.builder()
                .applicationComponent(getApplicationComponent())
                .accountModule(new AccountModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void setPresenter(AccountContract.AccountDetailPresenter presenter) {

    }

    @Override
    public void accountDetail(AccountDetailInfo accountDetailInfo) {
        if (accountDetailInfo != null) {
            storeid=accountDetailInfo.getStoreid();
            titleStr=accountDetailInfo.getTitle();
            GlideHelper.load(this, accountDetailInfo.getzPics(), R.mipmap.app_icon, photo);
            businessName.setText(accountDetailInfo.getTitle());
            price.setText("-" + accountDetailInfo.getAllprice());
            tradingState.setText("交易成功");
            String methodFirst=TextUtils.equals("0.00",accountDetailInfo.getPower())?"":("能量抵扣"+accountDetailInfo.getPower());
            String methodSecond=TextUtils.equals("0.00",accountDetailInfo.getQuannum())?"":("劵抵扣"+accountDetailInfo.getQuannum());
            String methodThird="";
            if(TextUtils.equals("1", accountDetailInfo.getPaytype())){
                methodThird=TextUtils.equals("0.00",accountDetailInfo.getPrice())?"":("支付宝支付"+accountDetailInfo.getPrice());
            }else if(TextUtils.equals("2", accountDetailInfo.getPaytype())){
                methodThird=TextUtils.equals("0.00",accountDetailInfo.getPrice())?"":("微信支付"+accountDetailInfo.getPrice());
            }
            paymentMethod.setText(methodFirst+methodSecond+methodThird);
            commodityDescription.setText(accountDetailInfo.getTitle());
            createdAt.setText(FormatUtil.formatDateByLine(accountDetailInfo.getCreatedAt()));
            orderNumber.setText(accountDetailInfo.getOrderno());
            merchantOrderNumber.setText(accountDetailInfo.getOrderno());
        }
    }

    @OnClick({R.id.back, R.id.go_recode_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.go_recode_rl:
                if(!TextUtils.isEmpty(storeid)){
                    IntercourseRecordActivity.start(this,storeid,titleStr);
                }
                break;
            default:
        }
    }
}
