package com.purchase.sls.mainframe.presenter;

import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.ChangeAppInfo;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.DetectionVersionRequest;
import com.purchase.sls.mainframe.MainFrameContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/5/28.
 */

public class MainFramePresenter implements MainFrameContract.MainFramePresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private MainFrameContract.MainFrameView mainFrameView;

    @Inject
    public MainFramePresenter(RestApiService restApiService, MainFrameContract.MainFrameView mainFrameView) {
        this.restApiService = restApiService;
        this.mainFrameView = mainFrameView;
    }

    @Inject
    public void setupListener() {
        mainFrameView.setPresenter(this);
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
        DetectionVersionRequest detectionVersionRequest=new DetectionVersionRequest(edition,type);
        Disposable disposable=restApiService.changeApp(detectionVersionRequest)
                .flatMap(new RxRemoteDataParse<ChangeAppInfo>())
                .compose(new RxSchedulerTransformer<ChangeAppInfo>())
                .subscribe(new Consumer<ChangeAppInfo>() {
                    @Override
                    public void accept(ChangeAppInfo changeAppInfo) throws Exception {
                        mainFrameView.detectionSuccess(changeAppInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mainFrameView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
