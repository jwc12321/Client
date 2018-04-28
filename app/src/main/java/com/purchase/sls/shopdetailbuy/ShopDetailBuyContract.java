package com.purchase.sls.shopdetailbuy;

import com.purchase.sls.BasePresenter;
import com.purchase.sls.BaseView;
import com.purchase.sls.data.entity.GeneratingOrderInfo;
import com.purchase.sls.data.entity.ShopDetailsInfo;
import com.purchase.sls.data.entity.UserpowerInfo;

/**
 * Created by JWC on 2018/4/24.
 */

public interface ShopDetailBuyContract {
    interface ShopDetailPresenter extends BasePresenter{
        void getShopDetail(String storeid);
        void addRemoveCollection(String storeid, String type, String[] fidArray );
    }
    interface ShopDetailView extends BaseView<ShopDetailPresenter>{
        void shopDetailInfo(ShopDetailsInfo shopDetailsInfo);
        void addRemoveSuccess();
    }

    interface PaymentOrderPresenter extends BasePresenter{
        void getUserpowerInfo(String price, String storeid);
        void setGeneratingOrder(String allprice, String storeid, String coupon, String power, String paytype, String notes);
    }
    interface PaymentOrderView extends BaseView<PaymentOrderPresenter>{
        void userpowerInfo(UserpowerInfo userpowerInfo);
        void generatingOrderSuccess(GeneratingOrderInfo generatingOrderInfo);
    }
}
