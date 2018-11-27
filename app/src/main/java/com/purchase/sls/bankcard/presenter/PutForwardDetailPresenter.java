package com.purchase.sls.bankcard.presenter;

import com.purchase.sls.bankcard.BankCardContract;
import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.Ignore;
import com.purchase.sls.data.entity.PfRecrodDetail;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.IdRequest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/11/27.
 */

public class PutForwardDetailPresenter implements BankCardContract.PutForwardDetailPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private BankCardContract.PutForwardDetailView putForwardDetailView;

    @Inject
    public PutForwardDetailPresenter(RestApiService restApiService, BankCardContract.PutForwardDetailView putForwardDetailView) {
        this.restApiService = restApiService;
        this.putForwardDetailView = putForwardDetailView;
    }

    @Inject
    public void setupListener() {
        putForwardDetailView.setPresenter(this);
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
    public void getPutForwardDetail(String id) {
        putForwardDetailView.showLoading();
        IdRequest idRequest=new IdRequest(id);
        Disposable disposable = restApiService.getPfRecrodDetail(idRequest)
                .flatMap(new RxRemoteDataParse<PfRecrodDetail>())
                .compose(new RxSchedulerTransformer<PfRecrodDetail>())
                .subscribe(new Consumer<PfRecrodDetail>() {
                    @Override
                    public void accept(PfRecrodDetail pfRecrodDetail) throws Exception {
                        putForwardDetailView.dismissLoading();
                        putForwardDetailView.renderPutForwardDetail(pfRecrodDetail);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        putForwardDetailView.dismissLoading();
                        putForwardDetailView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
