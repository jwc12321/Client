package com.purchase.sls.ecvoucher;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JWC on 2018/8/2.
 */
@Module
public class EcVoucherModule {
    private EcVoucherContract.EcVoucherInfosView ecVoucherInfosView;

    public EcVoucherModule(EcVoucherContract.EcVoucherInfosView ecVoucherInfosView) {
        this.ecVoucherInfosView = ecVoucherInfosView;
    }

    @Provides
    EcVoucherContract.EcVoucherInfosView provideEcVoucherInfosView() {
        return ecVoucherInfosView;
    }
}
