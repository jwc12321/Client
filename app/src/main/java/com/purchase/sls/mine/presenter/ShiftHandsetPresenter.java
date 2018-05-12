package com.purchase.sls.mine.presenter;

import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.Ignore;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.CheckCodeRequest;
import com.purchase.sls.data.request.CheckNewCodeRequest;
import com.purchase.sls.data.request.SendCodeRequest;
import com.purchase.sls.data.request.SendNewVCodeRequest;
import com.purchase.sls.mine.PersonalCenterContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/5/4.
 */

public class ShiftHandsetPresenter implements PersonalCenterContract.ShiftHandsetPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private PersonalCenterContract.ShiftHandsetView shiftHandsetView;

    @Inject
    public ShiftHandsetPresenter(RestApiService restApiService, PersonalCenterContract.ShiftHandsetView shiftHandsetView) {
        this.restApiService = restApiService;
        this.shiftHandsetView = shiftHandsetView;
    }

    @Inject
    public void setupListener() {
        shiftHandsetView.setPresenter(this);
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
    public void sendOldVcode(String tel, String dostr) {
        shiftHandsetView.showLoading();
        SendCodeRequest sendCodeRequest = new SendCodeRequest(tel, dostr);
        Disposable disposable = restApiService.sendCode(sendCodeRequest)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        shiftHandsetView.dismissLoading();
                        shiftHandsetView.oldVcodeSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        shiftHandsetView.dismissLoading();
                        shiftHandsetView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void checkOldCode(String tel, String code, String type) {
        shiftHandsetView.showLoading();
        CheckCodeRequest checkCodeRequest = new CheckCodeRequest(tel, code, type);
        Disposable disposable = restApiService.checkCode(checkCodeRequest)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        shiftHandsetView.dismissLoading();
                        shiftHandsetView.checkOldCodeSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        shiftHandsetView.dismissLoading();
                        shiftHandsetView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void sendNewVCode(String newtel) {
        shiftHandsetView.showLoading();
        SendNewVCodeRequest sendNewVCodeRequest = new SendNewVCodeRequest(newtel);
        Disposable disposable = restApiService.sendNewVcode(sendNewVCodeRequest)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        shiftHandsetView.dismissLoading();
                        shiftHandsetView.newVcodeSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        shiftHandsetView.dismissLoading();
                        shiftHandsetView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void checkNewCode(String newtel, String code) {
        shiftHandsetView.showLoading();
        CheckNewCodeRequest checkNewCodeRequest = new CheckNewCodeRequest(newtel, code);
        Disposable disposable = restApiService.checkNewCode(checkNewCodeRequest)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        shiftHandsetView.dismissLoading();
                        shiftHandsetView.checkNewCodeSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        shiftHandsetView.dismissLoading();
                        shiftHandsetView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
