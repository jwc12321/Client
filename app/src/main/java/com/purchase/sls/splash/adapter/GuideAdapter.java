package com.purchase.sls.splash.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.purchase.sls.R;
import com.purchase.sls.mainframe.ui.MainFrameActivity;

import java.util.List;

/**
 * Created by Administrator on 2016/9/19.
 */
public class GuideAdapter extends PagerAdapter {

    //引导页面列表
    private List<View> views;
    private Activity activity;

    //pref文件记录首次载入标志位
    private static final String SHAREDPREFERENCES_NAME = "first_pref";

    public GuideAdapter(List<View> views, Activity activity) {
        this.views = views;
        this.activity = activity;
    }


    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return  view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position), 0);
        return views.get(position);
    }

    /**
     * 跳转到主页面
     */
    private void goHome() {
        Intent intent = new Intent(activity, MainFrameActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    /**
     * 跳转到引导页面
     */
    private void setGuided() {
        SharedPreferences preferences = activity.getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        //走完一遍引导页面后设置标志为false
        editor.putBoolean("isFirstIn", false);
        editor.commit();
    }
}
