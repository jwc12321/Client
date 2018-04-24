package com.purchase.sls;


import com.purchase.sls.data.local.GreenDaoModule;
import com.purchase.sls.data.remote.RestApiModule;
import com.purchase.sls.data.remote.RestApiService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Administrator on 2017/12/27.
 */
@Singleton
@Component(modules = {ApplicationModule.class, RestApiModule.class, GreenDaoModule.class})
public interface ApplicationComponent {
    void inject(BaseActivity activity);
    RestApiService getRestApiService();
    DaoSession daoSession();
    UserInfoDao userInfoDao();
}
