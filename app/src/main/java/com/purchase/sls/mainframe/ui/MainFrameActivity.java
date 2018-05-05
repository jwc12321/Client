package com.purchase.sls.mainframe.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.BaseFragment;
import com.purchase.sls.R;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.common.widget.ViewPagerSlide;
import com.purchase.sls.energy.ui.EnergyFragment;
import com.purchase.sls.homepage.ui.HomePageFragment;
import com.purchase.sls.mainframe.adapter.MainPagerAdapter;
import com.purchase.sls.mine.ui.PersonalCenterFragment;
import com.purchase.sls.nearbymap.ui.NearbyMapFragment;
import com.purchase.sls.shoppingmall.ui.ShoppingMallFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/4/19.
 */

public class MainFrameActivity extends BaseActivity {


    @BindView(R.id.homepage_iv)
    ImageView homepageIv;
    @BindView(R.id.homepage_tt)
    TextView homepageTt;
    @BindView(R.id.homepage_rl)
    RelativeLayout homepageRl;
    @BindView(R.id.nearby_iv)
    ImageView nearbyIv;
    @BindView(R.id.nearby_tt)
    TextView nearbyTt;
    @BindView(R.id.nearby_rl)
    RelativeLayout nearbyRl;
    @BindView(R.id.shoppingmall_tt)
    TextView shoppingmallTt;
    @BindView(R.id.shoppingmall_rl)
    RelativeLayout shoppingmallRl;
    @BindView(R.id.energy_iv)
    ImageView energyIv;
    @BindView(R.id.energy_tt)
    TextView energyTt;
    @BindView(R.id.energy_rl)
    RelativeLayout energyRl;
    @BindView(R.id.mine_iv)
    ImageView mineIv;
    @BindView(R.id.mine_tt)
    TextView mineTt;
    @BindView(R.id.mine_rl)
    RelativeLayout mineRl;
    @BindView(R.id.shoppingmall_iv)
    ImageView shoppingmallIv;
    @BindView(R.id.viewPager)
    ViewPagerSlide viewPager;


    private RelativeLayout[] relativeLayouts;
    private BaseFragment[] fragments;
    private ImageView[] imageViews;
    private TextView[] textViews;
    private MainPagerAdapter adapter;

    private String goFirst;
    public static void start(Context context){
        Intent intent=new Intent(context,MainFrameActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainfram);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        fragments = new BaseFragment[5];
        fragments[0] = HomePageFragment.newInstance();
        fragments[1] = NearbyMapFragment.newInstance();
        fragments[2] = ShoppingMallFragment.newInstance();
        fragments[3] = EnergyFragment.newInstance();
        fragments[4] = PersonalCenterFragment.newInstance();
        relativeLayouts = new RelativeLayout[5];
        relativeLayouts[0] = homepageRl;
        relativeLayouts[1] = nearbyRl;
        relativeLayouts[2] = shoppingmallRl;
        relativeLayouts[3] = energyRl;
        relativeLayouts[4] = mineRl;
        imageViews = new ImageView[5];
        imageViews[0] = homepageIv;
        imageViews[1] = nearbyIv;
        imageViews[2] = shoppingmallIv;
        imageViews[3] = energyIv;
        imageViews[4] = mineIv;
        textViews = new TextView[5];
        textViews[0] = homepageTt;
        textViews[1] = nearbyTt;
        textViews[2] = shoppingmallTt;
        textViews[3] = energyTt;
        textViews[4] = mineTt;
        for (RelativeLayout relativeLayout : relativeLayouts) {
            relativeLayout.setOnClickListener(onClickListener);
        }
        viewPager.setOffscreenPageLimit(4);
        adapter = new MainPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(onPageChangeListener);
        viewPager.setCurrentItem(0);

    }


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for (int i = 0; i < relativeLayouts.length; i++) {
                if (v == relativeLayouts[i]) {
                    viewPager.setCurrentItem(i);
                    break;
                }
            }
        }
    };

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < imageViews.length; i++) {
                imageViews[i].setSelected(position == i);
                textViews[i].setSelected(position == i);
            }
        }
    };

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    protected void onStart() {
        super.onStart();
        goFirst=getIntent().getStringExtra(StaticData.GO_FIRST);
        if(TextUtils.equals("1",goFirst)){
            viewPager.setCurrentItem(0);
            goFirst="2";
        }
    }

    private String goFirst1;
    @Override
    protected void onResume() {
        super.onResume();
    }
}
