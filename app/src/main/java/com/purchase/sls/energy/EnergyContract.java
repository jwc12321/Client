package com.purchase.sls.energy;

import com.purchase.sls.BasePresenter;
import com.purchase.sls.BaseView;
import com.purchase.sls.data.entity.EnergyInfo;

/**
 * Created by JWC on 2018/4/19.
 */

public interface EnergyContract {
    interface EnergyInfoPresenter extends BasePresenter {
        void getEnergyInfo(String pool);

        void getMoreEnergyInfo(String pool);
    }

    interface EnergyInfoView extends BaseView<EnergyInfoPresenter>{
        void renderEnergyInfo(EnergyInfo energyInfo);
        void renderMoreEnergyInfo(EnergyInfo energyInfo);
    }
}
