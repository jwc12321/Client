package com.purchase.sls.login.presenter;

import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.LoginTokenResponse;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.LoginRequest;
import com.purchase.sls.login.LoginContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/4/17.
 */

public class LoginPresenter implements LoginContract.LoginPresenter {
    private LoginContract.LoginView loginView;
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    @Inject
    public LoginPresenter(LoginContract.LoginView loginView, RestApiService restApiService) {
        this.loginView = loginView;
        this.restApiService = restApiService;
    }



    @Inject
    public void setupListener() {
        loginView.setPresenter(this);
    }
    @Override
    public void login(String username, String type, String code, String password) {
        LoginRequest loginRequest=new LoginRequest(username,type,code,password);
        Disposable disposable=restApiService.login(loginRequest)
                .flatMap(new RxRemoteDataParse<LoginTokenResponse>())
                .compose(new RxSchedulerTransformer<LoginTokenResponse>())
                .subscribe(new Consumer<LoginTokenResponse>() {
                    @Override
                    public void accept(LoginTokenResponse loginTokenResponse) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

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
