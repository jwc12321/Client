package com.purchase.sls.goodsordermanage;

/**
 * Created by JWC on 2018/7/9.
 */

import com.purchase.sls.ActivityScope;
import com.purchase.sls.ApplicationComponent;
import com.purchase.sls.goodsordermanage.ui.AllGoodsOrderFragment;
import com.purchase.sls.goodsordermanage.ui.CompleteOrderFragment;
import com.purchase.sls.goodsordermanage.ui.GoodsOrderDetalActivity;
import com.purchase.sls.goodsordermanage.ui.OrderPayActivity;
import com.purchase.sls.goodsordermanage.ui.ToCollectOrderFragment;
import com.purchase.sls.goodsordermanage.ui.ToPayOrderFragment;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {GoodsOrderModule.class})
public interface GoodsOrderComponent {
    void inject(AllGoodsOrderFragment allGoodsOrderFragment);
    void inject(ToPayOrderFragment toPayOrderFragment);
    void inject(ToCollectOrderFragment toCollectOrderFragment);
    void inject(CompleteOrderFragment completeOrderFragment);
    void inject(GoodsOrderDetalActivity goodsOrderDetalActivity);
    void inject(OrderPayActivity orderPayActivity);
}
