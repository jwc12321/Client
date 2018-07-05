package com.purchase.sls.shoppingmall.presenter;

import android.text.TextUtils;

import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.GoodsItemList;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.GoodsItemRequest;
import com.purchase.sls.shoppingmall.ShoppingMallContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/7/5.
 */

public class GoodsSearchPresenter implements ShoppingMallContract.GoodsSearchPresenter {

    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private int currentIndex = 1;
    private ShoppingMallContract.GoodsSearchView goodsSearchView;

    @Inject
    public GoodsSearchPresenter(RestApiService restApiService, ShoppingMallContract.GoodsSearchView goodsSearchView) {
        this.restApiService = restApiService;
        this.goodsSearchView = goodsSearchView;
    }

    @Inject
    public void setupListener() {
        goodsSearchView.setPresenter(this);
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
    public void getGoodsItems(String refreshType, String keyword, String orderby, String order, String cate) {
        if (TextUtils.equals("1", refreshType)) {
            goodsSearchView.showLoading();
        }
        currentIndex = 1;
        GoodsItemRequest goodsItemRequest = new GoodsItemRequest(keyword, orderby, order, cate, String.valueOf(currentIndex));
        Disposable disposable = restApiService.getGoodsItemList(goodsItemRequest)
                .flatMap(new RxRemoteDataParse<GoodsItemList>())
                .compose(new RxSchedulerTransformer<GoodsItemList>())
                .subscribe(new Consumer<GoodsItemList>() {
                    @Override
                    public void accept(GoodsItemList goodsItemList) throws Exception {
                        goodsSearchView.dismissLoading();
                        goodsSearchView.renderGoodsItems(goodsItemList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        goodsSearchView.dismissLoading();
                        goodsSearchView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getMoreGoodsItems(String keyword, String orderby, String order, String cate) {
        currentIndex = currentIndex + 1;
        GoodsItemRequest goodsItemRequest = new GoodsItemRequest(keyword, orderby, order, cate, String.valueOf(currentIndex));
        Disposable disposable = restApiService.getGoodsItemList(goodsItemRequest)
                .flatMap(new RxRemoteDataParse<GoodsItemList>())
                .compose(new RxSchedulerTransformer<GoodsItemList>())
                .subscribe(new Consumer<GoodsItemList>() {
                    @Override
                    public void accept(GoodsItemList goodsItemList) throws Exception {
                        goodsSearchView.renderMoreGoodsItems(goodsItemList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        goodsSearchView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
