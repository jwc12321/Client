package com.purchase.sls;

import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.purchase.sls.MainApplication;
import com.purchase.sls.common.unit.WeiboDialogUtils;
import com.purchase.sls.data.RemoteDataException;
import com.purchase.sls.login.ui.AccountLoginActivity;

/**
 * Created by Administrator on 2017/12/27.
 */

public abstract class BaseActivity extends AppCompatActivity implements LoadDataView {
    private Toast toast;
    private Dialog mWeiboDialog;

    /**
     * snack bar holder view
     * 用于显示snack bar, 基于activity本身或者fragment调用统一使用该函数,方便处理一些视图的偏移,fab等.
     */
    public abstract View getSnackBarHolderView();

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


    //权限
    public boolean requestRuntimePermissions(final String[] permissions, final int requestCode) {
        boolean ret = true;
        for (String permission : permissions) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
                ret &= (PermissionChecker.checkSelfPermission(BaseActivity.this, permission) == PermissionChecker.PERMISSION_GRANTED);
            else
                ret &= (ContextCompat.checkSelfPermission(BaseActivity.this, permission) == PackageManager.PERMISSION_GRANTED);
        }
        if (ret) {
            return true;
        }
        boolean rationale = false;
        for (String permission : permissions) {
            rationale |= ActivityCompat.shouldShowRequestPermissionRationale(BaseActivity.this, permission);

        }
        if (rationale) {
            makePrimaryColorSnackBar(R.string.common_request_permission, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.common_allow_permission, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ActivityCompat.requestPermissions(BaseActivity.this, permissions, requestCode);
                        }
                    })
                    .show();

        } else {
            ActivityCompat.requestPermissions(BaseActivity.this, permissions, requestCode);

        }
        return false;
    }

    public Snackbar makeColorSnackBar(@StringRes int resId, int duration, @ColorInt int colorId) {
        Snackbar snackbar = Snackbar.make(getSnackBarHolderView(), resId, duration);
        snackbar.getView().setBackgroundColor(colorId);
        return snackbar;
    }

    public Snackbar makePrimaryColorSnackBar(@StringRes int resId, int duration) {
        return makeColorSnackBar(resId, duration, getResources().getColor(R.color.colorPrimary));
    }


    @Override
    public void showMessage(String msg) {
        if (getApplicationContext() != null) {
            toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    @Override
    public void showError(Throwable e) {
        if (e != null) {
            if (e instanceof RemoteDataException) {
                if (((RemoteDataException) e).isAuthFailed()) {
                    //跳转到登录页面
                    AccountLoginActivity.start(BaseActivity.this);
                } else {
                    showMessage(e.getMessage());
                }
            } else {
                showMessage(e.getMessage());
            }
        }

    }

    @Override
    public void showLoading() {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this);
    }

    @Override
    public void dismissLoading() {
        WeiboDialogUtils.closeDialog(mWeiboDialog);
    }

    public void showError(String errMsg) {
        if (getApplicationContext() != null) {
            Toast.makeText(getApplicationContext(), errMsg, Toast.LENGTH_SHORT).show();
        }
    }

}
