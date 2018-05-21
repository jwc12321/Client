package com.purchase.sls.homepage.presenter;

import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.OrderDetailInfo;
import com.purchase.sls.data.entity.ShopDetailsInfo;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.OrderDetailRequest;
import com.purchase.sls.data.request.ShopDetailsRequest;
import com.purchase.sls.homepage.HomePageContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/5/21.
 */

public class QrCodePresenter implements HomePageContract.QrCodePresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private HomePageContract.QrCodeView qrCodeView;

    @Inject
    public QrCodePresenter(RestApiService restApiService, HomePageContract.QrCodeView qrCodeView) {
        this.restApiService = restApiService;
        this.qrCodeView = qrCodeView;
    }

    @Inject
    public void setupListener() {
        qrCodeView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

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
                        qrCodeView.shopDetailInfo(shopDetailsInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        qrCodeView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
