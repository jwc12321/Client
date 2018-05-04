package com.purchase.sls.browse;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JWC on 2018/5/3.
 */

@Module
public class BrowseModule {
    private BrowseContract.BrowseView browseView;

    public BrowseModule(BrowseContract.BrowseView browseView) {
        this.browseView = browseView;
    }

    @Provides
    BrowseContract.BrowseView provideBrowseView() {
        return browseView;
    }
}
