package com.purchase.sls.login;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/12/27.
 */
@Module
public class LoginModule {
    private LoginContract.LoginView loginView;
    private LoginContract.RetrievePassWordView retrievePassWordView;
    public LoginModule(LoginContract.LoginView loginView){
        this.loginView=loginView;
    }
    public LoginModule(LoginContract.RetrievePassWordView retrievePassWordView) {
        this.retrievePassWordView = retrievePassWordView;
    }

    @Provides
    LoginContract.LoginView provideLoginView(){
        return loginView;
    }
    @Provides
    LoginContract.RetrievePassWordView provideLoginContractRetrievePassWordView() {
        return retrievePassWordView;
    }
}
