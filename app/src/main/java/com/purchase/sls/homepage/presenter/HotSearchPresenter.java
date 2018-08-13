package com.purchase.sls.homepage.presenter;

import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.AllCategoriesInfo;
import com.purchase.sls.data.entity.HotSearchInfo;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.TokenRequest;
import com.purchase.sls.homepage.HomePageContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class HotSearchPresenter implements HomePageContract.HotSearchPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private HomePageContract.HotSearchView hotSearchView;

    @Inject
    public HotSearchPresenter(RestApiService restApiService, HomePageContract.HotSearchView hotSearchView) {
        this.restApiService = restApiService;
        this.hotSearchView = hotSearchView;
    }

    @Inject
    public void setupListener() {
        hotSearchView.setPresenter(this);
    }

    @Override
    public void getHotSearchs() {
        hotSearchView.showLoading();
        TokenRequest tokenRequest=new TokenRequest();
        Disposable disposable = restApiService.getHotSearchs(tokenRequest)
                .flatMap(new RxRemoteDataParse<List<HotSearchInfo>>())
                .compose(new RxSchedulerTransformer<List<HotSearchInfo>>())
                .subscribe(new Consumer<List<HotSearchInfo>>() {
                    @Override
                    public void accept(List<HotSearchInfo> hotSearchInfos) throws Exception {
                        hotSearchView.dismissLoading();
                        hotSearchView.renderHotSearchs(hotSearchInfos);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        hotSearchView.dismissLoading();
                        hotSearchView.showError(throwable);
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
