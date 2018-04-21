package com.purchase.sls.nearbymap;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JWC on 2018/4/19.
 */
@Module
public class NearbyMapModule {
    private NearbyMapContract.NearbyView nearbyView;

    public NearbyMapModule(NearbyMapContract.NearbyView nearbyView) {
        this.nearbyView = nearbyView;
    }
    @Provides
    NearbyMapContract.NearbyView provideNearbyView(){
        return nearbyView;
    }
}
