package com.purchase.sls.account.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.account.AccountContract;
import com.purchase.sls.account.AccountModule;
import com.purchase.sls.account.DaggerAccountComponent;
import com.purchase.sls.account.adapter.AccountListAdapter;
import com.purchase.sls.account.presenter.AccountListPresenter;
import com.purchase.sls.common.refreshview.HeaderViewLayout;
import com.purchase.sls.common.unit.FormatUtil;
import com.purchase.sls.data.entity.AccountListInfo;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/5/2.
 */

public class AccountListActivity extends BaseActivity implements AccountContract.AccountListView, AccountListAdapter.OnAccountItemClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.account_data)
    TextView accountData;
    @BindView(R.id.account_sum)
    TextView accountSum;
    @BindView(R.id.screen_iv)
    ImageView screenIv;
    @BindView(R.id.account_rv)
    RecyclerView accountRv;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.empty_view)
    NestedScrollView emptyView;
    @BindView(R.id.refreshLayout)
    HeaderViewLayout refreshLayout;

    private AccountListAdapter accountListAdapter;
    private String totalSum;
    private String totalPower;
    @Inject
    AccountListPresenter accountListPresenter;

    public static void start(Context context) {
        Intent intent = new Intent(context, AccountListActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_list);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        refreshLayout.setOnRefreshListener(mOnRefreshListener);
        accountData.setText(FormatUtil.formatDateMonth(System.currentTimeMillis()));
        addAdapter();
        accountListPresenter.getAccountList("", "");
    }

    private void addAdapter() {
        accountListAdapter = new AccountListAdapter(this);
        accountListAdapter.setOnCollectionItemClickListener(this);
        accountRv.setAdapter(accountListAdapter);
    }

    HeaderViewLayout.OnRefreshListener mOnRefreshListener = new HeaderViewLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            accountListPresenter.getAccountList("", "");
        }

        @Override
        public void onLoadMore() {
            accountListPresenter.getMoreAccountList("", "");
        }

        @Override
        public void onModeChanged(@HeaderViewLayout.Mode int mode) {
        }
    };

    @Override
    protected void initializeInjector() {
        DaggerAccountComponent.builder()
                .applicationComponent(getApplicationComponent())
                .accountModule(new AccountModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void setPresenter(AccountContract.AccountListPresenter presenter) {

    }

    @Override
    public void accountListInfo(AccountListInfo accountListInfo) {
        refreshLayout.stopRefresh();
        if (accountListInfo != null) {
            if (accountListInfo.getAccountSums() != null && accountListInfo.getAccountSums().size() > 0) {
                totalSum = accountListInfo.getAccountSums().get(0).getSum();
            }
            if (accountListInfo.getAccountpowers() != null && accountListInfo.getAccountpowers().size() > 0) {
                totalPower = accountListInfo.getAccountpowers().get(0).getPower();
            }
            accountSum.setText("总支付¥" + totalSum + " 能量收入¥" + totalPower);
            if (accountListInfo.getAccountItemList() != null && accountListInfo.getAccountItemList().getAccountItemInfos() != null && accountListInfo.getAccountItemList().getAccountItemInfos().size() > 0) {
                refreshLayout.setCanLoadMore(true);
                accountListAdapter.setData(accountListInfo.getAccountItemList().getAccountItemInfos());
                emptyView.setVisibility(View.GONE);
                accountRv.setVisibility(View.VISIBLE);
            } else {
                refreshLayout.setCanLoadMore(false);
                emptyView.setVisibility(View.VISIBLE);
                accountRv.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void moreAccountListInfo(AccountListInfo accountListInfo) {
        refreshLayout.stopRefresh();
        if (accountListInfo != null && accountListInfo.getAccountItemList() != null && accountListInfo.getAccountItemList().getAccountItemInfos() != null && accountListInfo.getAccountItemList().getAccountItemInfos().size() > 0) {
            accountListAdapter.addMore(accountListInfo.getAccountItemList().getAccountItemInfos());
            refreshLayout.setCanLoadMore(true);
        } else {
            refreshLayout.setCanLoadMore(false);
        }

    }

    @Override
    public void goAccountDetail(String billid) {
        AccountDetailActivity.start(this, billid);
    }

    @OnClick({R.id.back,R.id.screen_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.screen_iv:
                AccountChooseTimeActivity.start(this);
                break;
            default:
        }
    }
}
