package com.purchase.sls.bankcard;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JWC on 2018/11/27.
 */

@Module
public class BankCardModule {
    private BankCardContract.BankCardsView bankCardsView;
    private BankCardContract.AddBankCardView addBankCardView;
    private BankCardContract.PutForwardView putForwardView;
    private BankCardContract.PutForwardRecordView putForwardRecordView;
    private BankCardContract.PutForwardDetailView putForwardDetailView;

    public BankCardModule(BankCardContract.BankCardsView bankCardsView) {
        this.bankCardsView = bankCardsView;
    }


    public BankCardModule(BankCardContract.AddBankCardView addBankCardView) {
        this.addBankCardView = addBankCardView;
    }

    public BankCardModule(BankCardContract.PutForwardView putForwardView) {
        this.putForwardView = putForwardView;
    }

    public BankCardModule(BankCardContract.PutForwardRecordView putForwardRecordView) {
        this.putForwardRecordView = putForwardRecordView;
    }

    public BankCardModule(BankCardContract.PutForwardDetailView putForwardDetailView) {
        this.putForwardDetailView = putForwardDetailView;
    }

    @Provides
    BankCardContract.BankCardsView provideBankCardsView(){
        return bankCardsView;
    }

    @Provides
    BankCardContract.AddBankCardView provideAddBankCardView(){
        return addBankCardView;
    }

    @Provides
    BankCardContract.PutForwardView providePutForwardView(){
        return putForwardView;
    }

    @Provides
    BankCardContract.PutForwardRecordView providePutForwardRecordView(){
        return putForwardRecordView;
    }

    @Provides
    BankCardContract.PutForwardDetailView providePutForwardDetailView(){
        return putForwardDetailView;
    }
}
