package com.purchase.sls.shopdetailbuy.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.common.widget.Banner.Banner;
import com.purchase.sls.common.widget.Banner.BannerConfig;
import com.purchase.sls.common.widget.ReboundScrollView;
import com.purchase.sls.data.entity.ShopDetailsInfo;
import com.purchase.sls.data.entity.StoreInfo;
import com.purchase.sls.homepage.adapter.LikeStoreAdapter;
import com.purchase.sls.shopdetailbuy.DaggerShopDetailBuyComponent;
import com.purchase.sls.shopdetailbuy.ShopDetailBuyContract;
import com.purchase.sls.shopdetailbuy.ShopDetailBuyModule;
import com.purchase.sls.shopdetailbuy.adapter.EvaluateAdapter;
import com.purchase.sls.shopdetailbuy.presenter.ShopDetailPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/4/24.
 * 商品详情页面
 */

public class ShopDetailActivity extends BaseActivity implements ShopDetailBuyContract.ShopDetailView, LikeStoreAdapter.OnLikeStoreClickListener {

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.popularity_number)
    TextView popularityNumber;
    @BindView(R.id.popularity_ll)
    LinearLayout popularityLl;
    @BindView(R.id.shop_name)
    TextView shopName;
    @BindView(R.id.shop_description)
    TextView shopDescription;
    @BindView(R.id.shop_info_rl)
    RelativeLayout shopInfoRl;
    @BindView(R.id.back_energy_tt)
    TextView backEnergyTt;
    @BindView(R.id.back_energy_number)
    TextView backEnergyNumber;
    @BindView(R.id.back_energy_rl)
    RelativeLayout backEnergyRl;
    @BindView(R.id.local_iv)
    ImageView localIv;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.call_ll)
    LinearLayout callLl;
    @BindView(R.id.fenge_vi)
    View fengeVi;
    @BindView(R.id.local_rl)
    RelativeLayout localRl;
    @BindView(R.id.shop_detail_ll)
    LinearLayout shopDetailLl;
    @BindView(R.id.evaluate_rv)
    RecyclerView evaluateRv;
    @BindView(R.id.look_all_comment_rl)
    RelativeLayout lookAllCommentRl;
    @BindView(R.id.evaluate_ll)
    LinearLayout evaluateLl;
    @BindView(R.id.more_shop_rv)
    RecyclerView moreShopRv;
    @BindView(R.id.more_shop_ll)
    LinearLayout moreShopLl;
    @BindView(R.id.scroll_content)
    ReboundScrollView scrollContent;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.collection)
    ImageView collection;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    private String storeid;
    @Inject
    ShopDetailPresenter shopDetailPresenter;

    private StoreInfo storeInfo;
    private LikeStoreAdapter likeStoreAdapter;
    private EvaluateAdapter evaluateAdapter;
    private int collectionType=0;

    public static void start(Context context, String storeid) {
        Intent intent = new Intent(context, ShopDetailActivity.class);
        intent.putExtra(StaticData.STOREID, storeid);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        storeid = getIntent().getStringExtra(StaticData.STOREID);
        bannerInitialization();
        evaluateAdapter();
        moreStore();
        shopDetailPresenter.getShopDetail("161");
    }

    //初始化banner
    private void bannerInitialization() {
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.isAutoPlay(true);
        banner.setOnBannerClickListener(new Banner.OnBannerClickListener() {
            @Override
            public void OnBannerClick(View view, int position) {

            }
        });
    }

    //添加更多列表
    private void moreStore() {
        likeStoreAdapter = new LikeStoreAdapter(this);
        likeStoreAdapter.setOnLikeStoreClickListener(this);
        moreShopRv.setAdapter(likeStoreAdapter);
    }

    private void evaluateAdapter() {
        evaluateAdapter = new EvaluateAdapter(this);
        evaluateRv.setAdapter(evaluateAdapter);
    }


    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        DaggerShopDetailBuyComponent.builder()
                .applicationComponent(getApplicationComponent())
                .shopDetailBuyModule(new ShopDetailBuyModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void setPresenter(ShopDetailBuyContract.ShopDetailPresenter presenter) {

    }

    @Override
    public void shopDetailInfo(ShopDetailsInfo shopDetailsInfo) {
        if (shopDetailsInfo != null) {
            if (shopDetailsInfo.getStoreInfo() != null) {
                storeInfo = shopDetailsInfo.getStoreInfo();
//                banner.setImages(storeInfo.getPics());
                shopName.setText(storeInfo.getTitle());
                shopDescription.setText(storeInfo.getDescription());
                popularityNumber.setText(storeInfo.getBuzz());
                address.setText(storeInfo.getAddress());
                backEnergyNumber.setText(storeInfo.getRebate());
                if(TextUtils.equals("1",storeInfo.getFavo())){
                    collection.setSelected(true);
                    collectionType=1;
                }else {
                    collection.setSelected(false);
                }

            }
            if (shopDetailsInfo.getEvaluateResult() != null && shopDetailsInfo.getEvaluateResult().getEvaluateInfo() != null
                    && shopDetailsInfo.getEvaluateResult().getEvaluateInfo().getEvaluateItemInfos() != null) {
                evaluateAdapter.setData(shopDetailsInfo.getEvaluateResult().getEvaluateInfo().getEvaluateItemInfos());
            }
            if (shopDetailsInfo.getLikeStoreResponse() != null && shopDetailsInfo.getLikeStoreResponse().getLikeInfos() != null) {
                likeStoreAdapter.setLikeInfos(shopDetailsInfo.getLikeStoreResponse().getLikeInfos());
            }
        }

    }

    @Override
    public void addRemoveSuccess() {

    }

    @Override
    public void likeStoreClickListener(String storeid) {

    }

    @OnClick({R.id.back, R.id.collection})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.collection:
                collectionType += 1;
                if (collectionType % 2 == 0) {
                    collection.setSelected(false);
                    shopDetailPresenter.addRemoveCollection("161","2",null);
                }else {
                    collection.setSelected(true);
                    shopDetailPresenter.addRemoveCollection("161","1",null);
                }
                break;
            default:
        }
    }
}
