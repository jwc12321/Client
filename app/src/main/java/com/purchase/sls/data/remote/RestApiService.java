package com.purchase.sls.data.remote;


import com.purchase.sls.data.RemoteDataWrapper;
import com.purchase.sls.data.entity.CeshiResponse;
import com.purchase.sls.data.entity.Ignore;
import com.purchase.sls.data.entity.LoginTokenResponse;
import com.purchase.sls.data.request.CaptchaRequest;
import com.purchase.sls.data.request.LoginRequest;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/12/27.
 */

public interface RestApiService {

    //密码登录
    @POST("logining")
    Flowable<RemoteDataWrapper<LoginTokenResponse>> login(@Body LoginRequest loginRequest);
    /**
     * send captcha
     */
    @POST("logining")
    Flowable<RemoteDataWrapper<CeshiResponse>> sendCaptcha(@Body CaptchaRequest request);


}
