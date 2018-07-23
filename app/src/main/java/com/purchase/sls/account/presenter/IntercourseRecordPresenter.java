package com.purchase.sls.account.presenter;

import android.text.TextUtils;

import com.purchase.sls.account.AccountContract;
import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.IntercourseRecordInfo;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.IntercourseRecordRequest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/5/2.
 */

public class IntercourseRecordPresenter implements AccountContract.IntercourseRecordPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private AccountContract.IntercourseRecordView intercourseRecordView;
    private int currentIndex = 1;  //待接单当前index

    @Inject
    public IntercourseRecordPresenter(RestApiService restApiService, AccountContract.IntercourseRecordView intercourseRecordView) {
        this.restApiService = restApiService;
        this.intercourseRecordView = intercourseRecordView;
    }

    @Inject
    public void setupListener() {
        intercourseRecordView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        for (Disposable disposable : mDisposableList) {
            if (!disposable.isDisposed()) {
                disposable.dispose();
            }
        }
    }

    /**
     * 来往记录
     * @param refreshType
     * @param storeid
     */
    @Override
    public void getIntercourseRecordInfo(String refreshType,String storeid) {
        if(TextUtils.equals("1",refreshType)){
            intercourseRecordView.showLoading();
        }
        currentIndex = 1;
        IntercourseRecordRequest intercourseRecordRequest = new IntercourseRecordRequest(String.valueOf(currentIndex),storeid);
        Disposable disposable = restApiService.getIntercourseRecordInfo(intercourseRecordRequest)
                .flatMap(new RxRemoteDataParse<IntercourseRecordInfo>())
                .compose(new RxSchedulerTransformer<IntercourseRecordInfo>())
                .subscribe(new Consumer<IntercourseRecordInfo>() {
                    @Override
                    public void accept(IntercourseRecordInfo intercourseRecordInfo) throws Exception {
                        intercourseRecordView.dismissLoading();
                        intercourseRecordView.intercourseRecordInfo(intercourseRecordInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        intercourseRecordView.dismissLoading();
                        intercourseRecordView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    /**
     * 获取更多来往记录
     * @param storeid
     */
    @Override
    public void getMoreIntercourseRecordInfo(String storeid) {
        currentIndex = currentIndex + 1;
        IntercourseRecordRequest intercourseRecordRequest = new IntercourseRecordRequest(String.valueOf(currentIndex),storeid);
        Disposable disposable = restApiService.getIntercourseRecordInfo(intercourseRecordRequest)
                .flatMap(new RxRemoteDataParse<IntercourseRecordInfo>())
                .compose(new RxSchedulerTransformer<IntercourseRecordInfo>())
                .subscribe(new Consumer<IntercourseRecordInfo>() {
                    @Override
                    public void accept(IntercourseRecordInfo intercourseRecordInfo) throws Exception {
                        intercourseRecordView.moreIntercourseRecordInfo(intercourseRecordInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        intercourseRecordView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
