package com.purchase.sls.ordermanage;

import com.purchase.sls.BasePresenter;
import com.purchase.sls.BaseView;
import com.purchase.sls.data.entity.ActivityOrderInfo;

import java.util.List;

/**
 * Created by JWC on 2018/6/6.
 */

public interface OrderManageContract {
    interface ActivityOrderListPresenter extends BasePresenter{
        void getActivityOrderList(String type);
        void getMoreActivityOrderList(String type);
    }
    interface ActivityOrderListView extends BaseView<ActivityOrderListPresenter>{
        void render(List<ActivityOrderInfo> activityOrderInfos);
        void renderMore(List<ActivityOrderInfo> activityOrderInfos);
    }
}
