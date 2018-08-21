package com.purchase.sls.paypassword;

/**
 * Created by JWC on 2018/8/20.
 */

import com.purchase.sls.ActivityScope;
import com.purchase.sls.ApplicationComponent;
import com.purchase.sls.paypassword.ui.FirstPayPwActivity;
import com.purchase.sls.paypassword.ui.SecondPayPwActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {PayPasswordModule.class})
public interface PayPasswordComponent {
    void inject(SecondPayPwActivity secondPayPwActivity);
}
