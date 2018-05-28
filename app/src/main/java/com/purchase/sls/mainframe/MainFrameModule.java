package com.purchase.sls.mainframe;


import dagger.Module;
import dagger.Provides;

/**
 * Created by JWC on 2018/4/19.
 */
@Module
public class MainFrameModule {
    private MainFrameContract.MainFrameView mainFrameView;

    public MainFrameModule(MainFrameContract.MainFrameView mainFrameView) {
        this.mainFrameView = mainFrameView;
    }
    @Provides
    MainFrameContract.MainFrameView provideMainFrameView(){
        return mainFrameView;
    }
}
