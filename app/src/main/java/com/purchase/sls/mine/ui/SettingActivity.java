package com.purchase.sls.mine.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/5/4.
 */

public class SettingActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.item_shift_handset)
    RelativeLayout itemShiftHandset;
    @BindView(R.id.item_user_password)
    RelativeLayout itemUserPassword;
    @BindView(R.id.item_user_protocol)
    RelativeLayout itemUserProtocol;
    @BindView(R.id.item_new_version_detection)
    RelativeLayout itemNewVersionDetection;
    @BindView(R.id.login_out)
    Button loginOut;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }


}
