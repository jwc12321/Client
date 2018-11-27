package com.purchase.sls.bankcard;

import com.purchase.sls.BasePresenter;
import com.purchase.sls.BaseView;
import com.purchase.sls.data.entity.BankCardInfo;
import com.purchase.sls.data.entity.CommissionInfo;
import com.purchase.sls.data.entity.PfRecrodDetail;
import com.purchase.sls.data.entity.PutForwardList;

import java.util.List;

/**
 * Created by JWC on 2018/11/27.
 */

public interface BankCardContract {
    interface BankCardsPresenter extends BasePresenter{
        void getBankCards(String refreshType);
        void deleteBankCard(String id);
    }

    interface BankCardsView extends BaseView<BankCardsPresenter>{
        void renderBankCards(List<BankCardInfo> bankCardInfos);
        void deleteSuccess();
    }

    interface AddBankCardPresenter extends BasePresenter{
        void addBankCard(String bankNumber, String bankName, String bankSubbranchName, String mobile, String ownerName);
    }

    interface AddBankCardView extends BaseView<AddBankCardPresenter>{
        void addSuccess();
    }

    interface PutForwardPresenter extends BasePresenter{
        void putForward(String userBankId, String price);
        void getCommissionInfo();
    }

    interface PutForwardView extends BaseView<PutForwardPresenter>{
        void putForwardSuccess();
        void renderCommissionInfo(CommissionInfo commissionInfo);
    }

    interface PutForwardRecordPresenter extends BasePresenter{
        void getPutForwardRecords(String refreshType);
        void getMorePutForwardRecords();
    }

    interface PutForwardRecordView extends BaseView<PutForwardRecordPresenter>{
        void renderPutFRecords(PutForwardList putForwardList);
        void renderMorePutFRecords(PutForwardList putForwardList);
    }

    interface PutForwardDetailPresenter extends BasePresenter{
        void getPutForwardDetail(String id);
    }

    interface PutForwardDetailView extends BaseView<PutForwardDetailPresenter>{
        void renderPutForwardDetail(PfRecrodDetail pfRecrodDetail);
    }
}
