package com.purchase.sls.homepage.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.purchase.sls.BaseFragment;
import com.purchase.sls.R;

import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/4/19.
 * 首页
 */

public class HomePageFragment extends BaseFragment {

    public HomePageFragment() {
    }
    public static HomePageFragment newInstance(){
        HomePageFragment homePageFragment=new HomePageFragment();
        return homePageFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.fragment_homepage,container,false);
        ButterKnife.bind(this, rootview);
        return rootview;
    }

    @Override
    protected void initializeInjector() {
        super.initializeInjector();
    }
}
