package com.purchase.sls.energy.presenter;

import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.TokenRequest;
import com.purchase.sls.energy.EnergyContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/6/11.
 */

public class SignInPresenter implements EnergyContract.SignInPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private EnergyContract.SignInView signInView;

    @Inject
    public SignInPresenter(RestApiService restApiService, EnergyContract.SignInView signInView) {
        this.restApiService = restApiService;
        this.signInView = signInView;
    }

    @Inject
    public void setupListener() {
        signInView.setPresenter(this);
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
    public void signIn() {
        signInView.showLoading();
        TokenRequest tokenRequest = new TokenRequest();
        Disposable disposable = restApiService.signIn(tokenRequest)
                .flatMap(new RxRemoteDataParse<String>())
                .compose(new RxSchedulerTransformer<String>())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String energy) throws Exception {
                        signInView.dismissLoading();
                        signInView.signInSuccess(energy);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        signInView.dismissLoading();
                        signInView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
