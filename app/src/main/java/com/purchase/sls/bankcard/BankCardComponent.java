package com.purchase.sls.bankcard;

/**
 * Created by JWC on 2018/11/27.
 */

import com.purchase.sls.ActivityScope;
import com.purchase.sls.ApplicationComponent;
import com.purchase.sls.bankcard.ui.AddBankCardActivity;
import com.purchase.sls.bankcard.ui.BankCardListActivity;
import com.purchase.sls.bankcard.ui.PutForwardActivity;
import com.purchase.sls.bankcard.ui.PutForwardDetailActivity;
import com.purchase.sls.bankcard.ui.PutForwardRecordActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {BankCardModule.class})
public interface BankCardComponent {
    void inject(BankCardListActivity bankCardListActivity);
    void inject(AddBankCardActivity addBankCardActivity);
    void inject(PutForwardActivity putForwardActivity);
    void inject(PutForwardRecordActivity putForwardRecordActivity);
    void inject(PutForwardDetailActivity putForwardDetailActivity);
}
