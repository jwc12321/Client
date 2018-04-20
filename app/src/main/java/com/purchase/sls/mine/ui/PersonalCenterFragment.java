package com.purchase.sls.mine.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.purchase.sls.BaseFragment;
import com.purchase.sls.R;
import com.purchase.sls.login.ui.AccountLoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/4/19.
 * 我的
 */

public class PersonalCenterFragment extends BaseFragment {
    @BindView(R.id.button)
    Button button;

    public PersonalCenterFragment() {
    }

    public static PersonalCenterFragment newInstance() {
        PersonalCenterFragment personalCenterFragment = new PersonalCenterFragment();
        return personalCenterFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_persional_center, container, false);
        ButterKnife.bind(this, rootview);
        return rootview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @OnClick({R.id.button})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.button:
                AccountLoginActivity.start(getActivity());
                break;
                default:
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
