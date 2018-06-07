package com.purchase.sls.ordermanage;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JWC on 2018/6/6.
 */
@Module
public class OrderManageModule {
    private OrderManageContract.ActivityOrderListView activityOrderListView;

    public OrderManageModule(OrderManageContract.ActivityOrderListView activityOrderListView) {
        this.activityOrderListView = activityOrderListView;
    }

    @Provides
    OrderManageContract.ActivityOrderListView provideActivityOrderView() {
        return activityOrderListView;
    }
}
