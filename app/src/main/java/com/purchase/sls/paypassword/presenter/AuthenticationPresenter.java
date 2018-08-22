package com.purchase.sls.paypassword.presenter;

import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.Ignore;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.PayPasswordRequest;
import com.purchase.sls.paypassword.PayPasswordContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/8/22.
 */

public class AuthenticationPresenter implements PayPasswordContract.AuthenticationPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private PayPasswordContract.AuthenticationView authenticationView;

    @Inject
    public AuthenticationPresenter(RestApiService restApiService, PayPasswordContract.AuthenticationView authenticationView) {
        this.restApiService = restApiService;
        this.authenticationView = authenticationView;
    }

    @Inject
    public void setupListener() {
        authenticationView.setPresenter(this);
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
    public void verifyPayPassword(String payPassword) {
        authenticationView.showLoading();
        PayPasswordRequest payPasswordRequest=new PayPasswordRequest(payPassword);
        Disposable disposable = restApiService.verifyPayPassword(payPasswordRequest)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        authenticationView.dismissLoading();
                        authenticationView.verifySuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        authenticationView.dismissLoading();
                        authenticationView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
