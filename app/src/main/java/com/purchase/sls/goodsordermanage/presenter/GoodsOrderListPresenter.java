package com.purchase.sls.goodsordermanage.presenter;

import android.text.TextUtils;

import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.ActivityOrderListResponse;
import com.purchase.sls.data.entity.GoodsOrderManage;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.TypePageRequest;
import com.purchase.sls.goodsordermanage.GoodsOrderContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/7/9.
 */

public class GoodsOrderListPresenter implements GoodsOrderContract.GoodsOrderListPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private GoodsOrderContract.GoodsOrderListView goodsOrderListView;


    private int allCurrentIndex = 1;      //全部当前index
    private int toPayCurrentIndex = 1;    //待付款当前index
    private int toCollectCurrentIndex = 1; //待收货当前index
    private int completeCurrentIndex = 1; //已完成当前index

    @Inject
    public GoodsOrderListPresenter(RestApiService restApiService, GoodsOrderContract.GoodsOrderListView goodsOrderListView) {
        this.restApiService = restApiService;
        this.goodsOrderListView = goodsOrderListView;
    }

    @Inject
    public void setupListener() {
        goodsOrderListView.setPresenter(this);
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
    public void getGoodOrderList(String refreshType, String type) {
        if (TextUtils.equals("1", refreshType)) {
            goodsOrderListView.showLoading();
        }
        if (TextUtils.equals("-1", type)) {
            allCurrentIndex = 1;
        } else if (TextUtils.equals("0", type)) {
            toPayCurrentIndex = 1;
        } else if (TextUtils.equals("2", type)) {
            toCollectCurrentIndex = 1;
        } else {
            completeCurrentIndex = 1;
        }
        TypePageRequest typePageRequest = new TypePageRequest(type, String.valueOf("1"));
        Disposable disposable = restApiService.getGoodsOrderManage(typePageRequest)
                .flatMap(new RxRemoteDataParse<GoodsOrderManage>())
                .compose(new RxSchedulerTransformer<GoodsOrderManage>())
                .subscribe(new Consumer<GoodsOrderManage>() {
                    @Override
                    public void accept(GoodsOrderManage goodsOrderManage) throws Exception {
                        goodsOrderListView.dismissLoading();
                        goodsOrderListView.render(goodsOrderManage.getGoodsOrderItemInfos());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        goodsOrderListView.dismissLoading();
                        goodsOrderListView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getMoreGoodOrderList(String type) {
        TypePageRequest typePageRequest;
        if (TextUtils.equals("-1", type)) {
            allCurrentIndex = allCurrentIndex + 1;
            typePageRequest = new TypePageRequest(type, String.valueOf(allCurrentIndex));
        } else if (TextUtils.equals("0", type)) {
            toPayCurrentIndex = toPayCurrentIndex + 1;
            typePageRequest = new TypePageRequest(type, String.valueOf(toPayCurrentIndex));
        } else if (TextUtils.equals("2", type)) {
            toCollectCurrentIndex = toCollectCurrentIndex + 1;
            typePageRequest = new TypePageRequest(type, String.valueOf(toCollectCurrentIndex));
        } else {
            completeCurrentIndex = completeCurrentIndex + 1;
            typePageRequest = new TypePageRequest(type, String.valueOf(completeCurrentIndex));
        }
        Disposable disposable = restApiService.getGoodsOrderManage(typePageRequest)
                .flatMap(new RxRemoteDataParse<GoodsOrderManage>())
                .compose(new RxSchedulerTransformer<GoodsOrderManage>())
                .subscribe(new Consumer<GoodsOrderManage>() {
                    @Override
                    public void accept(GoodsOrderManage goodsOrderManage) throws Exception {
                        goodsOrderListView.dismissLoading();
                        goodsOrderListView.renderMore(goodsOrderManage.getGoodsOrderItemInfos());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        goodsOrderListView.dismissLoading();
                        goodsOrderListView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
