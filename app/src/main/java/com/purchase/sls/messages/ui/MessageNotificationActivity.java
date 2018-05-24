package com.purchase.sls.messages.ui;

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
import com.purchase.sls.data.entity.MessageListInfo;
import com.purchase.sls.messages.DaggerMessagesComponent;
import com.purchase.sls.messages.MessagesContract;
import com.purchase.sls.messages.MessagesModule;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/5/8.
 * 消息通知
 */

public class MessageNotificationActivity extends BaseActivity implements MessagesContract.MessageListView {
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
        Intent intent = new Intent(context, MessageNotificationActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        ButterKnife.bind(this);
        setHeight(back,title,null);
        initView();
    }

    private void initView() {
        fragmentList = new ArrayList<>();
        titleList = new ArrayList<>();
        viewpager.setOffscreenPageLimit(1);
        fragmentList.add(NoticeMessageFragment.newInstance());
        fragmentList.add(CouponMessageFragment.newInstance());
        titleList.add("通知");
        titleList.add("优惠");
        baseListAdapter = new BaseListAdapter(getSupportFragmentManager(), fragmentList, titleList);
        viewpager.setAdapter(baseListAdapter);
        viewpager.setCurrentItem(0);
        indicator.removeAllTabs();
        indicator.setupWithViewPager(viewpager);
    }

    @Override
    protected void initializeInjector() {
        DaggerMessagesComponent.builder()
                .applicationComponent(getApplicationComponent())
                .messagesModule(new MessagesModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void setPresenter(MessagesContract.MessageListPresenter presenter) {

    }

    @Override
    public void render(List<MessageListInfo.MessageItem> messageItems) {

    }

    @Override
    public void renderMore(List<MessageListInfo.MessageItem> messageItems) {

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
