package com.purchase.sls.login.ui;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.purchase.sls.BaseActivity;

/**
 * Created by Administrator on 2018/4/18.
 */

public class SetPasswordActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, SetPasswordActivity.class);
        context.startActivity(intent);
    }
    @Override
    public View getSnackBarHolderView() {
        return null;
    }
}
