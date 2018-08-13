package com.purchase.sls.homepage.presenter;


import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.AllCategoriesInfo;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.TokenRequest;
import com.purchase.sls.homepage.HomePageContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class AllCategoriesPresenter implements HomePageContract.AllCategoriesPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private HomePageContract.AllCategoriesView allCategoriesView;

    @Inject
    public AllCategoriesPresenter(RestApiService restApiService, HomePageContract.AllCategoriesView allCategoriesView) {
        this.restApiService = restApiService;
        this.allCategoriesView = allCategoriesView;
    }

    @Inject
    public void setupListener() {
        allCategoriesView.setPresenter(this);
    }

    @Override
    public void getAllCategoriesInfos() {
        allCategoriesView.showLoading();
        TokenRequest tokenRequest=new TokenRequest();
        Disposable disposable = restApiService.getAllCategoriesInfos(tokenRequest)
                .flatMap(new RxRemoteDataParse<List<AllCategoriesInfo>>())
                .compose(new RxSchedulerTransformer<List<AllCategoriesInfo>>())
                .subscribe(new Consumer<List<AllCategoriesInfo>>() {
                    @Override
                    public void accept(List<AllCategoriesInfo> allCategoriesInfos) throws Exception {
                        allCategoriesView.dismissLoading();
                        allCategoriesView.renderAllCategoriesInfos(allCategoriesInfos);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        allCategoriesView.dismissLoading();
                        allCategoriesView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
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
}
