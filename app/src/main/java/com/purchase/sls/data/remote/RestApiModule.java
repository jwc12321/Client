package com.purchase.sls.data.remote;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.purchase.sls.BuildConfig;
import com.purchase.sls.common.unit.TokenManager;
import com.purchase.sls.data.EntitySerializer;
import com.purchase.sls.data.GsonSerializer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/1/5.
 */

@Module
public class RestApiModule {

    public RestApiModule() {

    }

    private String url = null;

    public RestApiModule(String url) {
        this.url = url;
    }

    @Singleton
    @Provides
    @Named("SupportExpose")
    Gson provideSupportExposeGson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }

    @Singleton
    @Provides
    Gson provideGson() {
        return new Gson();
    }

    @Singleton
    @Provides
    @Named("GsonSerializer")
    EntitySerializer provideEntitySerializer(Gson gson) {
        return new GsonSerializer(gson);
    }



    public class HttpLogger implements HttpLoggingInterceptor.Logger {
        @Override
        public void log(String message) {
            try {
                String text = URLDecoder.decode(message, "utf-8");
                Log.e("OKHttp-----", text);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                Log.e("OKHttp-----", message);
            }
        }
    }

    @Singleton
    @Provides
    @Named("HttpLogging")
    Interceptor provideHttpLoggingInterceptor() {
//        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLogger());
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }


    @Singleton
    @Provides
    @Named("AddFormData")
    Interceptor provideAddFormDataInterceptor() {

        //add platform param to request body for form-style request
        return new Interceptor() {
            //Android platform special param
            private static final String PARAM_KEY = "platform";
            private static final String PARAM_VALUE = "1";

            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
//                if (original.body() instanceof FormBody) {
//                    FormBody srcBody = (FormBody) original.body();
//                    FormBody.Builder builder = new FormBody.Builder();
//                    for (int i = 0; i < srcBody.size(); i++) {
//                        builder.addEncoded(srcBody.encodedName(i), srcBody.encodedValue(i));
//                    }
//                    builder.addEncoded(PARAM_KEY, PARAM_VALUE);
//                    FormBody dstBody = builder.build();
                Request request;
                if (TextUtils.isEmpty(TokenManager.getToken())) {
                    request = original.newBuilder()
                            .header("platform","android")
                            .header("version",BuildConfig.VERSION_NAME)
                            .build();
                } else {
                    request = original.newBuilder()
                            .header("Token", TokenManager.getToken() + "")
                            .header("platform","android")
                            .header("version",BuildConfig.VERSION_NAME)
                            .build();
                }
                return chain.proceed(request);
//                } else {
//                    return chain.proceed(original);
//                }
            }
        };
    }

    @Singleton
    @Provides
    Cache provideCache(Context context) {
        long cacheSize = BuildConfig.DEFAULT_HTTP_CACHE_MB * 1024 * 1024;
        return new Cache(context.getCacheDir(), cacheSize);
    }

    @Singleton
    @Provides
    @Named("NoCache")
    OkHttpClient provideOkHttpClient(@Named("AddFormData") Interceptor addFormData,
                                     @Named("HttpLogging") Interceptor httpLogging) {
        return new OkHttpClient.Builder()
                .connectTimeout(BuildConfig.DEFAULT_CONNECTION_TIMEOUT_SEC, TimeUnit.SECONDS)
                .readTimeout(BuildConfig.DEFAULT_READ_TIMEOUT_SEC, TimeUnit.SECONDS)
                .addNetworkInterceptor(addFormData)
                .addNetworkInterceptor(httpLogging)
//                .addInterceptor(httpLogging)
                .build();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(@Named("NoCache") OkHttpClient okHttpClient, Gson gson) {
        if (TextUtils.isEmpty(url)) {
            return new Retrofit.Builder()
                    .baseUrl(BuildConfig.API_BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        } else {
            return new Retrofit.Builder()
                    .baseUrl(url)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
    }


    @Singleton
    @Provides
    RestApiService provideRestApiService(Retrofit retrofit) {
        return retrofit.create(RestApiService.class);
    }
}
