package com.purchase.sls.energy.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.BaseFragment;
import com.purchase.sls.R;
import com.purchase.sls.common.widget.list.BaseListAdapter;
import com.purchase.sls.coupon.ui.AvailableCouponFragment;
import com.purchase.sls.coupon.ui.InvalidCouponFragment;
import com.purchase.sls.splash.SplashActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/4/19.
 * 能量
 */

public class EnergyFragment extends BaseFragment {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.share)
    ImageView share;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.indicator)
    TabLayout indicator;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    private boolean isFirstLoad = true;

    private List<Fragment> fragmentList;
    private List<String> titleList;
    private BaseListAdapter baseListAdapter;

    public EnergyFragment() {
    }

    public static EnergyFragment newInstance() {
        EnergyFragment energyFragment = new EnergyFragment();
        return energyFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_energy, container, false);
        ButterKnife.bind(this, rootview);
        return rootview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHeight(null,title,share);
        initView();
    }

    @Override
    protected void initializeInjector() {
        super.initializeInjector();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isFirstLoad) {
            if (getUserVisibleHint()) {
                isFirstLoad = false;
            }
        }
    }

    private void initView() {
        fragmentList = new ArrayList<>();
        titleList = new ArrayList<>();
        viewpager.setOffscreenPageLimit(2);
        fragmentList.add(SpikeFragment.newInstance());
        fragmentList.add(ExchangeFragment.newInstance());
        fragmentList.add(LotteryFragment.newInstance());
        titleList.add("秒杀");
        titleList.add("兑换");
        titleList.add("抽奖");
        baseListAdapter = new BaseListAdapter(getChildFragmentManager(), fragmentList, titleList);
        viewpager.setAdapter(baseListAdapter);
        viewpager.setCurrentItem(0);
        indicator.removeAllTabs();
        indicator.setupWithViewPager(viewpager);
    }




    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
