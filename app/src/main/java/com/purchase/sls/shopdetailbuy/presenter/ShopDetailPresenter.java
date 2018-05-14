package com.purchase.sls.shopdetailbuy.presenter;

import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.Ignore;
import com.purchase.sls.data.entity.ShopDetailsInfo;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.AddRemoveCollectionRequest;
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
        shopDetailView.showLoading();
        ShopDetailsRequest shopDetailsRequest = new ShopDetailsRequest(storeid);
        Disposable disposable = restApiService.getShopDetailsInfo(shopDetailsRequest)
                .flatMap(new RxRemoteDataParse<ShopDetailsInfo>())
                .compose(new RxSchedulerTransformer<ShopDetailsInfo>())
                .subscribe(new Consumer<ShopDetailsInfo>() {
                    @Override
                    public void accept(ShopDetailsInfo shopDetailsInfo) throws Exception {
                        shopDetailView.dismissLoading();
                        shopDetailView.shopDetailInfo(shopDetailsInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        shopDetailView.dismissLoading();
                        shopDetailView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    /**
     * 收藏的增加和删除
     *
     * @param storeid
     * @param type
     * @param fidArray
     */
    @Override
    public void addRemoveCollection(String storeid, final String type, String[] fidArray) {
        shopDetailView.showLoading();
        AddRemoveCollectionRequest addRemoveCollectionRequest = new AddRemoveCollectionRequest(storeid, type, fidArray);
        Disposable disposable = restApiService.addRemoveCollection(addRemoveCollectionRequest)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        shopDetailView.dismissLoading();
                        shopDetailView.addRemoveSuccess(type);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        shopDetailView.dismissLoading();
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
