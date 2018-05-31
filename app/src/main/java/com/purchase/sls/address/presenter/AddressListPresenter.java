package com.purchase.sls.address.presenter;

import android.text.TextUtils;

import com.purchase.sls.address.AddressContract;
import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.AddressInfo;
import com.purchase.sls.data.entity.Ignore;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.IdRequest;
import com.purchase.sls.data.request.TokenRequest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/5/31.
 */

public class AddressListPresenter implements AddressContract.AddressListPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private AddressContract.AddressListView addressListView;

    @Inject
    public AddressListPresenter(RestApiService restApiService, AddressContract.AddressListView addressListView) {
        this.restApiService = restApiService;
        this.addressListView = addressListView;
    }

    @Inject
    public void setupListener() {
        addressListView.setPresenter(this);
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
    public void getAddressList(String refreshType) {
        if(TextUtils.equals("1",refreshType)){
            addressListView.showLoading();
        }
        TokenRequest tokenRequest=new TokenRequest();
        Disposable disposable = restApiService.getAddressList(tokenRequest)
                .flatMap(new RxRemoteDataParse<List<AddressInfo>>())
                .compose(new RxSchedulerTransformer<List<AddressInfo>>())
                .subscribe(new Consumer<List<AddressInfo>>() {
                    @Override
                    public void accept(List<AddressInfo> addressInfos) throws Exception {
                        addressListView.dismissLoading();
                        addressListView.renderAddressList(addressInfos);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        addressListView.dismissLoading();
                        addressListView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void setDefault(String id) {
        IdRequest idRequest=new IdRequest(id);
        Disposable disposable = restApiService.setDefault(idRequest)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        addressListView.defaultSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        addressListView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void deleteAddress(String id) {
        IdRequest idRequest=new IdRequest(id);
        Disposable disposable = restApiService.deleteAddress(idRequest)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        addressListView.deleteSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        addressListView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
