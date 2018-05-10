package com.purchase.sls.shopdetailbuy.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.common.widget.MyClickRatingBar;
import com.purchase.sls.data.entity.OrderDetailInfo;
import com.purchase.sls.data.request.SubmitEvaluateRequest;
import com.purchase.sls.shopdetailbuy.DaggerShopDetailBuyComponent;
import com.purchase.sls.shopdetailbuy.ShopDetailBuyContract;
import com.purchase.sls.shopdetailbuy.ShopDetailBuyModule;
import com.purchase.sls.shopdetailbuy.presenter.OrderDetailPresenter;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/4/28.
 */

public class PaySuccessActivity extends BaseActivity implements ShopDetailBuyContract.OrderDetailView, MyClickRatingBar.OnStarItemClickListener {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.complete)
    TextView complete;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.shop_name)
    TextView shopName;
    @BindView(R.id.total_price)
    TextView totalPrice;
    @BindView(R.id.preferential_details)
    TextView preferentialDetails;
    @BindView(R.id.coupon_type)
    TextView couponType;
    @BindView(R.id.preferential_price)
    TextView preferentialPrice;
    @BindView(R.id.pay_details)
    TextView payDetails;
    @BindView(R.id.pay_type)
    TextView payType;
    @BindView(R.id.pay_price)
    TextView payPrice;
    @BindView(R.id.energy_number)
    TextView energyNumber;
    @BindView(R.id.rating_bar)
    MyClickRatingBar ratingBar;
    private String businessName;
    private String orderno;
    private String storeId;
    private OrderDetailInfo.OrderItem orderItem;
    private BigDecimal energyDecimal;//能量
    private BigDecimal couponDecimal;//优惠券
    private String evaluateStars;

    @Inject
    OrderDetailPresenter orderDetailPresenter;

    public static void start(Context context, String businessName, String orderno) {
        Intent intent = new Intent(context, PaySuccessActivity.class);
        intent.putExtra(StaticData.BUSINESS_NAME, businessName);
        intent.putExtra(StaticData.ORDER_NO, orderno);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paysuccess);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        evaluateStars="0";
        businessName = getIntent().getStringExtra(StaticData.BUSINESS_NAME);
        orderno = getIntent().getStringExtra(StaticData.ORDER_NO);
        shopName.setText(businessName);
        orderDetailPresenter.getOrderDetailInfo(orderno);
        ratingBar.setmStarItemClickListener(this);
    }

    @Override
    protected void initializeInjector() {
        DaggerShopDetailBuyComponent.builder()
                .applicationComponent(getApplicationComponent())
                .shopDetailBuyModule(new ShopDetailBuyModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void setPresenter(ShopDetailBuyContract.OrderDetailPresenter presenter) {

    }

    @Override
    public void renderOrderDetail(OrderDetailInfo orderDetailInfo) {
        if (orderDetailInfo != null) {
            if (orderDetailInfo.getOrderItem() != null) {
                orderItem = orderDetailInfo.getOrderItem();
                storeId=orderItem.getStoreid();
                totalPrice.setText(orderItem.getAllprice());
                couponType.setText((TextUtils.equals("0.00", orderItem.getPower()) ? "" : "能量") + (TextUtils.equals("0.00", orderItem.getQuannum()) ? "" : "优惠券"));
                preferentialPrice.setText((TextUtils.equals("0.00", orderItem.getPower()) ? "" : orderItem.getPower()) + (TextUtils.equals("0.00", orderItem.getQuannum()) ? "" : orderItem.getQuannum()));
                energyDecimal = new BigDecimal(TextUtils.isEmpty(orderItem.getPower()) ? "0" : orderItem.getPower()).setScale(2, RoundingMode.HALF_UP);
                couponDecimal = new BigDecimal(TextUtils.isEmpty(orderItem.getQuannum()) ? "0" : orderItem.getQuannum()).setScale(2, RoundingMode.HALF_UP);
                preferentialPrice.setText("¥" + (energyDecimal.add(couponDecimal)).toString());
                if (TextUtils.equals("1", orderItem.getPaytype())) {
                    payType.setText("支付宝支付");
                } else {
                    payType.setText("微信支付");
                }
                payPrice.setText(orderItem.getPrice());
            }
            if (orderDetailInfo.getResultsItem() != null && !TextUtils.isEmpty(orderDetailInfo.getResultsItem().getPower())) {
                energyNumber.setText(orderDetailInfo.getResultsItem().getPower());
            } else {
                energyNumber.setText("0");
            }
        }
    }

    @OnClick({R.id.back, R.id.complete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.complete:
                SubmitEvaluateRequest submitEvaluateRequest=new SubmitEvaluateRequest();
                submitEvaluateRequest.setOrderid(orderno);
                submitEvaluateRequest.setStarts(evaluateStars);
                submitEvaluateRequest.setStoreid(storeId);
                orderDetailPresenter.submitEvaluate(submitEvaluateRequest);
                break;
            default:
        }
    }

    @Override
    public void onItemClick(View view, int pos) {
        evaluateStars=String.valueOf(pos+1);
    }


    @Override
    public void submitSuccess() {
        finish();
    }
}
