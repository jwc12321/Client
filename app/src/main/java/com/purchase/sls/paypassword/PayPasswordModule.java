package com.purchase.sls.paypassword;

import com.purchase.sls.data.request.PageRequest;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JWC on 2018/8/20.
 */
@Module
public class PayPasswordModule {
    private PayPasswordContract.PayPasswordView payPasswordView;
    private PayPasswordContract.AuthenticationView authenticationView;
    private PayPasswordContract.SmsAuthenticationView smsAuthenticationView;
    private PayPasswordContract.PayPwPowerView payPwPowerView;
    private PayPasswordContract.EcPayPwPowerView ecPayPwPowerView;

    public PayPasswordModule(PayPasswordContract.PayPasswordView payPasswordView) {
        this.payPasswordView = payPasswordView;
    }

    public PayPasswordModule(PayPasswordContract.AuthenticationView authenticationView) {
        this.authenticationView = authenticationView;
    }

    public PayPasswordModule(PayPasswordContract.SmsAuthenticationView smsAuthenticationView) {
        this.smsAuthenticationView = smsAuthenticationView;
    }

    public PayPasswordModule(PayPasswordContract.PayPwPowerView payPwPowerView) {
        this.payPwPowerView = payPwPowerView;
    }

    public PayPasswordModule(PayPasswordContract.EcPayPwPowerView ecPayPwPowerView) {
        this.ecPayPwPowerView = ecPayPwPowerView;
    }

    @Provides
    PayPasswordContract.PayPasswordView providePayPasswordView(){
        return payPasswordView;
    }

    @Provides
    PayPasswordContract.AuthenticationView provideAuthenticationView(){
        return authenticationView;
    }

    @Provides
    PayPasswordContract.SmsAuthenticationView provideSmsAuthenticationView(){
        return smsAuthenticationView;
    }

    @Provides
    PayPasswordContract.PayPwPowerView providePayPwPowerView(){
        return payPwPowerView;
    }

    @Provides
    PayPasswordContract.EcPayPwPowerView provideEcPayPwPowerView(){
        return ecPayPwPowerView;
    }
}
