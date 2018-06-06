package com.purchase.sls.energy.presenter;

import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.ActivityInfo;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.SubmitSpikeRequest;
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
        SubmitSpikeRequest submitSpikeRequest=new SubmitSpikeRequest(id,aid);
        Disposable disposable = restApiService.submitSpike(submitSpikeRequest)
                .flatMap(new RxRemoteDataParse<String>())
                .compose(new RxSchedulerTransformer<String>())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String orderNumber) throws Exception {
                        activityDetailView.dismissLoading();
                        activityDetailView.submitSpikeSuccess(orderNumber);
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
