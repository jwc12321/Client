package com.purchase.sls.mine.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;
import com.purchase.sls.BaseFragment;
import com.purchase.sls.R;
import com.purchase.sls.account.ui.AccountListActivity;
import com.purchase.sls.address.ui.AddressListActivity;
import com.purchase.sls.applyvip.ui.ApplyVipActivity;
import com.purchase.sls.bankcard.ui.BankCardListActivity;
import com.purchase.sls.bankcard.ui.PutForwardActivity;
import com.purchase.sls.browse.ui.BrowseRecordsActivity;
import com.purchase.sls.collection.ui.CollectionListActivity;
import com.purchase.sls.common.GlideHelper;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.common.UMStaticData;
import com.purchase.sls.common.unit.PersionAppPreferences;
import com.purchase.sls.common.unit.TokenManager;
import com.purchase.sls.common.unit.UmengEventUtils;
import com.purchase.sls.coupon.ui.CouponListActivity;
import com.purchase.sls.data.entity.CmIncomeInfo;
import com.purchase.sls.data.entity.CommissionInfo;
import com.purchase.sls.data.entity.PersionInfoResponse;
import com.purchase.sls.data.entity.WebViewDetailInfo;
import com.purchase.sls.ecvoucher.ui.EcVoucherActivity;
import com.purchase.sls.energy.ui.EnergyInfoActivity;
import com.purchase.sls.evaluate.ui.ToBeEvaluatedActivity;
import com.purchase.sls.goodsordermanage.ui.GoodsOrderActivity;
import com.purchase.sls.login.ui.AccountLoginActivity;
import com.purchase.sls.messages.ui.MessageNotificationActivity;
import com.purchase.sls.mine.DaggerPersonalCenterComponent;
import com.purchase.sls.mine.PersonalCenterContract;
import com.purchase.sls.mine.PersonalCenterModule;
import com.purchase.sls.mine.presenter.PersonalCenterPresenter;
import com.purchase.sls.webview.ui.WebViewActivity;
import com.umeng.commonsdk.debug.E;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/4/19.
 * 我的
 */

public class PersonalCenterFragment extends BaseFragment implements PersonalCenterContract.PersonalCenterView {

    @BindView(R.id.setting_iv)
    ImageView settingIv;
    @BindView(R.id.information_iv)
    ImageView informationIv;
    @BindView(R.id.head_photo)
    RoundedImageView headPhoto;
    @BindView(R.id.persion_name)
    TextView persionName;
    @BindView(R.id.order_ll)
    LinearLayout orderLl;
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
    @BindView(R.id.item_address)
    FrameLayout itemAddress;
    @BindView(R.id.item_rdcode)
    FrameLayout itemRdcode;
    @BindView(R.id.item_browse_records)
    FrameLayout itemBrowseRecords;
    @BindView(R.id.item_customer_service_center)
    FrameLayout itemCustomerServiceCenter;
    @BindView(R.id.item_want_cooperate)
    FrameLayout itemWantCooperate;
    @BindView(R.id.item_about_neng)
    FrameLayout itemAboutNeng;
    @BindView(R.id.bg_iv)
    ImageView bgIv;
    @BindView(R.id.item_persion_im)
    RelativeLayout itemPersionIm;
    @BindView(R.id.bg_ll)
    LinearLayout bgLl;
    @BindView(R.id.item_ecvoucher)
    FrameLayout itemEcvoucher;
    @BindView(R.id.item_apply_vip)
    FrameLayout itemApplyVip;
    @BindView(R.id.item_bankcard)
    FrameLayout itemBankcard;
    @BindView(R.id.setting_rl)
    RelativeLayout settingRl;
    @BindView(R.id.commission_explain)
    TextView commissionExplain;
    @BindView(R.id.commission_number)
    TextView commissionNumber;
    @BindView(R.id.go_to_cash)
    TextView goToCash;
    @BindView(R.id.go_to_cash_rl)
    RelativeLayout goToCashRl;
    @BindView(R.id.daily_management)
    TextView dailyManagement;
    @BindView(R.id.yesterday_income)
    TextView yesterdayIncome;
    @BindView(R.id.month_income)
    TextView monthIncome;
    @BindView(R.id.cumulative_income)
    TextView cumulativeIncome;
    @BindView(R.id.user_commission_ll)
    LinearLayout userCommissionLl;
    private boolean isFirstLoad = true;

    private PersionAppPreferences persionAppPreferences;
    private String persionInfoStr;
    private PersionInfoResponse persionInfoResponse;
    private Gson gson;
    private WebViewDetailInfo webViewDetailInfo;
    private String phoneNumber;
    private String qrCodeUrl;

    private String firstCm = "0";
    private String firstPutF="0";

    @Inject
    PersonalCenterPresenter personalCenterPresenter;

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

    @Override
    protected void initializeInjector() {
        DaggerPersonalCenterComponent.builder()
                .applicationComponent(getApplicationComponent())
                .personalCenterModule(new PersonalCenterModule(this))
                .build()
                .inject(this);
    }

    //防止被顶替，然后换账号
    private void initVeiw() {
        if (!isFirstLoad && getUserVisibleHint()) {
            persionInfoStr = persionAppPreferences.getPersionInfo();
            gson = new Gson();
            if (!TextUtils.isEmpty(persionInfoStr) && !TextUtils.isEmpty(TokenManager.getToken())) {
                persionInfoResponse = gson.fromJson(persionInfoStr, PersionInfoResponse.class);
                if (!TextUtils.isEmpty(persionInfoResponse.getAvatar())) {
                    bgIv.setVisibility(View.VISIBLE);
                    GlideHelper.load(this, persionInfoResponse.getAvatar(), R.mipmap.head_photo_icon, headPhoto);
                    GlideHelper.load(this, persionInfoResponse.getAvatar(), R.mipmap.app_icon, bgIv);
                    bgLl.setBackgroundResource(R.color.appText5);
                    bgLl.setAlpha(0.3f);
                } else {
                    GlideHelper.load(this, persionInfoResponse.getAvatar(), R.mipmap.head_photo_icon, headPhoto);
                    bgIv.setVisibility(View.INVISIBLE);
                    bgLl.setBackgroundResource(R.color.backGround24);
                }

                if (!TextUtils.isEmpty(persionInfoResponse.getNickname())) {
                    persionName.setText(persionInfoResponse.getNickname());
                } else {
                    persionName.setText(persionInfoResponse.getUsername());
                }
                phoneNumber = persionInfoResponse.getTel();
                qrCodeUrl = persionInfoResponse.getQrcode();
                if (TextUtils.equals("0", firstCm)) {
                    personalCenterPresenter.getCmIncomeInfo();
                    firstCm = "1";
                }
                if(TextUtils.equals("0",firstPutF)){
                    personalCenterPresenter.getCommissionInfo();
                    firstPutF="1";
                }
            } else {
                AccountLoginActivity.start(getActivity());
            }
        }
    }


    @OnClick({R.id.information_iv, R.id.setting_iv, R.id.order_ll, R.id.collection_ll, R.id.comment_ll, R.id.account_ll, R.id.item_energy, R.id.item_ecvoucher, R.id.item_voucher, R.id.item_rdcode, R.id.item_address, R.id.item_browse_records, R.id.item_want_cooperate, R.id.item_about_neng, R.id.bg_ll, R.id.item_customer_service_center, R.id.item_apply_vip, R.id.item_bankcard,R.id.go_to_cash})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_iv://设置
                SettingActivity.start(getActivity(), phoneNumber);
                break;
            case R.id.information_iv:
                MessageNotificationActivity.start(getActivity());
                break;
            case R.id.bg_ll://个人主页
                UmengEventUtils.statisticsClick(getActivity(), UMStaticData.SHOW_MY_INFO);
                PersonalInformationActivity.start(getActivity());
                break;
            case R.id.order_ll://订单
                GoodsOrderActivity.start(getActivity());
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
            case R.id.item_ecvoucher://兑换券
                EcVoucherActivity.start(getActivity());
                break;
            case R.id.item_address:
                AddressListActivity.start(getActivity(), "0");
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
            case R.id.item_apply_vip://申请vip
                ApplyVipActivity.start(getActivity());
                break;
            case R.id.item_bankcard://我的银行卡
                Intent intent = new Intent(getActivity(), BankCardListActivity.class);
                intent.putExtra(StaticData.IS_BANK_INTO,"0");
                startActivity(intent);
                break;
            case R.id.go_to_cash://申请提现
                firstPutF="0";
                PutForwardActivity.start(getActivity());
                break;
            default:
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void setPresenter(PersonalCenterContract.PersonalCenterPresenter presenter) {

    }

    @Override
    public void renderCmIncomeInfo(CmIncomeInfo cmIncomeInfo) {
        if (cmIncomeInfo != null) {
            yesterdayIncome.setText("+"+cmIncomeInfo.getYesterdayMoney());
            monthIncome.setText("+"+cmIncomeInfo.getMonthMoney());
            cumulativeIncome.setText("+"+cmIncomeInfo.getTotalMoney());
        }
    }

    @Override
    public void renderCommissionInfo(CommissionInfo commissionInfo) {
        if(commissionInfo!=null){
            commissionNumber.setText(commissionInfo.getBalanceMoney());
            if (TextUtils.equals("1", commissionInfo.getIsvip())) {
                goToCashRl.setVisibility(View.VISIBLE);
                userCommissionLl.setVisibility(View.VISIBLE);
            } else {
                goToCashRl.setVisibility(View.GONE);
                userCommissionLl.setVisibility(View.GONE);
            }
        }
    }
}
