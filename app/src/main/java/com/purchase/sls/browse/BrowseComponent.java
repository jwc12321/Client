package com.purchase.sls.browse;

/**
 * Created by JWC on 2018/5/3.
 */

import com.purchase.sls.ActivityScope;
import com.purchase.sls.ApplicationComponent;
import com.purchase.sls.browse.ui.BrowseRecordsActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {BrowseModule.class})
public interface BrowseComponent {
    void inject(BrowseRecordsActivity browseRecordsActivity);

}
