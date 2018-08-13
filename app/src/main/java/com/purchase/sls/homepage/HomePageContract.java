package com.purchase.sls.homepage;

import com.purchase.sls.BasePresenter;
import com.purchase.sls.BaseView;
import com.purchase.sls.data.entity.AllCategoriesInfo;
import com.purchase.sls.data.entity.BannerHotResponse;
import com.purchase.sls.data.entity.ChangeAppInfo;
import com.purchase.sls.data.entity.ClassifyInfo;
import com.purchase.sls.data.entity.CollectionStoreInfo;
import com.purchase.sls.data.entity.HNearbyShopsInfo;
import com.purchase.sls.data.entity.HotSearchInfo;
import com.purchase.sls.data.entity.ScreeningListResponse;
import com.purchase.sls.data.entity.ShopDetailsInfo;

import java.util.List;

/**
 * Created by JWC on 2018/4/19.
 */

public interface HomePageContract {
    interface HomepagePresenter extends BasePresenter {
        void getBannerHotInfo(String refreshType, String areaname);

        void getLikeStore(String areaname);

        void getMoreLikeStore(String areaname);

        void detectionVersion(String edition, String type);

        void getHNearbyShopsInfos(String coordinate, String city);
    }

    interface HomepageView extends BaseView<HomepagePresenter> {
        void bannerHotInfo(BannerHotResponse bannerHotResponse);

        void likeStroeInfo(List<CollectionStoreInfo> collectionStoreInfos);

        void moreLikeStroeInfo(List<CollectionStoreInfo> collectionStoreInfos);

        void detectionSuccess(ChangeAppInfo changeAppInfo);


        void renderHNearbyShopsInfos(List<HNearbyShopsInfo> hNearbyShopsInfos);
    }

    interface ScreeningListPresenter extends BasePresenter {
        void getClassifyInfo(String cateid);

        void getScreeningList(String refreshType, String address, String cid, String sort, String screen, String storename,String location);

        void getMoreScreeningList(String address, String cid, String sort, String screen, String storename,String location);
    }

    interface ScreeningListView extends BaseView<ScreeningListPresenter> {
        void renderClassifyInfo(ClassifyInfo classifyInfo);

        void screeningListInfo(ScreeningListResponse screeningListResponse);

        void moreScreeningListInfo(ScreeningListResponse screeningListResponse);
    }

    interface QrCodePresenter extends BasePresenter {
        void getShopDetail(String storeid);
    }

    interface QrCodeView extends BaseView<QrCodePresenter> {
        void shopDetailInfo(ShopDetailsInfo shopDetailsInfo);
    }

    interface AllCategoriesPresenter extends BasePresenter{
        void getAllCategoriesInfos();
    }

    interface AllCategoriesView extends BaseView<AllCategoriesPresenter>{
        void renderAllCategoriesInfos(List<AllCategoriesInfo>allCategoriesInfos);
    }

    interface HotSearchPresenter extends BasePresenter{
        void getHotSearchs();
    }

    interface HotSearchView extends BaseView<HotSearchPresenter>{
        void renderHotSearchs(List<HotSearchInfo> hotSearchInfos);
    }
}
