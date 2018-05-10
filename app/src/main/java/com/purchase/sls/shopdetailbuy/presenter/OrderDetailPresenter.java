package com.purchase.sls.shopdetailbuy.presenter;

import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.OrderDetailInfo;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.OrderDetailRequest;
import com.purchase.sls.data.request.SubmitEvaluateRequest;
import com.purchase.sls.shopdetailbuy.ShopDetailBuyContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/5/7.
 */

public class OrderDetailPresenter implements ShopDetailBuyContract.OrderDetailPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private ShopDetailBuyContract.OrderDetailView orderDetailView;

    @Inject
    public OrderDetailPresenter(RestApiService restApiService, ShopDetailBuyContract.OrderDetailView orderDetailView) {
        this.restApiService = restApiService;
        this.orderDetailView = orderDetailView;
    }


    @Inject
    public void setupListener() {
        orderDetailView.setPresenter(this);
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
    public void getOrderDetailInfo(String orderno) {
        OrderDetailRequest orderDetailRequest=new OrderDetailRequest(orderno);
        Disposable disposable=restApiService.getOrderDetailInfo(orderDetailRequest)
                .flatMap(new RxRemoteDataParse<OrderDetailInfo>())
                .compose(new RxSchedulerTransformer<OrderDetailInfo>())
                .subscribe(new Consumer<OrderDetailInfo>() {
                    @Override
                    public void accept(OrderDetailInfo orderDetailInfo) throws Exception {
                        orderDetailView.renderOrderDetail(orderDetailInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        orderDetailView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void submitEvaluate(SubmitEvaluateRequest submitEvaluateRequest) {
        Disposable disposable = restApiService.submitEvalute(submitEvaluateRequest)
                .flatMap(new RxRemoteDataParse<String>())
                .compose(new RxSchedulerTransformer<String>())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String string) throws Exception {
                        orderDetailView.submitSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        orderDetailView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
