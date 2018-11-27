package com.purchase.sls.bankcard.presenter;

import com.purchase.sls.bankcard.BankCardContract;
import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.BankCardInfo;
import com.purchase.sls.data.entity.Ignore;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.AddBankCardRequest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/11/27.
 */

public class AddBankCardPresenter implements BankCardContract.AddBankCardPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private BankCardContract.AddBankCardView addBankCardView;

    @Inject
    public AddBankCardPresenter(RestApiService restApiService, BankCardContract.AddBankCardView addBankCardView) {
        this.restApiService = restApiService;
        this.addBankCardView = addBankCardView;
    }

    @Inject
    public void setupListener() {
        addBankCardView.setPresenter(this);
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
    public void addBankCard(String bankNumber, String bankName, String bankSubbranchName, String mobile, String ownerName) {
        addBankCardView.showLoading();
        AddBankCardRequest addBankCardRequest=new AddBankCardRequest(bankNumber,bankName,bankSubbranchName,mobile,ownerName);
        Disposable disposable = restApiService.addBankCard(addBankCardRequest)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        addBankCardView.dismissLoading();
                        addBankCardView.addSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        addBankCardView.dismissLoading();
                        addBankCardView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
