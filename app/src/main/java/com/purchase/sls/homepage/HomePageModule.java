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
    private HomePageContract.AllCategoriesView allCategoriesView;
    private HomePageContract.HotSearchView hotSearchView;

    public HomePageModule(HomePageContract.HomepageView homepageView) {
        this.homepageView = homepageView;
    }

    public HomePageModule(HomePageContract.ScreeningListView screeningListView) {
        this.screeningListView = screeningListView;
    }

    public HomePageModule(HomePageContract.QrCodeView qrCodeView) {
        this.qrCodeView = qrCodeView;
    }

    public HomePageModule(HomePageContract.AllCategoriesView allCategoriesView) {
        this.allCategoriesView = allCategoriesView;
    }

    public HomePageModule(HomePageContract.HotSearchView hotSearchView) {
        this.hotSearchView = hotSearchView;
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

    @Provides
    HomePageContract.AllCategoriesView provideAllCategoriesView(){
        return allCategoriesView;
    }

    @Provides
    HomePageContract.HotSearchView provideHotSearchView(){
        return hotSearchView;
    }
}
