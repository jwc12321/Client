package com.purchase.sls.paypassword.presenter;

import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.Ignore;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.CheckCodeRequest;
import com.purchase.sls.data.request.SendCodeRequest;
import com.purchase.sls.paypassword.PayPasswordContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/8/22.
 */

public class SmsAuthenticationPresenter implements PayPasswordContract.SmsAuthenticationPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private PayPasswordContract.SmsAuthenticationView smsAuthenticationView;

    @Inject
    public SmsAuthenticationPresenter(RestApiService restApiService, PayPasswordContract.SmsAuthenticationView smsAuthenticationView) {
        this.restApiService = restApiService;
        this.smsAuthenticationView = smsAuthenticationView;
    }

    @Inject
    public void setupListener() {
        smsAuthenticationView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        for (Disposable disposable : mDisposableList) {
            if (!disposable.isDisposed()) {
                disposable.dispose();
            }
        }
    }
    /**
     * 发送验证码
     * @param tel
     * @param dostr：register(注册)/login(登录)/changetel(修改手机)/changepwd（修改密码）/paypassword(支付密码)
     */
    @Override
    public void sendCode(String tel, String dostr) {
        smsAuthenticationView.showLoading();
        SendCodeRequest sendCodeRequest=new SendCodeRequest(tel,dostr);
        Disposable disposable=restApiService.sendCode(sendCodeRequest)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        smsAuthenticationView.dismissLoading();
                        smsAuthenticationView.sendCodeSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        smsAuthenticationView.dismissLoading();
                        smsAuthenticationView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void checkCode(String tel, String code, String type) {
        smsAuthenticationView.showLoading();
        CheckCodeRequest checkCodeRequest=new CheckCodeRequest(tel,code,type);
        Disposable disposable=restApiService.checkCode(checkCodeRequest)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        smsAuthenticationView.dismissLoading();
                        smsAuthenticationView.checkCodeSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        smsAuthenticationView.dismissLoading();
                        smsAuthenticationView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
