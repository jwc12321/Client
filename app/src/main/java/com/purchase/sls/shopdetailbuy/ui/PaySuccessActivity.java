package com.purchase.sls.shopdetailbuy.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.common.UMStaticData;
import com.purchase.sls.common.unit.UmengEventUtils;
import com.purchase.sls.common.widget.MyClickRatingBar;
import com.purchase.sls.data.entity.OrderDetailInfo;
import com.purchase.sls.data.entity.QuanInfo;
import com.purchase.sls.data.request.SubmitEvaluateRequest;
import com.purchase.sls.shopdetailbuy.DaggerShopDetailBuyComponent;
import com.purchase.sls.shopdetailbuy.ShopDetailBuyContract;
import com.purchase.sls.shopdetailbuy.ShopDetailBuyModule;
import com.purchase.sls.shopdetailbuy.adapter.ReceiveCouponAdapter;
import com.purchase.sls.shopdetailbuy.presenter.OrderDetailPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/4/28.
 */

public class PaySuccessActivity extends BaseActivity implements ShopDetailBuyContract.OrderDetailView, MyClickRatingBar.OnStarItemClickListener,ReceiveCouponAdapter.OnEventClicking {

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
    @BindView(R.id.coupon_number)
    TextView couponNumber;
    @BindView(R.id.choice_coupon)
    LinearLayout choiceCoupon;
    @BindView(R.id.coupon_text)
    TextView couponText;
    @BindView(R.id.coupon_rv)
    RecyclerView couponRv;
    @BindView(R.id.coupon_rv_ll)
    LinearLayout couponRvLl;
    @BindView(R.id.coupon_black_background)
    LinearLayout couponBlackBackground;
    @BindView(R.id.coupon_rl)
    RelativeLayout couponRl;
    private String businessName;
    private String orderno;
    private String storeId;
    private OrderDetailInfo.OrderItem orderItem;
    private String evaluateStars;
    private List<QuanInfo> quanInfos;
    private ReceiveCouponAdapter receiveCouponAdapter;
    private int choicePosition;
    private String mut;

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
        setHeight(back, title, complete);
        initView();
    }

    private void initView() {
        evaluateStars = "1";
        businessName = getIntent().getStringExtra(StaticData.BUSINESS_NAME);
        orderno = getIntent().getStringExtra(StaticData.ORDER_NO);
        shopName.setText(businessName);
        orderDetailPresenter.getOrderDetailInfo(orderno);
        ratingBar.setmStarItemClickListener(this);
        couponBlackBackground.setAlpha(0.4f);
        addAdapter();
    }

    private void addAdapter(){
        receiveCouponAdapter=new ReceiveCouponAdapter();
        receiveCouponAdapter.setOnEventClicking(this);
        couponRv.setAdapter(receiveCouponAdapter);
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
                storeId = orderItem.getStoreid();
                totalPrice.setText(orderItem.getAllprice());
                if (!TextUtils.equals("0.00", orderItem.getPower()) && !TextUtils.equals("0.00", orderItem.getQuannum())) {
                    couponType.setText("能量+优惠券");
                    preferentialPrice.setText("¥" + orderItem.getPower() + "+¥" + orderItem.getQuannum());
                } else if (TextUtils.equals("0.00", orderItem.getPower()) && !TextUtils.equals("0.00", orderItem.getQuannum())) {
                    couponType.setText("优惠券");
                    preferentialPrice.setText("¥" + orderItem.getQuannum());
                } else if (!TextUtils.equals("0.00", orderItem.getPower()) && TextUtils.equals("0.00", orderItem.getQuannum())) {
                    couponType.setText("能量");
                    preferentialPrice.setText("¥" + orderItem.getPower());
                } else {
                    couponType.setText("");
                    preferentialPrice.setText("");
                }
                if (TextUtils.equals("1", orderItem.getPaytype())) {
                    payType.setText("支付宝支付");
                } else {
                    payType.setText("微信支付");
                }
                payPrice.setText("¥" + orderItem.getPrice());
            }

            if (orderDetailInfo.getResultsItem() != null) {
                OrderDetailInfo.ResultsItem resultsItem = orderDetailInfo.getResultsItem();
                if (!TextUtils.isEmpty(resultsItem.getPower())) {
                    energyNumber.setText(resultsItem.getPower());
                } else {
                    energyNumber.setText("0");
                }
//                quanInfos = resultsItem.getQuanInfos();
//                if(resultsItem.getBusinessQInfos()!=null&&resultsItem.getBusinessQInfos().size()>0){
//                    if(quanInfos==null){
//                        quanInfos=new ArrayList<>();
//                    }
//                    quanInfos.addAll(resultsItem.getBusinessQInfos());
//                }
//                if(!TextUtils.isEmpty(resultsItem.getScquan())&&!TextUtils.equals("0.00",resultsItem.getScquan())){
//                    if(quanInfos==null){
//                        quanInfos=new ArrayList<>();
//                    }
//                    QuanInfo quanInfo=new QuanInfo();
//                    quanInfo.setCanReceive("1");
//                    quanInfo.setPrice(resultsItem.getScquan());
//                    quanInfo.setAddSc("3");
//                    quanInfos.add(0,quanInfo);
//                }
//                mut=resultsItem.getMut();
//                if(quanInfos==null||quanInfos.size()==0){
//                    couponNumber.setText("无优惠券可领");
//                    choiceCoupon.setVisibility(View.GONE);
//                }else {
//                    choiceCoupon.setVisibility(View.VISIBLE);
//                    if(TextUtils.equals("1",mut)){
//                        couponNumber.setText("你有"+quanInfos.size()+"张优惠券可领");
//                    }else {
//                        couponNumber.setText("你有1张优惠券可领");
//                    }
//                    receiveCouponAdapter.setData(quanInfos);
//                }
            }
        }
    }

    @OnClick({R.id.back, R.id.complete, R.id.choice_coupon,R.id.coupon_black_background})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.complete:
                SubmitEvaluateRequest submitEvaluateRequest = new SubmitEvaluateRequest();
                submitEvaluateRequest.setOrderno(orderno);
                submitEvaluateRequest.setStarts(evaluateStars);
                submitEvaluateRequest.setStoreid(storeId);
                orderDetailPresenter.submitEvaluate(submitEvaluateRequest);
                break;
            case R.id.choice_coupon:
                if(quanInfos!=null&&quanInfos.size()>0){
                    couponRl.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.coupon_black_background:
                couponRl.setVisibility(View.GONE);
                break;
            default:
        }
    }

    @Override
    public void onItemClick(View view, int pos) {
        evaluateStars = String.valueOf(pos + 1);
    }


    @Override
    public void submitSuccess() {
        UmengEventUtils.statisticsClick(this, UMStaticData.COMMENT_STORE);
        finish();
    }

    @Override
    public void receiveSuccess() {
        Toast.makeText(getApplicationContext(), "领取成功！", Toast.LENGTH_SHORT).show();
        if(TextUtils.equals("1",mut)){
            quanInfos.get(choicePosition).setCanReceive("0");
            int number=0;
            for(int i=0;i<quanInfos.size();i++){
                if(TextUtils.equals("1",quanInfos.get(i).getCanReceive())){
                    number++;
                }
            }
            if(TextUtils.equals("0",String.valueOf(number))){
                couponNumber.setText("无优惠券可领");
            }else {
                couponNumber.setText("你有"+number+"张优惠券可领");
            }
;        }else {
            for(int i=0;i<quanInfos.size();i++){
                quanInfos.get(i).setCanReceive("0");
            }
            couponNumber.setText("无优惠券可领");
        }
        receiveCouponAdapter.setData(quanInfos);
    }



    @Override
    public void couponItemClick(String id,int position) {
        choicePosition=position;
        orderDetailPresenter.receiveCoupon(id,orderno);
    }

    @Override
    public void shopVItemClick(int position) {
        choicePosition=position;
        orderDetailPresenter.receiveShopV(orderno);
    }
}
