package com.purchase.sls.collection;

import com.purchase.sls.BasePresenter;
import com.purchase.sls.BaseView;
import com.purchase.sls.data.entity.CollectionListInfo;
import com.purchase.sls.data.entity.CollectionListResponse;

/**
 * Created by JWC on 2018/4/26.
 */

public interface CollectionContract {
    interface CollectionPresenter extends BasePresenter{
        void getCollectionListInfo(String refreshType);
        void getMoreCollectionListInfo();
        void addRemoveCollection(String storeid, String type, String[] fidArray );
    }
    interface CollectionView extends BaseView<CollectionPresenter>{
        void collectionListInfo(CollectionListResponse collectionListResponse);
        void moreCollectionListInfo(CollectionListResponse collectionListResponse);
        void addRemoveSuccess();
    }
}
