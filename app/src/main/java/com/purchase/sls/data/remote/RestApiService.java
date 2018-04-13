package com.purchase.sls.data.remote;


import com.purchase.sls.data.RemoteDataWrapper;
import com.purchase.sls.data.entity.CeshiResponse;
import com.purchase.sls.data.entity.Ignore;
import com.purchase.sls.data.request.CaptchaRequest;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/12/27.
 */

public interface RestApiService {
    /**
     * send captcha
     */
    @POST("login/dologin")
    Flowable<RemoteDataWrapper<CeshiResponse>> sendCaptcha(@Body CaptchaRequest request);


}
