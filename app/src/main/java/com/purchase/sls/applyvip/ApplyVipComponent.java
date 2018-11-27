package com.purchase.sls.applyvip;

/**
 * Created by JWC on 2018/11/27.
 */

import com.purchase.sls.ActivityScope;
import com.purchase.sls.ApplicationComponent;
import com.purchase.sls.applyvip.ui.ApplyVipActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {ApplyVipModule.class})
public interface ApplyVipComponent {
    void inject(ApplyVipActivity applyVipActivity);
}
