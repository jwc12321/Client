package com.purchase.sls.coupon.presenter;

import android.text.TextUtils;

import com.purchase.sls.coupon.CouponContract;
import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.CollectionListResponse;
import com.purchase.sls.data.entity.CouponInfo;
import com.purchase.sls.data.entity.CouponListInfo;
import com.purchase.sls.data.entity.QuanInfo;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.CouponListRequest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/5/3.
 */

public class CouponPresenter implements CouponContract.CouponListPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private CouponContract.CouponListView couponListView;
    private int currentIndex = 1;  //待接单当前index

    @Inject
    public CouponPresenter(RestApiService restApiService, CouponContract.CouponListView couponListView) {
        this.restApiService = restApiService;
        this.couponListView = couponListView;
    }

    @Inject
    public void setupListener() {
        couponListView.setPresenter(this);
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
    public void getCouponList(String refreshType,final String type) {
        if(TextUtils.equals("1",refreshType)){
            couponListView.showLoading();
        }
        currentIndex = 1;
        CouponListRequest couponListRequest = new CouponListRequest(type, String.valueOf(currentIndex));
        Disposable disposable = restApiService.getCouponListInfo(couponListRequest)
                .flatMap(new RxRemoteDataParse<CouponListInfo>())
                .compose(new RxSchedulerTransformer<CouponListInfo>())
                .subscribe(new Consumer<CouponListInfo>() {
                    @Override
                    public void accept(CouponListInfo couponListInfo) throws Exception {
                        couponListView.dismissLoading();
                        if(couponListInfo!=null) {
                            List<CouponInfo> couponInfos;
                            if (TextUtils.equals("0", type)) {
                                CouponInfo couponInfo = new CouponInfo();
                                couponInfo.setAddSc("3");
                                couponInfo.setStatus("0");
                                QuanInfo quanInfo = new QuanInfo();
                                quanInfo.setPrice(couponListInfo.getScItem().getQuannum());
                                quanInfo.setTitle("商城抵金劵");
                                couponInfo.setQuanInfo(quanInfo);
                                couponInfos=couponListInfo.getCouponList().getCouponInfos();
                                couponInfos.add(0,couponInfo);
                                couponListView.render(couponInfos);
                            } else {
                                couponListView.render(couponListInfo.getCouponList().getCouponInfos());
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        couponListView.dismissLoading();
                        couponListView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getMoreCouponList(String type) {
        currentIndex = currentIndex + 1;
        CouponListRequest couponListRequest = new CouponListRequest(type, String.valueOf(currentIndex));
        Disposable disposable = restApiService.getCouponListInfo(couponListRequest)
                .flatMap(new RxRemoteDataParse<CouponListInfo>())
                .compose(new RxSchedulerTransformer<CouponListInfo>())
                .subscribe(new Consumer<CouponListInfo>() {
                    @Override
                    public void accept(CouponListInfo couponListInfo) throws Exception {
                        couponListView.renderMore(couponListInfo.getCouponList().getCouponInfos());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        couponListView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
