package com.purchase.sls.ordermanage;

/**
 * Created by JWC on 2018/6/6.
 */

import com.purchase.sls.ActivityScope;
import com.purchase.sls.ApplicationComponent;
import com.purchase.sls.ordermanage.ui.ActivityOrderDetailActivity;
import com.purchase.sls.ordermanage.ui.AllActivityOrderFragment;
import com.purchase.sls.ordermanage.ui.ExchangeOrderFragment;
import com.purchase.sls.ordermanage.ui.LotteryOrderFragment;
import com.purchase.sls.ordermanage.ui.SpikeOrderFragment;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {OrderManageModule.class})
public interface OrderManageComponent {
    void inject(AllActivityOrderFragment allActivityOrderFragment);
    void inject(SpikeOrderFragment spikeOrderFragment);
    void inject(ExchangeOrderFragment exchangeOrderFragment);
    void inject(LotteryOrderFragment lotteryOrderFragment);
    void inject(ActivityOrderDetailActivity activityOrderDetailActivity);
}
