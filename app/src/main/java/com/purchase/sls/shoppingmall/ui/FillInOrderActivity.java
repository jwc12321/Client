package com.purchase.sls.shoppingmall.ui;

import android.app.Activity;
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
import com.purchase.sls.address.ui.AddressListActivity;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.common.widget.GradationScrollView;
import com.purchase.sls.data.entity.AddressInfo;
import com.purchase.sls.data.entity.GoodsOrderInfo;
import com.purchase.sls.data.entity.GoodsOrderList;
import com.purchase.sls.shoppingmall.DaggerShoppingMallComponent;
import com.purchase.sls.shoppingmall.ShoppingMallContract;
import com.purchase.sls.shoppingmall.ShoppingMallModule;
import com.purchase.sls.shoppingmall.adapter.FillOrderGoodsAdapter;
import com.purchase.sls.shoppingmall.presenter.FillInOrderPresenter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/7/6.
 * 填写订单
 */

public class FillInOrderActivity extends BaseActivity implements ShoppingMallContract.FillOrderView {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.address_name)
    TextView addressName;
    @BindView(R.id.default_address)
    TextView defaultAddress;
    @BindView(R.id.address_tel)
    TextView addressTel;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.address_rl)
    RelativeLayout addressRl;
    @BindView(R.id.goods_rv)
    RecyclerView goodsRv;
    @BindView(R.id.use_voucher_iv)
    ImageView useVoucherIv;
    @BindView(R.id.voucher_price)
    TextView voucherPrice;
    @BindView(R.id.voucher_rl)
    RelativeLayout voucherRl;
    @BindView(R.id.zhifubao_pay)
    ImageView zhifubaoPay;
    @BindView(R.id.zhifubao_rl)
    RelativeLayout zhifubaoRl;
    @BindView(R.id.weixin_pay)
    ImageView weixinPay;
    @BindView(R.id.weixin_rl)
    RelativeLayout weixinRl;
    @BindView(R.id.scrollview)
    GradationScrollView scrollview;
    @BindView(R.id.confirm_bt)
    Button confirmBt;
    @BindView(R.id.no_address)
    TextView noAddress;
    @BindView(R.id.tatal)
    TextView tatal;
    @BindView(R.id.total_price)
    TextView totalPrice;

    private List<GoodsOrderInfo> goodsOrderInfos;
    private FillOrderGoodsAdapter fillOrderGoodsAdapter;
    private AddressInfo addressInfo;
    private GoodsOrderList goodsOrderList;

    @Inject
    FillInOrderPresenter fillInOrderPresenter;

    private static final int REQUEST_ADDRESS = 22;

    private BigDecimal quanHouPriceDd;//减掉优惠券的价格
    private BigDecimal quanPriceBd;//优惠券
    private BigDecimal totalPriceBd;//优惠券
    private String quanPrice;
    private String quanHouPrice;
    private boolean flag = true;


    public static void start(Context context, GoodsOrderList goodsOrderList) {
        Intent intent = new Intent(context, FillInOrderActivity.class);
        intent.putExtra(StaticData.GOODS_ORDER_LIST, goodsOrderList);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_in_order);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initView();
    }

    private void initView() {
        goodsOrderList = (GoodsOrderList) getIntent().getSerializableExtra(StaticData.GOODS_ORDER_LIST);
        if (goodsOrderList != null) {
            goodsOrderInfos = goodsOrderList.getGoodsOrderInfos();
            quanPrice = goodsOrderList.getQuan();
            quanHouPrice = goodsOrderList.getQuanhou();
            if (TextUtils.isEmpty(quanPrice) || TextUtils.equals("0", quanPrice)
                    || TextUtils.equals("0.0", quanPrice) || TextUtils.equals("0.00", quanPrice)) {
                voucherRl.setVisibility(View.GONE);
            } else {
                voucherRl.setVisibility(View.VISIBLE);
                useVoucherIv.setSelected(true);
                voucherPrice.setText("可抵扣¥" + quanPrice);
            }
            totalPrice.setText(quanHouPrice);
        }
        fillOrderGoodsAdapter = new FillOrderGoodsAdapter(this);
        goodsRv.setAdapter(fillOrderGoodsAdapter);
        goodsRv.setNestedScrollingEnabled(false);
        goodsRv.setFocusableInTouchMode(false);
        fillOrderGoodsAdapter.setData(goodsOrderInfos);
        fillInOrderPresenter.getAddressList();
    }

    //计算能抵扣多少优惠
    private void voucher(boolean isflag) {
        quanHouPriceDd = new BigDecimal(quanHouPrice).setScale(2, RoundingMode.HALF_UP);
        quanPriceBd = new BigDecimal(quanPrice).setScale(2, RoundingMode.HALF_UP);
        totalPriceBd=new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);
        if (isflag) {
            totalPriceBd=quanHouPriceDd;
            totalPrice.setText(totalPriceBd.toString());
        } else {
            totalPriceBd=quanHouPriceDd.add(quanPriceBd);
            totalPrice.setText(totalPriceBd.toString());
        }
    }

    @Override
    protected void initializeInjector() {
        DaggerShoppingMallComponent.builder()
                .applicationComponent(getApplicationComponent())
                .shoppingMallModule(new ShoppingMallModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @OnClick({R.id.back, R.id.address_rl, R.id.use_voucher_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.address_rl:
                choiceAddress();
                break;
            case R.id.use_voucher_iv:
                flag = !flag;
                if (flag) {
                    useVoucherIv.setSelected(true);
                } else {
                    useVoucherIv.setSelected(false);
                }
                voucher(flag);
                break;
            default:
        }
    }

    private void choiceAddress() {
        Intent intent = new Intent(this, AddressListActivity.class);
        intent.putExtra(StaticData.BACK_ADDRESS, "1");
        startActivityForResult(intent, REQUEST_ADDRESS);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_ADDRESS:
                    if (data != null) {
                        Bundle bundle = data.getExtras();
                        addressInfo = (AddressInfo) bundle.getSerializable(StaticData.CHOICE_ADDRESS);
                        if (addressInfo != null) {
                            noAddress.setVisibility(View.GONE);
                            addressName.setText(addressInfo.getUsername());
                            addressTel.setText(addressInfo.getTel());
                            address.setText(addressInfo.getProvince() + addressInfo.getCity() + addressInfo.getCountry() + addressInfo.getAddress());
                            if (TextUtils.equals("1", addressInfo.getType())) {
                                defaultAddress.setVisibility(View.VISIBLE);
                            } else {
                                defaultAddress.setVisibility(View.GONE);
                            }
                        }
                    }
                    break;
                default:
            }
        }
    }

    @Override
    public void setPresenter(ShoppingMallContract.FillInOrderPresenter presenter) {

    }

    @Override
    public void renderAddressList(List<AddressInfo> addressInfos) {
        if (addressInfos != null && addressInfos.size() > 0) {
            for (AddressInfo addressInfo : addressInfos) {
                if (TextUtils.equals("1", addressInfo.getType())) {
                    this.addressInfo = addressInfo;
                    break;
                }
            }
            if (addressInfo != null) {
                noAddress.setVisibility(View.GONE);
                addressName.setText(addressInfo.getUsername());
                addressTel.setText(addressInfo.getTel());
                address.setText(addressInfo.getProvince() + addressInfo.getCity() + addressInfo.getCountry() + addressInfo.getAddress());
            } else {
                noAddress.setVisibility(View.VISIBLE);
            }
        } else {
            noAddress.setVisibility(View.VISIBLE);
        }
    }


}
