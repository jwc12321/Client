package com.purchase.sls.homepage.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.amap.api.location.AMapLocation;
import com.makeramen.roundedimageview.RoundedImageView;
import com.purchase.sls.BaseFragment;
import com.purchase.sls.BuildConfig;
import com.purchase.sls.R;
import com.purchase.sls.common.GlideHelper;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.common.UMStaticData;
import com.purchase.sls.common.cityList.style.citylist.bean.CityInfoBean;
import com.purchase.sls.common.location.LocationHelper;
import com.purchase.sls.common.refreshview.HeaderViewLayout;
import com.purchase.sls.common.unit.CityManager;
import com.purchase.sls.common.unit.CommonAppPreferences;
import com.purchase.sls.common.unit.DownloadService;
import com.purchase.sls.common.unit.PermissionUtil;
import com.purchase.sls.common.unit.UmengEventUtils;
import com.purchase.sls.common.widget.Banner.Banner;
import com.purchase.sls.common.widget.Banner.BannerConfig;
import com.purchase.sls.common.widget.GradationScrollView;
import com.purchase.sls.common.widget.dialog.CommonDialog;
import com.purchase.sls.data.RemoteDataException;
import com.purchase.sls.data.entity.BannerHotResponse;
import com.purchase.sls.data.entity.ChangeAppInfo;
import com.purchase.sls.data.entity.CollectionStoreInfo;
import com.purchase.sls.data.entity.HNearbyShopsInfo;
import com.purchase.sls.homepage.DaggerHomePageComponent;
import com.purchase.sls.homepage.HomePageContract;
import com.purchase.sls.homepage.HomePageModule;
import com.purchase.sls.homepage.adapter.HNearbyShopsAdapter;
import com.purchase.sls.homepage.adapter.HotServicesAdapter;
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

public class HomePageSFragment extends BaseFragment implements HomePageContract.HomepageView, HotServicesAdapter.OnHotItemClickListener, LikeStoreAdapter.OnLikeStoreClickListener,HNearbyShopsAdapter.OnNearbyShopClickListener {

    @BindView(R.id.refreshLayout)
    HeaderViewLayout refreshLayout;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.scan)
    ImageView scan;
    @BindView(R.id.search_tt)
    TextView searchTt;
    @BindView(R.id.choice_city)
    TextView choiceCity;
    @BindView(R.id.like_store_rv)
    RecyclerView likeStoreRv;
    @BindView(R.id.search_ll)
    LinearLayout searchLl;
    @BindView(R.id.scrollview)
    GradationScrollView scrollview;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.all_cf_ll)
    LinearLayout allCfLl;
    @BindView(R.id.hot_search_rv)
    RecyclerView hotSearchRv;
    @BindView(R.id.ten_hot_icon)
    RoundedImageView tenHotIcon;
    @BindView(R.id.ten_hot_tv)
    TextView tenHotTv;
    @BindView(R.id.nearby_shops_rv)
    RecyclerView nearbyShopsRv;
    @BindView(R.id.nearby_shop_ll)
    LinearLayout nearbyShopLl;
    @BindView(R.id.ten_hot_service_ll)
    LinearLayout tenHotServiceLl;


    private LocationHelper mLocationHelper;
    private String city;
    private List<BannerHotResponse.BannerInfo> bannerInfos;
    private List<String> bannerImages;
    private HotServicesAdapter hotServicesAdapter;
    private List<BannerHotResponse.StorecateInfo> storecateInfos;
    private LikeStoreAdapter likeStoreAdapter;
    private HNearbyShopsAdapter hNearbyShopsAdapter;
    private List<BannerHotResponse.StorecateInfo> allStorecateInfos;
    private BannerHotResponse.StorecateInfo tenStorecateInfo;

    private static final int REQUEST_PERMISSION_LOCATION = 1;
    private static final int REFRESS_LOCATION_SCAN = 2;
    private static final int REFRESS_LOCATION_CODE = 3;
    private static final int REQUEST_CODE_CAMERA = 4;
    private static final int REQUEST_PERMISSION_WRITE = 5;


    private String longitude;
    private String latitude;
    private String coordinate;
    private ChangeAppInfo changeAppInfo;
    private CommonDialog dialogUpdate;
    private CommonDialog dialogmustUpdate;
    private String appStatus;//1：更新可忽略2：更新不能忽略
    private String isFirst = "1";

    @Inject
    HomePagePresenter homePagePresenter;

    private CommonAppPreferences commonAppPreferences;

    public HomePageSFragment() {
    }

    public static HomePageSFragment newInstance() {
        HomePageSFragment homePageFragment = new HomePageSFragment();
        return homePageFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_homepages, container, false);
        ButterKnife.bind(this, rootview);
        return rootview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHeight(choiceCity, searchLl, scan);
        initView();

    }

    private void initView() {
        commonAppPreferences = new CommonAppPreferences(getActivity());
        refreshLayout.setOnRefreshListener(mOnRefreshListener);
        bannerInitialization();
        hotService();
        nearbyShops();
        likeStore();
        homePagePresenter.getBannerHotInfo("1", "");//防止定位慢不去请求数据就空白，后台默认衢州
        mapLocal();
        refreshLayout.setCanLoadMore(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isFirstLoad && getUserVisibleHint() && TextUtils.equals("0", isFirst)) {
            isFirst = "1";
            if (TextUtils.isEmpty(choiceCity.getText().toString())) {

            } else {
                homePagePresenter.getBannerHotInfo("1", city);
                homePagePresenter.getHNearbyShopsInfos(coordinate, city);
                homePagePresenter.getLikeStore(city);
            }
        }
    }

    //首页token失效
    @Override
    public void showError(Throwable e) {
        if (e != null && e instanceof RemoteDataException && ((RemoteDataException) e).isAuthFailed()) {
            isFirst = "0";
        }
        super.showError(e);
    }

    //设置热门
    private void hotService() {
        storecateInfos = new ArrayList<>();
        hotSearchRv.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        hotServicesAdapter = new HotServicesAdapter(getActivity());
        hotServicesAdapter.setOnHotItemClickListener(this);
        hotSearchRv.setAdapter(hotServicesAdapter);
    }

    //附近好店
    private void nearbyShops() {
        hNearbyShopsAdapter = new HNearbyShopsAdapter(getActivity());
        hNearbyShopsAdapter.setOnNearbyShopClickListener(this);
        nearbyShopsRv.setAdapter(hNearbyShopsAdapter);
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
                if (aMapLocation == null || (TextUtils.isEmpty(aMapLocation.getDistrict()) && TextUtils.equals("0.0", String.valueOf(aMapLocation.getLongitude())) && TextUtils.equals("0.0", String.valueOf(aMapLocation.getLatitude())))) {
                    choiceCity.setText("定位失败，请重新定位");
                    city = "";
                    longitude = "";
                    latitude = "";
                } else {
                    city = aMapLocation.getDistrict();
                    longitude = aMapLocation.getLongitude() + "";
                    latitude = aMapLocation.getLatitude() + "";
                    choiceCity.setText(city);
                }
                CityManager.saveCity(city);
                likeStoreAdapter.setCity(city, longitude, latitude);
                hNearbyShopsAdapter.setCoordinate(longitude, latitude);
                homePagePresenter.getBannerHotInfo("0", city);
                if(!TextUtils.isEmpty(longitude)&&!TextUtils.isEmpty(latitude)) {
                    coordinate = longitude + "," + latitude;
                }else {
                    coordinate=""  ;
                }
                homePagePresenter.getHNearbyShopsInfos(coordinate, city);
                homePagePresenter.getLikeStore(city);
                commonAppPreferences.setCity(city);
                commonAppPreferences.setLocal(longitude, latitude);
                commonAppPreferences.setCurrLocalAddress(longitude, latitude);
                homePagePresenter.detectionVersion(BuildConfig.VERSION_NAME, "android");
            }
        });

        if (requestRuntimePermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION
        }, REQUEST_PERMISSION_LOCATION)) {
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
                UmengEventUtils.statisticsClick(getActivity(), UMStaticData.CLIENT_MAIN_BANNER);
                if (bannerInfos != null && bannerInfos.size() >= position) {
                    BannerHotResponse.BannerInfo bannerInfo = bannerInfos.get(position - 1);
                    if (!TextUtils.isEmpty(bannerInfo.getIds()) && !TextUtils.equals("0", bannerInfo.getIds())) {
                        ShopDetailActivity.start(getActivity(), bannerInfo.getIds());
                    }
                }

            }
        });
    }

    HeaderViewLayout.OnRefreshListener mOnRefreshListener = new HeaderViewLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            homePagePresenter.getBannerHotInfo("0", city);
            homePagePresenter.getHNearbyShopsInfos(coordinate, city);
            homePagePresenter.getLikeStore(city);
        }

        @Override
        public void onLoadMore() {
            homePagePresenter.getMoreLikeStore(city);
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
        allStorecateInfos = bannerHotResponse.getStorecateInfos();
        if (allStorecateInfos != null && allStorecateInfos.size() >= 10) {
            storecateInfos.clear();
            for (int i = 0; i < 9; i++) {
                storecateInfos.add(allStorecateInfos.get(i));
            }
            hotServicesAdapter.setData(storecateInfos);
            tenStorecateInfo=allStorecateInfos.get(9);
            GlideHelper.load(getActivity(), tenStorecateInfo.getPic(), R.mipmap.app_icon, tenHotIcon);
            tenHotTv.setText(tenStorecateInfo.getName());
        }

    }

    @Override
    public void likeStroeInfo(List<CollectionStoreInfo> collectionStoreInfos) {
        refreshLayout.stopRefresh();
        if (collectionStoreInfos != null && collectionStoreInfos.size() > 0) {
            refreshLayout.setCanLoadMore(true);
        } else {
            refreshLayout.setCanLoadMore(false);
        }
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

    @Override
    public void detectionSuccess(ChangeAppInfo changeAppInfo) {
        this.changeAppInfo = changeAppInfo;
        if (changeAppInfo != null) {
            appStatus = changeAppInfo.getStatus();
            if (TextUtils.equals("1", appStatus)) {
                if (requestRuntimePermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,}, REQUEST_PERMISSION_WRITE)) {
                    showUpdate(changeAppInfo);
                }
            } else if (TextUtils.equals("2", appStatus)) {
                if (requestRuntimePermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,}, REQUEST_PERMISSION_WRITE)) {
                    showMustUpdate(changeAppInfo);
                }
            }
        }
    }

    @Override
    public void renderHNearbyShopsInfos(List<HNearbyShopsInfo> hNearbyShopsInfos) {
        if (hNearbyShopsInfos != null && hNearbyShopsInfos.size() > 0) {
            nearbyShopLl.setVisibility(View.VISIBLE);
        } else {
            nearbyShopLl.setVisibility(View.GONE);
        }
        hNearbyShopsAdapter.setData(hNearbyShopsInfos);
    }

    @OnClick({R.id.choice_city, R.id.scan, R.id.search_ll,R.id.ten_hot_service_ll,R.id.all_cf_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.choice_city:
                Intent intent = new Intent(getActivity(), ChoiceCityActivity.class);
                intent.putExtra(StaticData.TRANSMIT_CITY, city);
                startActivityForResult(intent, REFRESS_LOCATION_CODE);
                break;
            case R.id.scan:
                scan();
                break;
            case R.id.search_ll:
                SearchShopActivity.start(getActivity());
                break;
            case R.id.ten_hot_service_ll:
                UmengEventUtils.statisticsClick(getActivity(), UMStaticData.KEY, tenStorecateInfo.getName(), UMStaticData.SELECT_TYPE);
                ScreeningListActivity.start(getActivity(), tenStorecateInfo.getId(), tenStorecateInfo.getName(), tenStorecateInfo.getSum(), "","1");
                break;
            case R.id.all_cf_ll:
                AllCategoriesActivity.start(getActivity());
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
                        city = cityInfoBean.getName();
                        CityManager.saveCity(city);
                        commonAppPreferences.setCity(city);
                        longitude = commonAppPreferences.getLongitude();
                        latitude = commonAppPreferences.getLatitude();
                        likeStoreAdapter.setCity(city, longitude, latitude);
                        if(!TextUtils.isEmpty(longitude)&&!TextUtils.isEmpty(latitude)) {
                            coordinate = longitude + "," + latitude;
                        }else {
                            coordinate=""  ;
                        }
                        homePagePresenter.getBannerHotInfo("1", city);
                        homePagePresenter.getHNearbyShopsInfos(coordinate, city);
                        homePagePresenter.getLikeStore(city);
                        choiceCity.setText(city);
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
            case REQUEST_PERMISSION_WRITE:
                for (int gra : grantResults) {
                    if (gra != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                if (TextUtils.equals("1", appStatus)) {
                    showUpdate(changeAppInfo);
                } else if (TextUtils.equals("2", appStatus)) {
                    showMustUpdate(changeAppInfo);
                }
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
        UmengEventUtils.statisticsClick(getActivity(), UMStaticData.KEY, storecateInfo.getName(), UMStaticData.SELECT_TYPE);
        ScreeningListActivity.start(getActivity(), storecateInfo.getId(), storecateInfo.getName(), storecateInfo.getSum(), "","1");
    }

    @Override
    public void likeStoreClickListener(String storeid) {
        UmengEventUtils.statisticsClick(getActivity(), UMStaticData.KEY, UMStaticData.SELECT_MAIN, UMStaticData.SELECT_LIKE);
        ShopDetailActivity.start(getActivity(), storeid);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLocationHelper != null) {
            mLocationHelper.cancelListen();
        }
    }

    private void showUpdate(final ChangeAppInfo changeAppInfo) {
        if (dialogUpdate == null)
            dialogUpdate = new CommonDialog.Builder()
                    .setTitle("版本更新")
                    .setContent(changeAppInfo.getTitle())
                    .setContentGravity(Gravity.CENTER)
                    .setCancelButton("忽略", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogUpdate.dismiss();
                        }
                    })
                    .setConfirmButton("更新", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showMessage("开始下载");
                            updateApk(changeAppInfo.getUrl());
                        }
                    }).create();
        dialogUpdate.show(getFragmentManager(), "");
    }

    private void showMustUpdate(final ChangeAppInfo changeAppInfo) {
        if (dialogmustUpdate == null)
            dialogmustUpdate = new CommonDialog.Builder()
                    .setTitle("版本更新")
                    .setContent(changeAppInfo.getTitle())
                    .setContentGravity(Gravity.CENTER)
                    .setConfirmButton("更新", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showMessage("开始下载");
                            updateApk(changeAppInfo.getUrl());
                        }
                    }).create();
        dialogmustUpdate.show(getFragmentManager(), "");
    }

    private MaterialDialog materialDialog;

    private void updateApk(String downUrl) {
        materialDialog = new MaterialDialog.Builder(getActivity())

                .title("版本升级")
                .content("正在下载安装包，请稍候")

                .progress(false, 100, false)
                .cancelable(false)
                .negativeText("取消")

                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        DownloadService.stopDownload();
                    }
                })
                .show();
        DownloadService.setMaterialDialog(materialDialog);
        DownloadService.start(getActivity(), downUrl, "6F7FBCECD46341DF08BE8B11A09E6925");
    }

    @Override
    public void nearbyShopClickListener(String storeid) {
        ShopDetailActivity.start(getActivity(), storeid);
    }
}
