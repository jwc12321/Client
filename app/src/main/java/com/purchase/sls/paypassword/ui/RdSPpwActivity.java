package com.purchase.sls.paypassword.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/8/22.
 */

public class RdSPpwActivity extends BaseActivity {
    @BindView(R.id.confirm)
    Button confirm;
    @BindView(R.id.item_rdsppw)
    RelativeLayout itemRdsppw;

    public static void start(Context context) {
        Intent intent = new Intent(context, RdSPpwActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rd_s_ppw);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.item_rdsppw, R.id.confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.item_rdsppw:
                finish();
                break;
            case R.id.confirm:
                FirstPayPwActivity.start(this,"0","");
                this.finish();
                break;
            default:
        }
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }
}
