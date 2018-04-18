package com.purchase.sls.login;


import com.purchase.sls.BasePresenter;
import com.purchase.sls.BaseView;

/**
 * Created by Administrator on 2017/12/27.
 */

public interface LoginContract {

    interface LoginPresenter extends BasePresenter{
       void login(String username,String type,String code,String password);
    }

    interface LoginView extends BaseView<LoginPresenter>{
        void success();
    }
    interface RetrievePassWordPresenter extends BasePresenter {
        void sendCaptcha(String phone);
    }

    interface RetrievePassWordView extends BaseView<RetrievePassWordPresenter> {
        void onCaptchaSend();

        void notRegister();
    }
}
