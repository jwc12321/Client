package com.purchase.sls;

import android.os.StrictMode;
import android.support.multidex.MultiDexApplication;
import com.meituan.android.walle.WalleChannelReader;
import com.purchase.sls.common.cityList.style.citylist.utils.CityListLoader;
import com.purchase.sls.common.unit.NetUtils;
import com.purchase.sls.common.unit.SPManager;
import com.purchase.sls.data.local.GreenDaoModule;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.PlatformConfig;

import cn.jpush.android.api.JPushInterface;

import static com.purchase.sls.BuildConfig.QQ_ZONE_APP_ID;
import static com.purchase.sls.BuildConfig.QQ_ZONE_APP_KEY;
import static com.purchase.sls.BuildConfig.WECHAT_APP_ID;
import static com.purchase.sls.BuildConfig.WECHAT_APP_SECRET;

/**
 * Created by Administrator on 2017/12/15.
 */

public class MainApplication  extends MultiDexApplication {
    private static ApplicationComponent mApplicationComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        initDaggerComponent();
        NetUtils.init(this);
        //Android7.0的照片问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        //友盟统计
        String channelId = WalleChannelReader.getChannel(this.getApplicationContext());
        MobclickAgent. startWithConfigure(new MobclickAgent.UMAnalyticsConfig(getApplicationContext(),"5ab7102aa40fa357cb000ba3",channelId));

        PlatformConfig.setWeixin(WECHAT_APP_ID,WECHAT_APP_SECRET);
        //QQ和QQ空间
        PlatformConfig.setQQZone(QQ_ZONE_APP_ID,QQ_ZONE_APP_KEY);
        /**
         * 预先加载一级列表所有城市的数据
         */
        CityListLoader.getInstance().loadCityData(this);
        SPManager.getInstance().register(this);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
    private void initDaggerComponent() {
        mApplicationComponent = DaggerApplicationComponent.builder()
                .greenDaoModule(new GreenDaoModule(this))
                .applicationModule(new ApplicationModule(this))
                .build();
    }
    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    public static void setmApplicationComponent(ApplicationComponent mApplicationComponent) {
        MainApplication.mApplicationComponent = mApplicationComponent;
    }
}
