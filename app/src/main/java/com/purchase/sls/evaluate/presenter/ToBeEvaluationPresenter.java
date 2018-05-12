package com.purchase.sls.evaluate.presenter;

import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.ToBeEvaluationInfo;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.PageRequest;
import com.purchase.sls.evaluate.EvaluateContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/5/5.
 */

public class ToBeEvaluationPresenter implements EvaluateContract.ToBeEvaluationPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private int currentIndex = 1;  //待接单当前index
    private EvaluateContract.ToBeEvaluationView toBeEvaluationView;

    @Inject
    public ToBeEvaluationPresenter(RestApiService restApiService, EvaluateContract.ToBeEvaluationView toBeEvaluationView) {
        this.restApiService = restApiService;
        this.toBeEvaluationView = toBeEvaluationView;
    }

    @Inject
    public void setupListener() {
        toBeEvaluationView.setPresenter(this);
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
    public void getToBeEvaluation() {
        toBeEvaluationView.showLoading();
        currentIndex = 1;
        PageRequest pageRequest = new PageRequest(String.valueOf(currentIndex));
        Disposable disposable = restApiService.getToBeEvaluation(pageRequest)
                .flatMap(new RxRemoteDataParse<ToBeEvaluationInfo>())
                .compose(new RxSchedulerTransformer<ToBeEvaluationInfo>())
                .subscribe(new Consumer<ToBeEvaluationInfo>() {
                    @Override
                    public void accept(ToBeEvaluationInfo toBeEvaluationInfo) throws Exception {
                        toBeEvaluationView.dismissLoading();
                        toBeEvaluationView.renderToBeEvaluation(toBeEvaluationInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        toBeEvaluationView.dismissLoading();
                        toBeEvaluationView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);

    }

    @Override
    public void getMoreToBeEvaluation() {
        toBeEvaluationView.showLoading();
        currentIndex = currentIndex + 1;
        PageRequest pageRequest = new PageRequest(String.valueOf(currentIndex));
        Disposable disposable = restApiService.getToBeEvaluation(pageRequest)
                .flatMap(new RxRemoteDataParse<ToBeEvaluationInfo>())
                .compose(new RxSchedulerTransformer<ToBeEvaluationInfo>())
                .subscribe(new Consumer<ToBeEvaluationInfo>() {
                    @Override
                    public void accept(ToBeEvaluationInfo toBeEvaluationInfo) throws Exception {
                        toBeEvaluationView.dismissLoading();
                        toBeEvaluationView.renderMoreToBeEvaluation(toBeEvaluationInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        toBeEvaluationView.dismissLoading();
                        toBeEvaluationView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
