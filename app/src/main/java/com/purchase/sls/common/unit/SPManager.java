package com.purchase.sls.common.unit;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/3/18.
 * SharePreferences 工具，用户获取持久化数据如Token等
 */
public class SPManager {

    private Context context;
    private static final String FILE = "NG_CLIENT_FILE";
    //上次弹出更新窗口的时间
    private static final String LAST_POPOUT_TIME ="LAST_POPOUT_TIME" ;

    public static final String DEFAULT_VALUE = null;


    private static SPManager manager;

    public void register(Context ctx) {
        context = ctx;
    }

    public static SPManager getInstance() {

        if (manager == null) {
            synchronized (SPManager.class) {
                manager = new SPManager();
            }
        }

        return manager;
    }

    private void check() {
        if (context == null) {
            throw new IllegalStateException("context not initialize");
        }
    }

    /**
     * 给定键值获取数据
     * @param key ..
     * @return
     */
    public String getData(String key) {

        check();

        SharedPreferences sp = context.getSharedPreferences(FILE, Context.MODE_PRIVATE);
        return sp.getString(key, DEFAULT_VALUE);
    }
    public boolean getBooleanData(String key) {

        check();

        SharedPreferences sp = context.getSharedPreferences(FILE, Context.MODE_PRIVATE);
        return sp.getBoolean(key,false);
    }
    public int getIntData(String key) {
        check();

        SharedPreferences sp = context.getSharedPreferences(FILE, Context.MODE_PRIVATE);
        return sp.getInt(key, 0);
    }
    /**
     * 给定键值和文件名获取数据
     *
     * @param key ..
     * @param filename ..
     * @return
     */


    /**
     * 存数据到默认文件内
     */
    public void putData(String key, String value) {
        check();
        SharedPreferences sp = context.getSharedPreferences(FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.commit();
    }

    /**
     * 存数据到默认文件内
     */
    public void putData(String key, int value) {
        check();
        SharedPreferences sp = context.getSharedPreferences(FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(key, value);
        edit.commit();
    }
    public String getData(String key, String filename) {
        check();
        SharedPreferences sp = context.getSharedPreferences(filename, Context.MODE_PRIVATE);
        return sp.getString(key, DEFAULT_VALUE);
    }
    public void putBooleanData(String key, boolean value) {
        check();
        SharedPreferences sp = context.getSharedPreferences(FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }
    /**
     * 存到指定的文件里面
     */
    public void putData(String filename, String key, String value) {
        check();
        SharedPreferences sp = context.getSharedPreferences(filename, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.commit();
    }


}
