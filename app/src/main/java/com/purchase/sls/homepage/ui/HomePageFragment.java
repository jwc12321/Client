package com.purchase.sls.homepage.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.purchase.sls.BaseFragment;
import com.purchase.sls.BuildConfig;
import com.purchase.sls.R;
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
import com.purchase.sls.common.widget.GridSpacesItemDecoration;
import com.purchase.sls.common.widget.LimitScrollerView;
import com.purchase.sls.common.widget.dialog.CommonDialog;
import com.purchase.sls.data.RemoteDataException;
import com.purchase.sls.data.entity.BannerHotResponse;
import com.purchase.sls.data.entity.ChangeAppInfo;
import com.purchase.sls.data.entity.CollectionStoreInfo;
import com.purchase.sls.data.entity.HNearbyShopsInfo;
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

public class HomePageFragment extends BaseFragment implements HomePageContract.HomepageView, HotServiceAdapter.OnHotItemClickListener, LikeStoreAdapter.OnLikeStoreClickListener, GradationScrollView.ScrollViewListener {

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
    private static final int REQUEST_PERMISSION_WRITE = 5;

    private String longitude;
    private String latitude;
    private ChangeAppInfo changeAppInfo;
    private CommonDialog dialogUpdate;
    private CommonDialog dialogmustUpdate;
    private CommonDialog testingDialog;
    private String appStatus;//1：更新可忽略2：更新不能忽略
    private String isFirst = "1";

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
        View rootview = inflater.inflate(R.layout.fragment_homepage, container, false);
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
        scrollview.setScrollViewListener(this);
        refreshLayout.setOnRefreshListener(mOnRefreshListener);
        bannerInitialization();
        hotService();
        scrollerUpDown();
        likeStore();
        homePagePresenter.getBannerHotInfo("1", "");//防止定位慢不去请求数据就空白，后台默认衢州
        mapLocal();
//        if (testOldVersion("com.nenggou.syn")) {
//            textDialog();
//        }
        refreshLayout.setCanLoadMore(false);
    }

    private void textDialog() {
        if (testingDialog == null)
            testingDialog = new CommonDialog.Builder()
                    .showTitle(false)
                    .setContent("检测到您当前手机含有旧能购APP，为了不影响您的使用，请先删除旧版本的APP")
                    .setContentGravity(Gravity.CENTER)
                    .showButton(false)
                    .setConfirmButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            testingDialog.dismiss();
                        }
                    }).create();
        testingDialog.show(getFragmentManager(), "");
    }

    @Override
    public void onResume() {
        super.onResume();
        limitScroll.startScroll();
        if (!isFirstLoad&&getUserVisibleHint() && TextUtils.equals("0", isFirst)) {
            isFirst="1";
            if (TextUtils.isEmpty(choiceCity.getText().toString())) {

            } else {
                homePagePresenter.getBannerHotInfo("1", city);
                homePagePresenter.getLikeStore(city);
            }
        }
    }

    private boolean testOldVersion(String packageName) {
        PackageManager packageManager = getActivity().getPackageManager();

        //获取手机系统的所有APP包名，然后进行一一比较
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                if (((PackageInfo) pinfo.get(i)).packageName
                        .equalsIgnoreCase(packageName))
                    return true;
            }
        }
        return false;
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
        hotSearchLink.setLayoutManager(new GridLayoutManager(getActivity(), 5));
        int space = 41;
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
                homePagePresenter.getBannerHotInfo("0", city);
                homePagePresenter.getLikeStore(city);
                commonAppPreferences.setCity(city);
                commonAppPreferences.setLocal(longitude, latitude);
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

    //设置上下轮播图
    private void scrollerUpDown() {
        //API:1、设置数据适配器
        myLimitScrollAdapter = new MyLimitScrollAdapter(getActivity());
        limitScroll.setDataAdapter(myLimitScrollAdapter);
        //API：4、设置条目点击事件
        limitScroll.setOnItemClickListener(new LimitScrollerView.OnItemClickListener() {
            @Override
            public void onItemClick(Object obj) {
                UmengEventUtils.statisticsClick(getActivity(), UMStaticData.CLIENT_MAIN_Toutiao);
            }
        });
    }

    HeaderViewLayout.OnRefreshListener mOnRefreshListener = new HeaderViewLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            homePagePresenter.getBannerHotInfo("0", city);
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
        myLimitScrollAdapter.setDatas(bannerHotResponse.getArticleInfo().getDatainfos());
        hotServiceAdapter.setData(bannerHotResponse.getStorecateInfos());
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

    }

    @OnClick({R.id.choice_city, R.id.scan, R.id.search_ll})
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
                        homePagePresenter.getBannerHotInfo("1", city);
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
    public void onScrollChanged(GradationScrollView scrollView, int x, int y, int oldx, int oldy) {
        // TODO Auto-generated method stub
        if (y <= 0) {   //设置标题的背景颜色
            titleRel.setBackgroundColor(Color.argb((int) 0, 144, 151, 166));
        } else if (y > 0 && y <= 180) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
            float scale = (float) y / 180;
            float alpha = (255 * scale);
            titleRel.setBackgroundColor(Color.argb((int) alpha, 255, 101, 40));
        } else {    //滑动到banner下面设置普通颜色
            titleRel.setBackgroundColor(Color.argb((int) 255, 255, 101, 40));
        }
    }

    //TODO 修改适配器绑定数据
    class MyLimitScrollAdapter implements LimitScrollerView.LimitScrollAdapter {

        private Context context;
        private List<BannerHotResponse.ArticleInfo.Datainfo> datas;

        public MyLimitScrollAdapter(Context context) {
            this.context = context;
        }

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
            View itemView = LayoutInflater.from(context).inflate(R.layout.limit_scroller_item, null, false);
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

}
