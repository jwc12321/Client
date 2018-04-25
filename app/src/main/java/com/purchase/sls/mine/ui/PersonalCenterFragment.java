package com.purchase.sls.mine.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
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
    @BindView(R.id.setting_iv)
    ImageView settingIv;
    @BindView(R.id.information_iv)
    ImageView informationIv;
    @BindView(R.id.photo)
    RoundedImageView photo;
    @BindView(R.id.persion_name)
    TextView persionName;
    @BindView(R.id.item_my_account)
    FrameLayout itemMyAccount;
    @BindView(R.id.item_voucher)
    FrameLayout itemVoucher;
    @BindView(R.id.item_browse_records)
    FrameLayout itemBrowseRecords;
    @BindView(R.id.item_customer_service_center)
    FrameLayout itemCustomerServiceCenter;
    @BindView(R.id.item_want_cooperate)
    FrameLayout itemWantCooperate;
    @BindView(R.id.item_about_neng)
    FrameLayout itemAboutNeng;
    private boolean isFirstLoad = true;

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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isFirstLoad) {
            if (getUserVisibleHint()) {
                isFirstLoad = false;
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    @OnClick({R.id.setting_iv})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.setting_iv:
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
