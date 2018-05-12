package com.purchase.sls.account.presenter;

import com.purchase.sls.account.AccountContract;
import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.AccountListInfo;
import com.purchase.sls.data.entity.CollectionListResponse;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.AccountListRequest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/5/2.
 */

public class AccountListPresenter implements AccountContract.AccountListPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private int currentIndex = 1;  //待接单当前index
    private AccountContract.AccountListView accountListView;

    @Inject
    public AccountListPresenter(RestApiService restApiService, AccountContract.AccountListView accountListView) {
        this.restApiService = restApiService;
        this.accountListView = accountListView;
    }

    @Inject
    public void setupListener() {
        accountListView.setPresenter(this);
    }

    /**
     * 获取订单列表
     *
     * @param dates1
     * @param dates2
     */
    @Override
    public void getAccountList(String dates1, String dates2) {
        accountListView.showLoading();
        currentIndex = 1;
        AccountListRequest accountListRequest = new AccountListRequest(String.valueOf(currentIndex), dates1, dates2);
        Disposable disposable = restApiService.getAccountListInfo(accountListRequest)
                .flatMap(new RxRemoteDataParse<AccountListInfo>())
                .compose(new RxSchedulerTransformer<AccountListInfo>())
                .subscribe(new Consumer<AccountListInfo>() {
                    @Override
                    public void accept(AccountListInfo accountListInfo) throws Exception {
                        accountListView.dismissLoading();
                        accountListView.accountListInfo(accountListInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        accountListView.dismissLoading();
                        accountListView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    /**
     * 获取更多
     *
     * @param dates1
     * @param dates2
     */
    @Override
    public void getMoreAccountList(String dates1, String dates2) {
        accountListView.showLoading();
        currentIndex = currentIndex + 1;
        AccountListRequest accountListRequest = new AccountListRequest(String.valueOf(currentIndex), dates1, dates2);
        Disposable disposable = restApiService.getAccountListInfo(accountListRequest)
                .flatMap(new RxRemoteDataParse<AccountListInfo>())
                .compose(new RxSchedulerTransformer<AccountListInfo>())
                .subscribe(new Consumer<AccountListInfo>() {
                    @Override
                    public void accept(AccountListInfo accountListInfo) throws Exception {
                        accountListView.dismissLoading();
                        accountListView.moreAccountListInfo(accountListInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        accountListView.dismissLoading();
                        accountListView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
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
}
