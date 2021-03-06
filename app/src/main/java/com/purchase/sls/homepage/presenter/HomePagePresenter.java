package com.purchase.sls.homepage.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.BannerHotResponse;
import com.purchase.sls.data.entity.ChangeAppInfo;
import com.purchase.sls.data.entity.HNearbyShopsInfo;
import com.purchase.sls.data.entity.LikeStoreResponse;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.BannerHotRequest;
import com.purchase.sls.data.request.CoordinateCityRequest;
import com.purchase.sls.data.request.DetectionVersionRequest;
import com.purchase.sls.data.request.LikeStoreRequest;
import com.purchase.sls.homepage.HomePageContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/4/19.
 */

public class HomePagePresenter implements HomePageContract.HomepagePresenter {
    private HomePageContract.HomepageView homepageView;
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();

    private int currentIndex = 1;  //待接单当前index

    @Inject
    public HomePagePresenter(HomePageContract.HomepageView homepageView, RestApiService restApiService) {
        this.homepageView = homepageView;
        this.restApiService = restApiService;
    }

    @Inject
    public void setupListener() {
        homepageView.setPresenter(this);
    }


    @Override
    public void start() {

    }

    @Override
    public void pause() {

    }


    /**
     * 获取banner和热门数据
     *
     * @param areaname
     */
    @Override
    public void getBannerHotInfo(String refreshType, String areaname) {
        if (TextUtils.equals("1", refreshType) && TextUtils.isEmpty(areaname)) {
            homepageView.showLoading();
        }
        BannerHotRequest bannerHotRequest = new BannerHotRequest(areaname);
        Disposable disposable = restApiService.getBannerHotInfo(bannerHotRequest)
                .flatMap(new RxRemoteDataParse<BannerHotResponse>())
                .compose(new RxSchedulerTransformer<BannerHotResponse>())
                .subscribe(new Consumer<BannerHotResponse>() {
                    @Override
                    public void accept(BannerHotResponse bannerHotResponse) throws Exception {
                        homepageView.dismissLoading();
                        homepageView.bannerHotInfo(bannerHotResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        homepageView.dismissLoading();
                        homepageView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    /**
     * 获取猜你喜欢数据
     */
    @Override
    public void getLikeStore(String areaname) {
        currentIndex = 1;
        LikeStoreRequest likeStoreRequest = new LikeStoreRequest(String.valueOf(currentIndex), areaname);
        Disposable disposable = restApiService.getLikeStoreInfo(likeStoreRequest)
                .flatMap(new RxRemoteDataParse<LikeStoreResponse>())
                .compose(new RxSchedulerTransformer<LikeStoreResponse>())
                .subscribe(new Consumer<LikeStoreResponse>() {
                    @Override
                    public void accept(LikeStoreResponse likeStoreResponse) throws Exception {
                        homepageView.likeStroeInfo(likeStoreResponse.getCollectionStoreInfos());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        homepageView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);

    }

    @Override
    public void getMoreLikeStore(String areaname) {
        currentIndex = currentIndex + 1;
        LikeStoreRequest likeStoreRequest = new LikeStoreRequest(String.valueOf(currentIndex), areaname);
        Disposable disposable = restApiService.getLikeStoreInfo(likeStoreRequest)
                .flatMap(new RxRemoteDataParse<LikeStoreResponse>())
                .compose(new RxSchedulerTransformer<LikeStoreResponse>())
                .subscribe(new Consumer<LikeStoreResponse>() {
                    @Override
                    public void accept(LikeStoreResponse likeStoreResponse) throws Exception {
                        homepageView.moreLikeStroeInfo(likeStoreResponse.getCollectionStoreInfos());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        homepageView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
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
                        homepageView.detectionSuccess(changeAppInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });
        mDisposableList.add(disposable);
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
    public void getHNearbyShopsInfos(String coordinate, String city) {
        CoordinateCityRequest coordinateCityRequest=new CoordinateCityRequest(coordinate,city);
        Disposable disposable=restApiService.getHNearbyShopsInfos(coordinateCityRequest)
                .flatMap(new RxRemoteDataParse<List<HNearbyShopsInfo>>())
                .compose(new RxSchedulerTransformer<List<HNearbyShopsInfo>>())
                .subscribe(new Consumer<List<HNearbyShopsInfo>>() {
                    @Override
                    public void accept(List<HNearbyShopsInfo> hNearbyShopsInfos) throws Exception {
                        homepageView.renderHNearbyShopsInfos(hNearbyShopsInfos);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });
        mDisposableList.add(disposable);
    }
}
