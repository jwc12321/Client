package com.purchase.sls.data.event;

/**
 * Created by JWC on 2017/5/4.
 */
public class PayAbortEvent {

    public String msg;
    public int code;

    public PayAbortEvent(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }

    public PayAbortEvent() {
    }
}
