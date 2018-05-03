package com.purchase.sls.energy.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.common.refreshview.HeaderViewLayout;
import com.purchase.sls.data.entity.EnergyInfo;
import com.purchase.sls.energy.DaggerEnergyComponent;
import com.purchase.sls.energy.EnergyContract;
import com.purchase.sls.energy.EnergyModule;
import com.purchase.sls.energy.adapter.EnergyItemAdapter;
import com.purchase.sls.energy.presenter.EnergyInfoPresente;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/5/3.
 */

public class EnergyInfoActivity extends BaseActivity implements EnergyContract.EnergyInfoView {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.energy_number)
    TextView energyNumber;
    @BindView(R.id.energy_rv)
    RecyclerView energyRv;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.empty_view)
    NestedScrollView emptyView;
    @BindView(R.id.refreshLayout)
    HeaderViewLayout refreshLayout;

    @Inject
    EnergyInfoPresente energyInfoPresente;

    private EnergyItemAdapter energyItemAdapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, EnergyInfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_energy_info);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        refreshLayout.setOnRefreshListener(mOnRefreshListener);
        energyItemAdapter = new EnergyItemAdapter();
        energyRv.setAdapter(energyItemAdapter);
        energyInfoPresente.getEnergyInfo("2");
    }


    HeaderViewLayout.OnRefreshListener mOnRefreshListener = new HeaderViewLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            energyInfoPresente.getEnergyInfo("2");
        }

        @Override
        public void onLoadMore() {
            energyInfoPresente.getMoreEnergyInfo("2");
        }

        @Override
        public void onModeChanged(@HeaderViewLayout.Mode int mode) {
        }
    };

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    protected void initializeInjector() {
        DaggerEnergyComponent.builder()
                .applicationComponent(getApplicationComponent())
                .energyModule(new EnergyModule(this))
                .build()
                .inject(this);
        ;
    }

    @Override
    public void setPresenter(EnergyContract.EnergyInfoPresenter presenter) {

    }

    @Override
    public void renderEnergyInfo(EnergyInfo energyInfo) {
        refreshLayout.stopRefresh();
        if (energyInfo != null) {
            if (energyInfo.getSumPower() != null) {
                energyNumber.setText("当前" + energyInfo.getSumPower().getPower() + "个能量");
            }
            if (energyInfo.getUserlog() != null && energyInfo.getUserlog().getEnergyIncomeDetails() != null && energyInfo.getUserlog().getEnergyIncomeDetails().size() > 0) {
                emptyView.setVisibility(View.GONE);
                energyRv.setVisibility(View.VISIBLE);
                energyItemAdapter.setData(energyInfo.getUserlog().getEnergyIncomeDetails());
                refreshLayout.setCanLoadMore(true);
            } else {
                emptyView.setVisibility(View.VISIBLE);
                energyRv.setVisibility(View.GONE);
                refreshLayout.setCanLoadMore(false);
            }
        }
    }

    @Override
    public void renderMoreEnergyInfo(EnergyInfo energyInfo) {
        refreshLayout.stopRefresh();
        if (energyInfo.getUserlog() != null && energyInfo.getUserlog().getEnergyIncomeDetails() != null && energyInfo.getUserlog().getEnergyIncomeDetails().size() > 0) {
            energyItemAdapter.addMore(energyInfo.getUserlog().getEnergyIncomeDetails());
            refreshLayout.setCanLoadMore(true);
        } else {
            refreshLayout.setCanLoadMore(false);
        }
    }
}
