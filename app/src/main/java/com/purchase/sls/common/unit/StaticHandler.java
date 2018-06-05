package com.purchase.sls.common.unit;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by JWC on 2018/6/5.
 */

public abstract class StaticHandler<T> extends Handler {
    private WeakReference<T> mTargets;

    public StaticHandler(T target) {
        mTargets = new WeakReference<>(target);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        T target = mTargets.get();
        if (target != null) {
            handle(target, msg);
        }
    }

    public abstract void handle(T target, Message msg);{
    }
}
