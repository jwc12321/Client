package com.purchase.sls.goodsordermanage;

import com.purchase.sls.BasePresenter;
import com.purchase.sls.BaseView;
import com.purchase.sls.data.entity.GoodsOrderDetailInfo;
import com.purchase.sls.data.entity.GoodsOrderItemInfo;
import com.purchase.sls.data.entity.MalllogisInfo;

import java.util.List;

/**
 * Created by JWC on 2018/7/9.
 */

public interface GoodsOrderContract {
    interface GoodsOrderListPresenter extends BasePresenter {
        void getGoodOrderList(String refreshType, String type);

        void getMoreGoodOrderList(String type);

        void cancelOrder(String orderCode);

        void deleteOrder(String orderCode);

        void completeOrder(String orderCode);

        void getMalllogisInfo(String orderCode);
    }

    interface GoodsOrderListView extends BaseView<GoodsOrderListPresenter> {
        void render(List<GoodsOrderItemInfo> goodsOrderItemInfos);

        void renderMore(List<GoodsOrderItemInfo> goodsOrderItemInfos);

        void cancelOrderSuccess();

        void deleteOrderSuccess();

        void completeOrderSuccess();

        void renderMalllogisInfo(MalllogisInfo malllogisInfo);
    }

    interface GoodsOrderDetailPresenter extends BasePresenter {
        void getGoodsOrderDetail(String ordernum);

        void cancelOrder(String orderCode);

        void deleteOrder(String orderCode);

        void completeOrder(String orderCode);

        void getMalllogisInfo(String orderCode);
    }

    interface GoodsOrderDetailView extends BaseView<GoodsOrderDetailPresenter> {
        void renderGoodsOrderDetail(GoodsOrderDetailInfo goodsOrderDetailInfo);

        void cancelOrderSuccess();

        void deleteOrderSuccess();

        void completeOrderSuccess();

        void renderMalllogisInfo(MalllogisInfo malllogisInfo);
    }
}
