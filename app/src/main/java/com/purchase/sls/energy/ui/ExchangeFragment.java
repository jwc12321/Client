package com.purchase.sls.energy.ui;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.purchase.sls.BaseFragment;
import com.purchase.sls.R;
import com.purchase.sls.common.refreshview.HeaderViewLayout;
import com.purchase.sls.common.widget.GradationScrollView;
import com.purchase.sls.common.widget.GridSameSpacesItemDecoration;
import com.purchase.sls.common.widget.GridSpacesItemDecoration;
import com.purchase.sls.data.entity.ActivityInfo;
import com.purchase.sls.energy.DaggerEnergyComponent;
import com.purchase.sls.energy.EnergyContract;
import com.purchase.sls.energy.EnergyModule;
import com.purchase.sls.energy.adapter.ExchangeAdapter;
import com.purchase.sls.energy.presenter.ActivityPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/5/30.
 * 兑换
 */

public class ExchangeFragment extends BaseFragment implements EnergyContract.ActivityView, ExchangeAdapter.OnExchangeItemClickListener {
    @BindView(R.id.sign_in)
    ImageView signIn;
    @BindView(R.id.sign_rl)
    RelativeLayout signRl;
    @BindView(R.id.exchange_rv)
    RecyclerView exchangeRv;
    @BindView(R.id.scrollview)
    GradationScrollView scrollview;
    @BindView(R.id.refreshLayout)
    HeaderViewLayout refreshLayout;
    @BindView(R.id.red_iv)
    ImageView redIv;

    private ExchangeAdapter exchangeAdapter;
    @Inject
    ActivityPresenter activityPresenter;
    private AnimationDrawable anim;

    public static ExchangeFragment newInstance() {
        ExchangeFragment exchangeFragment = new ExchangeFragment();
        return exchangeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_exchange, container, false);
        ButterKnife.bind(this, rootview);
        return rootview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        refreshLayout.setOnRefreshListener(mOnRefreshListener);
        refreshLayout.setCanLoadMore(false);
        initSign();
        addAdapter();
    }

    HeaderViewLayout.OnRefreshListener mOnRefreshListener = new HeaderViewLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            activityPresenter.getActivitys("1");
        }

        @Override
        public void onLoadMore() {
        }

        @Override
        public void onModeChanged(@HeaderViewLayout.Mode int mode) {
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        if (!isFirstLoad) {
            if (activityPresenter != null) {
                activityPresenter.getActivitys("1");
            }
        }
    }

    private boolean isFirstLoad = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isFirstLoad) {
            if (getUserVisibleHint()) {
                isFirstLoad = false;
                if (activityPresenter != null) {
                    activityPresenter.getActivitys("1");
                }
            }
        }
    }

    @Override
    protected void initializeInjector() {
        DaggerEnergyComponent.builder()
                .applicationComponent(getApplicationComponent())
                .energyModule(new EnergyModule(this))
                .build()
                .inject(this);
    }

    private void addAdapter() {
        exchangeRv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        int space = getResources().getDimensionPixelSize(R.dimen.space_bootom);
        exchangeRv.addItemDecoration(new GridSameSpacesItemDecoration(space, false));
        exchangeAdapter = new ExchangeAdapter(getActivity());
        exchangeAdapter.setOnExchangeItemClickListener(this);
        exchangeRv.setAdapter(exchangeAdapter);
        exchangeRv.setNestedScrollingEnabled(false);
        exchangeRv.setFocusableInTouchMode(false);
    }

    @Override
    public void setPresenter(EnergyContract.ActivityPresenter presenter) {

    }

    @Override
    public void renderActivitys(List<ActivityInfo> activityInfos) {
        refreshLayout.stopRefresh();
        exchangeAdapter.setData(activityInfos);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void goExchange(ActivityInfo activityInfo) {

    }

    @OnClick({R.id.sign_in, R.id.red_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in:
                redIv.setVisibility(View.VISIBLE);
                break;
            case R.id.red_iv:
                anim.setOneShot(true);
                anim.start();
                break;
            default:
        }
    }

    private void initSign() {
        redIv.setBackgroundResource(R.drawable.red_envelope);
        anim = (AnimationDrawable) redIv.getBackground();
    }
}
