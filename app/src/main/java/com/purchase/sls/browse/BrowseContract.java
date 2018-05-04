package com.purchase.sls.browse;

import com.purchase.sls.BasePresenter;
import com.purchase.sls.BaseView;
import com.purchase.sls.data.entity.BrowseInfo;

/**
 * Created by JWC on 2018/5/3.
 */

public interface BrowseContract {
    interface BrowsePresenter extends BasePresenter{
        void getBrowseInfo();
        void getMoreBrowseInfo();
        void removeBrowse(String[] idArray);
    }
    interface BrowseView extends BaseView<BrowsePresenter>{
        void renderBrowseInfo(BrowseInfo browseInfo);
        void renderMoreBrowseInfo(BrowseInfo browseInfo);
        void removeSuccess();
    }
}
