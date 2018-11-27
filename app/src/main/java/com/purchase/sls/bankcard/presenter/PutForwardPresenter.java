package com.purchase.sls.bankcard.presenter;

import com.purchase.sls.bankcard.BankCardContract;
import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.CommissionInfo;
import com.purchase.sls.data.entity.Ignore;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.PutForwardRequest;
import com.purchase.sls.data.request.TokenRequest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/11/27.
 */

public class PutForwardPresenter implements BankCardContract.PutForwardPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private BankCardContract.PutForwardView putForwardView;

    @Inject
    public PutForwardPresenter(RestApiService restApiService, BankCardContract.PutForwardView putForwardView) {
        this.restApiService = restApiService;
        this.putForwardView = putForwardView;
    }

    @Inject
    public void setupListener() {
        putForwardView.setPresenter(this);
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
    public void putForward(String userBankId, String price) {
        putForwardView.showLoading();
        PutForwardRequest putForwardRequest=new PutForwardRequest(userBankId,price);
        Disposable disposable = restApiService.putForward(putForwardRequest)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        putForwardView.dismissLoading();
                        putForwardView.putForwardSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        putForwardView.dismissLoading();
                        putForwardView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getCommissionInfo() {
        putForwardView.showLoading();
        TokenRequest tokenRequest=new TokenRequest();
        Disposable disposable = restApiService.getCommissionInfo(tokenRequest)
                .flatMap(new RxRemoteDataParse<CommissionInfo>())
                .compose(new RxSchedulerTransformer<CommissionInfo>())
                .subscribe(new Consumer<CommissionInfo>() {
                    @Override
                    public void accept(CommissionInfo commissionInfo) throws Exception {
                        putForwardView.dismissLoading();
                        putForwardView.renderCommissionInfo(commissionInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        putForwardView.dismissLoading();
                        putForwardView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
