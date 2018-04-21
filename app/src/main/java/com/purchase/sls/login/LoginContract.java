package com.purchase.sls.login;


import com.purchase.sls.BasePresenter;
import com.purchase.sls.BaseView;
import com.purchase.sls.data.entity.PersionInfoResponse;

/**
 * Created by JWC on 2017/12/27.
 */

public interface LoginContract {

    interface LoginPresenter extends BasePresenter{
       void accountLogin(String username,String pwd,String clientid);
       void sendCode(String tel,String dostr);
       void phoneLogin(String tel,String code);
       void registerPassword(String tel,String password,String address,String type);
       void checkCode(String tel,String code,String type);
    }

    interface LoginView extends BaseView<LoginPresenter>{
        void accountLoginSuccess(PersionInfoResponse persionInfoResponse);
        void codeSuccess();
        void checkCodeSuccess();
        void setPasswordSuccess();
    }
    interface RetrievePassWordPresenter extends BasePresenter {
        void sendCaptcha(String phone);
    }

    interface RetrievePassWordView extends BaseView<RetrievePassWordPresenter> {
        void onCaptchaSend();

        void notRegister();
    }
}
