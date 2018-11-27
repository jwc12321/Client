package com.purchase.sls.applyvip;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JWC on 2018/11/27.
 */
@Module
public class ApplyVipModule {
    private ApplyVipContract.ApplyVipView applyVipView;

    public ApplyVipModule(ApplyVipContract.ApplyVipView applyVipView) {
        this.applyVipView = applyVipView;
    }

    @Provides
    ApplyVipContract.ApplyVipView provideApplyVipView(){
        return applyVipView;
    }
}
