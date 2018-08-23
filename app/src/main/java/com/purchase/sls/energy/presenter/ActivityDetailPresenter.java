package com.purchase.sls.energy.presenter;

import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.ActivityOrderDetailInfo;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.PaysecKillRequest;
import com.purchase.sls.data.request.SubmitSpikeRequest;
import com.purchase.sls.data.request.TokenRequest;
import com.purchase.sls.energy.EnergyContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/6/6.
 */

public class ActivityDetailPresenter implements EnergyContract.ActivityDetailPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private EnergyContract.ActivityDetailView activityDetailView;

    @Inject
    public ActivityDetailPresenter(RestApiService restApiService, EnergyContract.ActivityDetailView activityDetailView) {
        this.restApiService = restApiService;
        this.activityDetailView = activityDetailView;
    }

    @Inject
    public void setupListener() {
        activityDetailView.setPresenter(this);
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
    public void submitSpike(String id, String aid) {
        activityDetailView.showLoading();
        SubmitSpikeRequest submitSpikeRequest = new SubmitSpikeRequest(id, aid);
        Disposable disposable = restApiService.submitSpike(submitSpikeRequest)
                .flatMap(new RxRemoteDataParse<ActivityOrderDetailInfo>())
                .compose(new RxSchedulerTransformer<ActivityOrderDetailInfo>())
                .subscribe(new Consumer<ActivityOrderDetailInfo>() {
                    @Override
                    public void accept(ActivityOrderDetailInfo activityOrderDetailInfo) throws Exception {
                        activityDetailView.dismissLoading();
                        activityDetailView.submitSpikeSuccess(activityOrderDetailInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        activityDetailView.dismissLoading();
                        activityDetailView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void submitLottery(String id, String aid) {
        activityDetailView.showLoading();
        SubmitSpikeRequest submitSpikeRequest = new SubmitSpikeRequest(id, aid);
        Disposable disposable = restApiService.submitLottery(submitSpikeRequest)
                .flatMap(new RxRemoteDataParse<ActivityOrderDetailInfo>())
                .compose(new RxSchedulerTransformer<ActivityOrderDetailInfo>())
                .subscribe(new Consumer<ActivityOrderDetailInfo>() {
                    @Override
                    public void accept(ActivityOrderDetailInfo activityOrderDetailInfo) throws Exception {
                        activityDetailView.dismissLoading();
                        activityDetailView.submitLotterySuccess(activityOrderDetailInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        activityDetailView.dismissLoading();
                        activityDetailView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    //是否设置了支付密码
    @Override
    public void isSetUpPayPw() {
        activityDetailView.showLoading();
        TokenRequest tokenRequest=new TokenRequest();
        Disposable disposable=restApiService.isSetUpPayPw(tokenRequest)
                .flatMap(new RxRemoteDataParse<String>())
                .compose(new RxSchedulerTransformer<String>())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String string) throws Exception {
                        activityDetailView.dismissLoading();
                        activityDetailView.renderIsSetUpPayPw(string);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        activityDetailView.dismissLoading();
                        activityDetailView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void paysecKill(String paypassword, String id, String aid) {
        activityDetailView.showLoading();
        PaysecKillRequest paysecKillRequest=new PaysecKillRequest(paypassword,id,aid);
        Disposable disposable = restApiService.paysecKill(paysecKillRequest)
                .flatMap(new RxRemoteDataParse<ActivityOrderDetailInfo>())
                .compose(new RxSchedulerTransformer<ActivityOrderDetailInfo>())
                .subscribe(new Consumer<ActivityOrderDetailInfo>() {
                    @Override
                    public void accept(ActivityOrderDetailInfo activityOrderDetailInfo) throws Exception {
                        activityDetailView.dismissLoading();
                        activityDetailView.paySuccess(activityOrderDetailInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        activityDetailView.dismissLoading();
                        activityDetailView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void paydrawOrder(String paypassword, String id, String aid) {
        activityDetailView.showLoading();
        PaysecKillRequest paysecKillRequest=new PaysecKillRequest(paypassword,id,aid);
        Disposable disposable = restApiService.paydrawOrder(paysecKillRequest)
                .flatMap(new RxRemoteDataParse<ActivityOrderDetailInfo>())
                .compose(new RxSchedulerTransformer<ActivityOrderDetailInfo>())
                .subscribe(new Consumer<ActivityOrderDetailInfo>() {
                    @Override
                    public void accept(ActivityOrderDetailInfo activityOrderDetailInfo) throws Exception {
                        activityDetailView.dismissLoading();
                        activityDetailView.paySuccess(activityOrderDetailInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        activityDetailView.dismissLoading();
                        activityDetailView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
