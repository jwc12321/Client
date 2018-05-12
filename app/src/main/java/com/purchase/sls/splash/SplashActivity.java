package com.purchase.sls.splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.FrameLayout;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.mainframe.ui.MainFrameActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/4/25.
 */

public class SplashActivity extends BaseActivity {


    //首次载入
    boolean isFirstIn = false;
    //用pref记录是否为首次载入
    private static final String SHAREDPREFERENCES_NAME = "first_pref";
    @BindView(R.id.splash)
    FrameLayout splash;
    private Bundle bundle;

    private static final int GO_HOME = 1000;
    private static final int GO_GUIDE = 1001;
    // 延迟1.5秒
    private static final long SPLASH_DELAY_MILLIS = 500;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_HOME:
                    goHome();
                    break;
                case GO_GUIDE:
                    goGuide();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public View getSnackBarHolderView() {
        return splash;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        splash.setBackgroundResource(R.mipmap.splash);
        bundle = getIntent().getExtras();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        init(bundle);

    }

    private void init(final Bundle bundle) {
        // 使用SharedPreferences来记录程序的是否为首次载入的状态
        SharedPreferences preferences = getSharedPreferences(SHAREDPREFERENCES_NAME, MODE_PRIVATE);

        // 取得相应的值，如果没有该值，说明还未写入，用true作为默认值
        isFirstIn = preferences.getBoolean("isFirstIn", true);
        //直接写入
        preferences.edit().putBoolean("isFirstIn", false).apply();

        // 判断程序与第几次运行，如果是第一次运行则跳转到引导界面，否则跳转到主界面
        if ( !isFirstIn ) {
        // 使用Handler的postDelayed方法，1秒后执行跳转
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainFrameActivity.class);
                if (bundle != null)
                    intent.putExtras(bundle);
                startActivity(intent);

                SplashActivity.this.finish();
            }
        }, 1000);

        }
        else {
            mHandler.sendEmptyMessageDelayed(GO_GUIDE, SPLASH_DELAY_MILLIS);
        }

    }

    //跳转到主页
    private void goHome() {
        Intent intent = new Intent(SplashActivity.this, MainFrameActivity.class);
        SplashActivity.this.startActivity(intent);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        SplashActivity.this.finish();
    }

    //跳转到引导页
    private void goGuide() {//目前暂时没有引导页，先做保留
        Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
        SplashActivity.this.startActivity(intent);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        SplashActivity.this.finish();
    }
}
