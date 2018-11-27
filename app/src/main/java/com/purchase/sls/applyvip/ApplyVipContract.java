package com.purchase.sls.applyvip;

import com.purchase.sls.BasePresenter;
import com.purchase.sls.BaseView;

/**
 * Created by JWC on 2018/11/27.
 */

public interface ApplyVipContract {
    interface ApplyVipPresenter extends BasePresenter{
        void applyVip(String idcard, String realname);
    }

    interface ApplyVipView extends BaseView<ApplyVipPresenter>{
        void applyVipSuccess();
    }
}
