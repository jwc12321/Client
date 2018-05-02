package com.purchase.sls.account;

/**
 * Created by JWC on 2018/5/2.
 */

import com.purchase.sls.ActivityScope;
import com.purchase.sls.ApplicationComponent;
import com.purchase.sls.account.ui.AccountDetailActivity;
import com.purchase.sls.account.ui.AccountListActivity;
import com.purchase.sls.account.ui.IntercourseRecordActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {AccountModule.class})
public interface AccountComponent {
    void inject(AccountListActivity accountListActivity);
    void inject(AccountDetailActivity accountDetailActivity);
    void inject(IntercourseRecordActivity intercourseRecordActivity);
}
