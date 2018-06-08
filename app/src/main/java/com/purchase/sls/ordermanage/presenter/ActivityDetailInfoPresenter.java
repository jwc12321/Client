package com.purchase.sls.ordermanage.presenter;

import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.ActivityInfo;
import com.purchase.sls.data.entity.ActivityOrderListResponse;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.ActivityIdRequest;
import com.purchase.sls.ordermanage.OrderManageContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/6/8.
 */

public class ActivityDetailInfoPresenter implements OrderManageContract.ActivityDetailInfoPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private OrderManageContract.ActivityDetailInfoView activityDetailInfoView;

    @Inject
    public ActivityDetailInfoPresenter(RestApiService restApiService, OrderManageContract.ActivityDetailInfoView activityDetailInfoView) {
        this.restApiService = restApiService;
        this.activityDetailInfoView = activityDetailInfoView;
    }

    @Inject
    public void setupListener() {
        activityDetailInfoView.setPresenter(this);
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

    @Override
    public void getActivityDetail(String activityId) {
        activityDetailInfoView.showLoading();
        ActivityIdRequest activityIdRequest=new ActivityIdRequest(activityId);
        Disposable disposable = restApiService.getActivityInfo(activityIdRequest)
                .flatMap(new RxRemoteDataParse<ActivityInfo>())
                .compose(new RxSchedulerTransformer<ActivityInfo>())
                .subscribe(new Consumer<ActivityInfo>() {
                    @Override
                    public void accept(ActivityInfo activityInfo) throws Exception {
                        activityDetailInfoView.dismissLoading();
                        activityDetailInfoView.activityDetailInfo(activityInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        activityDetailInfoView.dismissLoading();
                        activityDetailInfoView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);


    }
}
