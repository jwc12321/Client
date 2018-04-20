package com.purchase.sls.data.remote;


import com.purchase.sls.data.RemoteDataWrapper;
import com.purchase.sls.data.entity.CeshiResponse;
import com.purchase.sls.data.entity.Ignore;
import com.purchase.sls.data.entity.LoginTokenResponse;
import com.purchase.sls.data.entity.PersionInfoResponse;
import com.purchase.sls.data.request.CaptchaRequest;
import com.purchase.sls.data.request.CheckCodeRequest;
import com.purchase.sls.data.request.LoginRequest;
import com.purchase.sls.data.request.PhoneLoginRequest;
import com.purchase.sls.data.request.RegisterPasswordRequest;
import com.purchase.sls.data.request.SendCodeRequest;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/12/27.
 */

public interface RestApiService {

    //密码登录
    @POST("login/dologin")
    Flowable<RemoteDataWrapper<PersionInfoResponse>> accountLogin(@Body LoginRequest loginRequest);
    //发送验证码
    @POST("login/sendcode")
    Flowable<RemoteDataWrapper<Ignore>> sendCode(@Body SendCodeRequest sendCodeRequest);
    //短信登录
    @POST("login/bycode")
    Flowable<RemoteDataWrapper<PersionInfoResponse>> phoneLogin(@Body PhoneLoginRequest phoneLoginRequest);
    //注册/修改密码
    @POST("login/register")
    Flowable<RemoteDataWrapper<Ignore>> registerPassword(@Body RegisterPasswordRequest registerPasswordRequest);
    //验证验证码
    @POST("login/checkCode")
    Flowable<RemoteDataWrapper<Ignore>> checkCode(@Body CheckCodeRequest checkCodeRequest);
    /**
     * send captcha
     */
    @POST("logining/jj")
    Flowable<RemoteDataWrapper<CeshiResponse>> sendCaptcha1(@Body CaptchaRequest request);


}
