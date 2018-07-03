package com.purchase.sls.energy.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.BaseFragment;
import com.purchase.sls.R;
import com.purchase.sls.common.unit.TokenManager;
import com.purchase.sls.common.widget.ViewPagerSlide;
import com.purchase.sls.common.widget.dialog.ShareDialog;
import com.purchase.sls.common.widget.list.BaseListAdapter;
import com.purchase.sls.energy.DaggerEnergyComponent;
import com.purchase.sls.energy.EnergyContract;
import com.purchase.sls.energy.EnergyModule;
import com.purchase.sls.energy.presenter.SharePresenter;
import com.purchase.sls.login.ui.AccountLoginActivity;
import com.purchase.sls.ordermanage.ui.ActivityOrderActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/4/19.
 * 能量
 */

public class EnergyFragment extends BaseFragment implements ShareDialog.ShareListen,EnergyContract.ShareView{

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.share)
    ImageView share;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.indicator)
    TabLayout indicator;
    @BindView(R.id.viewpager)
    ViewPagerSlide viewpager;
    @BindView(R.id.order)
    ImageView order;
    private boolean isFirstLoad = true;
    private String firstIn="0";

    private List<Fragment> fragmentList;
    private List<String> titleList;
    private BaseListAdapter baseListAdapter;

    private SpikeFragment spikeFragment ;
    private ExchangeFragment exchangeFragment;
    private LotteryFragment lotteryFragment;

    @Inject
    SharePresenter sharePresenter;

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
        setHeight(order, title, share);
    }

    @Override
    protected void initializeInjector() {
        DaggerEnergyComponent.builder()
                .applicationComponent(getApplicationComponent())
                .energyModule(new EnergyModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isFirstLoad) {
            if (getUserVisibleHint()) {
                isFirstLoad = false;
                if (TextUtils.isEmpty(TokenManager.getToken())) {
                    AccountLoginActivity.start(getActivity());
                    return;
                }
                initView();
                firstIn="1";
            }
        }
    }

    private void initView() {
        fragmentList = new ArrayList<>();
        titleList = new ArrayList<>();
        viewpager.setOffscreenPageLimit(2);
        spikeFragment = new SpikeFragment();
        exchangeFragment=new ExchangeFragment();
        lotteryFragment=new LotteryFragment();
        fragmentList.add(spikeFragment);
        fragmentList.add(exchangeFragment);
        fragmentList.add(lotteryFragment);
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
        if(!isFirstLoad&&getUserVisibleHint()&&TextUtils.equals("0",firstIn)) {
            if (TextUtils.isEmpty(TokenManager.getToken())) {
                AccountLoginActivity.start(getActivity());
                return;
            }
            initView();
            firstIn="1";
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    private void share() {
        ShareDialog shareDialog = new ShareDialog(getActivity());
        shareDialog.setUrl("http://d.365neng.com/share/download.html");
        shareDialog.setShareListen(this);
        shareDialog.show();
    }

    @OnClick({R.id.share,R.id.order})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.share:
                share();
                break;
            case R.id.order:
                ActivityOrderActivity.start(getActivity());
                break;
            default:
        }
    }

    @Override
    public void shareSuccess() {
        sharePresenter.share();
    }

    @Override
    public void setPresenter(EnergyContract.SharePresenter presenter) {

    }

    @Override
    public void success(String energy) {
        if(spikeFragment!=null){
            spikeFragment.addShareEnergy();
        }
        if (exchangeFragment!=null){
            exchangeFragment.addShareEnergy();
        }
        if(lotteryFragment!=null){
            lotteryFragment.addShareEnergy();
        }
    }
}
