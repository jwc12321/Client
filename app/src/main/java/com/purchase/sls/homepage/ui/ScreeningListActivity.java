package com.purchase.sls.homepage.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.common.refreshview.HeaderViewLayout;
import com.purchase.sls.common.widget.GridSpacesItemDecoration;
import com.purchase.sls.data.entity.CollectionStoreInfo;
import com.purchase.sls.data.entity.ComprehensiveInfo;
import com.purchase.sls.data.entity.LikeStoreResponse;
import com.purchase.sls.data.entity.MealsNumberInfo;
import com.purchase.sls.data.entity.ScreeningListResponse;
import com.purchase.sls.homepage.DaggerHomePageComponent;
import com.purchase.sls.homepage.HomePageContract;
import com.purchase.sls.homepage.HomePageModule;
import com.purchase.sls.homepage.adapter.LikeStoreAdapter;
import com.purchase.sls.homepage.adapter.ScreeningFirstAdapter;
import com.purchase.sls.homepage.adapter.ScreeningSecondAdapter;
import com.purchase.sls.homepage.adapter.ScreeningThirdAdapter;
import com.purchase.sls.homepage.presenter.ScreeningListPresenter;
import com.purchase.sls.shopdetailbuy.ui.ShopDetailActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/4/23.
 * 首页筛选列表
 */

public class ScreeningListActivity extends BaseActivity implements HomePageContract.ScreeningListView, ScreeningFirstAdapter.OnFirstItemOnClickListener, ScreeningSecondAdapter.OnSecondItemOnClickListener, ScreeningThirdAdapter.OnThirdItemOnClickListener,LikeStoreAdapter.OnLikeStoreClickListener {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.search)
    ImageView search;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.choice_first_tt)
    TextView choiceFirstTt;
    @BindView(R.id.choice_first_iv)
    ImageView choiceFirstIv;
    @BindView(R.id.choice_first_ll)
    LinearLayout choiceFirstLl;
    @BindView(R.id.choice_second_tt)
    TextView choiceSecondTt;
    @BindView(R.id.choice_second_iv)
    ImageView choiceSecondIv;
    @BindView(R.id.choice_second_ll)
    LinearLayout choiceSecondLl;
    @BindView(R.id.choice_third_tt)
    TextView choiceThirdTt;
    @BindView(R.id.choice_third_iv)
    ImageView choiceThirdIv;
    @BindView(R.id.choice_third_ll)
    LinearLayout choiceThirdLl;
    @BindView(R.id.choose_attribute)
    LinearLayout chooseAttribute;
    @BindView(R.id.choice_shop_rv)
    RecyclerView choiceShopRv;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.empty_view)
    NestedScrollView emptyView;
    @BindView(R.id.refreshLayout)
    HeaderViewLayout refreshLayout;
    @BindView(R.id.type_first_ry)
    RecyclerView typeFirstRy;
    @BindView(R.id.first_black_background)
    LinearLayout firstBlackBackground;
    @BindView(R.id.choose_type_first_ll)
    LinearLayout chooseTypeFirstLl;
    @BindView(R.id.type_second_ry)
    RecyclerView typeSecondRy;
    @BindView(R.id.second_black_background)
    LinearLayout secondBlackBackground;
    @BindView(R.id.choose_type_second_ll)
    LinearLayout chooseTypeSecondLl;
    @BindView(R.id.type_third_ry)
    RecyclerView typeThirdRy;
    @BindView(R.id.ok_bg)
    Button okBg;
    @BindView(R.id.choose_type_third_ll)
    LinearLayout chooseTypeThirdLl;
    @BindView(R.id.third_black_background)
    LinearLayout thirdBlackBackground;

    private String city;
    private String bussinessCid;
    private String bussinessName;
    private String businessSort;
    private String businessSum;
    private String businessScreen;

    private String allCid;
    private String allName;
    private String allSum;
    private String choiceType = "0";
    private LikeStoreAdapter likeStoreAdapter;

    private ScreeningFirstAdapter screeningFirstAdapter;
    private int choiceFirstInt = 0;
    private int clickFirstType = 1;

    private ScreeningSecondAdapter screeningSecondAdapter;
    private int choiceSecondInt = 0;
    private int clickSecondType = 1;
    private List<ComprehensiveInfo> comprehensiveInfos;

    private ScreeningThirdAdapter screeningThirdAdapter;
    private List<MealsNumberInfo> mealsNumberInfos;
    private int choiceThirdInt = 0;
    private int clickThirdType =1;

    @Inject
    ScreeningListPresenter screeningListPresenter;

    public static void start(Context context, String city, String cid, String name, String sum) {
        Intent intent = new Intent(context, ScreeningListActivity.class);
        intent.putExtra(StaticData.CHOICE_CITY, city);
        intent.putExtra(StaticData.BUSINESS_CID, cid);
        intent.putExtra(StaticData.BUSINESS_NAME, name);
        intent.putExtra(StaticData.BUSINESS_SUM, sum);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screening_list);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
//        city = getIntent().getStringExtra(StaticData.CHOICE_CITY);
        city = "衢州市";

        allCid = getIntent().getStringExtra(StaticData.BUSINESS_CID);
        allName = getIntent().getStringExtra(StaticData.BUSINESS_NAME);
        allSum = getIntent().getStringExtra(StaticData.BUSINESS_SUM);
        bussinessCid = allCid;
        bussinessName = allName;
        businessSum = allSum;
        title.setText(bussinessName);
        choiceFirstTt.setText(bussinessName);
        refreshLayout.setOnRefreshListener(mOnRefreshListener);
        likeStore();
        screenFirst();
        screenSecond();
        screenThird();
        screeningListPresenter.getScreeningList(city, bussinessCid, "", "");
    }

    private void likeStore() {
        likeStoreAdapter = new LikeStoreAdapter(this);
        likeStoreAdapter.setOnLikeStoreClickListener(this);
        choiceShopRv.setAdapter(likeStoreAdapter);
    }

    private void screenFirst() {
        screeningFirstAdapter = new ScreeningFirstAdapter();
        screeningFirstAdapter.setFirstOnItemOnClickListener(this);
        typeFirstRy.setAdapter(screeningFirstAdapter);
        firstBlackBackground.setAlpha(0.4f);
    }

    private void screenSecond() {
        screeningSecondAdapter = new ScreeningSecondAdapter();
        screeningSecondAdapter.setOnSecondItemOnClickListener(this);
        typeSecondRy.setAdapter(screeningSecondAdapter);
        secondBlackBackground.setAlpha(0.4f);
        comprehensiveInfos = new ArrayList<>();
        ComprehensiveInfo comprehensiveInfo1 = new ComprehensiveInfo("普通排序", "1");
        ComprehensiveInfo comprehensiveInfo2 = new ComprehensiveInfo("人气排序", "2");
        ComprehensiveInfo comprehensiveInfo3 = new ComprehensiveInfo("人均排序", "3");
        comprehensiveInfos.add(comprehensiveInfo1);
        comprehensiveInfos.add(comprehensiveInfo2);
        comprehensiveInfos.add(comprehensiveInfo3);
        screeningSecondAdapter.setData(comprehensiveInfos);
    }

    private void screenThird() {
        typeThirdRy.setLayoutManager(new GridLayoutManager(this, 3));
        int space = getResources().getDimensionPixelSize(R.dimen.space_bootom);
        typeThirdRy.addItemDecoration(new GridSpacesItemDecoration(space, false));
        screeningThirdAdapter = new ScreeningThirdAdapter();
        screeningThirdAdapter.setOnThirdItemOnClickListener(this);
        typeThirdRy.setAdapter(screeningThirdAdapter);
        thirdBlackBackground.setAlpha(0.4f);
        mealsNumberInfos=new ArrayList<>();
        MealsNumberInfo mealsNumberInfo0=new MealsNumberInfo("0","不限");
        MealsNumberInfo mealsNumberInfo1=new MealsNumberInfo("1","100以下");
        MealsNumberInfo mealsNumberInfo2=new MealsNumberInfo("2","100-500");
        MealsNumberInfo mealsNumberInfo3=new MealsNumberInfo("3","500-1000");
        MealsNumberInfo mealsNumberInfo4=new MealsNumberInfo("4","1000-2000");
        MealsNumberInfo mealsNumberInfo5=new MealsNumberInfo("5","1000-2000");
        mealsNumberInfos.add(mealsNumberInfo0);
        mealsNumberInfos.add(mealsNumberInfo1);
        mealsNumberInfos.add(mealsNumberInfo2);
        mealsNumberInfos.add(mealsNumberInfo3);
        mealsNumberInfos.add(mealsNumberInfo4);
        mealsNumberInfos.add(mealsNumberInfo5);
        screeningThirdAdapter.setData(mealsNumberInfos);
    }

    HeaderViewLayout.OnRefreshListener mOnRefreshListener = new HeaderViewLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            if (TextUtils.equals("1", choiceType) || TextUtils.equals("0", choiceType)) {
                screeningListPresenter.getScreeningList(city, bussinessCid, "", "");
            } else if (TextUtils.equals("2", choiceType)) {
                screeningListPresenter.getScreeningList(city, allCid, businessSort, "");
            } else {

            }
        }

        @Override
        public void onLoadMore() {
            if (TextUtils.equals("1", choiceType) || TextUtils.equals("0", choiceType)) {
                screeningListPresenter.getMoreScreeningList(city, bussinessCid, "", "");
            } else if (TextUtils.equals("2", choiceType)) {
                screeningListPresenter.getMoreScreeningList(city, allCid, businessSort, "");
            } else {

            }
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
        super.initializeInjector();
        DaggerHomePageComponent.builder()
                .applicationComponent(getApplicationComponent())
                .homePageModule(new HomePageModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void setPresenter(HomePageContract.ScreeningListPresenter presenter) {

    }

    @Override
    public void screeningListInfo(ScreeningListResponse screeningListResponse) {
        refreshLayout.stopRefresh();
        refreshLayout.setCanLoadMore(true);
        if (screeningListResponse != null) {
            if (screeningListResponse.getLikeStoreResponse() != null && screeningListResponse.getLikeStoreResponse().getCollectionStoreInfos() != null) {
                likeStoreAdapter.setLikeInfos(screeningListResponse.getLikeStoreResponse().getCollectionStoreInfos());
            }
            if (screeningListResponse.getCateInfos() != null && screeningListResponse.getCateInfos().size() > 0) {
                List<ScreeningListResponse.CateInfo> cateInfos = screeningListResponse.getCateInfos();
                ScreeningListResponse.CateInfo cateInfo = new ScreeningListResponse.CateInfo();
                cateInfo.setId(allCid);
                cateInfo.setName("全部");
                cateInfo.setSum(allSum);
                cateInfos.add(0, cateInfo);
                screeningFirstAdapter.setData(cateInfos);
            }
        }
    }

    @Override
    public void moreScreeningListInfo(ScreeningListResponse screeningListResponse) {
        refreshLayout.stopRefresh();
        if (screeningListResponse != null && screeningListResponse.getLikeStoreResponse() != null && screeningListResponse.getLikeStoreResponse().getCollectionStoreInfos() != null) {
            List<CollectionStoreInfo> likeInfos = screeningListResponse.getLikeStoreResponse().getCollectionStoreInfos();
            if (likeInfos != null && likeInfos.size() > 0) {
                refreshLayout.setCanLoadMore(true);
                likeStoreAdapter.addMore(likeInfos);
            } else {
                refreshLayout.setCanLoadMore(false);
            }
        }
    }

    @OnClick({R.id.back, R.id.choice_first_ll, R.id.choice_second_ll, R.id.choice_third_ll, R.id.first_black_background, R.id.second_black_background,R.id.third_black_background,R.id.ok_bg})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.choice_first_ll:
                choiceType = "1";
                changeOption(choiceType);
                clickFirstType += 1;
                if (clickFirstType % 2 == 0) {
                    chooseTypeFirstLl.setVisibility(View.VISIBLE);
                    screeningFirstAdapter.setPosition(choiceFirstInt);
                } else {
                    chooseTypeFirstLl.setVisibility(View.GONE);
                }
                chooseTypeSecondLl.setVisibility(View.GONE);
                chooseTypeThirdLl.setVisibility(View.GONE);
                clickSecondType = 1;
                clickThirdType=1;
                break;
            case R.id.choice_second_ll:
                choiceType = "2";
                changeOption(choiceType);
                clickSecondType += 1;
                if (clickSecondType % 2 == 0) {
                    chooseTypeSecondLl.setVisibility(View.VISIBLE);
                    screeningSecondAdapter.setPosition(choiceSecondInt);
                } else {
                    chooseTypeSecondLl.setVisibility(View.GONE);
                }
                chooseTypeFirstLl.setVisibility(View.GONE);
                chooseTypeThirdLl.setVisibility(View.GONE);
                clickFirstType = 1;
                clickThirdType=1;
                break;
            case R.id.choice_third_ll:
                choiceType = "3";
                changeOption(choiceType);
                clickThirdType += 1;
                if (clickThirdType % 2 == 0) {
                    chooseTypeThirdLl.setVisibility(View.VISIBLE);
                } else {
                    chooseTypeThirdLl.setVisibility(View.GONE);
                }
                chooseTypeFirstLl.setVisibility(View.GONE);
                chooseTypeSecondLl.setVisibility(View.GONE);
                clickFirstType = 1;
                clickSecondType=1;
                break;
            case R.id.first_black_background:
                chooseTypeFirstLl.setVisibility(View.GONE);
                break;
            case R.id.second_black_background:
                chooseTypeSecondLl.setVisibility(View.GONE);
                break;
            case R.id.third_black_background:
                chooseTypeThirdLl.setVisibility(View.GONE);
                break;
            case R.id.ok_bg:
                chooseTypeThirdLl.setVisibility(View.GONE);
                screeningListPresenter.getScreeningList(city, allCid, "", businessScreen);
                break;
            default:
        }
    }

    /**
     * 全部 综合榜 筛选字和图切换
     *
     * @param choice
     */
    private void changeOption(String choice) {
        choiceFirstTt.setSelected(TextUtils.equals("1", choice));
        choiceSecondTt.setSelected(TextUtils.equals("2", choice));
        choiceThirdTt.setSelected(TextUtils.equals("3", choice));
        choiceFirstIv.setSelected(TextUtils.equals("1", choice));
        choiceSecondIv.setSelected(TextUtils.equals("2", choice));
        choiceThirdIv.setSelected(TextUtils.equals("3", choice));
    }

    @Override
    public void onFirstItemClick(String id, int position) {
        bussinessCid = id;
        chooseTypeFirstLl.setVisibility(View.GONE);
        choiceFirstInt = position;
        screeningListPresenter.getScreeningList(city, bussinessCid, "", "");
    }

    @Override
    public void onSecondItemClick(String sort, int position) {
        businessSort = sort;
        chooseTypeSecondLl.setVisibility(View.GONE);
        choiceSecondInt = position;
        screeningListPresenter.getScreeningList(city, allCid, businessSort, "");
    }

    @Override
    public void onThirdItemClick(String screenId, int itemPosition) {
        businessScreen=screenId;
        screeningThirdAdapter.setPosittion(choiceThirdInt,itemPosition);
        choiceThirdInt=itemPosition;
        businessScreen=String.valueOf(itemPosition);
    }

    @Override
    public void likeStoreClickListener(String storeid) {
        ShopDetailActivity.start(this,storeid);
    }
}
