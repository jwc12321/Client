package com.purchase.sls.push;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Sherily on 2017/5/6.
 */
@Module
public class PushModule {
    @Singleton
    @Provides
    PushUtil providePushUtil(){
        return new PushUtil();
    }
}
