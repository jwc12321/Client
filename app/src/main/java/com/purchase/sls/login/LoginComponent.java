package com.purchase.sls.login;

import com.purchase.sls.ActivityScope;
import com.purchase.sls.ApplicationComponent;
import com.purchase.sls.login.ui.AccountLoginActivity;
import com.purchase.sls.login.ui.RegisterFirstActivity;
import com.purchase.sls.login.ui.RegisterSecondActivity;
import com.purchase.sls.login.ui.SmsLoginActivity;

import dagger.Component;

/**
 * Created by JWC on 2017/12/27.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {LoginModule.class})
public interface LoginComponent {
    void inject(AccountLoginActivity accountLoginActivity);
    void inject(SmsLoginActivity smsLoginActivity);
    void inject(RegisterFirstActivity registerFirstActivity);
    void inject(RegisterSecondActivity registerSecondActivity);

}
