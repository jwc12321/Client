package com.purchase.sls.data;

import com.google.gson.Gson;

/**
 * Description: serialize with gson
 */

public class GsonSerializer<T> implements EntitySerializer<T> {

    private Gson mGson;

    public GsonSerializer(Gson gson) {
        mGson = gson;
    }

    @Override
    public String serialize(T t) {
        return mGson.toJson(t);
    }

    @Override
    public T deserialize(String string, Class<T> tClass) {
        return mGson.fromJson(string, tClass);
    }
}
