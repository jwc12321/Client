package com.purchase.sls.shoppingmall.presenter;

import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.AddressInfo;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.TokenRequest;
import com.purchase.sls.shoppingmall.ShoppingMallContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/7/7.
 */

public class FillInOrderPresenter implements ShoppingMallContract.FillInOrderPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private ShoppingMallContract.FillOrderView fillOrderView;

    @Inject
    public FillInOrderPresenter(RestApiService restApiService, ShoppingMallContract.FillOrderView fillOrderView) {
        this.restApiService = restApiService;
        this.fillOrderView = fillOrderView;
    }

    @Inject
    public void setupListener() {
        fillOrderView.setPresenter(this);
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
}
