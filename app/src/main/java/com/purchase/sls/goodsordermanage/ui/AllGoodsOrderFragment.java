package com.purchase.sls.goodsordermanage.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.purchase.sls.R;
import com.purchase.sls.common.refreshview.HeaderViewLayout;
import com.purchase.sls.common.widget.list.BaseListFragment;
import com.purchase.sls.data.entity.GoodsOrderItemInfo;
import com.purchase.sls.data.entity.MalllogisInfo;
import com.purchase.sls.goodsordermanage.DaggerGoodsOrderComponent;
import com.purchase.sls.goodsordermanage.GoodsOrderContract;
import com.purchase.sls.goodsordermanage.GoodsOrderModule;
import com.purchase.sls.goodsordermanage.adapter.GoodsOrderAdapter;
import com.purchase.sls.goodsordermanage.presenter.GoodsOrderListPresenter;
import com.purchase.sls.ordermanage.ui.LogisticsDetailsActivity;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by JWC on 2018/7/9.
 * 全部
 */

public class AllGoodsOrderFragment extends BaseListFragment<GoodsOrderItemInfo> implements GoodsOrderContract.GoodsOrderListView, HeaderViewLayout.OnRefreshListener, GoodsOrderAdapter.HostAction {

    private GoodsOrderAdapter goodsOrderAdapter;
    @Inject
    GoodsOrderListPresenter goodsOrderListPresenter;
    private String expressName;

    public static AllGoodsOrderFragment newInstance() {
        AllGoodsOrderFragment fragment = new AllGoodsOrderFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public RecyclerView.Adapter initAdapter(List<GoodsOrderItemInfo> list) {
        goodsOrderAdapter = new GoodsOrderAdapter(getActivity());
        goodsOrderAdapter.setHostAction(this);
        goodsOrderAdapter.setData(list);
        goodsOrderAdapter.setHostAction(this);
        return goodsOrderAdapter;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setEmptyView(R.mipmap.no_order_icon, "你还没有相关订单...");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (goodsOrderListPresenter != null && getUserVisibleHint()) {
            goodsOrderListPresenter.getGoodOrderList("0", "-1");
        }
    }

    private boolean isFirstLoad = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isFirstLoad) {
            if (getUserVisibleHint()) {
                isFirstLoad = false;
                if (goodsOrderListPresenter != null) {
                    goodsOrderListPresenter.getGoodOrderList("0", "-1");
                }
            }
        }
    }

    @Override
    protected void initializeInjector() {
        DaggerGoodsOrderComponent.builder()
                .applicationComponent(getApplicationComponent())
                .goodsOrderModule(new GoodsOrderModule(this))
                .build()
                .inject(this);
    }


    @Override
    public void cancelOrder(String orderNum) {
        if (goodsOrderListPresenter != null) {
            goodsOrderListPresenter.cancelOrder(orderNum);
        }
    }

    @Override
    public void payOrder(String orderNum) {
        OrderPayActivity.start(getActivity(),orderNum);
    }

    @Override
    public void seeLogistics(String orderNum, String expressName) {
        this.expressName = expressName;
        if (goodsOrderListPresenter != null) {
            goodsOrderListPresenter.getMalllogisInfo(orderNum);
        }
    }

    @Override
    public void completeOrder(String orderNum) {
        if (goodsOrderListPresenter != null) {
            goodsOrderListPresenter.completeOrder(orderNum);
        }
    }

    @Override
    public void deleteOrder(String orderNum) {
        if (goodsOrderListPresenter != null) {
            goodsOrderListPresenter.deleteOrder(orderNum);
        }
    }

    @Override
    public void goOrderDetail(String orderNum) {
        GoodsOrderDetalActivity.start(getActivity(), orderNum);
    }


    @Override
    public void onRefresh() {
        if (goodsOrderListPresenter != null) {
            goodsOrderListPresenter.getGoodOrderList("0", "-1");
        }
    }

    @Override
    public void onLoadMore() {
        if (goodsOrderListPresenter != null) {
            goodsOrderListPresenter.getMoreGoodOrderList("-1");
        }
    }

    @Override
    public void onModeChanged(int mode) {

    }

    @Override
    public void setPresenter(GoodsOrderContract.GoodsOrderListPresenter presenter) {

    }

    @Override
    public void cancelOrderSuccess() {
        if (goodsOrderListPresenter != null) {
            goodsOrderListPresenter.getGoodOrderList("1", "-1");
        }
    }

    @Override
    public void deleteOrderSuccess() {
        if (goodsOrderListPresenter != null) {
            goodsOrderListPresenter.getGoodOrderList("1", "-1");
        }
    }

    @Override
    public void completeOrderSuccess() {
        if (goodsOrderListPresenter != null) {
            goodsOrderListPresenter.getGoodOrderList("1", "-1");
        }
    }

    @Override
    public void renderMalllogisInfo(MalllogisInfo malllogisInfo) {
        if (malllogisInfo != null) {
            LogisticsDetailsActivity.start(getActivity(), expressName, malllogisInfo.getLogisticCode(), malllogisInfo.getLogisticRracesInfos());
        }
    }
}
