package com.purchase.sls.shopdetailbuy;

import com.purchase.sls.BasePresenter;
import com.purchase.sls.BaseView;
import com.purchase.sls.data.entity.ShopDetailsInfo;

/**
 * Created by JWC on 2018/4/24.
 */

public interface ShopDetailBuyContract {
    interface ShopDetailPresenter extends BasePresenter{
        void getShopDetail(String storeid);
    }
    interface ShopDetailView extends BaseView<ShopDetailPresenter>{
        void ShopDetailInfo(ShopDetailsInfo shopDetailsInfo);
    }
}
