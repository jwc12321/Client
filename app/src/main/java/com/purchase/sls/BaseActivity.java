package com.purchase.sls;

import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.purchase.sls.common.unit.HandleBackUtil;
import com.purchase.sls.common.unit.WeiboDialogUtils;
import com.purchase.sls.data.RemoteDataException;
import com.purchase.sls.login.ui.AccountLoginActivity;
import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2017/12/27.
 */

public abstract class BaseActivity extends AppCompatActivity implements LoadDataView {
    private Toast toast;
    private Dialog mWeiboDialog;
    int statusBarHeight1 = -1;

    /**
     * snack bar holder view
     * 用于显示snack bar, 基于activity本身或者fragment调用统一使用该函数,方便处理一些视图的偏移,fab等.
     */
    public abstract View getSnackBarHolderView();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeStateBar();
        initializeInjector();
    }

    public int statusBarheight(){
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight1;
    }

    public void setHeight(View view1,View view2,View view3){
        if (view1!=null&&view1.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view1.getLayoutParams();
            p.setMargins(0, statusBarheight(), 0, 0);
            view1.requestLayout();
        }
        if (view2!=null&&view2.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view2.getLayoutParams();
            p.setMargins(0, statusBarheight(), 0, 0);
            view2.requestLayout();
        }
        if (view3!=null&&view3.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view3.getLayoutParams();
            p.setMargins(0, statusBarheight(), 0, 0);
            view3.requestLayout();
        }
    }

    private void changeStateBar(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                Class decorViewClazz = Class.forName("com.android.internal.policy.DecorView");
                Field field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor");
                field.setAccessible(true);
                field.setInt(getWindow().getDecorView(), Color.TRANSPARENT);  //改为透明
            } catch (Exception e) {
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
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
        return makeColorSnackBar(resId, duration, Color.parseColor("#3F51B5"));
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

    @Override
    public void onBackPressed() {
        if (!HandleBackUtil.handleBackPress(this)) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
