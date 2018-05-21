package com.purchase.sls.homepage;

/**
 * Created by JWC on 2018/4/19.
 */

import com.purchase.sls.ActivityScope;
import com.purchase.sls.ApplicationComponent;
import com.purchase.sls.homepage.ui.HomePageFragment;
import com.purchase.sls.homepage.ui.QrCodeScanActivity;
import com.purchase.sls.homepage.ui.ScreeningListActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {HomePageModule.class})
public interface HomePageComponent {
    void inject(HomePageFragment homePageFragment);
    void inject(ScreeningListActivity screeningListActivity);
    void inject(QrCodeScanActivity qrCodeScanActivity);
}
