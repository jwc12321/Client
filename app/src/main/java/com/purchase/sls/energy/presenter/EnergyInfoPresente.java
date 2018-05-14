package com.purchase.sls.energy.presenter;

import android.text.TextUtils;

import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.CollectionListResponse;
import com.purchase.sls.data.entity.EnergyInfo;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.EnergyInfoRequest;
import com.purchase.sls.energy.EnergyContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/5/3.
 */

public class EnergyInfoPresente implements EnergyContract.EnergyInfoPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private int currentIndex = 1;  //待接单当前index
    private EnergyContract.EnergyInfoView energyInfoView;

    @Inject
    public EnergyInfoPresente(RestApiService restApiService, EnergyContract.EnergyInfoView energyInfoView) {
        this.restApiService = restApiService;
        this.energyInfoView = energyInfoView;
    }

    @Inject
    public void setupListener() {
        energyInfoView.setPresenter(this);
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
    public void getEnergyInfo(String refreshType,String pool) {
        if(TextUtils.equals("1",refreshType)){
            energyInfoView.showLoading();
        }
        currentIndex = 1;
        EnergyInfoRequest energyInfoRequest = new EnergyInfoRequest(pool, String.valueOf(currentIndex));
        Disposable disposable = restApiService.getEnergyInfo(energyInfoRequest)
                .flatMap(new RxRemoteDataParse<EnergyInfo>())
                .compose(new RxSchedulerTransformer<EnergyInfo>())
                .subscribe(new Consumer<EnergyInfo>() {
                    @Override
                    public void accept(EnergyInfo energyInfo) throws Exception {
                        energyInfoView.dismissLoading();
                        energyInfoView.renderEnergyInfo(energyInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        energyInfoView.dismissLoading();
                        energyInfoView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getMoreEnergyInfo(String pool) {
        currentIndex = currentIndex + 1;
        EnergyInfoRequest energyInfoRequest = new EnergyInfoRequest(pool, String.valueOf(currentIndex));
        Disposable disposable = restApiService.getEnergyInfo(energyInfoRequest)
                .flatMap(new RxRemoteDataParse<EnergyInfo>())
                .compose(new RxSchedulerTransformer<EnergyInfo>())
                .subscribe(new Consumer<EnergyInfo>() {
                    @Override
                    public void accept(EnergyInfo energyInfo) throws Exception {
                        energyInfoView.renderMoreEnergyInfo(energyInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        energyInfoView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
