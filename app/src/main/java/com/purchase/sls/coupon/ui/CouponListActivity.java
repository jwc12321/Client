package com.purchase.sls.coupon.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.common.widget.list.BaseListAdapter;
import com.purchase.sls.coupon.CouponContract;
import com.purchase.sls.coupon.CouponModule;
import com.purchase.sls.coupon.DaggerCouponComponent;
import com.purchase.sls.data.entity.CouponInfo;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/5/3.
 */

public class CouponListActivity extends BaseActivity implements CouponContract.CouponListView {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.indicator)
    TabLayout indicator;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    private List<Fragment> fragmentList;
    private List<String> titleList;
    private BaseListAdapter baseListAdapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, CouponListActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_list);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        fragmentList = new ArrayList<>();
        titleList = new ArrayList<>();
        viewpager.setOffscreenPageLimit(1);
        fragmentList.add(AvailableCouponFragment.newInstance());
        fragmentList.add(InvalidCouponFragment.newInstance());
        titleList.add("可用");
        titleList.add("失效");
        baseListAdapter = new BaseListAdapter(getSupportFragmentManager(), fragmentList, titleList);
        viewpager.setAdapter(baseListAdapter);
        viewpager.setCurrentItem(0);
        indicator.removeAllTabs();
        indicator.setupWithViewPager(viewpager);
    }

    @Override
    protected void initializeInjector() {
        DaggerCouponComponent.builder()
                .applicationComponent(getApplicationComponent())
                .couponModule(new CouponModule(this))
                .build()
                .inject(this);
    }


    @Override
    public View getSnackBarHolderView() {
        return null;
    }


    @Override
    public void setPresenter(CouponContract.CouponListPresenter presenter) {

    }


    @Override
    public void render(List<CouponInfo> couponInfos) {

    }

    @Override
    public void renderMore(List<CouponInfo> couponInfos) {

    }

    @OnClick({R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            default:
        }
    }
}
