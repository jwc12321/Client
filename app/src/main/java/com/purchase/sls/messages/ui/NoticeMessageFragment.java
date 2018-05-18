package com.purchase.sls.messages.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.purchase.sls.R;
import com.purchase.sls.common.refreshview.HeaderViewLayout;
import com.purchase.sls.common.widget.list.BaseListFragment;
import com.purchase.sls.coupon.ui.CouponListActivity;
import com.purchase.sls.data.entity.MessageListInfo;
import com.purchase.sls.data.entity.WebViewDetailInfo;
import com.purchase.sls.evaluate.ui.ToBeEvaluatedActivity;
import com.purchase.sls.messages.DaggerMessagesComponent;
import com.purchase.sls.messages.MessagesContract;
import com.purchase.sls.messages.MessagesModule;
import com.purchase.sls.messages.adapter.MessageAdapter;
import com.purchase.sls.messages.presenter.MessageListPresenter;
import com.purchase.sls.webview.ui.WebViewActivity;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by JWC on 2018/5/8.
 */

public class NoticeMessageFragment extends BaseListFragment<MessageListInfo.MessageItem> implements MessagesContract.MessageListView, HeaderViewLayout.OnRefreshListener, MessageAdapter.OnItemClick {

    @Inject
    MessageListPresenter messageListPresenter;
    private MessageAdapter messageAdapter;
    private WebViewDetailInfo webViewDetailInfo;

    public static NoticeMessageFragment newInstance() {
        NoticeMessageFragment fragment = new NoticeMessageFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (messageListPresenter != null) {
            messageListPresenter.getMessageList("1","1");
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setMoreLoadable(true);
    }

    @Override
    public RecyclerView.Adapter initAdapter(List<MessageListInfo.MessageItem> list) {
        messageAdapter = new MessageAdapter();
        messageAdapter.setData(list);
        messageAdapter.setonOnItemClick(this);
        return messageAdapter;
    }

    private boolean isFirstLoad = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isFirstLoad) {
            if (getUserVisibleHint()) {
                if (messageListPresenter != null) {
                    messageListPresenter.getMessageList("1","1");
                }
                isFirstLoad = false;
            }
        }
    }

    @Override
    public void onRefresh() {
        messageListPresenter.getMessageList("0","1");
    }

    @Override
    public void onLoadMore() {
        messageListPresenter.getMoreMessageList("1");
    }

    @Override
    public void setPresenter(MessagesContract.MessageListPresenter presenter) {

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
    public void itemClick(String type, String messageid) {
        if (TextUtils.equals("1", type)) {
            CouponListActivity.start(getActivity());
        } else if (TextUtils.equals("2", type)) {
            ToBeEvaluatedActivity.start(getActivity());
        } else {
            if (!TextUtils.isEmpty(messageid)) {
                String url = "http://open.365neng.com/api/home/message/info?id=" + messageid;
                webViewDetailInfo = new WebViewDetailInfo();
                webViewDetailInfo.setTitle("消息详情");
                webViewDetailInfo.setUrl(url);
                WebViewActivity.start(getActivity(), webViewDetailInfo);
            }
        }
    }
}
