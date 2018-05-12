package com.purchase.sls.browse.presenter;

import com.purchase.sls.browse.BrowseContract;
import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.BrowseInfo;
import com.purchase.sls.data.entity.CollectionListResponse;
import com.purchase.sls.data.entity.Ignore;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.PageRequest;
import com.purchase.sls.data.request.RemoveBrowseRequest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/5/4.
 */

public class BrowsePresenter implements BrowseContract.BrowsePresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private int currentIndex = 1;  //待接单当前index
    private BrowseContract.BrowseView browseView;

    @Inject
    public BrowsePresenter(RestApiService restApiService, BrowseContract.BrowseView browseView) {
        this.restApiService = restApiService;
        this.browseView = browseView;
    }

    @Inject
    public void setupListener() {
        browseView.setPresenter(this);
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
    public void getBrowseInfo() {
        browseView.showLoading();
        currentIndex = 1;
        PageRequest pageRequest = new PageRequest(String.valueOf(currentIndex));
        Disposable disposable = restApiService.getBrowseInfo(pageRequest)
                .flatMap(new RxRemoteDataParse<BrowseInfo>())
                .compose(new RxSchedulerTransformer<BrowseInfo>())
                .subscribe(new Consumer<BrowseInfo>() {
                    @Override
                    public void accept(BrowseInfo browseInfo) throws Exception {
                        browseView.dismissLoading();
                        browseView.renderBrowseInfo(browseInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        browseView.dismissLoading();
                        browseView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getMoreBrowseInfo() {
        browseView.showLoading();
        currentIndex = currentIndex + 1;
        PageRequest pageRequest = new PageRequest(String.valueOf(currentIndex));
        Disposable disposable = restApiService.getBrowseInfo(pageRequest)
                .flatMap(new RxRemoteDataParse<BrowseInfo>())
                .compose(new RxSchedulerTransformer<BrowseInfo>())
                .subscribe(new Consumer<BrowseInfo>() {
                    @Override
                    public void accept(BrowseInfo browseInfo) throws Exception {
                        browseView.dismissLoading();
                        browseView.renderMoreBrowseInfo(browseInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        browseView.dismissLoading();
                        browseView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void removeBrowse(String[] idArray) {
        browseView.showLoading();
        RemoveBrowseRequest removeBrowseRequest = new RemoveBrowseRequest(idArray);
        Disposable disposable = restApiService.removeBrowse(removeBrowseRequest)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        browseView.dismissLoading();
                        browseView.removeSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        browseView.dismissLoading();
                        browseView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
