package com.purchase.sls.shoppingmall.presenter;

import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.GoodsItemInfo;
import com.purchase.sls.data.entity.GoodsItemList;
import com.purchase.sls.data.entity.MallBannerInfo;
import com.purchase.sls.data.entity.MallCategoryInfo;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.GoodsItemRequest;
import com.purchase.sls.data.request.TokenRequest;
import com.purchase.sls.shoppingmall.ShoppingMallContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2018/12/11.
 */

public class ShoppingMallSPresenter implements ShoppingMallContract.ShoppingMallSPresenter{
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private ShoppingMallContract.ShoppingMallSView shoppingMallSView;
    private int currentIndex = 1;

    @Inject
    public ShoppingMallSPresenter(RestApiService restApiService, ShoppingMallContract.ShoppingMallSView shoppingMallSView) {
        this.restApiService = restApiService;
        this.shoppingMallSView = shoppingMallSView;
    }
    @Inject
    public void setupListener() {
        shoppingMallSView.setPresenter(this);
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
    public void getMallBanner() {
        TokenRequest tokenRequest=new TokenRequest();
        Disposable disposable = restApiService.getMallBannerInfo(tokenRequest)
                .flatMap(new RxRemoteDataParse<List<MallBannerInfo>>())
                .compose(new RxSchedulerTransformer<List<MallBannerInfo>>())
                .subscribe(new Consumer<List<MallBannerInfo>>() {
                    @Override
                    public void accept(List<MallBannerInfo> mallBannerInfos) throws Exception {
                        shoppingMallSView.renderMallBanner(mallBannerInfos);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        shoppingMallSView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getMallCategory() {
        TokenRequest tokenRequest=new TokenRequest();
        Disposable disposable = restApiService.getMallCategoryInfo(tokenRequest)
                .flatMap(new RxRemoteDataParse<List<MallCategoryInfo>>())
                .compose(new RxSchedulerTransformer<List<MallCategoryInfo>>())
                .subscribe(new Consumer<List<MallCategoryInfo>>() {
                    @Override
                    public void accept(List<MallCategoryInfo> mallCategoryInfos) throws Exception {
                        shoppingMallSView.renderMallCategory(mallCategoryInfos);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        shoppingMallSView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getGoodsItemInfo() {
        currentIndex = 1;
        TokenRequest tokenRequest = new TokenRequest();
        Disposable disposable = restApiService.getRdGoodsItemInfo(tokenRequest)
                .flatMap(new RxRemoteDataParse<GoodsItemList>())
                .compose(new RxSchedulerTransformer<GoodsItemList>())
                .subscribe(new Consumer<GoodsItemList>() {
                    @Override
                    public void accept(GoodsItemList goodsItemList) throws Exception {
                        shoppingMallSView.renderRdGoodsItems(goodsItemList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        shoppingMallSView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getMoreGoodsItemInfo() {
        currentIndex = currentIndex+1;
        TokenRequest tokenRequest = new TokenRequest();
        Disposable disposable = restApiService.getRdGoodsItemInfo(tokenRequest)
                .flatMap(new RxRemoteDataParse<GoodsItemList>())
                .compose(new RxSchedulerTransformer<GoodsItemList>())
                .subscribe(new Consumer<GoodsItemList>() {
                    @Override
                    public void accept(GoodsItemList goodsItemList) throws Exception {
                        shoppingMallSView.renderMoreRdGoodsItems(goodsItemList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        shoppingMallSView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
