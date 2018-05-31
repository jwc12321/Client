package com.purchase.sls.address;

/**
 * Created by JWC on 2018/5/29.
 */

import com.purchase.sls.ActivityScope;
import com.purchase.sls.ApplicationComponent;
import com.purchase.sls.address.ui.AddAddressActivity;
import com.purchase.sls.address.ui.AddressListActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {AddressModule.class})
public interface AddressComponent {
    void inject(AddressListActivity addressListActivity);
    void inject(AddAddressActivity addAddressActivity);
}
