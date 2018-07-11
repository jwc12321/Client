package com.purchase.sls.goodsordermanage.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.common.UMStaticData;
import com.purchase.sls.common.unit.UmengEventUtils;
import com.purchase.sls.data.event.PayAbortEvent;
import com.purchase.sls.data.event.WXSuccessPayEvent;
import com.purchase.sls.goodsordermanage.DaggerGoodsOrderComponent;
import com.purchase.sls.goodsordermanage.GoodsOrderContract;
import com.purchase.sls.goodsordermanage.GoodsOrderModule;
import com.purchase.sls.goodsordermanage.presenter.OrderPayPresenter;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/7/11.
 */

public class OrderPayActivity extends BaseActivity implements GoodsOrderContract.OrderPayView {

    @BindView(R.id.zhifubao_pay)
    ImageView zhifubaoPay;
    @BindView(R.id.zhifubao_rl)
    RelativeLayout zhifubaoRl;
    @BindView(R.id.weixin_pay)
    ImageView weixinPay;
    @BindView(R.id.weixin_rl)
    RelativeLayout weixinRl;
    @BindView(R.id.confirm_bt)
    Button confirmBt;
    @BindView(R.id.order_pay_item)
    RelativeLayout orderPayItem;

    private String orderNum;
    //1：支付包 2：微信
    private String payType = "1";

    @Inject
    OrderPayPresenter orderPayPresenter;
    private IWXAPI api;

    public static void start(Context context, String orderNum) {
        Intent intent = new Intent(context, OrderPayActivity.class);
        intent.putExtra(StaticData.ORDER_NUM, orderNum);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_pay);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        orderNum = getIntent().getStringExtra(StaticData.ORDER_NUM);
        zhifubaoPay.setSelected(true);
        weixinPay.setSelected(false);
        orderPayPresenter.setContext(this);
    }

    @Override
    protected void initializeInjector() {
        DaggerGoodsOrderComponent.builder()
                .applicationComponent(getApplicationComponent())
                .goodsOrderModule(new GoodsOrderModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @OnClick({R.id.order_pay_item, R.id.zhifubao_rl, R.id.weixin_rl, R.id.confirm_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.order_pay_item:
                finish();
                break;
            case R.id.zhifubao_rl:
                payType = "1";
                zhifubaoPay.setSelected(true);
                weixinPay.setSelected(false);
                break;
            case R.id.weixin_rl:
                payType = "2";
                zhifubaoPay.setSelected(false);
                weixinPay.setSelected(true);
                break;
            case R.id.confirm_bt:
                submitOrder();
                break;
            default:
        }
    }

    private void submitOrder() {
        if (TextUtils.equals("1", payType)) {
            orderPayPresenter.getAlipaySign(payType, orderNum);
        } else {
            orderPayPresenter.getWXPaySign(payType, orderNum);
        }
    }

    @Override
    public void setPresenter(GoodsOrderContract.OrderPayPresenter presenter) {

    }

    @Override
    public void onRechargetFail() {
        showMessage("支付宝支付失败");
    }

    @Override
    public void onRechargeSuccess() {
        UmengEventUtils.statisticsClick(this, UMStaticData.MALL_PAY_SUCCESS);
        this.finish();
    }

    @Override
    public void onRechargeCancel() {
        showMessage("支付宝支付取消");
    }

    @Override
    public void onAppIdReceive(String appId) {
        if (api == null) {
            api = WXAPIFactory.createWXAPI(this, appId);
            api.registerApp(appId);
            orderPayPresenter.setWXAPI(api);
        }
    }

    /*****
     * 微信支付结果的回调
     ******/

    //取消支付，或者支付不成功
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPayNotSuccess(PayAbortEvent event) {
        if (event.msg != null)
            showMessage(event.msg);
    }

    //支付成功
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPaySuccess(WXSuccessPayEvent event) {
        UmengEventUtils.statisticsClick(this, UMStaticData.MALL_PAY_SUCCESS);
        this.finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWxPayResult(PayAbortEvent event) {
        if (event.code == 0) {
            finish();
        } else {
            showMessage(event.msg);
        }
    }
}
