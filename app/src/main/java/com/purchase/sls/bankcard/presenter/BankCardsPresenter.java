package com.purchase.sls.bankcard.presenter;

import android.text.TextUtils;

import com.purchase.sls.bankcard.BankCardContract;
import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.BankCardInfo;
import com.purchase.sls.data.entity.Ignore;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.IdRequest;
import com.purchase.sls.data.request.TokenRequest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/11/27.
 */

public class BankCardsPresenter implements BankCardContract.BankCardsPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private BankCardContract.BankCardsView bankCardsView;

    @Inject
    public BankCardsPresenter(RestApiService restApiService, BankCardContract.BankCardsView bankCardsView) {
        this.restApiService = restApiService;
        this.bankCardsView = bankCardsView;
    }

    @Inject
    public void setupListener() {
        bankCardsView.setPresenter(this);
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
    public void getBankCards(String refreshType) {
        if(TextUtils.equals("1",refreshType)) {
            bankCardsView.showLoading();
        }
        TokenRequest tokenRequest=new TokenRequest();
        Disposable disposable = restApiService.getBankCardInfos(tokenRequest)
                .flatMap(new RxRemoteDataParse<List<BankCardInfo>>())
                .compose(new RxSchedulerTransformer<List<BankCardInfo>>())
                .subscribe(new Consumer<List<BankCardInfo>>() {
                    @Override
                    public void accept(List<BankCardInfo> bankCardInfos) throws Exception {
                        bankCardsView.dismissLoading();
                        bankCardsView.renderBankCards(bankCardInfos);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        bankCardsView.dismissLoading();
                        bankCardsView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void deleteBankCard(String id) {
        bankCardsView.showLoading();
        IdRequest idRequest=new IdRequest(id);
        Disposable disposable = restApiService.deleteBankCard(idRequest)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        bankCardsView.dismissLoading();
                        bankCardsView.deleteSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        bankCardsView.dismissLoading();
                        bankCardsView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
