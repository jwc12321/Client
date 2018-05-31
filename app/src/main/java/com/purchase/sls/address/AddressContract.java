package com.purchase.sls.address;

import com.purchase.sls.BasePresenter;
import com.purchase.sls.BaseView;
import com.purchase.sls.data.entity.AddressInfo;
import com.purchase.sls.data.request.AddAddressRequest;

import java.util.List;

/**
 * Created by JWC on 2018/5/29.
 */

public interface AddressContract {
    interface AddressListPresenter extends BasePresenter{
        void getAddressList(String refreshType);
        void setDefault(String id);
        void deleteAddress(String id);
    }
    interface AddressListView extends BaseView<AddressListPresenter>{
        void renderAddressList(List<AddressInfo> addressInfos);
        void defaultSuccess();
        void deleteSuccess();
    }
    interface AddAddressPresenter extends BasePresenter{
        void addAddress(AddAddressRequest addAddressRequest);
    }
    interface AddAddressView extends BaseView<AddAddressPresenter>{
        void addSuccess();
    }
}
