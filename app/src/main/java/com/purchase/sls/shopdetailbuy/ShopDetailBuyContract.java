package com.purchase.sls.shopdetailbuy;

import com.purchase.sls.BasePresenter;
import com.purchase.sls.BaseView;
import com.purchase.sls.data.entity.GeneratingOrderInfo;
import com.purchase.sls.data.entity.OrderDetailInfo;
import com.purchase.sls.data.entity.ShopDetailsInfo;
import com.purchase.sls.data.entity.UserpowerInfo;
import com.purchase.sls.data.request.SubmitEvaluateRequest;

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
        void addRemoveSuccess(String type);
    }

    interface PaymentOrderPresenter extends BasePresenter{
        void getUserpowerInfo(String price, String storeid);
        void setGeneratingOrder(String allprice, String storeid, String coupon, String power, String paytype, String notes);
        void getAlipaySign(String allprice, String storeid, String coupon, String power, String paytype, String notes);
        void getWXPaySign(String allprice, String storeid, String coupon, String power, String paytype, String notes);
        void isSetUpPayPw();
    }
    interface PaymentOrderView extends BaseView<PaymentOrderPresenter>{
        void userpowerInfo(UserpowerInfo userpowerInfo);
        void generatingOrderSuccess(GeneratingOrderInfo generatingOrderInfo);
        //充值失败回调
        void onRechargetFail();
        //充值成功回调
        void onRechargeSuccess();
        //充值取消
        void onRechargeCancel();
        void onAppIdReceive(String appId);
        void renderOrderno(String orderno);
        void renderIsSetUpPayPw(String what);
    }

    interface OrderDetailPresenter extends BasePresenter{
        void getOrderDetailInfo(String orderno);
        void submitEvaluate(SubmitEvaluateRequest submitEvaluateRequest);
        void receiveCoupon(String id,String orderno);
        void receiveShopV(String orderno);
    }
    interface OrderDetailView extends BaseView<OrderDetailPresenter>{
        void renderOrderDetail(OrderDetailInfo orderDetailInfo);
        void submitSuccess();
        void receiveSuccess();
    }
}
