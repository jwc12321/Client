package com.purchase.sls.energy;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JWC on 2018/4/19.
 */

@Module
public class EnergyModule {
    private EnergyContract.EnergyInfoView energyInfoView;

    public EnergyModule(EnergyContract.EnergyInfoView energyInfoView) {
        this.energyInfoView = energyInfoView;
    }
    @Provides
    EnergyContract.EnergyInfoView provideEnergyInfoView(){
        return energyInfoView;
    }
}
