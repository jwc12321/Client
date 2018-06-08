package com.purchase.sls.ordermanage;

import com.purchase.sls.BasePresenter;
import com.purchase.sls.BaseView;
import com.purchase.sls.data.entity.ActivityInfo;
import com.purchase.sls.data.entity.ActivityOrderDetailInfo;
import com.purchase.sls.data.entity.ActivityOrderInfo;

import java.util.List;

/**
 * Created by JWC on 2018/6/6.
 */

public interface OrderManageContract {
    interface ActivityOrderListPresenter extends BasePresenter{
        void getActivityOrderList(String type);
        void getMoreActivityOrderList(String type);
        void getActivityOrderDetail(String id);
        void deleteActivityOrder(String id);
    }
    interface ActivityOrderListView extends BaseView<ActivityOrderListPresenter>{
        void render(List<ActivityOrderInfo> activityOrderInfos);
        void renderMore(List<ActivityOrderInfo> activityOrderInfos);
        void activityOrderDetail(ActivityOrderDetailInfo activityOrderDetailInfo);
        void deleteSuccess();
    }

    interface ActivityDetailInfoPresenter extends BasePresenter{
        void getActivityDetail(String activityId);
    }
    interface ActivityDetailInfoView extends BaseView<ActivityDetailInfoPresenter>{
        void activityDetailInfo(ActivityInfo activityInfo);
    }
}
