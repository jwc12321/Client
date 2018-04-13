package com.purchase.sls.login;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/12/27.
 */
@Module
public class LoginModule {
    private LoginContract.RetrievePassWordView retrievePassWordView;
    public LoginModule(LoginContract.RetrievePassWordView retrievePassWordView) {
        this.retrievePassWordView = retrievePassWordView;
    }
    @Provides
    LoginContract.RetrievePassWordView provideLoginContractRetrievePassWordView() {
        return retrievePassWordView;
    }
}
