package com.purchase.sls.coupon;

import com.purchase.sls.BasePresenter;
import com.purchase.sls.BaseView;
import com.purchase.sls.data.entity.CouponInfo;
import com.purchase.sls.data.entity.CouponListInfo;

import java.util.List;

/**
 * Created by JWC on 2018/5/3.
 */

public interface CouponContract {
    interface CouponListPresenter extends BasePresenter{
        void getCouponList(String type);
        void getMoreCouponList(String type);
    }
    interface CouponListView extends BaseView<CouponListPresenter>{
        void render(List<CouponInfo> couponInfos);
        void renderMore(List<CouponInfo> couponInfos);
    }
}
