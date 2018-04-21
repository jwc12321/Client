package com.purchase.sls.homepage;

import com.purchase.sls.BasePresenter;
import com.purchase.sls.BaseView;
import com.purchase.sls.data.entity.BannerHotResponse;
import com.purchase.sls.data.entity.LikeStoreResponse;

import java.util.List;

/**
 * Created by JWC on 2018/4/19.
 */

public interface HomePageContract {
    interface HomepagePresenter extends BasePresenter {
        void getBannerHotInfo(String areaname);
        void getLikeStore();
        void getMoreLikeStore();
    }

    interface HomepageView extends BaseView<HomepagePresenter> {
        void bannerHotInfo(BannerHotResponse bannerHotResponse);
        void likeStroeInfo(List<LikeStoreResponse.likeInfo> likeInfos);
        void moreLikeStroeInfo(List<LikeStoreResponse.likeInfo> likeInfos);
    }
}
