package com.purchase.sls.goodsordermanage.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.common.unit.FormatUtil;
import com.purchase.sls.common.widget.GradationScrollView;
import com.purchase.sls.data.entity.GoodsOrderDetailInfo;
import com.purchase.sls.goodsordermanage.DaggerGoodsOrderComponent;
import com.purchase.sls.goodsordermanage.GoodsOrderContract;
import com.purchase.sls.goodsordermanage.GoodsOrderModule;
import com.purchase.sls.goodsordermanage.adapter.GoodsOrderItemAdapter;
import com.purchase.sls.goodsordermanage.presenter.GoodsOrderDetailPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/7/9.
 */

public class GoodsOrderDetalActivity extends BaseActivity implements GoodsOrderContract.GoodsOrderDetailView{
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.address_icon)
    ImageView addressIcon;
    @BindView(R.id.address_name)
    TextView addressName;
    @BindView(R.id.address_tel)
    TextView addressTel;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.goods_rv)
    RecyclerView goodsRv;
    @BindView(R.id.goods_price)
    TextView goodsPrice;
    @BindView(R.id.pay_bt)
    Button payBt;
    @BindView(R.id.see_bt)
    Button seeBt;
    @BindView(R.id.goods_total_price)
    TextView goodsTotalPrice;
    @BindView(R.id.freight)
    TextView freight;
    @BindView(R.id.ng_voucher)
    TextView ngVoucher;
    @BindView(R.id.real_payment)
    TextView realPayment;
    @BindView(R.id.pay_type)
    TextView payType;
    @BindView(R.id.pay_time)
    TextView payTime;
    @BindView(R.id.goods_order_number)
    TextView goodsOrderNumber;
    @BindView(R.id.place_order_time)
    TextView placeOrderTime;
    @BindView(R.id.distribution_type)
    TextView distributionType;
    @BindView(R.id.scrollview)
    GradationScrollView scrollview;

    @Inject
    GoodsOrderDetailPresenter goodsOrderDetailPresenter;
    private String orderNum;
    private GoodsOrderItemAdapter goodsOrderItemAdapter;

    public static void start(Context context,String orderNum) {
        Intent intent = new Intent(context, GoodsOrderDetalActivity.class);
        intent.putExtra(StaticData.ORDER_NUM,orderNum);
        context.startActivity(intent);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_order_detail);
        ButterKnife.bind(this);
        setHeight(back,title,null);
        initView();
    }

    private void initView(){
        orderNum=getIntent().getStringExtra(StaticData.ORDER_NUM);
        goodsOrderItemAdapter=new GoodsOrderItemAdapter(this);
        goodsRv.setAdapter(goodsOrderItemAdapter);
        goodsOrderDetailPresenter.getGoodsOrderDetail(orderNum);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
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
    public void setPresenter(GoodsOrderContract.GoodsOrderDetailPresenter presenter) {

    }

    @Override
    public void renderGoodsOrderDetail(GoodsOrderDetailInfo goodsOrderDetailInfo) {
        if(goodsOrderDetailInfo!=null){
            addressName.setText(goodsOrderDetailInfo.getName());
            addressTel.setText(goodsOrderDetailInfo.getTel());
            address.setText(goodsOrderDetailInfo.getProvince() + goodsOrderDetailInfo.getCity() + goodsOrderDetailInfo.getCounty() + goodsOrderDetailInfo.getAddress());
            if(goodsOrderDetailInfo.getGoodsInfos()!=null){
                goodsOrderItemAdapter.setData(goodsOrderDetailInfo.getGoodsInfos(),"");
            }
            goodsPrice.setText("¥"+goodsOrderDetailInfo.getOprice());
            buttonType(goodsOrderDetailInfo.getType());
            goodsTotalPrice.setText(goodsOrderDetailInfo.getOprice());
            freight.setText("0");
            ngVoucher.setText(goodsOrderDetailInfo.getAllquanPrice());
            realPayment.setText(goodsOrderDetailInfo.getPayprice());
            if(TextUtils.equals("1",goodsOrderDetailInfo.getPaytype())) {
                payType.setText("支付宝");
            }else {
                payType.setText("微信");
            }
            payTime.setText(FormatUtil.formatDateByLine(goodsOrderDetailInfo.getPaytime()));
            goodsOrderNumber.setText(goodsOrderDetailInfo.getOrdernum());
            placeOrderTime.setText(FormatUtil.formatDateByLine(goodsOrderDetailInfo.getAddtime()));
            distributionType.setText("");
        }
    }

    //0未付款1已付款2已发货3完成订单
    private void buttonType(String type){
        if(TextUtils.equals("0",type)){
            payBt.setVisibility(View.VISIBLE);
            seeBt.setVisibility(View.INVISIBLE);
            payBt.setText("付款");
        }else if(TextUtils.equals("1",type)){
            payBt.setVisibility(View.INVISIBLE);
            seeBt.setVisibility(View.INVISIBLE);
        }else if(TextUtils.equals("2",type)){
            payBt.setVisibility(View.VISIBLE);
            seeBt.setVisibility(View.VISIBLE);
            payBt.setText("确认收货");
        }else if(TextUtils.equals("3",type)){
            payBt.setVisibility(View.VISIBLE);
            seeBt.setVisibility(View.INVISIBLE);
            payBt.setText("删除订单");
        }
    }
}
