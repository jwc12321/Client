package com.purchase.sls.bankcard.presenter;

import android.text.TextUtils;

import com.purchase.sls.bankcard.BankCardContract;
import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.PutForwardList;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.PageRequest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/11/27.
 */

public class PutForwardRecordPresenter implements BankCardContract.PutForwardRecordPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private BankCardContract.PutForwardRecordView putForwardRecordView;
    private int currentIndex = 1;  //当前index

    @Inject
    public PutForwardRecordPresenter(RestApiService restApiService, BankCardContract.PutForwardRecordView putForwardRecordView) {
        this.restApiService = restApiService;
        this.putForwardRecordView = putForwardRecordView;
    }


    @Inject
    public void setupListener() {
        putForwardRecordView.setPresenter(this);
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

    @Override
    public void getPutForwardRecords(String refreshType) {
        if(TextUtils.equals("1",refreshType)){
            putForwardRecordView.showLoading();
        }
        currentIndex = 1;
        PageRequest pageRequest=new PageRequest(String.valueOf(currentIndex));
        Disposable disposable = restApiService.getPutForwardList(pageRequest)
                .flatMap(new RxRemoteDataParse<PutForwardList>())
                .compose(new RxSchedulerTransformer<PutForwardList>())
                .subscribe(new Consumer<PutForwardList>() {
                    @Override
                    public void accept(PutForwardList putForwardList) throws Exception {
                        putForwardRecordView.dismissLoading();
                        putForwardRecordView.renderPutFRecords(putForwardList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        putForwardRecordView.dismissLoading();
                        putForwardRecordView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getMorePutForwardRecords() {
        currentIndex = currentIndex+1;
        PageRequest pageRequest=new PageRequest(String.valueOf(currentIndex));
        Disposable disposable = restApiService.getPutForwardList(pageRequest)
                .flatMap(new RxRemoteDataParse<PutForwardList>())
                .compose(new RxSchedulerTransformer<PutForwardList>())
                .subscribe(new Consumer<PutForwardList>() {
                    @Override
                    public void accept(PutForwardList putForwardList) throws Exception {
                        putForwardRecordView.renderMorePutFRecords(putForwardList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        putForwardRecordView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
