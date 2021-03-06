package com.purchase.sls.homepage.presenter;

import android.text.TextUtils;

import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.ClassifyInfo;
import com.purchase.sls.data.entity.ScreeningListResponse;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.CateidRequest;
import com.purchase.sls.data.request.ScreeningListRequest;
import com.purchase.sls.homepage.HomePageContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/4/23.
 */

public class ScreeningListPresenter implements HomePageContract.ScreeningListPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private HomePageContract.ScreeningListView screeningListView;
    private int currentIndex = 1;  //待接单当前index

    @Inject
    public ScreeningListPresenter(RestApiService restApiService, HomePageContract.ScreeningListView screeningListView) {
        this.restApiService = restApiService;
        this.screeningListView = screeningListView;
    }

    @Inject
    public void setupListener() {
        screeningListView.setPresenter(this);
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
    public void getClassifyInfo(String cateid) {
        CateidRequest cateidRequest = new CateidRequest(cateid);
        Disposable disposable = restApiService.getClassifyInfo(cateidRequest)
                .flatMap(new RxRemoteDataParse<ClassifyInfo>())
                .compose(new RxSchedulerTransformer<ClassifyInfo>())
                .subscribe(new Consumer<ClassifyInfo>() {
                    @Override
                    public void accept(ClassifyInfo classifyInfo) throws Exception {
                        screeningListView.renderClassifyInfo(classifyInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        screeningListView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    /**
     * 获取热门筛选信息
     *
     * @param address
     * @param cid
     * @param sort
     * @param screen
     */
    @Override
    public void getScreeningList(String refreshType, String address, String cid, String sort, String screen, String storename,String location) {
        if (TextUtils.equals("1", refreshType)) {
            screeningListView.showLoading();
        }
        currentIndex = 1;
        ScreeningListRequest screeningListRequest = new ScreeningListRequest(address, cid, sort, String.valueOf(currentIndex), screen, storename,location);
        Disposable disposable = restApiService.getScreeningListInfo(screeningListRequest)
                .flatMap(new RxRemoteDataParse<ScreeningListResponse>())
                .compose(new RxSchedulerTransformer<ScreeningListResponse>())
                .subscribe(new Consumer<ScreeningListResponse>() {
                    @Override
                    public void accept(ScreeningListResponse screeningListResponse) throws Exception {
                        screeningListView.dismissLoading();
                        screeningListView.screeningListInfo(screeningListResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        screeningListView.dismissLoading();
                        screeningListView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getMoreScreeningList(String address, String cid, String sort, String screen, String storename,String location) {
        currentIndex = currentIndex + 1;
        ScreeningListRequest screeningListRequest = new ScreeningListRequest(address, cid, sort, String.valueOf(currentIndex), screen, storename,location);
        Disposable disposable = restApiService.getScreeningListInfo(screeningListRequest)
                .flatMap(new RxRemoteDataParse<ScreeningListResponse>())
                .compose(new RxSchedulerTransformer<ScreeningListResponse>())
                .subscribe(new Consumer<ScreeningListResponse>() {
                    @Override
                    public void accept(ScreeningListResponse screeningListResponse) throws Exception {
                        screeningListView.moreScreeningListInfo(screeningListResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        screeningListView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
