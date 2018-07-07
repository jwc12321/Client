package com.purchase.sls.shoppingmall;

/**
 * Created by JWC on 2018/4/19.
 */

import com.purchase.sls.ActivityScope;
import com.purchase.sls.ApplicationComponent;
import com.purchase.sls.shoppingmall.adapter.GoodsSearchItemAdapter;
import com.purchase.sls.shoppingmall.ui.FillInOrderActivity;
import com.purchase.sls.shoppingmall.ui.GoodsDetailActivity;
import com.purchase.sls.shoppingmall.ui.GoodsSearchActivity;
import com.purchase.sls.shoppingmall.ui.GoodsSearchItemActivity;
import com.purchase.sls.shoppingmall.ui.ShoppingCartActivity;
import com.purchase.sls.shoppingmall.ui.ShoppingMallFragment;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {ShoppingMallModule.class})
public interface ShoppingMallComponent {
    void inject(ShoppingMallFragment shoppingMallFragment);
    void inject(GoodsDetailActivity goodsDetailActivity);
    void inject(GoodsSearchItemActivity goodsSearchItemActivity);
    void inject(ShoppingCartActivity shoppingCartActivity);
    void inject(FillInOrderActivity fillInOrderActivity);
}
