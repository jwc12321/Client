package com.purchase.sls.address;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JWC on 2018/5/29.
 */
@Module
public class AddressModule {
    private AddressContract.AddressListView addressListView;
    private AddressContract.AddAddressView addAddressView;

    public AddressModule(AddressContract.AddressListView addressListView) {
        this.addressListView = addressListView;
    }

    public AddressModule(AddressContract.AddAddressView addAddressView) {
        this.addAddressView = addAddressView;
    }

    @Provides
    AddressContract.AddressListView provideAddressListView(){
        return addressListView;
    }

    @Provides
    AddressContract.AddAddressView provideAddAddressView(){
        return addAddressView;
    }
}
