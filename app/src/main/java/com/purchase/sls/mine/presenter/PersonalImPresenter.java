package com.purchase.sls.mine.presenter;

import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.Ignore;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.ChangeUserInfoRequest;
import com.purchase.sls.mine.PersonalCenterContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/5/4.
 */

public class PersonalImPresenter implements PersonalCenterContract.PersonalImPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private PersonalCenterContract.PersonalImView personalImView;

    @Inject
    public PersonalImPresenter(RestApiService restApiService, PersonalCenterContract.PersonalImView personalImView) {
        this.restApiService = restApiService;
        this.personalImView = personalImView;
    }
    @Inject
    public void setupListener() {
        personalImView.setPresenter(this);
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
    public void changeHeadPortrait(String photoUrl) {

    }

    @Override
    public void changeUserInfo(String nickname, String sex, String birthday) {
        ChangeUserInfoRequest changeUserInfoRequest=new ChangeUserInfoRequest(nickname,sex,birthday);
        Disposable disposable=restApiService.changeUserInfo(changeUserInfoRequest)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        personalImView.changeUserInfoSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        personalImView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);

    }
}
