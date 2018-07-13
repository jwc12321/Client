package com.purchase.sls.shoppingmall;

import com.purchase.sls.BasePresenter;
import com.purchase.sls.BaseView;
import com.purchase.sls.data.entity.AddressInfo;
import com.purchase.sls.data.entity.GoodsDetailInfo;
import com.purchase.sls.data.entity.GoodsItemList;
import com.purchase.sls.data.entity.GoodsOrderList;
import com.purchase.sls.data.entity.GoodsParentInfo;
import com.purchase.sls.data.entity.SMBannerInfo;
import com.purchase.sls.data.entity.ShoppingCartInfo;

import java.util.List;

/**
 * Created by JWC on 2018/4/19.
 */

public interface ShoppingMallContract {
    interface GoodsListPresenter extends BasePresenter{
        void getSMBannerInfo();
        void getGoodsParents();
        void getGoodsItems(String refreshType,String keyword, String orderby, String order, String cate);
        void getMoreGoodsItems(String keyword, String orderby, String order, String cate);
    }

    interface GoodsListView extends BaseView<GoodsListPresenter>{
        void smBannerInfo(List<SMBannerInfo> smBannerInfos);
        void renderGoodsParents(List<GoodsParentInfo> goodsParentInfos);
        void renderGoodsItems(GoodsItemList goodsItemList);
        void renderMoreGoodsItems(GoodsItemList goodsItemList);
    }

    interface GoodsDetailPresenter extends BasePresenter{
        void getGoodsDetail(String goodsid);
        void addToCart(String id, String taobaoid, String sku, String num, String skuinfo, String quan, String tjprice, String quan_url);
        void purchaseGoods(String goodsnum, String goodsId, String sku, String price, String skuinfo, String taobaoid, String quan, String quan_url);
    }

    interface GoodsDetailView extends BaseView<GoodsDetailPresenter>{
        void renderGoodsDetail(GoodsDetailInfo goodsDetailInfo);
        void addToCartSuccess();
        void purchaseGoodsSuccess(GoodsOrderList goodsOrderList);
    }

    interface GoodsSearchPresenter extends BasePresenter{
        void getGoodsItems(String refreshType,String keyword, String orderby, String order, String cate);
        void getMoreGoodsItems(String keyword, String orderby, String order, String cate);
    }

    interface GoodsSearchView extends BaseView<GoodsSearchPresenter>{
        void renderGoodsItems(GoodsItemList goodsItemList);
        void renderMoreGoodsItems(GoodsItemList goodsItemList);
    }

    interface ShoppingCartPresenter extends BasePresenter{
        void getShoppingCartList();
        void orderShopCart(String cartid, String num);
        void deleteshopCart(String id);
    }

    interface ShoppingCartView extends BaseView<ShoppingCartPresenter>{
        void renderShoppingCartList(List<ShoppingCartInfo> shoppingCartInfos);
        void orderShopCartSuccess(GoodsOrderList goodsOrderList);
        void deleteshopCartSuccess();
    }

   interface FillInOrderPresenter extends BasePresenter{
       void getAddressList();
       void getAlipaySign(String carts, String addressid, String paytype, String isquan);
       void getWXPaySign(String carts, String addressid, String paytype, String isquan);
   }

   interface FillOrderView extends BaseView<FillInOrderPresenter>{
       void renderAddressList(List<AddressInfo> addressInfos);
       //充值失败回调
       void onRechargetFail();
       //充值成功回调
       void onRechargeSuccess();
       //充值取消
       void onRechargeCancel();
       void onAppIdReceive(String appId);
       void renderOrderno(String orderno);
   }
}
