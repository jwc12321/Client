package com.purchase.sls.nearbymap.presenter;

import com.purchase.sls.data.RemoteDataException;
import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.CeshiResponse;
import com.purchase.sls.data.entity.NearbyInfoResponse;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.NearbyInfoRequest;
import com.purchase.sls.nearbymap.NearbyMapContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/4/19.
 */

public class NearbyMapPresenter implements NearbyMapContract.NearbyPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private NearbyMapContract.NearbyView nearbyView;
    @Inject
    public NearbyMapPresenter(RestApiService restApiService, NearbyMapContract.NearbyView nearbyView) {
        this.restApiService = restApiService;
        this.nearbyView = nearbyView;
    }

    @Inject
    public void setupListener() {
        nearbyView.setPresenter(this);
    }

    /**
     * 获取地图附近的信息
     *
     * @param address
     */
    @Override
    public void getNearbyInfo(String address) {
        NearbyInfoRequest nearbyInfoRequest = new NearbyInfoRequest(address);
        Disposable disposable = restApiService.getNearbyInfo(nearbyInfoRequest)
                .flatMap(new RxRemoteDataParse<List<NearbyInfoResponse>>())
                .compose(new RxSchedulerTransformer<List<NearbyInfoResponse>>())
                .subscribe(new Consumer<List<NearbyInfoResponse>>() {
                    @Override
                    public void accept(List<NearbyInfoResponse> nearbyInfoResponses) throws Exception {
                        nearbyView.nearbyInfo(nearbyInfoResponses);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });
        mDisposableList.add(disposable);

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
}
