package com.purchase.sls.homepage;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JWC on 2018/4/19.
 */
@Module
public class HomePageModule {
    private HomePageContract.HomepageView homepageView;
    private HomePageContract.ScreeningListView screeningListView;
    private HomePageContract.QrCodeView qrCodeView;

    public HomePageModule(HomePageContract.HomepageView homepageView) {
        this.homepageView = homepageView;
    }

    public HomePageModule(HomePageContract.ScreeningListView screeningListView) {
        this.screeningListView = screeningListView;
    }

    public HomePageModule(HomePageContract.QrCodeView qrCodeView) {
        this.qrCodeView = qrCodeView;
    }

    @Provides
    HomePageContract.HomepageView provideHomepageView() {
        return homepageView;
    }

    @Provides
    HomePageContract.ScreeningListView provideScreeningListView(){
        return screeningListView;
    }

    @Provides
    HomePageContract.QrCodeView provideQrCodeView(){
        return qrCodeView;
    }
}
