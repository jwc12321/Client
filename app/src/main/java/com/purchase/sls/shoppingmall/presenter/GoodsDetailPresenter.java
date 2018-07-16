package com.purchase.sls.shoppingmall.presenter;

import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.GoodsDetailInfo;
import com.purchase.sls.data.entity.GoodsOrderList;
import com.purchase.sls.data.entity.Ignore;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.AddToCartRequest;
import com.purchase.sls.data.request.GoodsidRequest;
import com.purchase.sls.data.request.PurchaseGoodsRequest;
import com.purchase.sls.shoppingmall.ShoppingMallContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/7/4.
 */

public class GoodsDetailPresenter implements ShoppingMallContract.GoodsDetailPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private ShoppingMallContract.GoodsDetailView goodsDetailView;

    @Inject
    public GoodsDetailPresenter(RestApiService restApiService, ShoppingMallContract.GoodsDetailView goodsDetailView) {
        this.restApiService = restApiService;
        this.goodsDetailView = goodsDetailView;
    }

    @Inject
    public void setupListener() {
        goodsDetailView.setPresenter(this);
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
    public void getGoodsDetail(String goodsid) {
        goodsDetailView.showLoading();
        GoodsidRequest goodsidRequest=new GoodsidRequest(goodsid);
        Disposable disposable = restApiService.getGoodsDetailInfo(goodsidRequest)
                .flatMap(new RxRemoteDataParse<GoodsDetailInfo>())
                .compose(new RxSchedulerTransformer<GoodsDetailInfo>())
                .subscribe(new Consumer<GoodsDetailInfo>() {
                    @Override
                    public void accept(GoodsDetailInfo goodsDetailInfo) throws Exception {
                        goodsDetailView.dismissLoading();
                        goodsDetailView.renderGoodsDetail(goodsDetailInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        goodsDetailView.dismissLoading();
                        goodsDetailView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void addToCart(String id, String taobaoid, String sku, String num, String skuinfo, String quan, String tjprice, String quan_url) {
        goodsDetailView.showLoading();
        AddToCartRequest addToCartRequest=new AddToCartRequest(id,taobaoid,sku,num,skuinfo,quan,tjprice,quan_url);
        Disposable disposable = restApiService.addToCart(addToCartRequest)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        goodsDetailView.dismissLoading();
                        goodsDetailView.addToCartSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        goodsDetailView.dismissLoading();
                        goodsDetailView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void purchaseGoods(PurchaseGoodsRequest purchaseGoodsRequest) {
        goodsDetailView.showLoading();
        Disposable disposable = restApiService.purchaseGoods(purchaseGoodsRequest)
                .flatMap(new RxRemoteDataParse<GoodsOrderList>())
                .compose(new RxSchedulerTransformer<GoodsOrderList>())
                .subscribe(new Consumer<GoodsOrderList>() {
                    @Override
                    public void accept(GoodsOrderList goodsOrderList) throws Exception {
                        goodsDetailView.dismissLoading();
                        goodsDetailView.purchaseGoodsSuccess(goodsOrderList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        goodsDetailView.dismissLoading();
                        goodsDetailView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
