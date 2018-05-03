package com.purchase.sls.coupon;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JWC on 2018/5/3.
 */

@Module
public class CouponModule {
    private CouponContract.CouponListView couponListView;

    public CouponModule(CouponContract.CouponListView couponListView) {
        this.couponListView = couponListView;
    }
    @Provides
    CouponContract.CouponListView provideCouponView(){
        return couponListView;
    }
}
