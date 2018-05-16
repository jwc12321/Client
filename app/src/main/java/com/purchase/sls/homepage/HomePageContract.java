package com.purchase.sls.homepage;

import com.purchase.sls.BasePresenter;
import com.purchase.sls.BaseView;
import com.purchase.sls.data.entity.BannerHotResponse;
import com.purchase.sls.data.entity.CollectionStoreInfo;
import com.purchase.sls.data.entity.LikeStoreResponse;
import com.purchase.sls.data.entity.ScreeningListResponse;

import java.util.List;

/**
 * Created by JWC on 2018/4/19.
 */

public interface HomePageContract {
    interface HomepagePresenter extends BasePresenter {
        void getBannerHotInfo(String refreshType,String areaname);

        void getLikeStore(String areaname);

        void getMoreLikeStore(String areaname);
    }

    interface HomepageView extends BaseView<HomepagePresenter> {
        void bannerHotInfo(BannerHotResponse bannerHotResponse);

        void likeStroeInfo(List<CollectionStoreInfo> collectionStoreInfos);

        void moreLikeStroeInfo(List<CollectionStoreInfo> collectionStoreInfos);
    }

    interface ScreeningListPresenter extends BasePresenter {
        void getScreeningList(String refreshType,String address, String cid, String sort, String screen,String storename);

        void getMoreScreeningList(String address, String cid, String sort, String screen,String storename);
    }

    interface ScreeningListView extends BaseView<ScreeningListPresenter> {
        void screeningListInfo(ScreeningListResponse screeningListResponse);

        void moreScreeningListInfo(ScreeningListResponse screeningListResponse);
    }
}
