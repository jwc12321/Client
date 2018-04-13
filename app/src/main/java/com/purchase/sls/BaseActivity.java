package com.purchase.sls;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.purchase.sls.MainApplication;

/**
 * Created by Administrator on 2017/12/27.
 */

public abstract class BaseActivity extends AppCompatActivity implements LoadDataView {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeInjector();
    }
    protected void initializeInjector() {
        getApplicationComponent().inject(this);
    }
    public ApplicationComponent getApplicationComponent() {
        return ((MainApplication) getApplication()).getApplicationComponent();
    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    public void showError(Throwable e) {

    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void dismissLoading() {

    }

}
