package com.purchase.sls.paypassword;

import com.purchase.sls.BasePresenter;
import com.purchase.sls.BaseView;

/**
 * Created by JWC on 2018/8/20.
 */

public interface PayPasswordContract {
    interface PayPasswordPresenter extends BasePresenter{
        void setPayPassword(String payPassword);
    }

    interface PayPasswordView extends BaseView<PayPasswordPresenter>{
        void renderSuccess();
    }
}
