package com.purchase.sls;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/12/27.
 */

@Module
public class ApplicationModule {
    private final Context mContext;
    ApplicationModule(Context context){
        mContext=context;
    }
    @Provides
    Context provideContext(){
        return mContext;
    }
    @Provides
    SharedPreferences provideSharedPreferences(){
        return PreferenceManager.getDefaultSharedPreferences(mContext);
    }
}

