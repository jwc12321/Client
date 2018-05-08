package com.purchase.sls.shopdetailbuy.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;
import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.common.GlideHelper;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.common.widget.KeywordUtil;
import com.purchase.sls.data.entity.CouponInfo;
import com.purchase.sls.data.entity.GeneratingOrderInfo;
import com.purchase.sls.data.entity.UserpowerInfo;
import com.purchase.sls.data.event.PayAbortEvent;
import com.purchase.sls.data.event.WXSuccessPayEvent;
import com.purchase.sls.shopdetailbuy.DaggerShopDetailBuyComponent;
import com.purchase.sls.shopdetailbuy.ShopDetailBuyContract;
import com.purchase.sls.shopdetailbuy.ShopDetailBuyModule;
import com.purchase.sls.shopdetailbuy.adapter.CouponAdapter;
import com.purchase.sls.shopdetailbuy.presenter.PaymentOrderPresenter;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/4/27.
 */

public class PaymentOrderActivity extends BaseActivity implements ShopDetailBuyContract.PaymentOrderView, CouponAdapter.onCouponItemClick {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.photo)
    RoundedImageView photo;
    @BindView(R.id.shop_name)
    TextView shopName;
    @BindView(R.id.money_et)
    EditText moneyEt;
    @BindView(R.id.add_notes_et)
    EditText addNotesEt;
    @BindView(R.id.go_iv)
    ImageView goIv;
    @BindView(R.id.reel_number)
    TextView reelNumber;
    @BindView(R.id.reel_iv)
    ImageView reelIv;
    @BindView(R.id.reel_rl)
    RelativeLayout reelRl;
    @BindView(R.id.energy_icon)
    ImageView energyIcon;
    @BindView(R.id.add_energy_et)
    EditText addEnergyEt;
    @BindView(R.id.item_energy)
    RelativeLayout itemEnergy;
    @BindView(R.id.zhifubao_pay)
    ImageView zhifubaoPay;
    @BindView(R.id.weixin_pay)
    ImageView weixinPay;
    @BindView(R.id.pay_details)
    TextView payDetails;
    @BindView(R.id.confirm_pay_bg)
    Button confirmPayBg;
    @BindView(R.id.coupon_text)
    TextView couponText;
    @BindView(R.id.coupon_rv)
    RecyclerView couponRv;
    @BindView(R.id.coupon_black_background)
    LinearLayout couponBlackBackground;
    @BindView(R.id.coupon_rl)
    RelativeLayout couponRl;
    @BindView(R.id.zhifubao_rl)
    RelativeLayout zhifubaoRl;
    @BindView(R.id.weixin_rl)
    RelativeLayout weixinRl;
    @BindView(R.id.coupon_rv_ll)
    LinearLayout couponRvLl;
    private String businessName;
    private String businessZpics;
    private String businessStoreId;
    private String energyStr;
    private static final int UPDATE_EDITTEXT = 99;
    private UserpowerInfo userpowerInfo;
    //1：支付包 2：微信
    private String payType = "1";
    @Inject
    PaymentOrderPresenter paymentOrderPresenter;
    private CouponAdapter couponAdapter;
    private List<CouponInfo> couponInfos;
    private MyHandler mHandler;
    private String couponId = "";
    private String couponMoney = "";


    private BigDecimal totalPriceBigDecimal;//总价
    private BigDecimal energyDecimal;//能量
    private BigDecimal couponDecimal;//优惠券
    private BigDecimal maxEnergyDecial;//最大的优惠金额
    private BigDecimal totalStCdDecimal;//输入的价格减去优惠券金额

    private String payTypeWhat="1";
    private IWXAPI api;
    private String ordreno;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_order);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        businessName = getIntent().getStringExtra(StaticData.BUSINESS_NAME);
        businessZpics = getIntent().getStringExtra(StaticData.BUSINESS_ZPICS);
        businessStoreId = getIntent().getStringExtra(StaticData.BUSINESS_STOREID);
        mHandler = new MyHandler(this);
        paymentOrderPresenter.setContext(this);
        editListener();
        GlideHelper.load(this, businessZpics, R.mipmap.app_icon, photo);
        shopName.setText(businessName);
        couponBlackBackground.setAlpha(0.4f);
        setCouponAdapter();
        zhifubaoPay.setSelected(true);
        weixinPay.setSelected(false);
        paymentOrderPresenter.getUserpowerInfo("", businessStoreId);
    }

    private void setCouponAdapter() {
        couponAdapter = new CouponAdapter();
        couponAdapter.setonCouponItemClick(this);
        couponRv.setAdapter(couponAdapter);
    }

    /**
     * edittext监听
     */
    private void editListener() {
        moneyEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(moneyEt.getText().toString())) {
                    confirmPayBg.setEnabled(true);
                } else {
                    confirmPayBg.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //监听edittext变化
        addEnergyEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(addEnergyEt.getText().toString())) {
                    if (mHandler != null) {
                        mHandler.removeCallbacksAndMessages(null);
                        mHandler.sendEmptyMessageDelayed(UPDATE_EDITTEXT, 500);
                    } else {
                        mHandler.sendEmptyMessageDelayed(UPDATE_EDITTEXT, 500);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    class MyHandler extends Handler {
        WeakReference<Activity> mWeakReference;

        public MyHandler(Activity activity) {
            mWeakReference = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final Activity activity = mWeakReference.get();
            if (activity != null) {
                if (msg.what == UPDATE_EDITTEXT) {
                    fillNumber();
                    CalculateEachPayment();
                }
            }
        }
    }

    private void fillNumber() {
        if (!TextUtils.isEmpty(addEnergyEt.getText().toString()) && !TextUtils.isEmpty(userpowerInfo.getPersionInfoResponse().getPower()) && !TextUtils.isEmpty(moneyEt.getText().toString())) {
            maxEnergyDecial = new BigDecimal(userpowerInfo.getPersionInfoResponse().getPower()).setScale(2, RoundingMode.HALF_UP);
            energyDecimal = new BigDecimal(addEnergyEt.getText().toString()).setScale(2, RoundingMode.HALF_UP);
            totalPriceBigDecimal = new BigDecimal(moneyEt.getText().toString()).setScale(2, RoundingMode.HALF_UP);
            couponDecimal = new BigDecimal(TextUtils.isEmpty(couponMoney) ? "0" : couponMoney).setScale(2, RoundingMode.HALF_UP);
            totalStCdDecimal = totalPriceBigDecimal.subtract(couponDecimal);
            if (energyDecimal.compareTo(maxEnergyDecial) > 0) {
                toast("最大优惠能量" + userpowerInfo.getPersionInfoResponse().getPower());
                addEnergyEt.setText(userpowerInfo.getPersionInfoResponse().getPower());
            }
            if (totalStCdDecimal.compareTo(energyDecimal) < 0) {
                toast("能量只需要填写" + totalStCdDecimal);
                addEnergyEt.setText(totalStCdDecimal.toString());
            }
        }
    }

    /**
     * 提示框
     *
     * @param toastStr
     */
    private void toast(String toastStr) {
        Toast toast = Toast.makeText(getApplicationContext(),
                toastStr, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


    public static void start(Context context, String businessName, String businessZpics, String businessStoreId) {
        Intent intent = new Intent(context, PaymentOrderActivity.class);
        intent.putExtra(StaticData.BUSINESS_NAME, businessName);
        intent.putExtra(StaticData.BUSINESS_ZPICS, businessZpics);
        intent.putExtra(StaticData.BUSINESS_STOREID, businessStoreId);
        context.startActivity(intent);
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
    public void setPresenter(ShopDetailBuyContract.PaymentOrderPresenter presenter) {

    }

    @Override
    public void userpowerInfo(UserpowerInfo userpowerInfo) {
        this.userpowerInfo = userpowerInfo;
        energyStr = userpowerInfo.getPersionInfoResponse().getPower();
        addEnergyEt.setHint("请输入金额（可用" + energyStr + "能量，抵用现金" + energyStr + "元");
        couponInfos = userpowerInfo.getCouponInfos();
        if (couponInfos != null && couponInfos.size() > 0) {
            reelRl.setEnabled(true);
        } else {
            reelRl.setEnabled(false);
            reelNumber.setText("无优惠券");
        }
    }

    @Override
    public void generatingOrderSuccess(GeneratingOrderInfo generatingOrderInfo) {
        PaySuccessActivity.start(this, businessName, generatingOrderInfo.getOrderno());
        this.finish();
    }

    @Override
    public void onRechargetFail() {
        showMessage("支付宝支付失败");
    }

    @Override
    public void onRechargeSuccess() {
        PaySuccessActivity.start(this, businessName,ordreno);
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
            paymentOrderPresenter.setWXAPI(api);
        }
    }

    @Override
    public void renderOrderno(String orderno) {
        this.ordreno=orderno;
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
        PaySuccessActivity.start(this, businessName, ordreno);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWxPayResult(PayAbortEvent event) {
        if (event.code == 0) {
            finish();
        } else {
            showMessage(event.msg);
        }
    }

    @OnClick({R.id.back, R.id.reel_rl, R.id.coupon_black_background, R.id.confirm_pay_bg, R.id.zhifubao_rl, R.id.weixin_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.reel_rl:
                couponRl.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(moneyEt.getText().toString())) {
                    couponAdapter.setData("0", couponInfos);
                } else {
                    couponAdapter.setData(moneyEt.getText().toString(), couponInfos);
                }
                break;
            case R.id.coupon_black_background:
                couponRl.setVisibility(View.GONE);
                break;
            case R.id.zhifubao_rl:
                payType = "1";
                zhifubaoPay.setSelected(true);
                weixinPay.setSelected(false);
                CalculateEachPayment();
                break;
            case R.id.weixin_rl:
                payType = "2";
                zhifubaoPay.setSelected(false);
                weixinPay.setSelected(true);
                CalculateEachPayment();
                break;
            case R.id.confirm_pay_bg:
                if(TextUtils.equals("1",payTypeWhat)){
                    paymentOrderPresenter.getAlipaySign(moneyEt.getText().toString(), businessStoreId, couponId, addEnergyEt.getText().toString(), payType, addNotesEt.getText().toString());
                }else if(TextUtils.equals("2",payTypeWhat)){
                    paymentOrderPresenter.getWXPaySign(moneyEt.getText().toString(), businessStoreId, couponId, addEnergyEt.getText().toString(), payType, addNotesEt.getText().toString());
                }else {
                    paymentOrderPresenter.setGeneratingOrder(moneyEt.getText().toString(), businessStoreId, couponId, addEnergyEt.getText().toString(), payType, addNotesEt.getText().toString());
                }
                break;
            default:

        }
    }


    @Override
    public void couponItemClick(CouponInfo couponInfo) {
        couponRl.setVisibility(View.GONE);
        reelNumber.setText("-¥" + couponInfo.getQuanInfo().getPrice());
        couponId = couponInfo.getId();
        couponMoney = couponInfo.getQuanInfo().getPrice();
        fillNumber();
        CalculateEachPayment();
    }

    private void CalculateEachPayment() {
        totalPriceBigDecimal = new BigDecimal(TextUtils.isEmpty(moneyEt.getText().toString()) ? "0" : moneyEt.getText().toString()).setScale(2, RoundingMode.HALF_UP);
        energyDecimal = new BigDecimal(TextUtils.isEmpty(addEnergyEt.getText().toString()) ? "0" : addEnergyEt.getText().toString()).setScale(2, RoundingMode.HALF_UP);
        couponDecimal = new BigDecimal(TextUtils.isEmpty(couponMoney) ? "0" : couponMoney).setScale(2, RoundingMode.HALF_UP);
        if (!(totalPriceBigDecimal.compareTo(energyDecimal.add(couponDecimal)) < 0)) {
            String payExplainStr;
            BigDecimal zfpayDecimal = totalPriceBigDecimal.subtract(energyDecimal).subtract(couponDecimal);
            if (TextUtils.equals("1", payType)) {
                payExplainStr = "能量支付" + energyDecimal.toString() + "支付宝支付" + zfpayDecimal.toString() + "元";
            } else {
                payExplainStr = "能量支付" + energyDecimal.toString() + "微信支付" + zfpayDecimal.toString() + "元";
            }
            payDetails.setText(KeywordUtil.matcherActivity(Color.parseColor("#f56165"), payExplainStr));
        }

        if(totalPriceBigDecimal.compareTo(energyDecimal.add(couponDecimal))>0){
            if (TextUtils.equals("1", payType)) {
                payTypeWhat="1";
            }else {
                payTypeWhat="2";
            }
        }else if(totalPriceBigDecimal.compareTo(energyDecimal.add(couponDecimal))==0){
            payTypeWhat="0";
        }
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
