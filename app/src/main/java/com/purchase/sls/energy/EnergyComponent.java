package com.purchase.sls.energy;

/**
 * Created by JWC on 2018/4/19.
 */

import com.purchase.sls.ActivityScope;
import com.purchase.sls.ApplicationComponent;
import com.purchase.sls.energy.ui.EnergyInfoActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {EnergyModule.class})
public interface EnergyComponent {
    void inject(EnergyInfoActivity energyInfoActivity);
}
