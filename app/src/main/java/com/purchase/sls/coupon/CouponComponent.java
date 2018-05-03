package com.purchase.sls.coupon;

/**
 * Created by JWC on 2018/5/3.
 */

import com.purchase.sls.ActivityScope;
import com.purchase.sls.ApplicationComponent;
import com.purchase.sls.coupon.ui.AvailableCouponFragment;
import com.purchase.sls.coupon.ui.CouponListActivity;
import com.purchase.sls.coupon.ui.InvalidCouponFragment;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {CouponModule.class})
public interface CouponComponent {
    void inject(CouponListActivity couponListActivity);
    void inject(AvailableCouponFragment availableCouponFragment);
    void inject(InvalidCouponFragment invalidCouponFragment);
}
