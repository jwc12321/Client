package com.purchase.sls.login.presenter;



import com.purchase.sls.data.RemoteDataException;
import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.CeshiResponse;
import com.purchase.sls.data.entity.Ignore;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.CaptchaRequest;
import com.purchase.sls.login.LoginContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2017/12/27.
 */

public class RetrievePassWordPresenter implements LoginContract.RetrievePassWordPresenter{

    private LoginContract.RetrievePassWordView mView;
    private RestApiService mApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    @Inject
    public RetrievePassWordPresenter(LoginContract.RetrievePassWordView view,
                                     RestApiService restApiService) {
        mView = view;
        mApiService = restApiService;
    }

    @Inject
    public void setupListener() {
        mView.setPresenter(this);
    }
    @Override
    public void sendCaptcha(String phone) {
        CaptchaRequest request = new CaptchaRequest("18238806099","123456","");
        Disposable disposable = mApiService.sendCaptcha(request)
                .flatMap(new RxRemoteDataParse<CeshiResponse>())
                .compose(new RxSchedulerTransformer<CeshiResponse>())
                .subscribe(new Consumer<CeshiResponse>() {
                    @Override
                    public void accept(CeshiResponse ceshiResponse) throws Exception {
                        mView.onCaptchaSend();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (throwable instanceof RemoteDataException){
                            if (((RemoteDataException) throwable).isNotRegister()){
                                mView.notRegister();
                            } else {
                                mView.showError(throwable);
                            }
                        }else
                            mView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void start() {

    }

    @Override
    public void pause() {
        dispose();
    }

    @Override
    public void destroy() {
        dispose();
    }
    private void dispose() {
        mView.dismissLoading();
        for (Disposable disposable : mDisposableList) {
            if (!disposable.isDisposed()) {
                disposable.dispose();
            }
        }
    }
}
