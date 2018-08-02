package com.purchase.sls.ecvoucher;

import com.purchase.sls.BasePresenter;
import com.purchase.sls.BaseView;
import com.purchase.sls.data.entity.EcVoucherInfo;

/**
 * Created by JWC on 2018/8/2.
 */

public interface EcVoucherContract {
    interface EcVoucherInfosPresenter extends BasePresenter{
        void getEcVoucherInfo(String refreshType);
        void getMoreEcVoucherInfo();
    }

    interface EcVoucherInfosView extends BaseView<EcVoucherInfosPresenter>{
        void renderEcVoucherInfo(EcVoucherInfo ecVoucherInfo);
        void renderMoreEcVoucherInfo(EcVoucherInfo ecVoucherInfo);
    }
}
