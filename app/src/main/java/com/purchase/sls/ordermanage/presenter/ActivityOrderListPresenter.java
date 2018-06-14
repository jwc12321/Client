package com.purchase.sls.ordermanage.presenter;

import android.text.TextUtils;

import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.ActivityOrderDetailInfo;
import com.purchase.sls.data.entity.ActivityOrderListResponse;
import com.purchase.sls.data.entity.Ignore;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.ActivityOrderListRequest;
import com.purchase.sls.data.request.IdRequest;
import com.purchase.sls.data.request.OrderCodeRequest;
import com.purchase.sls.ordermanage.OrderManageContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/6/7.
 */

public class ActivityOrderListPresenter implements OrderManageContract.ActivityOrderListPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private OrderManageContract.ActivityOrderListView activityOrderListView;

    private int allCurrentIndex = 1;      //全部当前index
    private int spikeCurrentIndex = 1;    //秒杀当前index
    private int exchangeCurrentIndex = 1; //兑换当前index
    private int lotteryCurrentIndex = 1;  //抽奖当前index

    @Inject
    public ActivityOrderListPresenter(RestApiService restApiService, OrderManageContract.ActivityOrderListView activityOrderListView) {
        this.restApiService = restApiService;
        this.activityOrderListView = activityOrderListView;
    }

    @Inject
    public void setupListener() {
        activityOrderListView.setPresenter(this);
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
    public void getActivityOrderList(String refreshType,String type) {
        if (TextUtils.equals("1", refreshType)) {
            activityOrderListView.showLoading();
        }
        if (TextUtils.equals("0", type)) {
            allCurrentIndex = 1;
        } else if (TextUtils.equals("1", type)) {
            spikeCurrentIndex = 1;
        } else if (TextUtils.equals("2", type)) {
            exchangeCurrentIndex = 1;
        } else {
            lotteryCurrentIndex = 1;
        }
        ActivityOrderListRequest activityOrderListRequest = new ActivityOrderListRequest("1", type);
        Disposable disposable = restApiService.getActivityOrderList(activityOrderListRequest)
                .flatMap(new RxRemoteDataParse<ActivityOrderListResponse>())
                .compose(new RxSchedulerTransformer<ActivityOrderListResponse>())
                .subscribe(new Consumer<ActivityOrderListResponse>() {
                    @Override
                    public void accept(ActivityOrderListResponse activityOrderListResponse) throws Exception {
                        activityOrderListView.dismissLoading();
                        activityOrderListView.render(activityOrderListResponse.getOrderInfos());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        activityOrderListView.dismissLoading();
                        activityOrderListView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getMoreActivityOrderList(String type) {
        ActivityOrderListRequest activityOrderListRequest;
        if (TextUtils.equals("0", type)) {
            allCurrentIndex = allCurrentIndex + 1;
            activityOrderListRequest = new ActivityOrderListRequest(String.valueOf(allCurrentIndex), type);
        } else if (TextUtils.equals("1", type)) {
            spikeCurrentIndex = spikeCurrentIndex + 1;
            activityOrderListRequest = new ActivityOrderListRequest(String.valueOf(spikeCurrentIndex), type);
        } else if (TextUtils.equals("2", type)) {
            exchangeCurrentIndex = exchangeCurrentIndex + 1;
            activityOrderListRequest = new ActivityOrderListRequest(String.valueOf(exchangeCurrentIndex), type);
        } else {
            lotteryCurrentIndex = lotteryCurrentIndex + 1;
            activityOrderListRequest = new ActivityOrderListRequest(String.valueOf(lotteryCurrentIndex), type);
        }
        Disposable disposable = restApiService.getActivityOrderList(activityOrderListRequest)
                .flatMap(new RxRemoteDataParse<ActivityOrderListResponse>())
                .compose(new RxSchedulerTransformer<ActivityOrderListResponse>())
                .subscribe(new Consumer<ActivityOrderListResponse>() {
                    @Override
                    public void accept(ActivityOrderListResponse activityOrderListResponse) throws Exception {
                        activityOrderListView.renderMore(activityOrderListResponse.getOrderInfos());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        activityOrderListView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getActivityOrderDetail(String id) {
        activityOrderListView.showLoading();
        IdRequest idRequest = new IdRequest(id);
        Disposable disposable = restApiService.getActivityOrderDetail(idRequest)
                .flatMap(new RxRemoteDataParse<ActivityOrderDetailInfo>())
                .compose(new RxSchedulerTransformer<ActivityOrderDetailInfo>())
                .subscribe(new Consumer<ActivityOrderDetailInfo>() {
                    @Override
                    public void accept(ActivityOrderDetailInfo activityOrderDetailInfo) throws Exception {
                        activityOrderListView.dismissLoading();
                        activityOrderListView.activityOrderDetail(activityOrderDetailInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        activityOrderListView.dismissLoading();
                        activityOrderListView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);

    }

    @Override
    public void deleteActivityOrder(String id) {
        activityOrderListView.showLoading();
        IdRequest idRequest = new IdRequest(id);
        Disposable disposable = restApiService.deleteActivityOrder(idRequest)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        activityOrderListView.dismissLoading();
                        activityOrderListView.deleteSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        activityOrderListView.dismissLoading();
                        activityOrderListView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void confirmActivityOrder(String orderCode) {
        activityOrderListView.showLoading();
        OrderCodeRequest orderCodeRequest = new OrderCodeRequest(orderCode);
        Disposable disposable = restApiService.confirmActivityOrder(orderCodeRequest)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        activityOrderListView.dismissLoading();
                        activityOrderListView.confirmSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        activityOrderListView.dismissLoading();
                        activityOrderListView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
