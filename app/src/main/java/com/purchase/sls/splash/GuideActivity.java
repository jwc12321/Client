package com.purchase.sls.splash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.purchase.sls.R;
import com.purchase.sls.mainframe.ui.MainFrameActivity;
import com.purchase.sls.splash.adapter.GuideAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuideActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.guide_tv)
    TextView guideTv;
    @BindView(R.id.guide_bt)
    Button guideBt;
    private List<View> views;
    private GuideAdapter vpAdapter;
    //用pref记录是否为首次载入
    private static final String SHAREDPREFERENCES_NAME = "first_pref";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LOW_PROFILE);
        intViews();
    }


    /**
     * 初始化引导页面
     */
    private void intViews() {
        LayoutInflater inflater = LayoutInflater.from(this);
        views = new ArrayList<View>();

        //初始化引导页面列表
        views.add(inflater.inflate(R.layout.guide_page_1, null));
        views.add(inflater.inflate(R.layout.guide_page_2, null));
        views.add(inflater.inflate(R.layout.guide_page_3, null));

        //初始化Adapter
        vpAdapter = new GuideAdapter(views, this);
        viewPager.setAdapter(vpAdapter);
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if(position==2){
            guideBt.setVisibility(View.VISIBLE);
        }else {
            guideBt.setVisibility(View.GONE);
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @OnClick({R.id.guide_tv, R.id.guide_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.guide_tv:
            case R.id.guide_bt:
                setGuided();
                Intent intent = new Intent(GuideActivity.this, MainFrameActivity.class);
                startActivity(intent);
                this.finish();
                break;
            default:
        }
    }

    /**
     * 跳转到引导页面
     */
    private void setGuided() {
        SharedPreferences preferences = getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        //走完一遍引导页面后设置标志为false
        editor.putBoolean("isFirstIn", false);
        editor.commit();
    }


}
