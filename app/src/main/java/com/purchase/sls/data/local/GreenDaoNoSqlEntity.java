package com.purchase.sls.data.local;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Description: simple no sql use greendao for key-value store
 */

@Entity
public class GreenDaoNoSqlEntity {
    @Id
    private String key;

    private String value;

    private Long time;

    @Generated(hash = 53403251)
    public GreenDaoNoSqlEntity(String key, String value, Long time) {
        this.key = key;
        this.value = value;
        this.time = time;
    }

    @Generated(hash = 354060677)
    public GreenDaoNoSqlEntity() {
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getTime() {
        return this.time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
