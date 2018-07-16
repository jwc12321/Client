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
import com.purchase.sls.common.UMStaticData;
import com.purchase.sls.common.unit.UmengEventUtils;
import com.purchase.sls.common.widget.GradationScrollView;
import com.purchase.sls.data.entity.AddressInfo;
import com.purchase.sls.data.entity.GoodsOrderInfo;
import com.purchase.sls.data.entity.GoodsOrderList;
import com.purchase.sls.data.event.PayAbortEvent;
import com.purchase.sls.data.event.WXSuccessPayEvent;
import com.purchase.sls.data.request.CartidRequest;
import com.purchase.sls.data.request.PurchaseGoodsRequest;
import com.purchase.sls.goodsordermanage.ui.GoodsOrderDetalActivity;
import com.purchase.sls.shoppingmall.DaggerShoppingMallComponent;
import com.purchase.sls.shoppingmall.ShoppingMallContract;
import com.purchase.sls.shoppingmall.ShoppingMallModule;
import com.purchase.sls.shoppingmall.adapter.FillOrderGoodsAdapter;
import com.purchase.sls.shoppingmall.presenter.FillInOrderPresenter;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    //1：支付包 2：微信
    private String payType = "1";

    @Inject
    FillInOrderPresenter fillInOrderPresenter;

    private static final int REQUEST_ADDRESS = 22;

    private BigDecimal quanHouPriceDd;//减掉优惠券的价格
    private BigDecimal totalPriceBd;//优惠券
    private BigDecimal ownQuanPriceBd;//自己有的劵
    private String quanPrice;
    private String quanHouPrice;
    private String ownQuanPrice;
    private boolean flag = true;
    private IWXAPI api;
    private String ordreno;
    private String cartId;
    private String isQuan = "1";

    private String payWhere; //1：购物车 2：直接购买
    private PurchaseGoodsRequest purchaseGoodsRequest;
    private CartidRequest cartidRequest;


    public static void start(Context context, GoodsOrderList goodsOrderList, String payWhere, PurchaseGoodsRequest purchaseGoodsRequest, CartidRequest cartidRequest) {
        Intent intent = new Intent(context, FillInOrderActivity.class);
        intent.putExtra(StaticData.GOODS_ORDER_LIST, goodsOrderList);
        intent.putExtra(StaticData.PAY_WHERE, payWhere);
        intent.putExtra(StaticData.PURCHASE_GOODS_REQUEST, purchaseGoodsRequest);
        intent.putExtra(StaticData.CARTID_REQUEST, cartidRequest);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_in_order);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        goodsOrderList = (GoodsOrderList) getIntent().getSerializableExtra(StaticData.GOODS_ORDER_LIST);
        payWhere = getIntent().getStringExtra(StaticData.PAY_WHERE);
        purchaseGoodsRequest = (PurchaseGoodsRequest) getIntent().getSerializableExtra(StaticData.PURCHASE_GOODS_REQUEST);
        cartidRequest = (CartidRequest) getIntent().getSerializableExtra(StaticData.CARTID_REQUEST);
        zhifubaoPay.setSelected(true);
        weixinPay.setSelected(false);
        fillInOrderPresenter.setContext(this);
        fillOrderGoodsAdapter = new FillOrderGoodsAdapter(this);
        goodsRv.setAdapter(fillOrderGoodsAdapter);
        goodsRv.setNestedScrollingEnabled(false);
        goodsRv.setFocusableInTouchMode(false);
        initGoodsInfo(goodsOrderList);
        fillInOrderPresenter.getAddressList();
    }

    private void initGoodsInfo(GoodsOrderList goodsOrderList) {
        if (goodsOrderList != null) {
            goodsOrderInfos = goodsOrderList.getGoodsOrderInfos();
            quanPrice = goodsOrderList.getQuan();
            quanHouPrice = goodsOrderList.getQuanhou();
            cartId = goodsOrderList.getCartid();
            ownQuanPrice = goodsOrderList.getOwnquan();
            ownQuanPriceBd = new BigDecimal(TextUtils.isEmpty(ownQuanPrice) ? "0" : ownQuanPrice).setScale(2, RoundingMode.HALF_UP);
            useVoucherIv.setSelected(true);
            voucherPrice.setText("可抵扣¥" + ownQuanPriceBd.toString());
            totalPrice.setText(quanHouPrice);
            fillOrderGoodsAdapter.setData(goodsOrderInfos);
        }
    }

    //计算能抵扣多少优惠
    private void voucher(boolean isflag) {
        quanHouPriceDd = new BigDecimal(quanHouPrice).setScale(2, RoundingMode.HALF_UP);
        totalPriceBd = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);
        if (isflag) {
            totalPriceBd = quanHouPriceDd;
            totalPrice.setText(totalPriceBd.toString());
        } else {
            totalPriceBd = quanHouPriceDd.add(ownQuanPriceBd);
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

    @OnClick({R.id.back, R.id.address_rl, R.id.use_voucher_iv, R.id.zhifubao_rl, R.id.weixin_rl, R.id.confirm_bt})
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
                    isQuan = "1";
                    useVoucherIv.setSelected(true);
                } else {
                    isQuan = "0";
                    useVoucherIv.setSelected(false);
                }
                voucher(flag);
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
        if (addressInfo == null || TextUtils.isEmpty(addressInfo.getId())) {
            showMessage("请选择地址");
            return;
        }
        if (TextUtils.equals("1", payType)) {
            fillInOrderPresenter.getAlipaySign(cartId, addressInfo.getId(), payType, isQuan);
        } else {
            fillInOrderPresenter.getWXPaySign(cartId, addressInfo.getId(), payType, isQuan);
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
                            confirmBt.setEnabled(true);
                        } else {
                            confirmBt.setEnabled(false);
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
                defaultAddress.setVisibility(View.VISIBLE);
                confirmBt.setEnabled(true);
            } else {
                noAddress.setVisibility(View.VISIBLE);
                confirmBt.setEnabled(false);
            }
        } else {
            noAddress.setVisibility(View.VISIBLE);
            confirmBt.setEnabled(false);
        }
    }

    @Override
    public void onRechargetFail() {
        showMessage("支付宝支付失败");
        if (TextUtils.equals("1", payWhere)) {
            fillInOrderPresenter.orderShopCart(cartidRequest);
        } else {
            fillInOrderPresenter.purchaseGoods(purchaseGoodsRequest);
        }
    }

    @Override
    public void onRechargeSuccess() {
        UmengEventUtils.statisticsClick(this, UMStaticData.MALL_PAY_SUCCESS);
        GoodsOrderDetalActivity.start(this, ordreno);
        this.finish();
    }

    @Override
    public void onRechargeCancel() {
        showMessage("支付宝支付取消");
        if (TextUtils.equals("1", payWhere)) {
            fillInOrderPresenter.orderShopCart(cartidRequest);
        } else {
            fillInOrderPresenter.purchaseGoods(purchaseGoodsRequest);
        }
    }

    @Override
    public void onAppIdReceive(String appId) {
        if (api == null) {
            api = WXAPIFactory.createWXAPI(this, appId);
            api.registerApp(appId);
            fillInOrderPresenter.setWXAPI(api);
        }
    }

    @Override
    public void renderOrderno(String orderno) {
        this.ordreno = orderno;
    }

    @Override
    public void subGoodsSuccess(GoodsOrderList goodsOrderList) {
        this.goodsOrderList=goodsOrderList;
        initGoodsInfo(goodsOrderList);
    }


    /*****
     * 微信支付结果的回调
     ******/

    //取消支付，或者支付不成功
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPayNotSuccess(PayAbortEvent event) {
        if (TextUtils.equals("1", payWhere)) {
            fillInOrderPresenter.orderShopCart(cartidRequest);
        } else {
            fillInOrderPresenter.purchaseGoods(purchaseGoodsRequest);
        }
        if (event.msg != null)
            showMessage(event.msg);
    }

    //支付成功
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPaySuccess(WXSuccessPayEvent event) {
        UmengEventUtils.statisticsClick(this, UMStaticData.MALL_PAY_SUCCESS);
        GoodsOrderDetalActivity.start(this, ordreno);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
