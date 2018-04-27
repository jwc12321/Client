package com.purchase.sls.collection;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JWC on 2018/4/26.
 */

@Module
public class CollectionModule {
    private CollectionContract.CollectionView collectionView;

    public CollectionModule(CollectionContract.CollectionView collectionView) {
        this.collectionView = collectionView;
    }

    @Provides
    CollectionContract.CollectionView provideCollectionView() {
        return collectionView;
    }
}
