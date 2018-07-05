package com.purchase.sls.shoppingmall.presenter;

import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.GoodsDetailInfo;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.GoodsidRequest;
import com.purchase.sls.shoppingmall.ShoppingMallContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/7/4.
 */

public class GoodsDetailPresenter implements ShoppingMallContract.GoodsDetailPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private ShoppingMallContract.GoodsDetailView goodsDetailView;

    @Inject
    public GoodsDetailPresenter(RestApiService restApiService, ShoppingMallContract.GoodsDetailView goodsDetailView) {
        this.restApiService = restApiService;
        this.goodsDetailView = goodsDetailView;
    }

    @Inject
    public void setupListener() {
        goodsDetailView.setPresenter(this);
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
    public void getGoodsDetail(String goodsid) {
        goodsDetailView.showLoading();
        GoodsidRequest goodsidRequest=new GoodsidRequest(goodsid);
        Disposable disposable = restApiService.getGoodsDetailInfo(goodsidRequest)
                .flatMap(new RxRemoteDataParse<GoodsDetailInfo>())
                .compose(new RxSchedulerTransformer<GoodsDetailInfo>())
                .subscribe(new Consumer<GoodsDetailInfo>() {
                    @Override
                    public void accept(GoodsDetailInfo goodsDetailInfo) throws Exception {
                        goodsDetailView.dismissLoading();
                        goodsDetailView.renderGoodsDetail(goodsDetailInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        goodsDetailView.dismissLoading();
                        goodsDetailView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
