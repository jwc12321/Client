package com.purchase.sls.collection.presenter;

import com.purchase.sls.collection.CollectionContract;
import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.CollectionListResponse;
import com.purchase.sls.data.entity.Ignore;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.AddRemoveCollectionRequest;
import com.purchase.sls.data.request.CollectionListRequest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/4/27.
 */

public class CollectionListPresenter implements CollectionContract.CollectionPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private int currentIndex = 1;  //待接单当前index
    private CollectionContract.CollectionView collectionView;

    @Inject
    public CollectionListPresenter(RestApiService restApiService, CollectionContract.CollectionView collectionView) {
        this.restApiService = restApiService;
        this.collectionView = collectionView;
    }

    @Inject
    public void setupListener() {
        collectionView.setPresenter(this);
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

    /**
     * 获取收藏列表
     */
    @Override
    public void getCollectionListInfo() {
        currentIndex = 1;
        CollectionListRequest collectionListRequest = new CollectionListRequest(String.valueOf(currentIndex));
        Disposable disposable = restApiService.getCollectionListInfo(collectionListRequest)
                .flatMap(new RxRemoteDataParse<CollectionListResponse>())
                .compose(new RxSchedulerTransformer<CollectionListResponse>())
                .subscribe(new Consumer<CollectionListResponse>() {
                    @Override
                    public void accept(CollectionListResponse collectionListResponse) throws Exception {
                        collectionView.collectionListInfo(collectionListResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        collectionView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    /**
     * 获取更多
     */
    @Override
    public void getMoreCollectionListInfo() {
        currentIndex = currentIndex + 1;
        CollectionListRequest collectionListRequest = new CollectionListRequest(String.valueOf(currentIndex));
        Disposable disposable = restApiService.getCollectionListInfo(collectionListRequest)
                .flatMap(new RxRemoteDataParse<CollectionListResponse>())
                .compose(new RxSchedulerTransformer<CollectionListResponse>())
                .subscribe(new Consumer<CollectionListResponse>() {
                    @Override
                    public void accept(CollectionListResponse collectionListResponse) throws Exception {
                        collectionView.moreCollectionListInfo(collectionListResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        collectionView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    /**
     * 收藏的增加和删除
     *
     * @param storeid
     * @param type
     * @param fidArray
     */
    @Override
    public void addRemoveCollection(String storeid, String type, String[] fidArray) {
        AddRemoveCollectionRequest addRemoveCollectionRequest = new AddRemoveCollectionRequest(storeid, type, fidArray);
        Disposable disposable = restApiService.addRemoveCollection(addRemoveCollectionRequest)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        collectionView.addRemoveSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        collectionView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
