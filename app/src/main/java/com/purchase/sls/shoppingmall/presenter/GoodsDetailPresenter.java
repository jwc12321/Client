package com.purchase.sls.shoppingmall.presenter;

import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.shoppingmall.ShoppingMallContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

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
    }
}
