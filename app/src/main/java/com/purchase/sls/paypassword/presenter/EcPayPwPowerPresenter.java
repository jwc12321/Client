package com.purchase.sls.paypassword.presenter;

import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.ActivityOrderDetailInfo;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.PaysecKillRequest;
import com.purchase.sls.paypassword.PayPasswordContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/8/22.
 */

public class EcPayPwPowerPresenter implements PayPasswordContract.EcPayPwPowerPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private PayPasswordContract.EcPayPwPowerView ecPayPwPowerView;

    @Inject
    public EcPayPwPowerPresenter(RestApiService restApiService, PayPasswordContract.EcPayPwPowerView ecPayPwPowerView) {
        this.restApiService = restApiService;
        this.ecPayPwPowerView = ecPayPwPowerView;
    }

    @Inject
    public void setupListener() {
        ecPayPwPowerView.setPresenter(this);
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
    public void paysecKill(String paypassword, String id, String aid) {
        ecPayPwPowerView.showLoading();
        PaysecKillRequest paysecKillRequest=new PaysecKillRequest(paypassword,id,aid);
        Disposable disposable = restApiService.paysecKill(paysecKillRequest)
                .flatMap(new RxRemoteDataParse<ActivityOrderDetailInfo>())
                .compose(new RxSchedulerTransformer<ActivityOrderDetailInfo>())
                .subscribe(new Consumer<ActivityOrderDetailInfo>() {
                    @Override
                    public void accept(ActivityOrderDetailInfo activityOrderDetailInfo) throws Exception {
                        ecPayPwPowerView.dismissLoading();
                        ecPayPwPowerView.paySuccess(activityOrderDetailInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ecPayPwPowerView.dismissLoading();
                        ecPayPwPowerView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void paydrawOrder(String paypassword, String id, String aid) {
        ecPayPwPowerView.showLoading();
        PaysecKillRequest paysecKillRequest=new PaysecKillRequest(paypassword,id,aid);
        Disposable disposable = restApiService.paydrawOrder(paysecKillRequest)
                .flatMap(new RxRemoteDataParse<ActivityOrderDetailInfo>())
                .compose(new RxSchedulerTransformer<ActivityOrderDetailInfo>())
                .subscribe(new Consumer<ActivityOrderDetailInfo>() {
                    @Override
                    public void accept(ActivityOrderDetailInfo activityOrderDetailInfo) throws Exception {
                        ecPayPwPowerView.dismissLoading();
                        ecPayPwPowerView.paySuccess(activityOrderDetailInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ecPayPwPowerView.dismissLoading();
                        ecPayPwPowerView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
