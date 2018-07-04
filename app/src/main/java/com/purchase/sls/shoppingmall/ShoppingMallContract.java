package com.purchase.sls.shoppingmall;

import com.purchase.sls.BasePresenter;
import com.purchase.sls.BaseView;
import com.purchase.sls.data.entity.GoodsItemList;
import com.purchase.sls.data.entity.GoodsParentInfo;

import java.util.List;

/**
 * Created by JWC on 2018/4/19.
 */

public interface ShoppingMallContract {
    interface GoodsListPresenter extends BasePresenter{
        void getGoodsParents();
        void getGoodsItems(String refreshType,String keyword, String orderby, String order, String cate);
        void getMoreGoodsItems(String keyword, String orderby, String order, String cate);
    }

    interface GoodsListView extends BaseView<GoodsListPresenter>{
        void renderGoodsParents(List<GoodsParentInfo> goodsParentInfos);
        void renderGoodsItems(GoodsItemList goodsItemList);
        void renderMoreGoodsItems(GoodsItemList goodsItemList);
    }

    interface GoodsDetailPresenter extends BasePresenter{
        void getGoodsDetail(String goodsid);
    }

    interface GoodsDetailView extends BaseView<GoodsDetailPresenter>{
        void renderGoodsDetail();
    }

}
