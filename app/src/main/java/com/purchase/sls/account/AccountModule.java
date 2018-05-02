package com.purchase.sls.account;

import java.lang.ref.PhantomReference;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JWC on 2018/5/2.
 */

@Module
public class AccountModule {
    private AccountContract.AccountListView accountListView;
    private AccountContract.AccountDetailView accountDetailView;
    private AccountContract.IntercourseRecordView intercourseRecordView;

    public AccountModule(AccountContract.AccountListView accountListView) {
        this.accountListView = accountListView;
    }

    public AccountModule(AccountContract.AccountDetailView accountDetailView) {
        this.accountDetailView = accountDetailView;
    }

    public AccountModule(AccountContract.IntercourseRecordView intercourseRecordView) {
        this.intercourseRecordView = intercourseRecordView;
    }

    @Provides
    AccountContract.AccountListView provideAccountListView(){
        return accountListView;
    }
    @Provides
    AccountContract.AccountDetailView provideAccountDetailView(){
        return accountDetailView;
    }
    @Provides
    AccountContract.IntercourseRecordView provideIntercourseRecordView(){
        return intercourseRecordView;
    }
}
