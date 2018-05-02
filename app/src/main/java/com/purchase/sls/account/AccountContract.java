package com.purchase.sls.account;

import com.purchase.sls.BasePresenter;
import com.purchase.sls.BaseView;
import com.purchase.sls.data.entity.AccountDetailInfo;
import com.purchase.sls.data.entity.AccountListInfo;
import com.purchase.sls.data.entity.IntercourseRecordInfo;

/**
 * Created by JWC on 2018/5/2.
 */

public interface AccountContract {
    interface AccountListPresenter extends BasePresenter{
        void getAccountList(String dates1, String dates2);
        void getMoreAccountList(String dates1, String dates2);
    }
    interface AccountListView extends BaseView<AccountListPresenter>{
        void accountListInfo(AccountListInfo accountListInfo);
        void moreAccountListInfo(AccountListInfo accountListInfo);
    }

    interface AccountDetailPresenter extends BasePresenter{
        void getAccountDetail(String billid);
    }
    interface AccountDetailView extends BaseView<AccountDetailPresenter>{
        void accountDetail(AccountDetailInfo accountDetailInfo);
    }

    interface IntercourseRecordPresenter extends BasePresenter{
        void getIntercourseRecordInfo(String storeid);
        void getMoreIntercourseRecordInfo(String storeid);
    }
    interface IntercourseRecordView extends BaseView<IntercourseRecordPresenter>{
        void intercourseRecordInfo(IntercourseRecordInfo intercourseRecordInfo);
        void moreIntercourseRecordInfo(IntercourseRecordInfo intercourseRecordInfo);
    }
}
