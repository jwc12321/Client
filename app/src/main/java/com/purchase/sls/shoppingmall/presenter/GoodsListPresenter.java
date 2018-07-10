package com.purchase.sls.shoppingmall.presenter;

import android.text.TextUtils;

import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.GoodsItemList;
import com.purchase.sls.data.entity.GoodsParentInfo;
import com.purchase.sls.data.entity.SMBannerInfo;
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
 * Created by JWC on 2018/7/4.
 */

public class GoodsListPresenter implements ShoppingMallContract.GoodsListPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private int currentIndex = 1;
    private ShoppingMallContract.GoodsListView goodsListView;

    @Inject
    public GoodsListPresenter(RestApiService restApiService, ShoppingMallContract.GoodsListView goodsListView) {
        this.restApiService = restApiService;
        this.goodsListView = goodsListView;
    }

    @Inject
    public void setupListener() {
        goodsListView.setPresenter(this);
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
    public void getSMBannerInfo() {
        TokenRequest tokenRequest=new TokenRequest();
        Disposable disposable = restApiService.getSMBannerInfo(tokenRequest)
                .flatMap(new RxRemoteDataParse<List<SMBannerInfo>>())
                .compose(new RxSchedulerTransformer<List<SMBannerInfo>>())
                .subscribe(new Consumer<List<SMBannerInfo>>() {
                    @Override
                    public void accept(List<SMBannerInfo> smBannerInfos) throws Exception {
                        goodsListView.smBannerInfo(smBannerInfos);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        goodsListView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getGoodsParents() {
        goodsListView.showLoading();
        TokenRequest tokenRequest = new TokenRequest();
        Disposable disposable = restApiService.getGoodsParentInfos(tokenRequest)
                .flatMap(new RxRemoteDataParse<List<GoodsParentInfo>>())
                .compose(new RxSchedulerTransformer<List<GoodsParentInfo>>())
                .subscribe(new Consumer<List<GoodsParentInfo>>() {
                    @Override
                    public void accept(List<GoodsParentInfo> goodsParentInfos) throws Exception {
                        goodsListView.renderGoodsParents(goodsParentInfos);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        goodsListView.dismissLoading();
                        goodsListView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    /**
     * @param keyword 搜索
     * @param orderby 排序类型 1销量2价格3券
     * @param order   排序规则 1小到大/ 2大到小
     * @param cate    分类id
     */
    @Override
    public void getGoodsItems(String refreshType, String keyword, String orderby, String order, String cate) {
        if (TextUtils.equals("1", refreshType)) {
            goodsListView.showLoading();
        }
        currentIndex = 1;
        GoodsItemRequest goodsItemRequest = new GoodsItemRequest(keyword, orderby, order, cate, String.valueOf(currentIndex));
        Disposable disposable = restApiService.getGoodsItemList(goodsItemRequest)
                .flatMap(new RxRemoteDataParse<GoodsItemList>())
                .compose(new RxSchedulerTransformer<GoodsItemList>())
                .subscribe(new Consumer<GoodsItemList>() {
                    @Override
                    public void accept(GoodsItemList goodsItemList) throws Exception {
                        goodsListView.dismissLoading();
                        goodsListView.renderGoodsItems(goodsItemList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        goodsListView.dismissLoading();
                        goodsListView.showError(throwable);
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
                        goodsListView.renderMoreGoodsItems(goodsItemList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        goodsListView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
