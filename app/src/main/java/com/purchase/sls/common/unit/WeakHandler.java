package com.purchase.sls.common.unit;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by JWC on 2018/6/5.
 */

public abstract class WeakHandler<T> extends Handler {

    protected WeakReference<T> reference;

    //创建子线程Handler使用的构造器
    public WeakHandler(Looper looper, T reference) {
        super(looper);
        this.reference = new WeakReference<>(reference);
    }

    //创建主线程Handler使用的构造器
    public WeakHandler(T reference) {
        this.reference = new WeakReference<>(reference);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        T t = reference.get();
        if (t == null)
            return;
        handleMessage(t, msg);
    }

    protected abstract void handleMessage(T t, Message message);{
    }
}
