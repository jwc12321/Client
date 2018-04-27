package com.purchase.sls.webview.unit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.webkit.WebView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


public class BridgeImpl implements IBridge {
    private static Context context;
    private static Callback callbackData;

    public BridgeImpl(Context context) {
        this.context = context;
    }

    public static void showToast(WebView webView, JSONObject param, final Callback callback) {
        String message = param.optString("msg");
        Toast.makeText(webView.getContext(), message, Toast.LENGTH_SHORT).show();
        if (null != callback) {
            try {
                JSONObject object = new JSONObject();
                object.put("key", "value");
                object.put("key1", "value1");
                callback.apply(getJSONObject(0, "ok", object));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //js调用我们方法
    public static void testThread(WebView webView, JSONObject param, Callback callback) {
        callbackData=callback;
//        Intent intent=new Intent(context,DataActivity.class);
//        ((Activity)context).startActivityForResult(intent,StaticData.CALLBACK_DATA);
    }

    private static JSONObject getJSONObject(int code, String msg, JSONObject result) {
        JSONObject object = new JSONObject();
        try {
            object.put("code", code);
            object.put("msg", msg);
            object.putOpt("result", result);
            return object;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void returnBackData(String data){
        JSONObject object = new JSONObject();
        try {
            object.put("key", data);
            callbackData.apply(getJSONObject(0, "ok", object));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
