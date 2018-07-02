package com.purchase.sls.energy.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.BaseFragment;
import com.purchase.sls.R;
import com.purchase.sls.common.UMStaticData;
import com.purchase.sls.common.refreshview.HeaderViewLayout;
import com.purchase.sls.common.unit.TokenManager;
import com.purchase.sls.common.unit.UmengEventUtils;
import com.purchase.sls.common.widget.GradationScrollView;
import com.purchase.sls.common.widget.KeywordUtil;
import com.purchase.sls.data.entity.ActivityInfo;
import com.purchase.sls.data.entity.EnergyInfo;
import com.purchase.sls.energy.DaggerEnergyComponent;
import com.purchase.sls.energy.EnergyContract;
import com.purchase.sls.energy.EnergyModule;
import com.purchase.sls.energy.adapter.LotteryAdapter;
import com.purchase.sls.energy.presenter.ActivityPresenter;
import com.purchase.sls.login.ui.AccountLoginActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/5/30.
 * 抽奖
 */

public class LotteryFragment extends BaseFragment implements EnergyContract.ActivityView, LotteryAdapter.OnLotteryItemClickListener {
    @BindView(R.id.sign_in)
    ImageView signIn;
    @BindView(R.id.sign_rl)
    RelativeLayout signRl;
    @BindView(R.id.spike_total)
    TextView spikeTotal;
    @BindView(R.id.lottery_rv)
    RecyclerView lotteryRv;
    @BindView(R.id.scrollview)
    GradationScrollView scrollview;
    @BindView(R.id.refreshLayout)
    HeaderViewLayout refreshLayout;
    @BindView(R.id.energy_total)
    TextView energyTotal;

    private String lotteryFirstIn = "0";

    private LotteryAdapter lotteryAdapter;
    @Inject
    ActivityPresenter activityPresenter;

    public void addShareEnergy(){
        if (!isFirstLoad&&getUserVisibleHint()) {
            if (activityPresenter != null) {
                getEnergy("1");
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_lottery, container, false);
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
        addAdapter();
    }

    HeaderViewLayout.OnRefreshListener mOnRefreshListener = new HeaderViewLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            activityPresenter.getActivitys("3");
            getEnergy("0");
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
        if (!isFirstLoad && getUserVisibleHint() && TextUtils.equals("0", lotteryFirstIn)) {
            if (activityPresenter != null) {
                activityPresenter.getActivitys("3");
                getEnergy("1");
                lotteryFirstIn = "1";
            }
        }
    }


    private boolean isFirstLoad = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            getEnergy("1");
        }
        if (isFirstLoad) {
            if (getUserVisibleHint()) {
                isFirstLoad = false;
                lotteryFirstIn = "1";
                if (activityPresenter != null) {
                    activityPresenter.getActivitys("3");
                }
            }
        }
    }

    private void getEnergy(String refreshType) {
        if (!TextUtils.isEmpty(TokenManager.getToken()) && activityPresenter != null) {
            activityPresenter.getEnergyInfo(refreshType, "2");
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
        lotteryAdapter = new LotteryAdapter(getActivity());
        lotteryAdapter.setOnLotteryItemClickListener(this);
        lotteryRv.setAdapter(lotteryAdapter);
        lotteryRv.setNestedScrollingEnabled(false);
        lotteryRv.setFocusableInTouchMode(false);
    }

    @Override
    public void setPresenter(EnergyContract.ActivityPresenter presenter) {

    }

    @Override
    public void renderActivitys(List<ActivityInfo> activityInfos) {
        refreshLayout.stopRefresh();
        if (activityInfos == null) {
            spikeTotal.setText("当前能量抽奖活动(0)");
        } else {
            spikeTotal.setText("当前能量抽奖活动(" + activityInfos.size() + ")");
        }
        lotteryAdapter.setData(activityInfos);
    }

    @Override
    public void renderEnergyInfo(EnergyInfo energyInfo) {
        if (energyInfo != null && energyInfo.getSumPower() != null) {
            energyTotal.setText(KeywordUtil.matcherActivity(2.0f, "当前" + energyInfo.getSumPower().getPower() + "个能量"));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void goLottery(ActivityInfo activityInfo) {
        lotteryFirstIn = "0";
        UmengEventUtils.statisticsClick(getActivity(), UMStaticData.ENG_CHOU_JIANG);
        SkEcLtActivity.start(getActivity(), activityInfo);
    }


    @OnClick({R.id.sign_in})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in:
                lotteryFirstIn = "0";
                if (TextUtils.isEmpty(TokenManager.getToken())) {
                    AccountLoginActivity.start(getActivity());
                    return;
                } else {
                    Intent intent = new Intent(getActivity(), SignInActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                }
                break;
            default:
        }
    }
}
