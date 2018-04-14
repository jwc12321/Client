package com.purchase.sls.data.local;

import com.purchase.sls.DaoSession;
import com.purchase.sls.data.EntitySerializer;

/**
 * Description: key-value use green dao
 */

public class NoSqlHelper {

    private DaoSession mDaoSession;
    private EntitySerializer mEntitySerializer;

    public NoSqlHelper(DaoSession daoSession, EntitySerializer serializer) {
        mDaoSession = daoSession;
        mEntitySerializer = serializer;
    }

    @SuppressWarnings("unchecked")
    public <T> long save(String key, T t) {
        String value = mEntitySerializer.serialize(t);
        long time = System.currentTimeMillis();
        GreenDaoNoSqlEntity entity = new GreenDaoNoSqlEntity(key, value, time);
        return mDaoSession.insertOrReplace(entity);
    }

    @SuppressWarnings("unchecked")
    public <T> T load(String key, Class<T> tClass) {
        GreenDaoNoSqlEntity entity = mDaoSession.getGreenDaoNoSqlEntityDao()
                .load(key);
        if (entity != null && entity.getValue() != null) {
            return (T) mEntitySerializer.deserialize(entity.getValue(), tClass);
        } else {
            return null;
        }
    }

    public void delete(String key) {
        mDaoSession.getGreenDaoNoSqlEntityDao()
                .deleteByKey(key);
    }
}
