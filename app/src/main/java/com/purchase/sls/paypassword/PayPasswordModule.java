package com.purchase.sls.paypassword;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JWC on 2018/8/20.
 */
@Module
public class PayPasswordModule {
    private PayPasswordContract.PayPasswordView payPasswordView;

    public PayPasswordModule(PayPasswordContract.PayPasswordView payPasswordView) {
        this.payPasswordView = payPasswordView;
    }

    @Provides
    PayPasswordContract.PayPasswordView providePayPasswordView(){
        return payPasswordView;
    }
}
