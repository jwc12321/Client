package com.purchase.sls.data;

/**
 * Description: serialize entity
 */

public interface EntitySerializer<T> {

    String serialize(T t);

    T deserialize(String string, Class<T> tClass);
}