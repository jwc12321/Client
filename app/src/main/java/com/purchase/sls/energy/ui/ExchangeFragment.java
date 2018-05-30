package com.purchase.sls.energy.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.purchase.sls.BaseFragment;
import com.purchase.sls.R;

import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/5/30.
 * 兑换
 */

public class ExchangeFragment extends BaseFragment{
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
    }
}
