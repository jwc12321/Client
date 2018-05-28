package com.purchase.sls.mainframe;

import com.purchase.sls.BasePresenter;
import com.purchase.sls.BaseView;
import com.purchase.sls.data.entity.ChangeAppInfo;

/**
 * Created by JWC on 2018/4/19.
 */

public interface MainFrameContract {
    interface MainFramePresenter extends BasePresenter {
        void detectionVersion(String edition, String type);
    }

    interface MainFrameView extends BaseView<MainFramePresenter> {
        void detectionSuccess(ChangeAppInfo changeAppInfo);
    }
}
