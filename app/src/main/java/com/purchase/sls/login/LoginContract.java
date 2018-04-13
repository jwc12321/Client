package com.purchase.sls.login;


import com.purchase.sls.BasePresenter;
import com.purchase.sls.BaseView;

/**
 * Created by Administrator on 2017/12/27.
 */

public interface LoginContract {
    int CAPTCHA_PASSWORD = 2;

    interface RetrievePassWordPresenter extends BasePresenter {
        void sendCaptcha(String phone);
    }

    interface RetrievePassWordView extends BaseView<RetrievePassWordPresenter> {
        void onCaptchaSend();

        void notRegister();
    }
}
