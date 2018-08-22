package com.purchase.sls.mine.presenter;

import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.ChangeAppInfo;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.DetectionVersionRequest;
import com.purchase.sls.data.request.TokenRequest;
import com.purchase.sls.mine.PersonalCenterContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/5/4.
 */

public class SettingPresenter implements PersonalCenterContract.SettingPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private PersonalCenterContract.SettingView settingView;

    @Inject
    public SettingPresenter(RestApiService restApiService, PersonalCenterContract.SettingView settingView) {
        this.restApiService = restApiService;
        this.settingView = settingView;
    }

    @Inject
    public void setupListener() {
        settingView.setPresenter(this);
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
    public void detectionVersion(String edition, String type) {
        settingView.showLoading();
        DetectionVersionRequest detectionVersionRequest=new DetectionVersionRequest(edition,type);
        Disposable disposable=restApiService.changeApp(detectionVersionRequest)
                .flatMap(new RxRemoteDataParse<ChangeAppInfo>())
                .compose(new RxSchedulerTransformer<ChangeAppInfo>())
                .subscribe(new Consumer<ChangeAppInfo>() {
                    @Override
                    public void accept(ChangeAppInfo changeAppInfo) throws Exception {
                        settingView.dismissLoading();
                        settingView.detectionSuccess(changeAppInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        settingView.dismissLoading();
                        settingView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void isSetUpPayPw() {
        settingView.showLoading();
        TokenRequest tokenRequest=new TokenRequest();
        Disposable disposable=restApiService.isSetUpPayPw(tokenRequest)
                .flatMap(new RxRemoteDataParse<String>())
                .compose(new RxSchedulerTransformer<String>())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String string) throws Exception {
                        settingView.dismissLoading();
                        settingView.renderIsSetUpPayPw(string);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        settingView.dismissLoading();
                        settingView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
