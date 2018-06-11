package com.purchase.sls.energy;

import com.purchase.sls.BasePresenter;
import com.purchase.sls.BaseView;
import com.purchase.sls.data.entity.ActivityInfo;
import com.purchase.sls.data.entity.ActivityOrderDetailInfo;
import com.purchase.sls.data.entity.EnergyInfo;

import java.util.List;

/**
 * Created by JWC on 2018/4/19.
 */

public interface EnergyContract {
    interface EnergyInfoPresenter extends BasePresenter {
        void getEnergyInfo(String refreshType, String pool);

        void getMoreEnergyInfo(String pool);
    }

    interface EnergyInfoView extends BaseView<EnergyInfoPresenter> {
        void renderEnergyInfo(EnergyInfo energyInfo);

        void renderMoreEnergyInfo(EnergyInfo energyInfo);
    }

    interface ActivityPresenter extends BasePresenter {
        void getActivitys(String refreshType, String type);

        void getEnergyInfo(String pool);
    }

    interface ActivityView extends BaseView<ActivityPresenter> {
        void renderActivitys(List<ActivityInfo> activityInfos);

        void renderEnergyInfo(EnergyInfo energyInfo);
    }

    interface ActivityDetailPresenter extends BasePresenter {
        void submitSpike(String id, String aid);

        void submitLottery(String id, String aid);
    }

    interface ActivityDetailView extends BaseView<ActivityDetailPresenter> {
        void submitSpikeSuccess(ActivityOrderDetailInfo activityOrderDetailInfo);

        void submitLotterySuccess(ActivityOrderDetailInfo activityOrderDetailInfo);
    }

    interface SignInPresenter extends BasePresenter {
        void signIn();
    }

    interface SignInView extends BaseView<SignInPresenter> {
        void signInSuccess(String energy);
    }

    interface SharePresenter extends BasePresenter{
        void share();
    }

    interface ShareView extends BaseView<SharePresenter>{
        void success(String energy);
    }
}
