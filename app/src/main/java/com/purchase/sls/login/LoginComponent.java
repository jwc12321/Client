package com.purchase.sls.login;

import com.purchase.sls.ActivityScope;
import com.purchase.sls.ApplicationComponent;
import com.purchase.sls.login.ui.LoginActivity;
import com.purchase.sls.login.ui.RetrievePassWordActivity;

import dagger.Component;

/**
 * Created by Administrator on 2017/12/27.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {LoginModule.class})
public interface LoginComponent {
    void inject(LoginActivity loginActivity);
    void inject(RetrievePassWordActivity retrievePassWordActivity);
}
