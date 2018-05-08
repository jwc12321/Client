package com.purchase.sls.shopdetailbuy.presenter;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.purchase.sls.BuildConfig;
import com.purchase.sls.common.unit.PayResult;
import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.AliPaySignResponse;
import com.purchase.sls.data.entity.GeneratingOrderInfo;
import com.purchase.sls.data.entity.UserpowerInfo;
import com.purchase.sls.data.entity.WXPaySignResponse;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.GeneratingOrderRequest;
import com.purchase.sls.data.request.UserpowerRequest;
import com.purchase.sls.shopdetailbuy.ShopDetailBuyContract;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static org.greenrobot.eventbus.EventBus.TAG;

/**
 * Created by JWC on 2018/4/28.
 */

public class PaymentOrderPresenter implements ShopDetailBuyContract.PaymentOrderPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private ShopDetailBuyContract.PaymentOrderView paymentOrderView;
    private Context context;
    private static final int SDK_PAY_FLAG = 1;
    private IWXAPI WXAPI;

    @Inject
    public PaymentOrderPresenter(RestApiService restApiService, ShopDetailBuyContract.PaymentOrderView paymentOrderView) {
        this.restApiService = restApiService;
        this.paymentOrderView = paymentOrderView;
        WXAPI = WXAPIFactory.createWXAPI(context, null);
    }

    @Inject
    public void setupListener() {
        paymentOrderView.setPresenter(this);
    }

    public void setContext(Context context) {
        this.context = context;
    }
    public void setWXAPI(IWXAPI WXAPI) {
        this.WXAPI = WXAPI;
    }
    /**
     * 获取能量
     * @param price
     * @param storeid
     */
    @Override
    public void getUserpowerInfo(String price, String storeid) {
        UserpowerRequest userpowerRequest=new UserpowerRequest(price,storeid);
        Disposable disposable=restApiService.getUserpowerInfo(userpowerRequest)
                .flatMap(new RxRemoteDataParse<UserpowerInfo>())
                .compose(new RxSchedulerTransformer<UserpowerInfo>())
                .subscribe(new Consumer<UserpowerInfo>() {
                    @Override
                    public void accept(UserpowerInfo userpowerInfo) throws Exception {
                        paymentOrderView.userpowerInfo(userpowerInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        paymentOrderView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);

    }

    /**
     * 提交订单能量支付
     * @param allprice
     * @param storeid
     * @param coupon
     * @param power
     * @param paytype
     * @param notes
     */
    @Override
    public void setGeneratingOrder(String allprice, String storeid, String coupon, String power, String paytype, String notes) {
        GeneratingOrderRequest generatingOrderRequest=new GeneratingOrderRequest(allprice,storeid,coupon,power,paytype,notes);
        Disposable disposable=restApiService.getGeneratingOrderInfo(generatingOrderRequest)
                .flatMap(new RxRemoteDataParse<GeneratingOrderInfo>())
                .compose(new RxSchedulerTransformer<GeneratingOrderInfo>())
                .subscribe(new Consumer<GeneratingOrderInfo>() {
                    @Override
                    public void accept(GeneratingOrderInfo generatingOrderInfo) throws Exception {
                        paymentOrderView.generatingOrderSuccess(generatingOrderInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        paymentOrderView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    /**
     * 支付宝支付
     * @param allprice
     * @param storeid
     * @param coupon
     * @param power
     * @param paytype
     * @param notes
     */
    @Override
    public void getAlipaySign(String allprice, String storeid, String coupon, String power, String paytype, String notes) {
        GeneratingOrderRequest generatingOrderRequest=new GeneratingOrderRequest(allprice,storeid,coupon,power,paytype,notes);
        Disposable disposable=restApiService.getAliPaySignResponse(generatingOrderRequest)
                .flatMap(new RxRemoteDataParse<AliPaySignResponse>())
                .compose(new RxSchedulerTransformer<AliPaySignResponse>())
                .subscribe(new Consumer<AliPaySignResponse>() {
                    @Override
                    public void accept(AliPaySignResponse aliPaySignResponse) throws Exception {
                       String sign = aliPaySignResponse.getSign();
                       paymentOrderView.renderOrderno(aliPaySignResponse.getOrderno());
                       startAliPay(sign);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        paymentOrderView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    /**
     * 微信支付
     * @param allprice
     * @param storeid
     * @param coupon
     * @param power
     * @param paytype
     * @param notes
     */
    @Override
    public void getWXPaySign(String allprice, String storeid, String coupon, String power, String paytype, String notes) {
        GeneratingOrderRequest generatingOrderRequest=new GeneratingOrderRequest(allprice,storeid,coupon,power,paytype,notes);
        Disposable disposable=restApiService.getWXPaySignResponse(generatingOrderRequest)
                .flatMap(new RxRemoteDataParse<WXPaySignResponse>())
                .compose(new RxSchedulerTransformer<WXPaySignResponse>())
                .subscribe(new Consumer<WXPaySignResponse>() {
                    @Override
                    public void accept(WXPaySignResponse wXPaySignResponse) throws Exception {
                        paymentOrderView.renderOrderno(wXPaySignResponse.getOrderno());
                        startWXPay(wXPaySignResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        paymentOrderView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }


    @Override
    public void start() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        for (Disposable disposable : mDisposableList) {
            if (!disposable.isDisposed()) {
                disposable.dispose();
            }
        }
    }

    private void startAliPay(final String sign) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                PayTask payTask = new PayTask((Activity) context);
                String result = payTask.pay(sign, true);

                Message message = Message.obtain();
                message.what = SDK_PAY_FLAG;
                message.obj = result;
                handler.sendMessage(message);
            }
        };

        new Thread(runnable).start();
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case SDK_PAY_FLAG:
                    PayResult payResult = new PayResult((String) msg.obj);
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    if (TextUtils.equals(resultStatus, "9000")) {
                        paymentOrderView.onRechargeSuccess();
                    } else {
                        if (TextUtils.equals(resultStatus, "8000")) {
                            paymentOrderView.onRechargetFail();
                        } else if (TextUtils.equals(resultStatus, "6001")) {
                            paymentOrderView.onRechargeCancel();
                        } else {
                            paymentOrderView.onRechargetFail();
                        }
                    }
                    break;
            }
        }
    };

    private void startWXPay(WXPaySignResponse wxPaySignResponse){
        WXPaySignResponse.WxpaySignBean wxpaySign = wxPaySignResponse.getWxpaySign();
        if (null != wxpaySign) {
            PayReq req = new PayReq();
            req.appId = wxpaySign.getAppid();
            req.partnerId = wxpaySign.getPartnerid();
            req.prepayId = wxpaySign.getPrepayid();
            req.nonceStr = wxpaySign.getNoncestr();
            req.timeStamp = wxpaySign.getTimestamp();
            req.packageValue = wxpaySign.getPackageX();
            req.sign = wxpaySign.getSign();
            req.extData = "WxPay";
            // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
            if (BuildConfig.DEBUG)
                Log.i(TAG, "onSucceed: " + wxpaySign.getAppid());
            paymentOrderView.onAppIdReceive(req.appId);
            WXAPI.sendReq(req);
        } else {
            if (BuildConfig.DEBUG)
                Log.d("PAY_GET", "返回错误");
        }
    }

}
