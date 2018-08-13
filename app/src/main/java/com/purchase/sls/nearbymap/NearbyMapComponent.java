package com.purchase.sls.nearbymap;

/**
 * Created by JWC on 2018/4/19.
 */

import com.purchase.sls.ActivityScope;
import com.purchase.sls.ApplicationComponent;
import com.purchase.sls.nearbymap.ui.NearbyMapFragment;
import com.purchase.sls.nearbymap.ui.SearchAddressActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {NearbyMapModule.class})
public interface NearbyMapComponent {
    void inject(NearbyMapFragment nearbyMapFragment);
    void inject(SearchAddressActivity searchAddressActivity);
}
