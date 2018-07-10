package com.purchase.sls.goodsordermanage.presenter;

import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.GoodsOrderDetailInfo;
import com.purchase.sls.data.entity.Ignore;
import com.purchase.sls.data.entity.MalllogisInfo;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.GoodsOrderCodeRequest;
import com.purchase.sls.data.request.MalllogisRequest;
import com.purchase.sls.data.request.OrdernumRequest;
import com.purchase.sls.goodsordermanage.GoodsOrderContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/7/9.
 */

public class GoodsOrderDetailPresenter implements GoodsOrderContract.GoodsOrderDetailPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private GoodsOrderContract.GoodsOrderDetailView goodsOrderDetailView;

    @Inject
    public GoodsOrderDetailPresenter(RestApiService restApiService, GoodsOrderContract.GoodsOrderDetailView goodsOrderDetailView) {
        this.restApiService = restApiService;
        this.goodsOrderDetailView = goodsOrderDetailView;
    }

    @Inject
    public void setupListener() {
        goodsOrderDetailView.setPresenter(this);
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
    public void getGoodsOrderDetail(String ordernum) {
        goodsOrderDetailView.showLoading();
        OrdernumRequest ordernumRequest=new OrdernumRequest(ordernum);
        Disposable disposable = restApiService.getGoodsOrderDetailInfo(ordernumRequest)
                .flatMap(new RxRemoteDataParse<GoodsOrderDetailInfo>())
                .compose(new RxSchedulerTransformer<GoodsOrderDetailInfo>())
                .subscribe(new Consumer<GoodsOrderDetailInfo>() {
                    @Override
                    public void accept(GoodsOrderDetailInfo goodsOrderDetailInfo) throws Exception {
                        goodsOrderDetailView.dismissLoading();
                        goodsOrderDetailView.renderGoodsOrderDetail(goodsOrderDetailInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        goodsOrderDetailView.dismissLoading();
                        goodsOrderDetailView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void cancelOrder(String orderCode) {
        goodsOrderDetailView.showLoading();
        GoodsOrderCodeRequest goodsOrderCodeRequest = new GoodsOrderCodeRequest(orderCode);
        Disposable disposable = restApiService.cancelOrder(goodsOrderCodeRequest)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        goodsOrderDetailView.dismissLoading();
                        goodsOrderDetailView.cancelOrderSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        goodsOrderDetailView.dismissLoading();
                        goodsOrderDetailView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void deleteOrder(String orderCode) {
        goodsOrderDetailView.showLoading();
        GoodsOrderCodeRequest goodsOrderCodeRequest = new GoodsOrderCodeRequest(orderCode);
        Disposable disposable = restApiService.deleteOrder(goodsOrderCodeRequest)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        goodsOrderDetailView.dismissLoading();
                        goodsOrderDetailView.cancelOrderSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        goodsOrderDetailView.dismissLoading();
                        goodsOrderDetailView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void completeOrder(String orderCode) {
        goodsOrderDetailView.showLoading();
        GoodsOrderCodeRequest goodsOrderCodeRequest = new GoodsOrderCodeRequest(orderCode);
        Disposable disposable = restApiService.completeOrder(goodsOrderCodeRequest)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        goodsOrderDetailView.dismissLoading();
                        goodsOrderDetailView.cancelOrderSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        goodsOrderDetailView.dismissLoading();
                        goodsOrderDetailView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getMalllogisInfo(String orderCode) {
       goodsOrderDetailView.showLoading();
        MalllogisRequest malllogisRequest=new MalllogisRequest(orderCode);
        Disposable disposable = restApiService.getMalllogisInfo(malllogisRequest)
                .flatMap(new RxRemoteDataParse<MalllogisInfo>())
                .compose(new RxSchedulerTransformer<MalllogisInfo>())
                .subscribe(new Consumer<MalllogisInfo>() {
                    @Override
                    public void accept(MalllogisInfo malllogisInfo) throws Exception {
                        goodsOrderDetailView.dismissLoading();
                        goodsOrderDetailView.renderMalllogisInfo(malllogisInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        goodsOrderDetailView.dismissLoading();
                        goodsOrderDetailView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
