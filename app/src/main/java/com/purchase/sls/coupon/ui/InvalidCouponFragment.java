package com.purchase.sls.coupon.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import com.purchase.sls.common.refreshview.HeaderViewLayout;
import com.purchase.sls.common.widget.list.BaseListFragment;
import com.purchase.sls.coupon.CouponContract;
import com.purchase.sls.coupon.CouponModule;
import com.purchase.sls.coupon.DaggerCouponComponent;
import com.purchase.sls.coupon.adapter.CouponListAdapter;
import com.purchase.sls.coupon.presenter.CouponPresenter;
import com.purchase.sls.data.entity.CouponInfo;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by JWC on 2018/5/3.
 * 失效优惠券
 */

public class InvalidCouponFragment extends BaseListFragment<CouponInfo> implements CouponContract.CouponListView, HeaderViewLayout.OnRefreshListener {
    @Inject
    CouponPresenter couponPresenter;
    private CouponListAdapter couponListAdapter;
    private String inFirstIn="0";

    public static InvalidCouponFragment newInstance() {
        InvalidCouponFragment fragment = new InvalidCouponFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!isFirstLoad&&couponPresenter!=null&&getUserVisibleHint()&& TextUtils.equals("0",inFirstIn)) {
            couponPresenter.getCouponList("1","1");
            inFirstIn="1";
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setMoreLoadable(true);
    }

    @Override
    public RecyclerView.Adapter initAdapter(List<CouponInfo> list) {
        couponListAdapter = new CouponListAdapter("1");
        couponListAdapter.setData(list);
        return couponListAdapter;
    }

    private boolean isFirstLoad = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isFirstLoad) {
            if (getUserVisibleHint()) {
                if(couponPresenter!=null) {
                    couponPresenter.getCouponList("1","1");
                }
                isFirstLoad = false;
            }
        }
    }

    @Override
    public void onRefresh() {
        couponPresenter.getCouponList("0","1");
    }

    @Override
    public void onLoadMore() {
        couponPresenter.getMoreCouponList("1");
    }

    @Override
    public void setPresenter(CouponContract.CouponListPresenter presenter) {

    }


    @Override
    protected void initializeInjector() {
        DaggerCouponComponent.builder()
                .applicationComponent(getApplicationComponent())
                .couponModule(new CouponModule(this))
                .build().inject(this);
    }

}
