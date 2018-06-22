package com.purchase.sls.messages.presenter;

import android.text.TextUtils;

import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.CouponListInfo;
import com.purchase.sls.data.entity.MessageListInfo;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.MessageListRequest;
import com.purchase.sls.messages.MessagesContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/5/8.
 */

public class MessageListPresenter implements MessagesContract.MessageListPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private MessagesContract.MessageListView messageListView;
    private int couponCurrentIndex = 1;  //通知
    private int noticeCurrentIndex = 1;  //优惠

    @Inject
    public MessageListPresenter(RestApiService restApiService, MessagesContract.MessageListView messageListView) {
        this.restApiService = restApiService;
        this.messageListView = messageListView;
    }

    @Inject
    public void setupListener() {
        messageListView.setPresenter(this);
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
    public void getMessageList(String refreshType, String type) {
        if (TextUtils.equals("1", refreshType)) {
            messageListView.showLoading();
        }
        if (TextUtils.equals("0", type)) {
            couponCurrentIndex = 1;
        } else {
            noticeCurrentIndex = 1;
        }
        MessageListRequest messageListRequest = new MessageListRequest(String.valueOf(1), type);
        Disposable disposable = restApiService.getMessageListInfo(messageListRequest)
                .flatMap(new RxRemoteDataParse<MessageListInfo>())
                .compose(new RxSchedulerTransformer<MessageListInfo>())
                .subscribe(new Consumer<MessageListInfo>() {
                    @Override
                    public void accept(MessageListInfo messageListInfo) throws Exception {
                        messageListView.dismissLoading();
                        messageListView.render(messageListInfo.getMessageItems());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        messageListView.dismissLoading();
                        messageListView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getMoreMessageList(String type) {
        MessageListRequest messageListRequest;
        if (TextUtils.equals("0", type)) {
            couponCurrentIndex = couponCurrentIndex + 1;
            messageListRequest = new MessageListRequest(String.valueOf(couponCurrentIndex), type);
        } else {
            noticeCurrentIndex = noticeCurrentIndex + 1;
            messageListRequest = new MessageListRequest(String.valueOf(noticeCurrentIndex), type);
        }
        Disposable disposable = restApiService.getMessageListInfo(messageListRequest)
                .flatMap(new RxRemoteDataParse<MessageListInfo>())
                .compose(new RxSchedulerTransformer<MessageListInfo>())
                .subscribe(new Consumer<MessageListInfo>() {
                    @Override
                    public void accept(MessageListInfo messageListInfo) throws Exception {
                        messageListView.renderMore(messageListInfo.getMessageItems());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        messageListView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
