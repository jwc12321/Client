package com.purchase.sls.ordermanage.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.purchase.sls.common.refreshview.HeaderViewLayout;
import com.purchase.sls.common.widget.list.BaseListFragment;
import com.purchase.sls.data.entity.ActivityOrderInfo;
import com.purchase.sls.ordermanage.DaggerOrderManageComponent;
import com.purchase.sls.ordermanage.OrderManageContract;
import com.purchase.sls.ordermanage.OrderManageModule;
import com.purchase.sls.ordermanage.adapter.ActivityOrderAdatper;
import com.purchase.sls.ordermanage.presenter.ActivityOrderListPresenter;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by JWC on 2018/6/7.
 */

public class AllActivityOrderFragment extends BaseListFragment<ActivityOrderInfo> implements HeaderViewLayout.OnRefreshListener, OrderManageContract.ActivityOrderListView, ActivityOrderAdatper.HostAction {

    @Inject
    ActivityOrderListPresenter activityOrderListPresenter;
    private ActivityOrderAdatper activityOrderAdatper;

    public static AllActivityOrderFragment newInstance() {
        AllActivityOrderFragment fragment = new AllActivityOrderFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setMoreLoadable(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(activityOrderListPresenter!=null){
            activityOrderListPresenter.getActivityOrderList("0");
        }
    }

    private boolean isFirstLoad = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isFirstLoad) {
            if (getUserVisibleHint()) {
                isFirstLoad = false;
                if(activityOrderListPresenter!=null){
                    activityOrderListPresenter.getActivityOrderList("0");
                }
            }
        }
    }

    @Override
    public RecyclerView.Adapter initAdapter(List<ActivityOrderInfo> list) {
        activityOrderAdatper = new ActivityOrderAdatper(getActivity());
        activityOrderAdatper.setData(list);
        activityOrderAdatper.setHostAction(this);
        return activityOrderAdatper;
    }

    @Override
    public void onRefresh() {
        activityOrderListPresenter.getActivityOrderList("0");
    }

    @Override
    public void onLoadMore() {
        activityOrderListPresenter.getMoreActivityOrderList("0");
    }

    @Override
    protected void initializeInjector() {
        DaggerOrderManageComponent.builder()
                .applicationComponent(getApplicationComponent())
                .orderManageModule(new OrderManageModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void setPresenter(OrderManageContract.ActivityOrderListPresenter presenter) {

    }

    @Override
    public void deleteOrder() {

    }

    @Override
    public void goOrderDetail() {

    }
}
