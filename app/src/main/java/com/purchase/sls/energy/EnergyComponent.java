package com.purchase.sls.energy;

/**
 * Created by JWC on 2018/4/19.
 */

import com.purchase.sls.ActivityScope;
import com.purchase.sls.ApplicationComponent;
import com.purchase.sls.energy.ui.EnergyFragment;
import com.purchase.sls.energy.ui.EnergyInfoActivity;
import com.purchase.sls.energy.ui.ExchangeFragment;
import com.purchase.sls.energy.ui.LotteryFragment;
import com.purchase.sls.energy.ui.SignInActivity;
import com.purchase.sls.energy.ui.SkEcLtActivity;
import com.purchase.sls.energy.ui.SpikeFragment;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {EnergyModule.class})
public interface EnergyComponent {
    void inject(EnergyInfoActivity energyInfoActivity);
    void inject(SpikeFragment spikeFragment);
    void inject(ExchangeFragment exchangeFragment);
    void inject(LotteryFragment lotteryFragment);
    void inject(SkEcLtActivity skEcLtActivity);
    void inject(SignInActivity signInActivity);
    void inject(EnergyFragment energyFragment);
}
