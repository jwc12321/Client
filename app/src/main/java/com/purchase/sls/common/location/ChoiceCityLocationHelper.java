package com.purchase.sls.common.location;

import android.content.Context;
import android.location.LocationManager;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.model.LatLng;

import java.util.HashSet;

/**
 * Created by JWC on 2018/4/17.
 */
public class ChoiceCityLocationHelper {
    private static final String TAG = "LocationHelper";

    public static final String COOR_TYPE_BD09 = "bd09";

    public static final String COOR_TYPE_BD09LL = "bd09ll";

    public static final String COOR_TYPE_GCJ02 = "gcj02";

    private Context mContext;

    private AMapLocationClient mLocationClient;

    private HashSet<OnLocatedListener> mOnLocatedListeners;

    private AMapLocationClientOption mLocationOption;

    private double mLastLatitude = 0.0d;

    private double mLastLongitude = 0.0d;

    /**
     * 定位监听
     */
    public interface OnLocatedListener {

        /**
         * 定位成功后回调
         */
        void onLocated(AMapLocation aMapLocation);
    }

    private AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            Log.d(TAG, "onReceiveLocation: " + (aMapLocation == null ? null :
                    "[" + aMapLocation.getLatitude() + "," + aMapLocation.getLongitude() + "]"));

            if (aMapLocation != null) {
                mLocationClient.stopLocation();
                mLastLatitude = aMapLocation.getLatitude();
                mLastLongitude = aMapLocation.getLongitude();
                for (OnLocatedListener onLocatedListener : mOnLocatedListeners) {
                    onLocatedListener.onLocated(aMapLocation);
                }
            }
        }
    };

    private static ChoiceCityLocationHelper locationHelper = null;

    public static ChoiceCityLocationHelper sharedInstance(Context context) {
        if (locationHelper == null) {
            locationHelper = new ChoiceCityLocationHelper(context);
        }
        return locationHelper;
    }

    private ChoiceCityLocationHelper(Context context) {
        mContext = context.getApplicationContext();
        mLocationClient = new AMapLocationClient(mContext);
        mLocationClient.setLocationListener(mLocationListener);
        // 设置定位的相关配置
        mLocationOption = new AMapLocationClientOption();


        //设置定位模式为Hight_Accuracy高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
    }

//    public LocationHelper setCoorType(String coorType) {
//        mOption.setCoorType(coorType);
//        return this;
//    }
//
//    public LocationHelper setLocationMode(LocationClientOption.LocationMode locationMode) {
//        mOption.setLocationMode(locationMode);
//        return this;
//    }

    public double getLastLatitude() {
        return mLastLatitude;
    }

    public double getLastLongitude() {
        return mLastLongitude;
    }

    public LatLng getLastLocation() {
        return new LatLng(mLastLatitude, mLastLongitude);
    }

//    public double getDistance(LatLng desc) {
//        return DistanceUtil.getDistance(getLastLocation(), desc);
//    }

    /**
     * GPS定位是否可用
     */
    public boolean isGpsLocationEnable() {
        LocationManager locationManager = (LocationManager) mContext.
                getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * 网络定位是否可用
     */
    public boolean isNetworkLocationEnable() {
        LocationManager locationManager = (LocationManager) mContext.
                getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    /**
     * 开始定位
     */
    public void start() {
//        if (!mLocationClient.isStarted()) {
        mLocationClient.startLocation();
//        }
    }

    /**
     * 取消定位
     */
    public void cancel() {
        if (mLocationClient != null && mLocationClient.isStarted()) {
            mLocationClient.stopLocation();
        }
    }

    public void destroyLocation(){
        if (null != mLocationClient) {
            mOnLocatedListeners.clear();
            mLocationClient.stopLocation();
        }
    }

    /**
     * 添加一个定位监听
     *
     * @param onLocatedListener 要添加的定位监听对象
     */
    public void addOnLocatedListener(OnLocatedListener onLocatedListener) {
        if (mOnLocatedListeners == null) {
            mOnLocatedListeners = new HashSet<>();
        }
        mOnLocatedListeners.add(onLocatedListener);
    }

    /**
     * 删除指定的定位监听
     *
     * @param onLocatedListener 要删除的定位监听
     */
    public void removeOnLocatedListener(OnLocatedListener onLocatedListener) {
        if (mOnLocatedListeners != null) {
            mOnLocatedListeners.remove(onLocatedListener);
        }
    }

    /**
     * 删除所有的定位监听
     */
    public void clearOnLocatedListener() {
        if (mOnLocatedListeners != null) {
            mOnLocatedListeners.clear();
        }
    }

}
