package com.purchase.sls.homepage;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JWC on 2018/4/19.
 */
@Module
public class HomePageModule {
    private HomePageContract.HomepageView homepageView;

    public HomePageModule(HomePageContract.HomepageView homepageView) {
        this.homepageView = homepageView;
    }

    @Provides
    HomePageContract.HomepageView provideHomepageView() {
        return homepageView;
    }
}
