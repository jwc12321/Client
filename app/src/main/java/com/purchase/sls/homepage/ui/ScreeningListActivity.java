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
import com.purchase.sls.common.unit.CommonAppPreferences;
import com.purchase.sls.common.widget.GridSpacesItemDecoration;
import com.purchase.sls.data.entity.CateInfo;
import com.purchase.sls.data.entity.ClassifyInfo;
import com.purchase.sls.data.entity.CollectionStoreInfo;
import com.purchase.sls.data.entity.ScreeningListResponse;
import com.purchase.sls.data.entity.TopCateInfo;
import com.purchase.sls.homepage.DaggerHomePageComponent;
import com.purchase.sls.homepage.HomePageContract;
import com.purchase.sls.homepage.HomePageModule;
import com.purchase.sls.homepage.adapter.LikeStoreAdapter;
import com.purchase.sls.homepage.adapter.ScreeningFirstAdapter;
import com.purchase.sls.homepage.adapter.ScreeningSecondAdapter;
import com.purchase.sls.homepage.adapter.ScreeningThirdAdapter;
import com.purchase.sls.homepage.adapter.TopCateAdapter;
import com.purchase.sls.homepage.presenter.ScreeningListPresenter;
import com.purchase.sls.shopdetailbuy.ui.ShopDetailActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.umeng.socialize.utils.ContextUtil.getContext;

/**
 * Created by JWC on 2018/4/23.
 * 首页筛选列表
 */

public class ScreeningListActivity extends BaseActivity implements HomePageContract.ScreeningListView, ScreeningFirstAdapter.OnFirstItemOnClickListener, ScreeningSecondAdapter.OnSecondItemOnClickListener, ScreeningThirdAdapter.OnThirdItemOnClickListener, LikeStoreAdapter.OnLikeStoreClickListener, TopCateAdapter.OnTopCateItemClickListener {


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
    @BindView(R.id.topcate_rv)
    RecyclerView topcateRv;
    @BindView(R.id.first_title)
    TextView firstTitle;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.topcate_ll)
    LinearLayout topcateLl;

    private String bussinessCid;
    private String bussinessName;
    private String businessSort = "0";
    private String businessScreen = "0";
    private String storename;
    private String longitude;
    private String latitude;
    private String city;

    private String choiceType = "0";
    private LikeStoreAdapter likeStoreAdapter;

    private ScreeningFirstAdapter screeningFirstAdapter;
    private int choiceFirstInt = 0;
    private int clickFirstType = 1;

    private ScreeningSecondAdapter screeningSecondAdapter;
    private int choiceSecondInt = 0;
    private int clickSecondType = 1;

    private ScreeningThirdAdapter screeningThirdAdapter;
    private int choiceThirdInt = 0;
    private int clickThirdType = 1;

    private CommonAppPreferences commonAppPreferences;
    private String location;

    private TopCateAdapter topCateAdapter;


    @Inject
    ScreeningListPresenter screeningListPresenter;

    public static void start(Context context, String cid, String name, String sum, String storename) {
        Intent intent = new Intent(context, ScreeningListActivity.class);
        intent.putExtra(StaticData.BUSINESS_CID, cid);
        intent.putExtra(StaticData.BUSINESS_NAME, name);
        intent.putExtra(StaticData.BUSINESS_SUM, sum);
        intent.putExtra(StaticData.STORE_NAME, storename);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screening_list);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initView();

    }

    private void initView() {
        commonAppPreferences = new CommonAppPreferences(this);
        city = commonAppPreferences.getCity();
        longitude = commonAppPreferences.getLongitude();
        latitude = commonAppPreferences.getLatitude();
        location = longitude + "," + latitude;
        bussinessCid = getIntent().getStringExtra(StaticData.BUSINESS_CID);
        bussinessName = getIntent().getStringExtra(StaticData.BUSINESS_NAME);
        storename = getIntent().getStringExtra(StaticData.STORE_NAME);
        title.setText(bussinessName);
        if (TextUtils.equals("搜索结果", bussinessName)) {
            choiceFirstTt.setText("分类");
        } else {
            choiceFirstTt.setText(bussinessName);
        }
        refreshLayout.setOnRefreshListener(mOnRefreshListener);
        likeStore();
        addTopCateAdapter();
        screenFirst();
        screenSecond();
        screenThird();
        screeningListPresenter.getClassifyInfo(bussinessCid);
        screeningListPresenter.getScreeningList("1", city, bussinessCid, "0", "0", storename, location);
    }

    private void likeStore() {
        likeStoreAdapter = new LikeStoreAdapter(this, city, longitude, latitude);
        likeStoreAdapter.setOnLikeStoreClickListener(this);
        choiceShopRv.setAdapter(likeStoreAdapter);
    }

    //增加二级分类上面部分
    private void addTopCateAdapter() {
        topcateRv.setLayoutManager(new GridLayoutManager(getContext(), 4));
        int space = 41;
        topcateRv.addItemDecoration(new GridSpacesItemDecoration(space, false));
        topCateAdapter = new TopCateAdapter(this);
        topCateAdapter.setOnTopCateItemClickListener(this);
        topcateRv.setAdapter(topCateAdapter);
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
    }

    private void screenThird() {
        typeThirdRy.setLayoutManager(new GridLayoutManager(this, 3));
        int space = getResources().getDimensionPixelSize(R.dimen.space_bootom);
        typeThirdRy.addItemDecoration(new GridSpacesItemDecoration(space, false));
        screeningThirdAdapter = new ScreeningThirdAdapter();
        screeningThirdAdapter.setOnThirdItemOnClickListener(this);
        typeThirdRy.setAdapter(screeningThirdAdapter);
        thirdBlackBackground.setAlpha(0.4f);
    }

    HeaderViewLayout.OnRefreshListener mOnRefreshListener = new HeaderViewLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            screeningListPresenter.getScreeningList("0", city, bussinessCid, businessSort, businessScreen, storename, location);
        }

        @Override
        public void onLoadMore() {
            screeningListPresenter.getMoreScreeningList(city, bussinessCid, businessSort, businessScreen, storename, location);
        }

        ;

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
    public void renderClassifyInfo(ClassifyInfo classifyInfo) {
        if (classifyInfo != null) {
            if (classifyInfo.getCateInfos() != null && classifyInfo.getCateInfos().size() > 0) {
                List<CateInfo> cateInfos = classifyInfo.getCateInfos();
                CateInfo cateInfo = new CateInfo();
                cateInfo.setId(bussinessCid);
                cateInfo.setName("全部");
                cateInfos.add(0, cateInfo);
                screeningFirstAdapter.setData(cateInfos);
            }
            if (classifyInfo.getSortInfos() != null && classifyInfo.getSortInfos().size() > 0) {
                screeningSecondAdapter.setData(classifyInfo.getSortInfos());
            }
            if (classifyInfo.getScreenInfos() != null) {
                screeningThirdAdapter.setData(classifyInfo.getScreenInfos());
            }
        }

    }

    @Override
    public void screeningListInfo(ScreeningListResponse screeningListResponse) {
        refreshLayout.stopRefresh();
        refreshLayout.setCanLoadMore(true);
        if (screeningListResponse != null) {
            if (screeningListResponse.getLikeStoreResponse() != null && screeningListResponse.getLikeStoreResponse().getCollectionStoreInfos() != null && screeningListResponse.getLikeStoreResponse().getCollectionStoreInfos().size() > 0) {
                likeStoreAdapter.setLikeInfos(screeningListResponse.getLikeStoreResponse().getCollectionStoreInfos());
                refreshLayout.setCanLoadMore(true);
                choiceShopRv.setVisibility(View.VISIBLE);
                emptyView.setVisibility(View.GONE);
            } else {
                refreshLayout.setCanLoadMore(false);
                choiceShopRv.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
            }
            if (screeningListResponse.getTopCateInfos() != null && screeningListResponse.getTopCateInfos().size() > 0) {
                topcateLl.setVisibility(View.VISIBLE);
            } else {
                topcateLl.setVisibility(View.GONE);
            }
            topCateAdapter.setData(screeningListResponse.getTopCateInfos());
        } else {
            refreshLayout.setCanLoadMore(false);
            choiceShopRv.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            topcateLl.setVisibility(View.GONE);
            topCateAdapter.setData(null);
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

    @OnClick({R.id.back, R.id.choice_first_ll, R.id.choice_second_ll, R.id.choice_third_ll, R.id.first_black_background, R.id.second_black_background, R.id.third_black_background, R.id.ok_bg})
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
                clickThirdType = 1;
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
                clickThirdType = 1;
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
                clickSecondType = 1;
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
                screeningListPresenter.getScreeningList("1", city, bussinessCid, businessSort, businessScreen, storename, location);
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
        screeningListPresenter.getClassifyInfo(bussinessCid);
        screeningListPresenter.getScreeningList("1", city, bussinessCid, businessSort, businessScreen, storename, location);
    }

    @Override
    public void onSecondItemClick(String sort, int position) {
        businessSort = sort;
        chooseTypeSecondLl.setVisibility(View.GONE);
        choiceSecondInt = position;
        screeningListPresenter.getScreeningList("1", city, bussinessCid, businessSort, businessScreen, storename, location);
    }

    @Override
    public void onThirdItemClick(String screenId, int itemPosition) {
        businessScreen = screenId;
        screeningThirdAdapter.setPosittion(choiceThirdInt, itemPosition);
        choiceThirdInt = itemPosition;
        businessScreen = String.valueOf(itemPosition);
    }

    @Override
    public void likeStoreClickListener(String storeid) {
        ShopDetailActivity.start(this, storeid);
    }

    @Override
    public void topCateItemClickListener(TopCateInfo topCateInfo) {
        ScreeningListActivity.start(this, topCateInfo.getId(), topCateInfo.getName(), "0", "");
        this.finish();
    }
}