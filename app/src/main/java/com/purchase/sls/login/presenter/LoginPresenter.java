package com.purchase.sls.login.presenter;

import android.text.TextUtils;

import com.purchase.sls.UserInfoDao;
import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.Ignore;
import com.purchase.sls.data.entity.LoginTokenResponse;
import com.purchase.sls.data.entity.PersionInfoResponse;
import com.purchase.sls.data.entity.UserInfo;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.ChangepwdRequest;
import com.purchase.sls.data.request.CheckCodeRequest;
import com.purchase.sls.data.request.LoginRequest;
import com.purchase.sls.data.request.PhoneLoginRequest;
import com.purchase.sls.data.request.RegisterPasswordRequest;
import com.purchase.sls.data.request.SendCodeRequest;
import com.purchase.sls.login.LoginContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/4/17.
 */

public class LoginPresenter implements LoginContract.LoginPresenter {
    private LoginContract.LoginView loginView;
    private RestApiService restApiService;
    private UserInfoDao mUserInfoDao;
    private List<Disposable> mDisposableList = new ArrayList<>();
    @Inject
    public LoginPresenter(LoginContract.LoginView loginView, RestApiService restApiService,UserInfoDao mUserInfoDao) {
        this.loginView = loginView;
        this.restApiService = restApiService;
        this.mUserInfoDao=mUserInfoDao;
    }



    @Inject
    public void setupListener() {
        loginView.setPresenter(this);
    }

    /**
     * 密码登录和短信登录
     */
    @Override
    public void accountLogin(String username, String pwd,String clientid) {
        loginView.showLoading();
        LoginRequest loginRequest=new LoginRequest(username,pwd,clientid);
        Disposable disposable=restApiService.accountLogin(loginRequest)
                .flatMap(new RxRemoteDataParse<PersionInfoResponse>())
                .compose(new RxSchedulerTransformer<PersionInfoResponse>())
                .subscribe(new Consumer<PersionInfoResponse>() {
                    @Override
                    public void accept(PersionInfoResponse persionInfoResponse) throws Exception {
                        loginView.dismissLoading();
                        loginView.accountLoginSuccess(persionInfoResponse);
                        UserInfo userInfo = new UserInfo();
                        userInfo.setPhone("");
                        userInfo.setPassword("");
                        mUserInfoDao.insertOrReplace(userInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        loginView.dismissLoading();
                        loginView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    /**
     * 发送验证码
     * @param tel
     * @param dostr：register(注册)/login(登录)/changetel(修改手机)/changepwd（修改密码）
     */
    @Override
    public void sendCode(String tel, String dostr) {
        loginView.showLoading();
        SendCodeRequest sendCodeRequest=new SendCodeRequest(tel,dostr);
        Disposable disposable=restApiService.sendCode(sendCodeRequest)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        loginView.dismissLoading();
                        loginView.codeSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        loginView.dismissLoading();
                        loginView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    /**
     * 手机验证码登录
     * @param tel
     * @param code
     */
    @Override
    public void phoneLogin(String tel, String code) {
        loginView.showLoading();
        PhoneLoginRequest phoneLoginRequest=new PhoneLoginRequest(tel,code);
        Disposable disposable=restApiService.phoneLogin(phoneLoginRequest)
                .flatMap(new RxRemoteDataParse<PersionInfoResponse>())
                .compose(new RxSchedulerTransformer<PersionInfoResponse>())
                .subscribe(new Consumer<PersionInfoResponse>() {
                    @Override
                    public void accept(PersionInfoResponse persionInfoResponse) throws Exception {
                        loginView.dismissLoading();
                        loginView.accountLoginSuccess(persionInfoResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        loginView.dismissLoading();
                        loginView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    /**
     * 注册和修改密码
     * @param tel
     * @param password
     * @param address
     * @param type register(注册)/changepwd(修改密码)
     */
    @Override
    public void registerPassword(String tel, String password, String address, String type,String storeid,String code) {
        loginView.showLoading();
        RegisterPasswordRequest registerPasswordRequest;
        if(TextUtils.isEmpty(code)){
            registerPasswordRequest=new RegisterPasswordRequest(tel,password,address,type,storeid);
        }else {
            registerPasswordRequest=new RegisterPasswordRequest(tel,password,address,type,storeid,code);
        }
        Disposable disposable=restApiService.registerPassword(registerPasswordRequest)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        loginView.dismissLoading();
                        loginView.setPasswordSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        loginView.dismissLoading();
                        loginView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    /**
     * 验证验证码
     * @param tel
     * @param code
     * @param type
     */
    @Override
    public void checkCode(String tel, String code, String type) {
        loginView.showLoading();
        CheckCodeRequest checkCodeRequest=new CheckCodeRequest(tel,code,type);
        Disposable disposable=restApiService.checkCode(checkCodeRequest)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        loginView.dismissLoading();
                        loginView.checkCodeSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        loginView.dismissLoading();
                        loginView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void changepwd(String tel, String password, String type) {
        loginView.showLoading();
        ChangepwdRequest changepwdRequest=new ChangepwdRequest(tel,password,type);
        Disposable disposable=restApiService.changepwd(changepwdRequest)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        loginView.dismissLoading();
                        loginView.setPasswordSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        loginView.dismissLoading();
                        loginView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
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
}
