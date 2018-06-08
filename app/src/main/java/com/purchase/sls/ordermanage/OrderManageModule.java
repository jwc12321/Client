package com.purchase.sls.ordermanage;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JWC on 2018/6/6.
 */
@Module
public class OrderManageModule {
    private OrderManageContract.ActivityOrderListView activityOrderListView;
    private OrderManageContract.ActivityDetailInfoView activityDetailInfoView;

    public OrderManageModule(OrderManageContract.ActivityOrderListView activityOrderListView) {
        this.activityOrderListView = activityOrderListView;
    }

    public OrderManageModule(OrderManageContract.ActivityDetailInfoView activityDetailInfoView) {
        this.activityDetailInfoView = activityDetailInfoView;
    }

    @Provides
    OrderManageContract.ActivityOrderListView provideActivityOrderView() {
        return activityOrderListView;
    }

    @Provides
    OrderManageContract.ActivityDetailInfoView provideActivityDetailInfoView(){
        return activityDetailInfoView;
    }
}
