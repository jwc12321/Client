package com.purchase.sls.applyvip.presenter;

import com.purchase.sls.applyvip.ApplyVipContract;
import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.AddressInfo;
import com.purchase.sls.data.entity.Ignore;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.ApplyVipRequest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/11/27.
 */

public class ApplyVipPresenter implements ApplyVipContract.ApplyVipPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private ApplyVipContract.ApplyVipView applyVipView;

    @Inject
    public ApplyVipPresenter(RestApiService restApiService, ApplyVipContract.ApplyVipView applyVipView) {
        this.restApiService = restApiService;
        this.applyVipView = applyVipView;
    }


    @Inject
    public void setupListener() {
        applyVipView.setPresenter(this);
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
    public void applyVip(String idcard, String realname) {
        applyVipView.showLoading();
        ApplyVipRequest applyVipRequest=new ApplyVipRequest(idcard,realname);
        Disposable disposable = restApiService.applyVip(applyVipRequest)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        applyVipView.dismissLoading();
                        applyVipView.applyVipSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        applyVipView.dismissLoading();
                        applyVipView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
