package com.purchase.sls.nearbymap.ui;

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

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.MyLocationStyle;
import com.purchase.sls.BaseFragment;
import com.purchase.sls.R;
import com.purchase.sls.data.entity.NearbyInfoResponse;
import com.purchase.sls.nearbymap.DaggerNearbyMapComponent;
import com.purchase.sls.nearbymap.NearbyMapContract;
import com.purchase.sls.nearbymap.NearbyMapModule;
import com.purchase.sls.nearbymap.adapter.NearbyItemAdapter;
import com.purchase.sls.nearbymap.adapter.NearbyMunuAdapter;
import com.purchase.sls.nearbymap.presenter.NearbyMapPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/4/19.
 */

public class NearbyMapFragment extends BaseFragment implements NearbyMapContract.NearbyView, NearbyMunuAdapter.OnMenuItemClickListener, NearbyItemAdapter.OnItemClickListener, LocationSource, AMapLocationListener {

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
                isFirstLoad=false;
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
    public void onPause() {
        super.onPause();
        super.onDestroy();
        mapView.onDestroy();
        if (null != mlocationClient) {
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
        if (nearbyInfoResponses != null && nearbyInfoResponses.size() > 0) {
            nearbyLl.setVisibility(View.VISIBLE);
            nearbyMunuAdapter.setMunuList(nearbyInfoResponses);
            nearbyItemAdapter.setItemList(nearbyInfoResponses.get(0).getCateInfos());
        } else {
            nearbyLl.setVisibility(View.GONE);
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
    }

    @Override
    public void menuItemClickListener(int menuPosition) {
        nearbyMunuAdapter.setPosittion(munuLastPosition, menuPosition);
        munuLastPosition = menuPosition;
        nearbyItemAdapter.setItemList(nearbyInfoResponses.get(menuPosition).getCateInfos());
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
                Log.d("jjj0", "精度和纬度" + amapLocation.getLatitude() + "=====" + amapLocation.getLongitude());
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
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
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
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
}
