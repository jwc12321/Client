package com.purchase.sls.mine.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;
import com.purchase.sls.BaseFragment;
import com.purchase.sls.R;
import com.purchase.sls.account.ui.AccountListActivity;
import com.purchase.sls.address.ui.AddAddressActivity;
import com.purchase.sls.address.ui.AddressListActivity;
import com.purchase.sls.address.ui.SelectAddressActivity;
import com.purchase.sls.browse.ui.BrowseRecordsActivity;
import com.purchase.sls.collection.ui.CollectionListActivity;
import com.purchase.sls.common.GlideHelper;
import com.purchase.sls.common.UMStaticData;
import com.purchase.sls.common.unit.PersionAppPreferences;
import com.purchase.sls.common.unit.UmengEventUtils;
import com.purchase.sls.coupon.ui.CouponListActivity;
import com.purchase.sls.data.entity.PersionInfoResponse;
import com.purchase.sls.data.entity.WebViewDetailInfo;
import com.purchase.sls.energy.ui.EnergyInfoActivity;
import com.purchase.sls.evaluate.ui.ToBeEvaluatedActivity;
import com.purchase.sls.login.ui.AccountLoginActivity;
import com.purchase.sls.messages.ui.MessageNotificationActivity;
import com.purchase.sls.webview.ui.WebViewActivity;

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
    @BindView(R.id.psersoin_homepage_ll)
    LinearLayout psersoinHomepageLl;
    @BindView(R.id.collection_ll)
    LinearLayout collectionLl;
    @BindView(R.id.comment_ll)
    LinearLayout commentLl;
    @BindView(R.id.account_ll)
    LinearLayout accountLl;
    @BindView(R.id.item_energy)
    FrameLayout itemEnergy;
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
    @BindView(R.id.item_persion_im)
    ImageView itemPersionIm;
    @BindView(R.id.item_rdcode)
    FrameLayout itemRdcode;
    @BindView(R.id.item_address)
    FrameLayout itemAddress;
    private boolean isFirstLoad = true;

    private PersionAppPreferences persionAppPreferences;
    private String persionInfoStr;
    private PersionInfoResponse persionInfoResponse;
    private Gson gson;
    private WebViewDetailInfo webViewDetailInfo;
    private String phoneNumber;
    private String qrCodeUrl;
    private String firstIn="0";

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
        persionAppPreferences = new PersionAppPreferences(getActivity());
        return rootview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHeight(settingIv, null, informationIv);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isFirstLoad) {
            if (getUserVisibleHint()) {
                isFirstLoad = false;
                initVeiw();
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        initVeiw();
    }

    private void initVeiw() {
        if (!isFirstLoad&&getUserVisibleHint()&&TextUtils.equals("0",firstIn)) {
            persionInfoStr = persionAppPreferences.getPersionInfo();
            gson = new Gson();
            if (!TextUtils.isEmpty(persionInfoStr)) {
                persionInfoResponse = gson.fromJson(persionInfoStr, PersionInfoResponse.class);
                GlideHelper.load(this, persionInfoResponse.getAvatar(), R.mipmap.app_icon, photo);
                persionName.setText(persionInfoResponse.getUsername());
                phoneNumber = persionInfoResponse.getTel();
                qrCodeUrl = persionInfoResponse.getQrcode();
                firstIn="1";
            } else {
                AccountLoginActivity.start(getActivity());
            }
        }
    }


    @OnClick({R.id.information_iv, R.id.setting_iv, R.id.collection_ll, R.id.comment_ll, R.id.account_ll, R.id.item_energy, R.id.item_voucher, R.id.item_rdcode, R.id.item_address,R.id.item_browse_records, R.id.item_want_cooperate, R.id.item_about_neng, R.id.item_persion_im, R.id.item_customer_service_center})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_iv://设置
                firstIn="0";
                SettingActivity.start(getActivity(), phoneNumber);
                break;
            case R.id.information_iv:
                MessageNotificationActivity.start(getActivity());
                break;
            case R.id.item_persion_im://个人主页
                firstIn="0";
                UmengEventUtils.statisticsClick(getActivity(), UMStaticData.SHOW_MY_INFO);
                PersonalInformationActivity.start(getActivity());
                break;
            case R.id.collection_ll://收藏
                UmengEventUtils.statisticsClick(getActivity(), UMStaticData.SHOW_COLLECTION);
                CollectionListActivity.start(getActivity());
                break;
            case R.id.comment_ll://点评
                UmengEventUtils.statisticsClick(getActivity(), UMStaticData.SHOW_COMMANDS);
                ToBeEvaluatedActivity.start(getActivity());
                break;
            case R.id.account_ll://账单
                UmengEventUtils.statisticsClick(getActivity(), UMStaticData.SHOW_BILL);
                AccountListActivity.start(getActivity());
                break;
            case R.id.item_energy://能量
                EnergyInfoActivity.start(getActivity());
                break;
            case R.id.item_voucher://优惠券
                CouponListActivity.start(getActivity());
                break;
            case R.id.item_address:
                AddressListActivity.start(getActivity(),"0");
                break;
            case R.id.item_rdcode://我的推荐码
                RdCodeActivity.start(getActivity(), qrCodeUrl);
                break;
            case R.id.item_browse_records://浏览记录
                BrowseRecordsActivity.start(getActivity());
                break;
            case R.id.item_customer_service_center:
                webViewDetailInfo = new WebViewDetailInfo();
                webViewDetailInfo.setTitle("客服中心");
                webViewDetailInfo.setUrl("https://open.365neng.com/api/home/index/services");
                WebViewActivity.start(getActivity(), webViewDetailInfo);
                break;
            case R.id.item_want_cooperate://我要合作
                webViewDetailInfo = new WebViewDetailInfo();
                webViewDetailInfo.setTitle("我要合作");
                webViewDetailInfo.setUrl("https://open.365neng.com/api/home/index/admission");
                WebViewActivity.start(getActivity(), webViewDetailInfo);
                break;
            case R.id.item_about_neng://关于我们
                webViewDetailInfo = new WebViewDetailInfo();
                webViewDetailInfo.setTitle("关于能购");
                webViewDetailInfo.setUrl("https://open.365neng.com/api/home/index/androidAbout");
                WebViewActivity.start(getActivity(), webViewDetailInfo);
                break;
            default:
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
