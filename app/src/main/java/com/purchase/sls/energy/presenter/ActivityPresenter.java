package com.purchase.sls.energy.presenter;

import android.text.TextUtils;

import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.ActivityInfo;
import com.purchase.sls.data.entity.EnergyInfo;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.EnergyInfoRequest;
import com.purchase.sls.data.request.TypeRequest;
import com.purchase.sls.energy.EnergyContract;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/6/4.
 */

public class ActivityPresenter implements EnergyContract.ActivityPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private EnergyContract.ActivityView activityView;

    @Inject
    public ActivityPresenter(RestApiService restApiService, EnergyContract.ActivityView activityView) {
        this.restApiService = restApiService;
        this.activityView = activityView;
    }

    @Inject
    public void setupListener() {
        activityView.setPresenter(this);
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

    /**
     * @param type：1，秒杀 2：兑换 3：抽奖
     */
    @Override
    public void getActivitys(String refreshType, String type) {
        if (TextUtils.equals("1", refreshType)) {
            activityView.showLoading();
        }
        TypeRequest typeRequest = new TypeRequest(type);
        Disposable disposable = restApiService.getActivityInfos(typeRequest)
                .flatMap(new RxRemoteDataParse<List<ActivityInfo>>())
                .compose(new RxSchedulerTransformer<List<ActivityInfo>>())
                .subscribe(new Consumer<List<ActivityInfo>>() {
                    @Override
                    public void accept(List<ActivityInfo> activityInfos) throws Exception {
                        activityView.dismissLoading();
                        activityView.renderActivitys(activityInfos);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        activityView.dismissLoading();
                        activityView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getEnergyInfo(String pool) {
        EnergyInfoRequest energyInfoRequest = new EnergyInfoRequest(pool, String.valueOf(1));
        Disposable disposable = restApiService.getEnergyInfo(energyInfoRequest)
                .flatMap(new RxRemoteDataParse<EnergyInfo>())
                .compose(new RxSchedulerTransformer<EnergyInfo>())
                .subscribe(new Consumer<EnergyInfo>() {
                    @Override
                    public void accept(EnergyInfo energyInfo) throws Exception {
                        activityView.renderEnergyInfo(energyInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        activityView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
