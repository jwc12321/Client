package com.purchase.sls.address.presenter;

import android.text.TextUtils;

import com.purchase.sls.address.AddressContract;
import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.AddressInfo;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.AddAddressRequest;
import com.purchase.sls.data.request.TokenRequest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/5/31.
 */

public class AddAddressPresenter implements AddressContract.AddAddressPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private AddressContract.AddAddressView addAddressView;

    @Inject
    public AddAddressPresenter(RestApiService restApiService, AddressContract.AddAddressView addAddressView) {
        this.restApiService = restApiService;
        this.addAddressView = addAddressView;
    }

    @Inject
    public void setupListener() {
        addAddressView.setPresenter(this);
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
    public void addAddress(AddAddressRequest addAddressRequest) {
        addAddressView.showLoading();
        Disposable disposable = restApiService.addAddress(addAddressRequest)
                .flatMap(new RxRemoteDataParse<String>())
                .compose(new RxSchedulerTransformer<String>())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String string) throws Exception {
                        addAddressView.dismissLoading();
                        addAddressView.addSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        addAddressView.dismissLoading();
                        addAddressView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
