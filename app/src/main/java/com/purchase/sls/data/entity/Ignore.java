package com.purchase.sls.data.entity;

/**
 * Created by Administrator on 2017/3/1.
 * RxJava2 不允许emit null对象，所以封装一个空对象
 */

public class Ignore {

    public static final Ignore GET = new Ignore();

    private Ignore() {

    }
}
