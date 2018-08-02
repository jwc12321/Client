package com.purchase.sls.ecvoucher.presenter;

import android.text.TextUtils;

import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.EcVoucherInfo;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.PageRequest;
import com.purchase.sls.ecvoucher.EcVoucherContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/8/2.
 */

public class EcVoucherInfosPresenter implements EcVoucherContract.EcVoucherInfosPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private int currentIndex = 1;  //当前index
    private EcVoucherContract.EcVoucherInfosView ecVoucherInfosView;

    @Inject
    public EcVoucherInfosPresenter(RestApiService restApiService, EcVoucherContract.EcVoucherInfosView ecVoucherInfosView) {
        this.restApiService = restApiService;
        this.ecVoucherInfosView = ecVoucherInfosView;
    }

    @Inject
    public void setupListener() {
        ecVoucherInfosView.setPresenter(this);
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
    public void getEcVoucherInfo(String refreshType) {
        if (TextUtils.equals("1", refreshType)) {
            ecVoucherInfosView.showLoading();
        }
        currentIndex = 1;
        PageRequest pageRequest = new PageRequest(String.valueOf(currentIndex));
        Disposable disposable = restApiService.getEcVoucherInfo(pageRequest)
                .flatMap(new RxRemoteDataParse<EcVoucherInfo>())
                .compose(new RxSchedulerTransformer<EcVoucherInfo>())
                .subscribe(new Consumer<EcVoucherInfo>() {
                    @Override
                    public void accept(EcVoucherInfo ecVoucherInfo) throws Exception {
                        ecVoucherInfosView.dismissLoading();
                        ecVoucherInfosView.renderEcVoucherInfo(ecVoucherInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ecVoucherInfosView.dismissLoading();
                        ecVoucherInfosView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getMoreEcVoucherInfo() {
        currentIndex = currentIndex + 1;
        PageRequest pageRequest = new PageRequest(String.valueOf(currentIndex));
        Disposable disposable = restApiService.getEcVoucherInfo(pageRequest)
                .flatMap(new RxRemoteDataParse<EcVoucherInfo>())
                .compose(new RxSchedulerTransformer<EcVoucherInfo>())
                .subscribe(new Consumer<EcVoucherInfo>() {
                    @Override
                    public void accept(EcVoucherInfo ecVoucherInfo) throws Exception {
                        ecVoucherInfosView.dismissLoading();
                        ecVoucherInfosView.renderMoreEcVoucherInfo(ecVoucherInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ecVoucherInfosView.dismissLoading();
                        ecVoucherInfosView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
