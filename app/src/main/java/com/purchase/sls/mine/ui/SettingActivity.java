package com.purchase.sls.mine.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.common.unit.APKVersionCodeUtils;
import com.purchase.sls.common.unit.CommonAppPreferences;
import com.purchase.sls.common.unit.PersionAppPreferences;
import com.purchase.sls.common.unit.TokenManager;
import com.purchase.sls.data.entity.WebViewDetailInfo;
import com.purchase.sls.login.ui.AccountLoginActivity;
import com.purchase.sls.login.ui.RegisterSecondActivity;
import com.purchase.sls.mine.DaggerPersonalCenterComponent;
import com.purchase.sls.mine.PersonalCenterContract;
import com.purchase.sls.mine.PersonalCenterModule;
import com.purchase.sls.mine.presenter.SettingPresenter;
import com.purchase.sls.webview.ui.WebViewActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/5/4.
 */

public class SettingActivity extends BaseActivity implements PersonalCenterContract.SettingView{
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

    private PersionAppPreferences persionAppPreferences;
    private String phoneNumber;
    private WebViewDetailInfo webViewDetailInfo;
    @Inject
    SettingPresenter settingPresenter;

    public static void start(Context context, String phoneNumber) {
        Intent intent = new Intent(context, SettingActivity.class);
        intent.putExtra(StaticData.PHONE_NUMBER, phoneNumber);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        persionAppPreferences = new PersionAppPreferences(this);
        phoneNumber = getIntent().getStringExtra(StaticData.PHONE_NUMBER);
    }

    @Override
    protected void initializeInjector() {
        DaggerPersonalCenterComponent.builder()
                .applicationComponent(getApplicationComponent())
                .personalCenterModule(new PersonalCenterModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @OnClick({R.id.back, R.id.item_shift_handset, R.id.item_user_password, R.id.login_out, R.id.item_user_protocol,R.id.item_new_version_detection})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.item_shift_handset:
                ShiftHandsetActivity.start(this);
                break;
            case R.id.item_user_password:
                RegisterSecondActivity.start(this, StaticData.CHANGEPWD, phoneNumber,"","");
                break;
            case R.id.login_out:
                persionAppPreferences.clean();
                TokenManager.saveToken("");
                AccountLoginActivity.start(this);
                finish();
                break;
            case R.id.item_user_protocol:
                webViewDetailInfo = new WebViewDetailInfo();
                webViewDetailInfo.setTitle("用户协议");
                webViewDetailInfo.setUrl("http://www.365nengs.com/api/home/index/Agreement");
                WebViewActivity.start(this, webViewDetailInfo);
                break;
            case R.id.item_new_version_detection:
                settingPresenter.detectionVersion(String.valueOf(APKVersionCodeUtils.getVersionCode(this)),"android");
                break;
            default:
        }
    }

    @Override
    public void setPresenter(PersonalCenterContract.SettingPresenter presenter) {

    }

    @Override
    public void detectionSuccess() {
        showMessage("有新版本");
    }

    public int packageCode(Context context) {
        PackageManager manager = context.getPackageManager();
        int code = 0;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            code = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return code;
    }
}
