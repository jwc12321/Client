package com.purchase.sls.energy;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JWC on 2018/4/19.
 */

@Module
public class EnergyModule {
    private EnergyContract.EnergyInfoView energyInfoView;
    private EnergyContract.ActivityView activityView;

    public EnergyModule(EnergyContract.EnergyInfoView energyInfoView) {
        this.energyInfoView = energyInfoView;
    }

    public EnergyModule(EnergyContract.ActivityView activityView) {
        this.activityView = activityView;
    }

    @Provides
    EnergyContract.EnergyInfoView provideEnergyInfoView(){
        return energyInfoView;
    }

    @Provides
    EnergyContract.ActivityView provideActivityView(){
        return activityView;
    }
}
