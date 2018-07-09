package com.purchase.sls.goodsordermanage;

import com.purchase.sls.BasePresenter;
import com.purchase.sls.BaseView;
import com.purchase.sls.data.entity.GoodsOrderDetailInfo;
import com.purchase.sls.data.entity.GoodsOrderItemInfo;

import java.util.List;

/**
 * Created by JWC on 2018/7/9.
 */

public interface GoodsOrderContract {
    interface GoodsOrderListPresenter extends BasePresenter {
        void getGoodOrderList(String refreshType, String type);

        void getMoreGoodOrderList(String type);
    }

    interface GoodsOrderListView extends BaseView<GoodsOrderListPresenter> {
        void render(List<GoodsOrderItemInfo> goodsOrderItemInfos);

        void renderMore(List<GoodsOrderItemInfo> goodsOrderItemInfos);
    }

    interface GoodsOrderDetailPresenter extends BasePresenter {
        void getGoodsOrderDetail(String ordernum);
    }

    interface GoodsOrderDetailView extends BaseView<GoodsOrderDetailPresenter> {
        void renderGoodsOrderDetail(GoodsOrderDetailInfo goodsOrderDetailInfo);
    }
}
