package com.purchase.sls.evaluate;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JWC on 2018/5/5.
 */

@Module
public class EvaluateModule {
    private EvaluateContract.AllEvaluationView allEvaluationView;
    private EvaluateContract.ToBeEvaluationView toBeEvaluationView;
    private EvaluateContract.SubmitEvaluateView submitEvaluateView;


    public EvaluateModule(EvaluateContract.AllEvaluationView allEvaluationView) {
        this.allEvaluationView = allEvaluationView;
    }

    public EvaluateModule(EvaluateContract.ToBeEvaluationView toBeEvaluationView) {
        this.toBeEvaluationView = toBeEvaluationView;
    }

    public EvaluateModule(EvaluateContract.SubmitEvaluateView submitEvaluateView) {
        this.submitEvaluateView = submitEvaluateView;
    }

    @Provides
    EvaluateContract.AllEvaluationView provideAllEvaluationView() {
        return allEvaluationView;
    }

    @Provides
    EvaluateContract.ToBeEvaluationView provideToBeEvaluationView() {
        return toBeEvaluationView;
    }

    @Provides
    EvaluateContract.SubmitEvaluateView provideSubmitEvaluateView() {
        return submitEvaluateView;
    }
}
