package com.purchase.sls.nearbymap;

import com.purchase.sls.BasePresenter;
import com.purchase.sls.BaseView;
import com.purchase.sls.data.entity.NearbyInfoResponse;

import java.util.List;

/**
 * Created by JWC on 2018/4/19.
 */

public interface NearbyMapContract {
    interface NearbyPresenter extends BasePresenter{
        void getNearbyInfo(String address);
    }
    interface NearbyView extends BaseView<NearbyPresenter>{
        void nearbyInfo(List<NearbyInfoResponse> nearbyInfoResponses);
    }
}
