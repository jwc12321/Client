package com.purchase.sls.evaluate;

import com.purchase.sls.BasePresenter;
import com.purchase.sls.BaseView;
import com.purchase.sls.data.entity.AllEvaluationInfo;
import com.purchase.sls.data.entity.ToBeEvaluationInfo;
import com.purchase.sls.data.request.SubmitEvaluateRequest;

/**
 * Created by JWC on 2018/5/5.
 */

public interface EvaluateContract {
    interface AllEvaluationPresenter extends BasePresenter {
        void getAllEvaluation(String refreshType,String storeId);

        void getMoreAllEvaluation(String storeId);
    }

    interface AllEvaluationView extends BaseView<AllEvaluationPresenter> {
        void renderAllEvaluation(AllEvaluationInfo allEvaluationInfo);

        void renderMoreAllEvaluation(AllEvaluationInfo allEvaluationInfo);
    }

    interface ToBeEvaluationPresenter extends BasePresenter {
        void getToBeEvaluation(String refreshType);

        void getMoreToBeEvaluation();
    }

    interface ToBeEvaluationView extends BaseView<ToBeEvaluationPresenter> {
        void renderToBeEvaluation(ToBeEvaluationInfo toBeEvaluationInfo);

        void renderMoreToBeEvaluation(ToBeEvaluationInfo toBeEvaluationInfo);
    }

    interface SubmitEvaluatePresenter extends BasePresenter {
        void uploadFile(String photoUrl);
        void submitEvaluate(SubmitEvaluateRequest submitEvaluateRequest);
    }

    interface SubmitEvaluateView extends BaseView<SubmitEvaluatePresenter> {
        void uploadFileSuccess(String photoUrl);
        void submitSuccess();
    }
}
