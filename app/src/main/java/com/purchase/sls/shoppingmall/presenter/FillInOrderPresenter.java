package com.purchase.sls.shoppingmall.presenter;

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
import com.purchase.sls.data.entity.AddressInfo;
import com.purchase.sls.data.entity.AliPaySignResponse;
import com.purchase.sls.data.entity.WXPaySignResponse;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.CreateOrderRequest;
import com.purchase.sls.data.request.GeneratingOrderRequest;
import com.purchase.sls.data.request.TokenRequest;
import com.purchase.sls.shoppingmall.ShoppingMallContract;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static org.greenrobot.eventbus.EventBus.TAG;

/**
 * Created by JWC on 2018/7/7.
 */

public class FillInOrderPresenter implements ShoppingMallContract.FillInOrderPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private ShoppingMallContract.FillOrderView fillOrderView;
    private Context context;
    private static final int SDK_PAY_FLAG = 1;
    private IWXAPI WXAPI;

    @Inject
    public FillInOrderPresenter(RestApiService restApiService, ShoppingMallContract.FillOrderView fillOrderView) {
        this.restApiService = restApiService;
        this.fillOrderView = fillOrderView;
    }

    @Inject
    public void setupListener() {
        fillOrderView.setPresenter(this);
    }

    public void setContext(Context context) {
        this.context = context;
    }
    public void setWXAPI(IWXAPI WXAPI) {
        this.WXAPI = WXAPI;
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

    @Override
    public void getAddressList() {
        fillOrderView.showLoading();
        TokenRequest tokenRequest = new TokenRequest();
        Disposable disposable = restApiService.getAddressList(tokenRequest)
                .flatMap(new RxRemoteDataParse<List<AddressInfo>>())
                .compose(new RxSchedulerTransformer<List<AddressInfo>>())
                .subscribe(new Consumer<List<AddressInfo>>() {
                    @Override
                    public void accept(List<AddressInfo> addressInfos) throws Exception {
                        fillOrderView.dismissLoading();
                        fillOrderView.renderAddressList(addressInfos);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        fillOrderView.dismissLoading();
                        fillOrderView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getAlipaySign(String carts, String addressid, String paytype, String isquan) {
        fillOrderView.showLoading();
        CreateOrderRequest createOrderRequest=new CreateOrderRequest(carts,addressid,paytype,isquan);
        Disposable disposable=restApiService.getGoodsAliPaySignResponse(createOrderRequest)
                .flatMap(new RxRemoteDataParse<AliPaySignResponse>())
                .compose(new RxSchedulerTransformer<AliPaySignResponse>())
                .subscribe(new Consumer<AliPaySignResponse>() {
                    @Override
                    public void accept(AliPaySignResponse aliPaySignResponse) throws Exception {
                        fillOrderView.dismissLoading();
                        String sign = aliPaySignResponse.getSign();
                        fillOrderView.renderOrderno(aliPaySignResponse.getOrderno());
                        startAliPay(sign);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        fillOrderView.dismissLoading();
                        fillOrderView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getWXPaySign(String carts, String addressid, String paytype, String isquan) {
        fillOrderView.showLoading();
        CreateOrderRequest createOrderRequest=new CreateOrderRequest(carts,addressid,paytype,isquan);
        Disposable disposable=restApiService.getGoodsWXPaySignResponse(createOrderRequest)
                .flatMap(new RxRemoteDataParse<WXPaySignResponse>())
                .compose(new RxSchedulerTransformer<WXPaySignResponse>())
                .subscribe(new Consumer<WXPaySignResponse>() {
                    @Override
                    public void accept(WXPaySignResponse wXPaySignResponse) throws Exception {
                        fillOrderView.dismissLoading();
                        fillOrderView.renderOrderno(wXPaySignResponse.getOrderno());
                        startWXPay(wXPaySignResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        fillOrderView.dismissLoading();
                        fillOrderView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
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
                        fillOrderView.onRechargeSuccess();
                    } else {
                        if (TextUtils.equals(resultStatus, "8000")) {
                            fillOrderView.onRechargetFail();
                        } else if (TextUtils.equals(resultStatus, "6001")) {
                            fillOrderView.onRechargeCancel();
                        } else {
                            fillOrderView.onRechargetFail();
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
            fillOrderView.onAppIdReceive(req.appId);
            WXAPI.sendReq(req);
        } else {
            if (BuildConfig.DEBUG)
                Log.d("PAY_GET", "返回错误");
        }
    }
}
