package com.purchase.sls.collection;

/**
 * Created by JWC on 2018/4/26.
 */

import com.purchase.sls.ActivityScope;
import com.purchase.sls.ApplicationComponent;
import com.purchase.sls.collection.ui.CollectionListActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {CollectionModule.class})
public interface CollectionComponent {
    void inject(CollectionListActivity collectionListActivity);

}
