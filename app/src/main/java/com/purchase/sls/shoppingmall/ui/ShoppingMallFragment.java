package com.purchase.sls.shoppingmall.ui;

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
 */

public class ShoppingMallFragment extends BaseFragment {
    public ShoppingMallFragment() {
    }
    public static ShoppingMallFragment newInstance(){
        ShoppingMallFragment shoppingMallFragment=new ShoppingMallFragment();
        return shoppingMallFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.fragment_shopping_mall,container,false);
        ButterKnife.bind(this, rootview);
        return rootview;
    }
}
