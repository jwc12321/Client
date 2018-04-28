package com.purchase.sls.shopdetailbuy.presenter;

import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.GeneratingOrderInfo;
import com.purchase.sls.data.entity.UserpowerInfo;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.GeneratingOrderRequest;
import com.purchase.sls.data.request.UserpowerRequest;
import com.purchase.sls.shopdetailbuy.ShopDetailBuyContract;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/4/28.
 */

public class PaymentOrderPresenter implements ShopDetailBuyContract.PaymentOrderPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private ShopDetailBuyContract.PaymentOrderView paymentOrderView;

    @Inject
    public PaymentOrderPresenter(RestApiService restApiService, ShopDetailBuyContract.PaymentOrderView paymentOrderView) {
        this.restApiService = restApiService;
        this.paymentOrderView = paymentOrderView;
    }

    @Inject
    public void setupListener() {
        paymentOrderView.setPresenter(this);
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
     * 提交订单
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

}
