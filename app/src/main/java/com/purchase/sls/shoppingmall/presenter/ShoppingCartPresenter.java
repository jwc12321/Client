package com.purchase.sls.shoppingmall.presenter;

import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.GoodsOrderList;
import com.purchase.sls.data.entity.Ignore;
import com.purchase.sls.data.entity.ShoppingCartInfo;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.CartidRequest;
import com.purchase.sls.data.request.IdRequest;
import com.purchase.sls.data.request.TokenRequest;
import com.purchase.sls.shoppingmall.ShoppingMallContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/7/7.
 */

public class ShoppingCartPresenter implements ShoppingMallContract.ShoppingCartPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private ShoppingMallContract.ShoppingCartView shoppingCartView;

    @Inject
    public ShoppingCartPresenter(RestApiService restApiService, ShoppingMallContract.ShoppingCartView shoppingCartView) {
        this.restApiService = restApiService;
        this.shoppingCartView = shoppingCartView;
    }

    @Inject
    public void setupListener() {
        shoppingCartView.setPresenter(this);
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
    public void getShoppingCartList() {
        shoppingCartView.showLoading();
        TokenRequest tokenRequest = new TokenRequest();
        Disposable disposable = restApiService.getShoppingCartList(tokenRequest)
                .flatMap(new RxRemoteDataParse<List<ShoppingCartInfo>>())
                .compose(new RxSchedulerTransformer<List<ShoppingCartInfo>>())
                .subscribe(new Consumer<List<ShoppingCartInfo>>() {
                    @Override
                    public void accept(List<ShoppingCartInfo> shoppingCartInfos) throws Exception {
                        shoppingCartView.dismissLoading();
                        shoppingCartView.renderShoppingCartList(shoppingCartInfos);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        shoppingCartView.dismissLoading();
                        shoppingCartView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void orderShopCart(String cartid, String num) {
        CartidRequest cartidRequest = new CartidRequest(cartid,num);
        Disposable disposable = restApiService.orderShopCart(cartidRequest)
                .flatMap(new RxRemoteDataParse<GoodsOrderList>())
                .compose(new RxSchedulerTransformer<GoodsOrderList>())
                .subscribe(new Consumer<GoodsOrderList>() {
                    @Override
                    public void accept(GoodsOrderList goodsOrderList) throws Exception {
                        shoppingCartView.dismissLoading();
                        shoppingCartView.orderShopCartSuccess(goodsOrderList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        shoppingCartView.dismissLoading();
                        shoppingCartView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void deleteshopCart(String id) {
        IdRequest idRequest = new IdRequest(id);
        Disposable disposable = restApiService.deleteShopCart(idRequest)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        shoppingCartView.dismissLoading();
                        shoppingCartView.deleteshopCartSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        shoppingCartView.dismissLoading();
                        shoppingCartView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
