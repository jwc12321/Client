package com.purchase.sls.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


import com.purchase.sls.BuildConfig;
import com.purchase.sls.DaoMaster;
import com.purchase.sls.DaoSession;
import com.purchase.sls.UserInfoDao;
import com.purchase.sls.data.EntitySerializer;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Description: dagger module : green dao module
 */

@Module
public class GreenDaoModule {

    private final Context mContext;

    public GreenDaoModule(Context context) {
        mContext = context;
    }


    @Singleton
    @Provides
    SQLiteDatabase provideSQLiteDatabase() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(mContext, BuildConfig.GREEN_DAO_DB_NAME, null);
        return devOpenHelper.getWritableDatabase();
    }

    @Singleton
    @Provides
    DaoMaster provideDaoMaster(SQLiteDatabase sqLiteDatabase) {
        return new DaoMaster(sqLiteDatabase);
    }

    @Singleton
    @Provides
    DaoSession provideDaoSession(DaoMaster daoMaster) {
        return daoMaster.newSession();
    }

    @Singleton
    @Provides
    NoSqlHelper provideNoSqlHelper(DaoSession daoSession, @Named("GsonSerializer") EntitySerializer entitySerializer) {
        return new NoSqlHelper(daoSession, entitySerializer);
    }

    @Provides
    UserInfoDao provideUserInfoDao(DaoSession daoSession) {
        return daoSession.getUserInfoDao();
    }
}
