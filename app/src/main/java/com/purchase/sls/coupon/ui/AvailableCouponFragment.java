package com.purchase.sls.coupon.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.purchase.sls.R;
import com.purchase.sls.common.refreshview.HeaderViewLayout;
import com.purchase.sls.common.unit.CommonAppPreferences;
import com.purchase.sls.common.widget.list.BaseListFragment;
import com.purchase.sls.coupon.CouponContract;
import com.purchase.sls.coupon.CouponModule;
import com.purchase.sls.coupon.DaggerCouponComponent;
import com.purchase.sls.coupon.adapter.CouponListAdapter;
import com.purchase.sls.coupon.presenter.CouponPresenter;
import com.purchase.sls.data.entity.CouponInfo;
import com.purchase.sls.data.entity.WebViewDetailInfo;
import com.purchase.sls.mainframe.ui.MainFrameActivity;
import com.purchase.sls.webview.ui.WebViewActivity;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by JWC on 2018/5/3.
 * 可用优惠券
 */

public class AvailableCouponFragment extends BaseListFragment<CouponInfo> implements CouponContract.CouponListView, HeaderViewLayout.OnRefreshListener,CouponListAdapter.OnBtClick {
    @Inject
    CouponPresenter couponPresenter;
    private CouponListAdapter couponListAdapter;
    private CommonAppPreferences commonAppPreferences;
    private WebViewDetailInfo webViewDetailInfo;

    public static AvailableCouponFragment newInstance() {
        AvailableCouponFragment fragment = new AvailableCouponFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commonAppPreferences=new CommonAppPreferences(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        if(couponPresenter!=null&&getUserVisibleHint()) {
            couponPresenter.getCouponList("1","0");
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setMoreLoadable(true);
    }

    @Override
    public RecyclerView.Adapter initAdapter(List<CouponInfo> list) {
        couponListAdapter = new CouponListAdapter("0");
        couponListAdapter.setData(list);
        couponListAdapter.setonCouponItemClick(this);
        return couponListAdapter;
    }

    private boolean isFirstLoad = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isFirstLoad) {
            if (getUserVisibleHint()) {
                if(couponPresenter!=null) {
                    couponPresenter.getCouponList("1","0");
                }
                isFirstLoad = false;
            }
        }
    }

    @Override
    public void onRefresh() {
        couponPresenter.getCouponList("0","0");
    }

    @Override
    public void onLoadMore() {
        couponPresenter.getMoreCouponList("0");
    }

    @Override
    public void setPresenter(CouponContract.CouponListPresenter presenter) {

    }

    @Override
    protected void initializeInjector() {
        DaggerCouponComponent.builder()
                .applicationComponent(getApplicationComponent())
                .couponModule(new CouponModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void btClick(String mainGo) {
        commonAppPreferences.setMianGoWhere(mainGo);
        MainFrameActivity.start(getActivity());
    }

    @Override
    public void couponDetail(String qid) {
        webViewDetailInfo = new WebViewDetailInfo();
        webViewDetailInfo.setTitle("优惠券详情");
        String url="https://open.365neng.com/api/home/codeWeb?id="+qid;
        webViewDetailInfo.setUrl(url);
        WebViewActivity.start(getActivity(), webViewDetailInfo);
    }
}
