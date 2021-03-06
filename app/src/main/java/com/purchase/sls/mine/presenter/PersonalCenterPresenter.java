package com.purchase.sls.mine.presenter;

import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.CmIncomeInfo;
import com.purchase.sls.data.entity.CommissionInfo;
import com.purchase.sls.data.entity.Ignore;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.TokenRequest;
import com.purchase.sls.mine.PersonalCenterContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/4/19.
 */

public class PersonalCenterPresenter implements PersonalCenterContract.PersonalCenterPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private PersonalCenterContract.PersonalCenterView personalCenterView;

    @Inject
    public PersonalCenterPresenter(RestApiService restApiService, PersonalCenterContract.PersonalCenterView personalCenterView) {
        this.restApiService = restApiService;
        this.personalCenterView = personalCenterView;
    }

    @Inject
    public void setupListener() {
        personalCenterView.setPresenter(this);
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
    public void getCmIncomeInfo() {
        TokenRequest tokenRequest=new TokenRequest();
        Disposable disposable = restApiService.getCmIncomeInfo(tokenRequest)
                .flatMap(new RxRemoteDataParse<CmIncomeInfo>())
                .compose(new RxSchedulerTransformer<CmIncomeInfo>())
                .subscribe(new Consumer<CmIncomeInfo>() {
                    @Override
                    public void accept(CmIncomeInfo cmIncomeInfo) throws Exception {
                        personalCenterView.dismissLoading();
                        personalCenterView.renderCmIncomeInfo(cmIncomeInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        personalCenterView.dismissLoading();
                        personalCenterView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getCommissionInfo() {
        TokenRequest tokenRequest=new TokenRequest();
        Disposable disposable = restApiService.getCommissionInfo(tokenRequest)
                .flatMap(new RxRemoteDataParse<CommissionInfo>())
                .compose(new RxSchedulerTransformer<CommissionInfo>())
                .subscribe(new Consumer<CommissionInfo>() {
                    @Override
                    public void accept(CommissionInfo commissionInfo) throws Exception {
                        personalCenterView.dismissLoading();
                        personalCenterView.renderCommissionInfo(commissionInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        personalCenterView.dismissLoading();
                        personalCenterView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
