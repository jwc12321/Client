package com.purchase.sls.account.presenter;

import com.purchase.sls.account.AccountContract;
import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.AccountDetailInfo;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.AccountDetailRequest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/5/2.
 */

public class AccountDetailPresenter implements AccountContract.AccountDetailPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private AccountContract.AccountDetailView accountDetailView;

    @Inject
    public AccountDetailPresenter(RestApiService restApiService, AccountContract.AccountDetailView accountDetailView) {
        this.restApiService = restApiService;
        this.accountDetailView = accountDetailView;
    }

    @Inject
    public void setupListener() {
        accountDetailView.setPresenter(this);
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
     * 获取订单详情
     *
     * @param billid
     */
    @Override
    public void getAccountDetail(String billid) {
        accountDetailView.showLoading();
        AccountDetailRequest accountDetailRequest = new AccountDetailRequest(billid);
        Disposable disposable = restApiService.getAccountDetail(accountDetailRequest)
                .flatMap(new RxRemoteDataParse<AccountDetailInfo>())
                .compose(new RxSchedulerTransformer<AccountDetailInfo>())
                .subscribe(new Consumer<AccountDetailInfo>() {
                    @Override
                    public void accept(AccountDetailInfo accountDetailInfo) throws Exception {
                        accountDetailView.dismissLoading();
                        accountDetailView.accountDetail(accountDetailInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        accountDetailView.dismissLoading();
                        accountDetailView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
