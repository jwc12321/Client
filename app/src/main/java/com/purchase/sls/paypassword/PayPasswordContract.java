package com.purchase.sls.paypassword;

import com.purchase.sls.BasePresenter;
import com.purchase.sls.BaseView;
import com.purchase.sls.data.entity.ActivityOrderDetailInfo;

/**
 * Created by JWC on 2018/8/20.
 */

public interface PayPasswordContract {
    interface PayPasswordPresenter extends BasePresenter{
        void setPayPassword(String payPassword, String type, String details);
    }

    interface PayPasswordView extends BaseView<PayPasswordPresenter>{
        void renderSuccess();
    }

    interface AuthenticationPresenter extends BasePresenter{
        void verifyPayPassword(String payPassword);
    }

    interface AuthenticationView extends BaseView<AuthenticationPresenter>{
        void verifySuccess();
    }

    interface SmsAuthenticationPresenter extends BasePresenter{
        void sendCode(String tel,String dostr);
        void checkCode(String tel,String code,String type);
    }

    interface SmsAuthenticationView extends BaseView<SmsAuthenticationPresenter>{
        void sendCodeSuccess();
        void checkCodeSuccess();
    }

    interface PayPwPowerPresenter extends BasePresenter{
        void payPwPower(String orderno, String paypassword);
    }

    interface PayPwPowerView extends BaseView<PayPwPowerPresenter>{
        void payPwPowerSuccess();
    }

    interface EcPayPwPowerPresenter extends BasePresenter{
        void paysecKill(String paypassword, String id, String aid);
        void paydrawOrder(String paypassword, String id, String aid);
    }

    interface EcPayPwPowerView extends BaseView<EcPayPwPowerPresenter>{
        void paySuccess(ActivityOrderDetailInfo activityOrderDetailInfo);
    }
}
