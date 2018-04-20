package com.purchase.sls;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

/**
 *
 */

public class BaseFragment extends Fragment implements LoadDataView{
    private Toast toast;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeInjector();
    }


    public View getSnackBarHolderView() {
        if (getActivity() instanceof BaseActivity) {
            return ((BaseActivity) getActivity()).getSnackBarHolderView();
        } else {
            throw new RuntimeException(getString(R.string.snack_bar_exception_msg));
        }
    }

    public boolean requestRuntimePermissions(final String[] permissions, final int requestCode) {
        boolean ret = true;
        for (String permission : permissions) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
                ret &= (PermissionChecker.checkSelfPermission(getContext(), permission) == PermissionChecker.PERMISSION_GRANTED);
            else
                ret &= (ContextCompat.checkSelfPermission(getContext(), permission) == PackageManager.PERMISSION_GRANTED);
        }
        if (ret) {
            return true;
        }
        boolean rationale = false;
        for (String permission : permissions) {
            rationale |= shouldShowRequestPermissionRationale(permission);
        }
        if (rationale) {
            makePrimaryColorSnackBar(R.string.common_request_permission, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.common_allow_permission, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                requestPermissions(permissions, requestCode);
                            }
                    })
                    .show();

        } else {
            requestPermissions(permissions, requestCode);
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
    public ApplicationComponent getApplicationComponent() {
        if (getActivity() instanceof BaseActivity) {
            return ((BaseActivity) getActivity()).getApplicationComponent();
        } else {
            throw new RuntimeException(getString(R.string.snack_bar_exception_msg));
        }
    }
    protected void initializeInjector() {

    }
    @Override
    public void showMessage(String msg) {
        if(getActivity()!=null) {
            toast = Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    @Override
    public void showError(Throwable e) {
        if(getActivity()!=null) {
            if (getActivity() instanceof BaseActivity) {
                ((BaseActivity) getActivity()).showError(e);
            }
        }
//        if (e instanceof RemoteDataException) {
//            if (((RemoteDataException)e).isAuthFailed()){
//                //跳转到登录页面
//                LoginActivity.startAndClearTask(getActivity());
//            } else {
//                showMessage(e.getMessage());
//            }
//        } else {
//            showMessage(e.getMessage());
//        }
    }

    @Override
    public void showLoading(String title) {
    }

    @Override
    public void dismissLoading() {
    }

    protected void notifyNetWorkChange(boolean isConnected){

    }
}
