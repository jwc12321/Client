package com.purchase.sls.evaluate.presenter;

import android.support.v4.util.ArrayMap;

import com.google.gson.Gson;
import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.SubmitEvaluateRequest;
import com.purchase.sls.data.request.UploadFileRequest;
import com.purchase.sls.evaluate.EvaluateContract;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by JWC on 2018/5/5.
 */

public class SubmitEvaluatePresenter implements EvaluateContract.SubmitEvaluatePresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private EvaluateContract.SubmitEvaluateView submitEvaluateView;

    @Inject
    public SubmitEvaluatePresenter(RestApiService restApiService, EvaluateContract.SubmitEvaluateView submitEvaluateView) {
        this.restApiService = restApiService;
        this.submitEvaluateView = submitEvaluateView;
    }

    @Inject
    public void setupListener() {
        submitEvaluateView.setPresenter(this);
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
     * 上传阿里云图片
     *
     * @param photoUrl
     */
    @Override
    public void uploadFile(String photoUrl) {
        UploadFileRequest headPhoneRequest = new UploadFileRequest(photoUrl);
        Gson gson = new Gson();
        Map<String, RequestBody> requestBodyMap = new ArrayMap<>();
        File file = new File(photoUrl);
        String fileName = file.getName();
        RequestBody photo = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        requestBodyMap.put("file\"; filename=\"" + fileName, photo);
        RequestBody json = RequestBody.create(MediaType.parse("application/json"), gson.toJson(headPhoneRequest));
        requestBodyMap.put("json_data", json);
        Disposable disposable = restApiService.uploadFile(requestBodyMap)
                .flatMap(new RxRemoteDataParse<String>())
                .compose(new RxSchedulerTransformer<String>())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String string) throws Exception {
                        submitEvaluateView.uploadFileSuccess(string);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        submitEvaluateView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    /**
     * 提交评价
     * @param submitEvaluateRequest
     */
    @Override
    public void submitEvaluate(SubmitEvaluateRequest submitEvaluateRequest) {
        Disposable disposable = restApiService.submitEvalute(submitEvaluateRequest)
                .flatMap(new RxRemoteDataParse<String>())
                .compose(new RxSchedulerTransformer<String>())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String string) throws Exception {
                        submitEvaluateView.dismissLoading();
                        submitEvaluateView.submitSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        submitEvaluateView.dismissLoading();
                        submitEvaluateView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
