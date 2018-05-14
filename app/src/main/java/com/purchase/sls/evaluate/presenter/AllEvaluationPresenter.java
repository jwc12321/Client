package com.purchase.sls.evaluate.presenter;

import android.text.TextUtils;

import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.AllEvaluationInfo;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.StoreIdPageRequest;
import com.purchase.sls.evaluate.EvaluateContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/5/5.
 */

public class AllEvaluationPresenter implements EvaluateContract.AllEvaluationPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private int currentIndex = 1;  //待接单当前index
    private EvaluateContract.AllEvaluationView allEvaluationView;

    @Inject
    public AllEvaluationPresenter(RestApiService restApiService, EvaluateContract.AllEvaluationView allEvaluationView) {
        this.restApiService = restApiService;
        this.allEvaluationView = allEvaluationView;
    }

    @Inject
    public void setupListener() {
        allEvaluationView.setPresenter(this);
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
    public void getAllEvaluation(String refreshType,String storeId) {
        if(TextUtils.equals("1",refreshType)){
            allEvaluationView.showLoading();
        }
        currentIndex=1;
        StoreIdPageRequest storeIdPageRequest=new StoreIdPageRequest(String.valueOf(currentIndex),storeId);
        Disposable disposable=restApiService.getAllEvaluation(storeIdPageRequest)
                .flatMap(new RxRemoteDataParse<AllEvaluationInfo>())
                .compose(new RxSchedulerTransformer<AllEvaluationInfo>())
                .subscribe(new Consumer<AllEvaluationInfo>() {
                    @Override
                    public void accept(AllEvaluationInfo allEvaluationInfo) throws Exception {
                        allEvaluationView.dismissLoading();
                        allEvaluationView.renderAllEvaluation(allEvaluationInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        allEvaluationView.dismissLoading();
                        allEvaluationView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getMoreAllEvaluation(String storeId) {
        allEvaluationView.showLoading();
        currentIndex=currentIndex+1;
        StoreIdPageRequest storeIdPageRequest=new StoreIdPageRequest(String.valueOf(currentIndex),storeId);
        Disposable disposable=restApiService.getAllEvaluation(storeIdPageRequest)
                .flatMap(new RxRemoteDataParse<AllEvaluationInfo>())
                .compose(new RxSchedulerTransformer<AllEvaluationInfo>())
                .subscribe(new Consumer<AllEvaluationInfo>() {
                    @Override
                    public void accept(AllEvaluationInfo allEvaluationInfo) throws Exception {
                        allEvaluationView.dismissLoading();
                        allEvaluationView.renderMoreAllEvaluation(allEvaluationInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        allEvaluationView.dismissLoading();
                        allEvaluationView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
