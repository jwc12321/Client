package com.purchase.sls.nearbymap.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.purchase.sls.BaseFragment;
import com.purchase.sls.R;
import com.purchase.sls.common.UMStaticData;
import com.purchase.sls.common.unit.TokenManager;
import com.purchase.sls.common.unit.UmengEventUtils;
import com.purchase.sls.common.unit.ViewUtil;
import com.purchase.sls.data.entity.MapMarkerInfo;
import com.purchase.sls.data.entity.NearbyInfoResponse;
import com.purchase.sls.nearbymap.DaggerNearbyMapComponent;
import com.purchase.sls.nearbymap.NearbyMapContract;
import com.purchase.sls.nearbymap.NearbyMapModule;
import com.purchase.sls.nearbymap.adapter.NearbyItemAdapter;
import com.purchase.sls.nearbymap.adapter.NearbyMunuAdapter;
import com.purchase.sls.nearbymap.presenter.NearbyMapPresenter;
import com.purchase.sls.shopdetailbuy.ui.ShopDetailActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by JWC on 2018/4/19.
 */

public class NearbyMapFragment extends BaseFragment implements NearbyMapContract.NearbyView, NearbyMunuAdapter.OnMenuItemClickListener, NearbyItemAdapter.OnItemClickListener, LocationSource, AMapLocationListener, AMap.OnMarkerClickListener, AMap.InfoWindowAdapter, AMap.OnInfoWindowClickListener {

    @BindView(R.id.map)
    MapView mapView;
    @BindView(R.id.nearby_munu_ry)
    RecyclerView nearbyMunuRy;
    @BindView(R.id.nearby_item_ry)
    RecyclerView nearbyItemRy;
    @BindView(R.id.nearby_ll)
    LinearLayout nearbyLl;

    //地图
    private AMap aMap;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
    private static final int FILL_COLOR = Color.argb(10, 0, 0, 180);

    private NearbyMunuAdapter nearbyMunuAdapter;
    private NearbyItemAdapter nearbyItemAdapter;
    private LinearLayoutManager layoutManager;
    private List<NearbyInfoResponse> nearbyInfoResponses;
    private int munuLastPosition = 0;
    private int itemLastPosition = 0;
    @Inject
    NearbyMapPresenter nearbyMapPresenter;

    BitmapDescriptor bitmapDescriptor;

    private String latitude;
    private String longitude;

    private MapMarkerInfo mapMarkerInfo;

    public NearbyMapFragment() {
    }

    public static NearbyMapFragment newInstance() {
        NearbyMapFragment nearbyMapFragment = new NearbyMapFragment();
        return nearbyMapFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_nearby_map, container, false);
        ButterKnife.bind(this, rootview);
        return rootview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        initMap();
    }

    private boolean isFirstLoad = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isFirstLoad) {
            if (getUserVisibleHint()) {
                if (isFirstLoad && mlocationClient != null) {
                    mlocationClient.startLocation();
                }
                isFirstLoad = false;
            }
        }
    }


    private void initView() {
        nearbyMunuAdapter = new NearbyMunuAdapter();
        nearbyMunuAdapter.setOnMenuItemClickListener(this);
        nearbyMunuRy.setAdapter(nearbyMunuAdapter);
        nearbyItemAdapter = new NearbyItemAdapter();
        nearbyItemAdapter.setOnItemClickListener(this);
        nearbyItemRy.setAdapter(nearbyItemAdapter);
    }

    private void initMap() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setOnMarkerClickListener(this);
        aMap.setInfoWindowAdapter(this);
        aMap.setOnInfoWindowClickListener(this);
        setupLocationStyle();
    }

    private void setupLocationStyle() {
        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.
                fromResource(R.mipmap.gps_point));
        // 自定义精度范围的圆形边框颜色
        myLocationStyle.strokeColor(STROKE_COLOR);
        //自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(5);
        // 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(FILL_COLOR);
        // 将自定义的 myLocationStyle 对象添加到地图上
        aMap.setMyLocationStyle(myLocationStyle);
    }


    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void initializeInjector() {
        DaggerNearbyMapComponent.builder()
                .applicationComponent(getApplicationComponent())
                .nearbyMapModule(new NearbyMapModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void setPresenter(NearbyMapContract.NearbyPresenter presenter) {

    }

    @Override
    public void nearbyInfo(List<NearbyInfoResponse> nearbyInfoResponses) {
        this.nearbyInfoResponses = nearbyInfoResponses;
        munuLastPosition = 0;
        itemLastPosition = 0;
        if (nearbyInfoResponses != null && nearbyInfoResponses.size() > 0) {
            nearbyLl.setVisibility(View.VISIBLE);
            nearbyMunuAdapter.setMunuList(nearbyInfoResponses, munuLastPosition);
            nearbyItemAdapter.setItemList(nearbyInfoResponses.get(0).getCateInfos(), itemLastPosition);
            if (nearbyInfoResponses.get(0).getCateInfos() != null && nearbyInfoResponses.get(0).getCateInfos().size() > 0) {
                nearbyMapPresenter.getMapMarkerInfo(nearbyInfoResponses.get(0).getCateInfos().get(0).getId(), (longitude + "," + latitude));
            }
        } else {
            nearbyLl.setVisibility(View.GONE);
        }
    }

    @Override
    public void renderapMarkers(List<MapMarkerInfo> mapMarkerInfos) {
        removeMarker();
        for (int i = 0; i < mapMarkerInfos.size(); i++) {
            addCustomMarker(mapMarkerInfos.get(i));
        }
    }

    @Override
    public void uploadXySuccess() {

    }

    private void removeMarker() {
        List<Marker> saveMarkerList = aMap.getMapScreenMarkers();
        if (saveMarkerList == null || saveMarkerList.size() <= 0)
            return;
        for (int i = 0; i < saveMarkerList.size(); i++) {
            Marker marker = saveMarkerList.get(i);
            if (marker.getObject() instanceof MapMarkerInfo) {
                marker.remove();//移除当前Marker
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void itemClickListener(NearbyInfoResponse.CateInfo cateInfo, int itemPosition) {
        nearbyItemAdapter.setPosittion(itemLastPosition, itemPosition);
        itemLastPosition = itemPosition;
        nearbyMapPresenter.getMapMarkerInfo(cateInfo.getId(), (longitude + "," + latitude));
    }

    @Override
    public void menuItemClickListener(int menuPosition) {
        nearbyMunuAdapter.setPosittion(munuLastPosition, menuPosition);
        munuLastPosition = menuPosition;
        itemLastPosition = 0;
        nearbyItemAdapter.setItemList(nearbyInfoResponses.get(menuPosition).getCateInfos(), itemLastPosition);
        if (nearbyInfoResponses.get(menuPosition).getCateInfos() != null && nearbyInfoResponses.get(menuPosition).getCateInfos().size() > 0) {
            nearbyMapPresenter.getMapMarkerInfo(nearbyInfoResponses.get(menuPosition).getCateInfos().get(0).getId(), (longitude + "," + latitude));
        }
    }


    //地图

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                if (!TextUtils.isEmpty(amapLocation.getCity())) {
                    nearbyMapPresenter.getNearbyInfo(amapLocation.getCity());
                }
                if (!TextUtils.isEmpty(TokenManager.getToken()) && !TextUtils.isEmpty(amapLocation.getLatitude() + "") && !TextUtils.isEmpty(amapLocation.getLongitude() + "")) {
                    nearbyMapPresenter.uploadXy(String.valueOf(amapLocation.getLongitude()), String.valueOf(amapLocation.getLatitude()));
                }
                Log.d("nearbyFragment", "精度和纬度" + amapLocation.getLatitude() + "=====" + amapLocation.getLongitude());
                latitude = amapLocation.getLatitude() + "";
                longitude = amapLocation.getLongitude() + "";
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);

            }
        }
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(getActivity());
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mLocationOption.setInterval(600000);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    String url;
    String addressXy;
    String[] addressXys;

    /**
     * by moos on 2018/01/12
     * func:添加单个自定义marker
     */
    private void addCustomMarker(final MapMarkerInfo mapMarkerInfo) {
        MarkerOptions markerOptions = new MarkerOptions();
        addressXy = mapMarkerInfo.getAddressXy();
        addressXys = addressXy.split(",");
        markerOptions.position(new LatLng(Double.parseDouble(addressXys[1]), Double.parseDouble(addressXys[0])));
        markerOptions.visible(true);
        markerOptions.title("当前位置");
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.marker_icon));
        markerOptions.icon(bitmapDescriptor);
        Marker marker;
        marker = aMap.addMarker(markerOptions);
        marker.setObject(mapMarkerInfo);
    }

    /**
     * by moos on 2018/01/12
     * func:定制化marker的图标
     *
     * @return
     */
    private void customizeMarkerIcon(String url, final OnMarkerIconLoadListener listener) {
        final View markerView = LayoutInflater.from(getActivity()).inflate(R.layout.marker_bg, null);
        final CircleImageView icon = (CircleImageView) markerView.findViewById(R.id.marker_item_icon);
        Glide.with(this)
                .load(url + "!/format/webp")
                .asBitmap()
                .thumbnail(0.2f)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .centerCrop()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                        //待图片加载完毕后再设置bitmapDes
                        icon.setImageBitmap(bitmap);
                        bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(ViewUtil.convertViewToBitmap(markerView));
                        listener.markerIconLoadingFinished(markerView);
                    }
                });
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        return false;
    }

    View infoWindow;
    View InfoContent;

    @Override
    public View getInfoWindow(Marker marker) {
        if (infoWindow == null) {
            infoWindow = getLayoutInflater().inflate(R.layout.map_iw, null);
        }
        TextView shopName = (TextView) infoWindow.findViewById(R.id.shop_name);
        mapMarkerInfo = (MapMarkerInfo) marker.getObject();
        shopName.setText(mapMarkerInfo.getTitle());
        return infoWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        if (InfoContent == null) {
            InfoContent = getLayoutInflater().inflate(R.layout.map_iw, null);
        }
        TextView shopName = (TextView) InfoContent.findViewById(R.id.shop_name);
        mapMarkerInfo = (MapMarkerInfo) marker.getObject();
        shopName.setText(mapMarkerInfo.getTitle());
        return InfoContent;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        mapMarkerInfo = (MapMarkerInfo) marker.getObject();
        UmengEventUtils.statisticsClick(getActivity(), UMStaticData.SHOW_STORE_WITH_MAP);
        ShopDetailActivity.start(getActivity(), mapMarkerInfo.getId());
    }

    /**
     * by moos on 2018/01/12
     * func:自定义监听接口,用来marker的icon加载完毕后回调添加marker属性
     */
    public interface OnMarkerIconLoadListener {
        void markerIconLoadingFinished(View view);
    }
}
