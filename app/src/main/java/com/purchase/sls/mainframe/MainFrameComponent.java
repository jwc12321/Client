package com.purchase.sls.mainframe;

/**
 * Created by JWC on 2018/4/19.
 */

import com.purchase.sls.ActivityScope;
import com.purchase.sls.ApplicationComponent;
import com.purchase.sls.mainframe.ui.MainFrameActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {MainFrameModule.class})
public interface MainFrameComponent {
    void inject(MainFrameActivity mainFrameActivity);
}
