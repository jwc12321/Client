package com.purchase.sls.shopdetailbuy;

/**
 * Created by JWC on 2018/4/24.
 */

import com.purchase.sls.ActivityScope;
import com.purchase.sls.ApplicationComponent;
import com.purchase.sls.shopdetailbuy.ui.PaySuccessActivity;
import com.purchase.sls.shopdetailbuy.ui.PaymentOrderActivity;
import com.purchase.sls.shopdetailbuy.ui.ShopDetailActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {ShopDetailBuyModule.class})
public interface ShopDetailBuyComponent {
    void inject(ShopDetailActivity shopDetailActivity);
    void inject(PaymentOrderActivity paymentOrderActivity);
    void inject(PaySuccessActivity paySuccessActivity);

}
