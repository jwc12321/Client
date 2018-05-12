package com.purchase.sls.push;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Sherily on 2017/5/6.
 */
@Singleton
@Component(modules = {PushModule.class})
public interface PushComponent {
    void inject(PushReceiver pushReceiver);
}
