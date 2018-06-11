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
    private EnergyContract.ActivityDetailView activityDetailView;
    private EnergyContract.SignInView signInView;
    private EnergyContract.ShareView shareView;

    public EnergyModule(EnergyContract.EnergyInfoView energyInfoView) {
        this.energyInfoView = energyInfoView;
    }

    public EnergyModule(EnergyContract.ActivityView activityView) {
        this.activityView = activityView;
    }

    public EnergyModule(EnergyContract.ActivityDetailView activityDetailView) {
        this.activityDetailView = activityDetailView;
    }

    public EnergyModule(EnergyContract.ShareView shareView) {
        this.shareView = shareView;
    }

    public EnergyModule(EnergyContract.SignInView signInView) {
        this.signInView = signInView;
    }

    @Provides
    EnergyContract.EnergyInfoView provideEnergyInfoView(){
        return energyInfoView;
    }

    @Provides
    EnergyContract.ActivityView provideActivityView(){
        return activityView;
    }

    @Provides
    EnergyContract.ActivityDetailView provideActivityDetailView(){
        return activityDetailView;
    }

    @Provides
    EnergyContract.SignInView provideSignInView(){
        return signInView;
    }

    @Provides
    EnergyContract.ShareView provideShareView(){
        return shareView;
    }
}
