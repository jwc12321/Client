package com.purchase.sls.common;


import com.purchase.sls.BuildConfig;


/**
 * Created by Sherily on 2016/7/22.
 */
public class Url {


    public static String API_BASE_URL_DEFAULT = BuildConfig.API_BASE_URL;
    //    public static String HTM_BASE_URL_DEFAULT = BuildConfig.HTM_BASE_URL;
    //API
    public static final String API_BASE_URL_DEBUG = "http://copen.homepaas.com/api/v3/";
    public static final String API_BASE_URL_RELEASE = "http://copen.zhujiash.com/api/v3/";
    public static final String API_BASE_URL_DEVELOPER = "http://192.168.1.191:3001/api/v3/";

    //HTM
    public static final String HTM_BASE_URL_DEBUG = "http://copen.homepaas.com/htm/";
    public static final String HTM_BASE_URL_RELEASE = "http://copen.zhujiash.com/htm/";
    public static final String HTM_BASE_URL_DEVELOPER = "http://192.168.1.191:3001/htm/";

//    public static void setApiBaseUrlDefault() {
//        API_BASE_URL_DEFAULT = API_BASE_URL_RELEASE;
//    }
//
//    public static void setHtmBaseUrlDefault() {
//        HTM_BASE_URL_DEFAULT = HTM_BASE_URL_RELEASE;
//    }

    public static void setRelease() {

        API_BASE_URL_DEFAULT = API_BASE_URL_RELEASE;
//       HTM_BASE_URL_DEFAULT = HTM_BASE_URL_RELEASE;
    }

    public static void setDebug() {

        API_BASE_URL_DEFAULT = API_BASE_URL_DEBUG;
//        HTM_BASE_URL_DEFAULT = HTM_BASE_URL_DEBUG;
    }

    public static void setDeveloper() {

        API_BASE_URL_DEFAULT = API_BASE_URL_DEVELOPER;
//        HTM_BASE_URL_DEFAULT = HTM_BASE_URL_DEVELOPER;
    }


}
