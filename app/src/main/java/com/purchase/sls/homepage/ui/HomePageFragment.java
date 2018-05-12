package com.purchase.sls.homepage.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.purchase.sls.BaseFragment;
import com.purchase.sls.R;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.common.cityList.style.citylist.bean.CityInfoBean;
import com.purchase.sls.common.location.LocationHelper;
import com.purchase.sls.common.refreshview.HeaderViewLayout;
import com.purchase.sls.common.unit.CommonAppPreferences;
import com.purchase.sls.common.unit.PermissionUtil;
import com.purchase.sls.common.unit.StatusBarUtil;
import com.purchase.sls.common.widget.Banner.Banner;
import com.purchase.sls.common.widget.Banner.BannerConfig;
import com.purchase.sls.common.widget.GradationScrollView;
import com.purchase.sls.common.widget.GridSpacesItemDecoration;
import com.purchase.sls.common.widget.LimitScrollerView;
import com.purchase.sls.data.entity.BannerHotResponse;
import com.purchase.sls.data.entity.CollectionStoreInfo;
import com.purchase.sls.homepage.DaggerHomePageComponent;
import com.purchase.sls.homepage.HomePageContract;
import com.purchase.sls.homepage.HomePageModule;
import com.purchase.sls.homepage.adapter.HotServiceAdapter;
import com.purchase.sls.homepage.adapter.LikeStoreAdapter;
import com.purchase.sls.homepage.presenter.HomePagePresenter;
import com.purchase.sls.shopdetailbuy.ui.ShopDetailActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/4/19.
 * 首页
 */

public class HomePageFragment extends BaseFragment implements HomePageContract.HomepageView, HotServiceAdapter.OnHotItemClickListener, LikeStoreAdapter.OnLikeStoreClickListener,GradationScrollView.ScrollViewListener {

    @BindView(R.id.refreshLayout)
    HeaderViewLayout refreshLayout;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.hot_search_link)
    RecyclerView hotSearchLink;
    @BindView(R.id.scan)
    ImageView scan;
    @BindView(R.id.search_tt)
    TextView searchTt;
    @BindView(R.id.choice_city)
    TextView choiceCity;
    @BindView(R.id.like_store_rv)
    RecyclerView likeStoreRv;
    @BindView(R.id.limitScroll)
    LimitScrollerView limitScroll;
    @BindView(R.id.search_ll)
    LinearLayout searchLl;
    @BindView(R.id.scrollview)
    GradationScrollView scrollview;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;


    private LocationHelper mLocationHelper;
    private String city;
    private List<BannerHotResponse.BannerInfo> bannerInfos;
    private List<String> bannerImages;
    private HotServiceAdapter hotServiceAdapter;
    private LikeStoreAdapter likeStoreAdapter;
    private MyLimitScrollAdapter myLimitScrollAdapter;

    private static final int REQUEST_PERMISSION_LOCATION = 1;
    private static final int REFRESS_LOCATION_SCAN = 2;
    private static final int REFRESS_LOCATION_CODE = 3;
    private static final int REQUEST_CODE_CAMERA = 4;

    private String longitude;
    private String latitude;

    @Inject
    HomePagePresenter homePagePresenter;

    private CommonAppPreferences commonAppPreferences;

    public HomePageFragment() {
    }

    public static HomePageFragment newInstance() {
        HomePageFragment homePageFragment = new HomePageFragment();
        return homePageFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        StatusBarUtil.setImgTransparent(getActivity());
        View rootview = inflater.inflate(R.layout.fragment_homepage, container, false);
        ButterKnife.bind(this, rootview);
        return rootview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

    }

    private void initView() {
        commonAppPreferences=new CommonAppPreferences(getActivity());
        scrollview.setScrollViewListener(this);
        refreshLayout.setOnRefreshListener(mOnRefreshListener);
        bannerInitialization();
        hotService();
        scrollerUpDown();
        likeStore();
        mapLocal();
        homePagePresenter.getLikeStore();

    }

    @Override
    public void onResume() {
        super.onResume();
        limitScroll.startScroll();
    }

    //设置热门
    private void hotService() {
        hotSearchLink.setLayoutManager(new GridLayoutManager(getContext(), 5));
        int space = getResources().getDimensionPixelSize(R.dimen.space_bootom);
        hotSearchLink.addItemDecoration(new GridSpacesItemDecoration(space, false));
        hotServiceAdapter = new HotServiceAdapter(getActivity());
        hotServiceAdapter.setOnHotItemClickListener(this);
        hotSearchLink.setAdapter(hotServiceAdapter);
    }

    //添加猜你喜欢列表
    private void likeStore() {
        likeStoreAdapter = new LikeStoreAdapter(getActivity());
        likeStoreAdapter.setOnLikeStoreClickListener(this);
        likeStoreRv.setAdapter(likeStoreAdapter);
    }

    //地图定位
    private void mapLocal() {
        mLocationHelper = LocationHelper.sharedInstance(getContext());
        mLocationHelper.addOnLocatedListener(new LocationHelper.OnLocatedListener() {
            @Override
            public void onLocated(AMapLocation aMapLocation) {
                city = aMapLocation.getCity();
                longitude=aMapLocation.getLongitude()+"";
                latitude=aMapLocation.getLatitude()+"";
                homePagePresenter.getBannerHotInfo(city);
                Log.d("1111", "城市" + city+"经纬度"+longitude+","+latitude);
                commonAppPreferences.setLocalAddress(city,longitude,latitude);
                likeStoreAdapter.setCity(city,longitude,latitude);
            }
        });

        if (requestRuntimePermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,}, REQUEST_PERMISSION_LOCATION)) {
            mLocationHelper.start();
        }
    }

    //初始化banner
    private void bannerInitialization() {
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.isAutoPlay(true);
        banner.setOnBannerClickListener(new Banner.OnBannerClickListener() {
            @Override
            public void OnBannerClick(View view, int position) {
//                showMessage("点击了第" + position + "张图片");
                if (bannerInfos != null && bannerInfos.size() >= position) {
                    BannerHotResponse.BannerInfo bannerInfo = bannerInfos.get(position - 1);
                }

            }
        });
    }

    //设置上下轮播图
    private void scrollerUpDown() {
        //API:1、设置数据适配器
        myLimitScrollAdapter = new MyLimitScrollAdapter();
        limitScroll.setDataAdapter(myLimitScrollAdapter);
    }

    HeaderViewLayout.OnRefreshListener mOnRefreshListener = new HeaderViewLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            homePagePresenter.getBannerHotInfo("杭州");
            homePagePresenter.getLikeStore();
        }

        @Override
        public void onLoadMore() {
            homePagePresenter.getMoreLikeStore();
        }

        @Override
        public void onModeChanged(@HeaderViewLayout.Mode int mode) {
        }
    };

    private boolean isFirstLoad = true;

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
    protected void initializeInjector() {
        DaggerHomePageComponent.builder()
                .applicationComponent(getApplicationComponent())
                .homePageModule(new HomePageModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void setPresenter(HomePageContract.HomepagePresenter presenter) {

    }

    @Override
    public void bannerHotInfo(BannerHotResponse bannerHotResponse) {
        refreshLayout.stopRefresh();
        bannerInfos = bannerHotResponse.getBannerInfos();
        bannerImages = new ArrayList<>();
        for (BannerHotResponse.BannerInfo bannerInfo : bannerInfos) {
            bannerImages.add(bannerInfo.getBanner());
        }
        banner.setImages(bannerImages);
        myLimitScrollAdapter.setDatas(bannerHotResponse.getArticleInfo().getDatainfos());
        hotServiceAdapter.setData(bannerHotResponse.getStorecateInfos());
    }

    @Override
    public void likeStroeInfo(List<CollectionStoreInfo> collectionStoreInfos) {
        refreshLayout.stopRefresh();
        refreshLayout.setCanLoadMore(true);
        likeStoreAdapter.setLikeInfos(collectionStoreInfos);
    }

    @Override
    public void moreLikeStroeInfo(List<CollectionStoreInfo> collectionStoreInfos) {
        refreshLayout.stopRefresh();
        if (collectionStoreInfos != null && collectionStoreInfos.size() > 0) {
            refreshLayout.setCanLoadMore(true);
            likeStoreAdapter.addMore(collectionStoreInfos);
        } else {
            refreshLayout.setCanLoadMore(false);
        }
    }

    @OnClick({R.id.choice_city, R.id.scan, R.id.search_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.choice_city:
                Intent intent=new Intent(getActivity(),ChoiceCityActivity.class);
                intent.putExtra(StaticData.CHOICE_CITY, city);
                startActivityForResult(intent,REFRESS_LOCATION_CODE);
                break;
            case R.id.scan:
                scan();
                break;
            case R.id.search_ll:
                SearchShopActivity.start(getActivity());
                break;
            default:
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REFRESS_LOCATION_CODE:
                    if (data != null) {
                        Bundle bundle = data.getExtras();
                        CityInfoBean cityInfoBean = (CityInfoBean) bundle.getParcelable(StaticData.CHOICE_CITY);
                        if (cityInfoBean == null) {
                            return;
                        }
                        choiceCity.setText(cityInfoBean.getName());
                    }
                    break;
                case REFRESS_LOCATION_SCAN:
                    break;
                default:
            }
        }
    }

    /**
     * 扫描
     */
    void scan() {
        List<String> group = new ArrayList<>();
        group.add(Manifest.permission_group.CAMERA);
        if (requestRuntimePermissions(PermissionUtil.permissionGroup(group, null), REQUEST_CODE_CAMERA)) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Intent intent = new Intent(getActivity(), QrCodeScanActivity.class);
            startActivityForResult(intent, REFRESS_LOCATION_SCAN);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_LOCATION:
                for (int gra : grantResults) {
                    if (gra != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                mLocationHelper.start();
                break;
            case REQUEST_CODE_CAMERA:
                if (grantResults.length > 0) {
                    for (int gra : grantResults) {
                        if (gra != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                    }
                }
                scan();
                break;

        }
    }

    /**
     * 点击热门
     *
     * @param storecateInfo
     */
    @Override
    public void hotItemClickListener(BannerHotResponse.StorecateInfo storecateInfo) {
        ScreeningListActivity.start(getActivity(), storecateInfo.getId(), storecateInfo.getName(), storecateInfo.getSum(), "");
    }

    @Override
    public void likeStoreClickListener(String storeid) {
        ShopDetailActivity.start(getActivity(), storeid);
    }

    @Override
    public void onScrollChanged(GradationScrollView scrollView, int x, int y, int oldx, int oldy) {
        // TODO Auto-generated method stub
        if (y <= 0) {   //设置标题的背景颜色
            titleRel.setBackgroundColor(Color.argb((int) 0, 144,151,166));
        } else if (y > 0 && y <= 180) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
            float scale = (float) y / 180;
            float alpha = (255 * scale);
            titleRel.setBackgroundColor(Color.argb((int) alpha, 255,101,40));
        } else {    //滑动到banner下面设置普通颜色
            titleRel.setBackgroundColor(Color.argb((int) 255, 255,101,40));
        }
    }

    //TODO 修改适配器绑定数据
    class MyLimitScrollAdapter implements LimitScrollerView.LimitScrollAdapter {

        private List<BannerHotResponse.ArticleInfo.Datainfo> datas;

        public void setDatas(List<BannerHotResponse.ArticleInfo.Datainfo> datas) {
            this.datas = datas;
            //API:2、开始滚动
            limitScroll.startScroll();
        }

        @Override
        public int getCount() {
            return datas == null ? 0 : datas.size();
        }

        @Override
        public View getView(int index) {
            View itemView = LayoutInflater.from(getActivity()).inflate(R.layout.limit_scroller_item, null, false);
            ImageView iv_icon = (ImageView) itemView.findViewById(R.id.iv_icon);
            TextView tv_text = (TextView) itemView.findViewById(R.id.tv_text);

            //绑定数据
            BannerHotResponse.ArticleInfo.Datainfo data = datas.get(index);
            itemView.setTag(data);
            tv_text.setText(data.getTitle());
            return itemView;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        limitScroll.cancel();
    }
}
