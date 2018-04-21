package com.purchase.sls.common.unit;

import com.purchase.sls.common.Url;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sherily on 2016/9/22.
 */
public class UrlUtils {
    public static boolean isUrl(String url){
        if (url != null){
            Pattern pattern = Pattern.compile("((http|ftp|https)://)(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*(/[a-zA-Z0-9\\&%_\\./-~-]*)?");
            Matcher matcher = pattern.matcher(url);
            return matcher.matches();
        }
        return false;
    }

    public static String baseUrl(){
//        String baseUrl = BuildConfig.API_BASE_URL;
        String baseUrl = Url.API_BASE_URL_DEFAULT;
        String[] strings = baseUrl.split("/");
//        for (String string : strings){
//            if (BuildConfig.DEBUG){
//                android.util.Log.d("baseurl", "baseUrl: "+string);
//            }
//        }
        android.util.Log.d("baseurl", "baseUrl: "+strings[0]+"//"+strings[2]+"/qrcode?");
        return strings[2]+"/qrcode";
    }

    public static String domain(){
        String baseUrl = Url.API_BASE_URL_DEFAULT;
        String[] strings = baseUrl.split("/");
//        for (String string : strings){
//            if (BuildConfig.DEBUG){
//                android.util.Log.d("baseurl", "baseUrl: "+string);
//            }
//        }
        android.util.Log.d("baseurl", "baseUrl: "+strings[0]+"//"+strings[2]+"/qrcode?");
        return strings[0]+"://"+strings[2];
    }

    public static String[] spiltString(String result, String spiltString){
        return  result.split(spiltString);
    }
}
