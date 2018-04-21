package com.purchase.sls;

import android.app.Application;

import com.purchase.sls.ApplicationComponent;
import com.purchase.sls.ApplicationModule;
import com.purchase.sls.DaggerApplicationComponent;
import com.purchase.sls.common.cityList.style.citylist.utils.CityListLoader;
import com.purchase.sls.common.unit.SPManager;
import com.purchase.sls.data.local.GreenDaoModule;

/**
 * Created by Administrator on 2017/12/15.
 */

public class MainApplication extends Application {
    private static ApplicationComponent mApplicationComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        initDaggerComponent();
        /**
         * 预先加载一级列表所有城市的数据
         */
        CityListLoader.getInstance().loadCityData(this);
        SPManager.getInstance().register(this);
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
