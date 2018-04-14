package com.purchase.sls.data.remote;



import com.purchase.sls.data.RemoteDataException;
import com.purchase.sls.data.RemoteDataWrapper;
import com.purchase.sls.data.entity.Ignore;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.functions.Function;

/**
 * Created by Administrator on 2018/1/5.
 */

public final class RxRemoteDataParse<T> implements Function<RemoteDataWrapper<T>,Flowable<T>> {
    private final String unchecked="unchecked";
    @SuppressWarnings(unchecked)
    @Override
    public Flowable<T> apply(final RemoteDataWrapper<T> tRemoteDataWrapper) throws Exception {
        return Flowable.create(new FlowableOnSubscribe<T>() {
            @Override
            public void subscribe(FlowableEmitter<T> e) throws Exception {
                if(tRemoteDataWrapper.isSuccess()){
                    if (tRemoteDataWrapper.data==null){
                        e.onNext((T) Ignore.GET);
                    }else {
                        e.onNext(tRemoteDataWrapper.data);
                    }
                }else {
                    e.onError(new RemoteDataException(tRemoteDataWrapper.getErrorCode(), tRemoteDataWrapper.getErrorStr()));
                }
            }
        }, BackpressureStrategy.BUFFER);
    }
}
