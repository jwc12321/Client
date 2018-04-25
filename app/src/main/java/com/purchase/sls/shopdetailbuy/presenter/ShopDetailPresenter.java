package com.purchase.sls.shopdetailbuy.presenter;

import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.ShopDetailsInfo;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.ShopDetailsRequest;
import com.purchase.sls.shopdetailbuy.ShopDetailBuyContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/4/24.
 */

public class ShopDetailPresenter implements ShopDetailBuyContract.ShopDetailPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private ShopDetailBuyContract.ShopDetailView shopDetailView;

    @Inject
    public ShopDetailPresenter(RestApiService restApiService, ShopDetailBuyContract.ShopDetailView shopDetailView) {
        this.restApiService = restApiService;
        this.shopDetailView = shopDetailView;
    }

    @Inject
    public void setupListener() {
        shopDetailView.setPresenter(this);
    }

    @Override
    public void getShopDetail(String storeid) {
        ShopDetailsRequest shopDetailsRequest = new ShopDetailsRequest(storeid);
        Disposable disposable = restApiService.getShopDetailsInfo(shopDetailsRequest)
                .flatMap(new RxRemoteDataParse<ShopDetailsInfo>())
                .compose(new RxSchedulerTransformer<ShopDetailsInfo>())
                .subscribe(new Consumer<ShopDetailsInfo>() {
                    @Override
                    public void accept(ShopDetailsInfo shopDetailsInfo) throws Exception {
                        shopDetailView.ShopDetailInfo(shopDetailsInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        shopDetailView.showError(throwable);
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
