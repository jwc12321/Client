package com.purchase.sls.ecvoucher;

/**
 * Created by JWC on 2018/8/2.
 */

import com.purchase.sls.ActivityScope;
import com.purchase.sls.ApplicationComponent;
import com.purchase.sls.ecvoucher.ui.EcVoucherActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {EcVoucherModule.class})
public interface EcVoucherComponent {
    void inject(EcVoucherActivity ecVoucherActivity);
}
