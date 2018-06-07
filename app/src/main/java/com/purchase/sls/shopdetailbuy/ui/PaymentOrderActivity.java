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
import android.util.Log;
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
import com.purchase.sls.common.UMStaticData;
import com.purchase.sls.common.unit.UmengEventUtils;
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

import org.greenrobot.eventbus.EventBus;
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
    private static final int UPDATE_TOTAL_MONEY = 98;
    private static final int UPDATE_ENERGY = 99;
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
    private BigDecimal maxEnergyDecial;//用户有多少能量
    private BigDecimal totalStCdDecimal;//输入的价格减去优惠券金额乘以兑换比例，算出所需的能量值
    private BigDecimal proportionDecimal;//能量兑换比例
    private BigDecimal offsetCashDecimal;//能抵用的现金金额
    private BigDecimal percentageDecimal;//能量兑换比是200，要除以100才行
    private BigDecimal zfpayDecimal;//支付宝和微信支付的钱
    private BigDecimal leastCostDecimal;//满足多少钱才能使用优惠券

    private String payTypeWhat = "1";
    private IWXAPI api;
    private String ordreno;
    private String proportion; //能量兑换比例  如果是200，就是一个能量相当于2块钱
    private int digits = 2;

    boolean moneyDouble=true;
    boolean energyDouble=true;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_order);
        ButterKnife.bind(this);
        setHeight(back,title,null);
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        businessName = getIntent().getStringExtra(StaticData.BUSINESS_NAME);
        businessZpics = getIntent().getStringExtra(StaticData.BUSINESS_ZPICS);
        businessStoreId = getIntent().getStringExtra(StaticData.BUSINESS_STOREID);
        percentageDecimal = new BigDecimal(100).setScale(2, RoundingMode.HALF_UP);
        offsetCashDecimal = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);
        zfpayDecimal = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);
        leastCostDecimal = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);
        totalStCdDecimal=new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);
        proportionDecimal = new BigDecimal(100).setScale(2, RoundingMode.HALF_UP);
        maxEnergyDecial=new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);
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
                if (TextUtils.isEmpty(moneyEt.getText().toString())) {
                    confirmPayBg.setEnabled(false);
                    moneyDouble=true;
                    if (mHandler != null) {
                        mHandler.removeCallbacksAndMessages(null);
                        mHandler.sendEmptyMessageDelayed(UPDATE_TOTAL_MONEY, 1000);
                    } else {
                        mHandler.sendEmptyMessageDelayed(UPDATE_TOTAL_MONEY, 1000);
                    }
                } else {
                    try {
                        Double db = Double.valueOf(moneyEt.getText().toString());
                        moneyDouble = true;
                    } catch (Exception e) {
                        moneyDouble = false;
                        Toast.makeText(PaymentOrderActivity.this, "请正确填写", Toast.LENGTH_SHORT).show();
                    }
                    if (moneyDouble) {
                        if (!TextUtils.isEmpty(moneyEt.getText().toString())) {
                            limitedDecimal(moneyEt.getText().toString(), moneyEt);
                            confirmPayBg.setEnabled(true);
                            if (mHandler != null) {
                                mHandler.removeCallbacksAndMessages(null);
                                mHandler.sendEmptyMessageDelayed(UPDATE_TOTAL_MONEY, 1000);
                            } else {
                                mHandler.sendEmptyMessageDelayed(UPDATE_TOTAL_MONEY, 1000);
                            }
                        } else {
                            confirmPayBg.setEnabled(false);
                        }
                    }
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
                if (TextUtils.isEmpty(addEnergyEt.getText().toString())) {
                    energyDouble=true;
                    if (mHandler != null) {
                        mHandler.removeCallbacksAndMessages(null);
                        mHandler.sendEmptyMessageDelayed(UPDATE_ENERGY, 1000);
                    } else {
                        mHandler.sendEmptyMessageDelayed(UPDATE_ENERGY, 1000);
                    }
                } else {
                    try {
                        Double db = Double.valueOf(addEnergyEt.getText().toString());
                        energyDouble = true;
                    } catch (Exception e) {
                        energyDouble = false;
                        Toast.makeText(PaymentOrderActivity.this, "请正确填写", Toast.LENGTH_SHORT).show();
                    }
                    if (energyDouble) {
                        if (!TextUtils.isEmpty(addEnergyEt.getText().toString()) && !TextUtils.equals("0", addEnergyEt.getText().toString())
                                && !TextUtils.equals("0.0", addEnergyEt.getText().toString()) && !TextUtils.equals("0.0", addEnergyEt.getText().toString())) {
                            limitedDecimal(addEnergyEt.getText().toString(), addEnergyEt);
                            if (mHandler != null) {
                                mHandler.removeCallbacksAndMessages(null);
                                mHandler.sendEmptyMessageDelayed(UPDATE_ENERGY, 1000);
                            } else {
                                mHandler.sendEmptyMessageDelayed(UPDATE_ENERGY, 1000);
                            }
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }


    private void limitedDecimal(String s,EditText editText){
        //删除“.”后面超过2位后的数据
        if (s.toString().contains(".")) {
            if (s.length() - 1 - s.toString().indexOf(".") > digits) {
                s = (String) s.toString().subSequence(0, s.toString().indexOf(".") + digits+1);
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

    class MyHandler extends Handler {
        WeakReference<Activity> mWeakReference;

        public MyHandler(Activity activity) {
            mWeakReference = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final Activity activity = mWeakReference.get();
            if (activity != null) {
                switch (msg.what) {
                    case UPDATE_TOTAL_MONEY:
                        if(moneyDouble&&energyDouble) {
                            fillNumber();
                        }
                        break;
                    case UPDATE_ENERGY:
                        if(moneyDouble&&energyDouble) {
                            fillNumber();
                        }
                        break;
                }
            }
        }
    }

    private void fillNumber() {
        String payExplainStr;
        totalPriceBigDecimal = new BigDecimal(TextUtils.isEmpty(moneyEt.getText().toString()) ? "0" : moneyEt.getText().toString()).setScale(2, RoundingMode.HALF_UP);
        if (totalPriceBigDecimal.compareTo(leastCostDecimal) < 0) {
            couponMoney = "0";
            reelNumber.setText("");
        }
        energyDecimal = new BigDecimal(TextUtils.isEmpty(addEnergyEt.getText().toString()) ? "0" : addEnergyEt.getText().toString()).setScale(2, RoundingMode.HALF_UP);
        couponDecimal = new BigDecimal(TextUtils.isEmpty(couponMoney) ? "0" : couponMoney).setScale(2, RoundingMode.HALF_UP);
        totalStCdDecimal = (totalPriceBigDecimal.subtract(couponDecimal)).divide((proportionDecimal.divide(percentageDecimal,2,BigDecimal.ROUND_HALF_UP)),2,BigDecimal.ROUND_HALF_UP);
        if(totalStCdDecimal.doubleValue()>0) {
            if (energyDecimal.compareTo(maxEnergyDecial) > 0) {//输入能量大于最多的能量
                toast("能量最多只能填写" + maxEnergyDecial.toString());
                addEnergyEt.setText(maxEnergyDecial.toString());
                return;
            }
        }
        if (energyDecimal.compareTo(totalStCdDecimal) < 0) {//需要微信和支付包支付
            zfpayDecimal = (totalPriceBigDecimal.subtract(couponDecimal)).subtract(energyDecimal.multiply(proportionDecimal).divide(percentageDecimal,2,BigDecimal.ROUND_HALF_UP));
            if (TextUtils.equals("1", payType)) {
                payExplainStr = "能量支付" + energyDecimal.toString() + ",支付宝支付" + zfpayDecimal.toString() + "元";
                payTypeWhat = "1";
            } else {
                payExplainStr = "能量支付" + energyDecimal.toString() + ",微信支付" + zfpayDecimal.toString() + "元";
                payTypeWhat = "2";
            }
            payDetails.setText(KeywordUtil.matcherActivity(Color.parseColor("#f56165"), payExplainStr));
        } else if (energyDecimal.compareTo(totalStCdDecimal) > 0) {//输入的能量太多了
            if(totalStCdDecimal.doubleValue()<0){
                addEnergyEt.setText("0");
                if (TextUtils.equals("1", payType)) {
                    payExplainStr = "能量支付0,支付宝支付0元";
                } else {
                    payExplainStr = "能量支付0,微信支付0元";
                }
                payDetails.setText(KeywordUtil.matcherActivity(Color.parseColor("#f56165"), payExplainStr));
                payTypeWhat = "0";
            }else {
                toast("能量只需要填写" + totalStCdDecimal.toString());
                addEnergyEt.setText(totalStCdDecimal.toString());
            }
        } else {
            if (TextUtils.equals("1", payType)) {
                payExplainStr = "能量支付" + energyDecimal.toString() + ",支付宝支付0元";
            } else {
                payExplainStr = "能量支付" + energyDecimal.toString() + ",微信支付0元";
            }
            payDetails.setText(KeywordUtil.matcherActivity(Color.parseColor("#f56165"), payExplainStr));
            payTypeWhat = "0";
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
        proportion = userpowerInfo.getProportion();
        energyStr = userpowerInfo.getPersionInfoResponse().getPower();
        maxEnergyDecial = new BigDecimal(TextUtils.isEmpty(energyStr) ? "0" : energyStr).setScale(2, RoundingMode.HALF_UP);
        proportionDecimal = new BigDecimal(TextUtils.isEmpty(proportion) ? "0" : proportion).setScale(2, RoundingMode.HALF_UP);
        offsetCashDecimal = maxEnergyDecial.multiply(proportionDecimal).divide(percentageDecimal,2,BigDecimal.ROUND_HALF_UP);
        addEnergyEt.setHint("请输入金额(可用" + energyStr + "能量，抵用现金" + (offsetCashDecimal.toString()) + "元)");
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
        UmengEventUtils.statisticsClick(this, UMStaticData.PAY_SUCCESS);
        PaySuccessActivity.start(this, businessName, generatingOrderInfo.getOrderno());
        this.finish();
    }

    @Override
    public void onRechargetFail() {
        showMessage("支付宝支付失败");
    }

    @Override
    public void onRechargeSuccess() {
        UmengEventUtils.statisticsClick(this, UMStaticData.PAY_SUCCESS);
        PaySuccessActivity.start(this, businessName, ordreno);
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
            paymentOrderPresenter.setWXAPI(api);
        }
    }

    @Override
    public void renderOrderno(String orderno) {
        this.ordreno = orderno;
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
        UmengEventUtils.statisticsClick(this, UMStaticData.PAY_SUCCESS);
        PaySuccessActivity.start(this, businessName, ordreno);
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
                fillNumber();
                break;
            case R.id.weixin_rl:
                payType = "2";
                zhifubaoPay.setSelected(false);
                weixinPay.setSelected(true);
                fillNumber();
                break;
            case R.id.confirm_pay_bg:
                if(moneyDouble&&energyDouble) {
                    if (TextUtils.equals("1", payTypeWhat)) {
                        paymentOrderPresenter.getAlipaySign(moneyEt.getText().toString(), businessStoreId, couponId, addEnergyEt.getText().toString(), payType, addNotesEt.getText().toString());
                    } else if (TextUtils.equals("2", payTypeWhat)) {
                        paymentOrderPresenter.getWXPaySign(moneyEt.getText().toString(), businessStoreId, couponId, addEnergyEt.getText().toString(), payType, addNotesEt.getText().toString());
                    } else {
                        paymentOrderPresenter.setGeneratingOrder(moneyEt.getText().toString(), businessStoreId, couponId, addEnergyEt.getText().toString(), payType, addNotesEt.getText().toString());
                    }
                }else {
                    Toast.makeText(PaymentOrderActivity.this, "请正确填写", Toast.LENGTH_SHORT).show();
                }
                break;
            default:

        }
    }


    @Override
    public void couponItemClick(CouponInfo couponInfo) {
        couponRl.setVisibility(View.GONE);
        leastCostDecimal = new BigDecimal(TextUtils.isEmpty(couponInfo.getQuanInfo().getLeastCost()) ? "0" : couponInfo.getQuanInfo().getLeastCost()).setScale(2, RoundingMode.HALF_UP);
        reelNumber.setText("-¥" + couponInfo.getQuanInfo().getPrice());
        couponId = couponInfo.getId();
        couponMoney = couponInfo.getQuanInfo().getPrice();
        fillNumber();
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

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
