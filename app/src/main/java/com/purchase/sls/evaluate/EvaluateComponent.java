package com.purchase.sls.evaluate;

/**
 * Created by JWC on 2018/5/5.
 */

import com.purchase.sls.ActivityScope;
import com.purchase.sls.ApplicationComponent;
import com.purchase.sls.evaluate.ui.AllEvaluationActivity;
import com.purchase.sls.evaluate.ui.SubmitEvaluateActivity;
import com.purchase.sls.evaluate.ui.ToBeEvaluatedActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {EvaluateModule.class})
public interface EvaluateComponent {
    void inject(AllEvaluationActivity allEvaluationActivity);
    void inject(ToBeEvaluatedActivity toBeEvaluatedActivity);
    void inject(SubmitEvaluateActivity submitEvaluateActivity);
}
