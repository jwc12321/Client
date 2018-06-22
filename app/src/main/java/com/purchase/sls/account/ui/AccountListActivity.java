package com.purchase.sls.account.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.purchase.sls.common.StaticData;
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
    private String chooseTimeType;
    private String monthlyTime;
    private String startTime;
    private String endTime;
    @Inject
    AccountListPresenter accountListPresenter;
    private static final int CHOOSETIME = 0x00f0;

    public static void start(Context context) {
        Intent intent = new Intent(context, AccountListActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_list);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initView();
    }

    private void initView() {
        refreshLayout.setOnRefreshListener(mOnRefreshListener);
        accountData.setText(FormatUtil.formatDateMonth(System.currentTimeMillis()));
        addAdapter();
        accountListPresenter.getAccountList("1", "", "");
    }

    private void addAdapter() {
        accountListAdapter = new AccountListAdapter(this);
        accountListAdapter.setOnCollectionItemClickListener(this);
        accountRv.setAdapter(accountListAdapter);
    }

    HeaderViewLayout.OnRefreshListener mOnRefreshListener = new HeaderViewLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            if (TextUtils.isEmpty(chooseTimeType)) {
                accountListPresenter.getAccountList("0", "", "");
            } else if (TextUtils.equals("1", chooseTimeType)) {
                accountListPresenter.getAccountList("0", monthlyTime, "");
            } else {
                accountListPresenter.getAccountList("0", startTime, endTime);
            }
        }

        @Override
        public void onLoadMore() {
            if (TextUtils.isEmpty(chooseTimeType)) {
                accountListPresenter.getMoreAccountList("", "");
            } else if (TextUtils.equals("1", chooseTimeType)) {
                accountListPresenter.getMoreAccountList(monthlyTime, "");
            } else {
                accountListPresenter.getMoreAccountList(startTime, endTime);
            }
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
            accountSum.setText("总支付¥" + (TextUtils.isEmpty(totalSum) ? 0 : totalSum) + " 能量收入¥" + (TextUtils.isEmpty(totalPower) ? 0 : totalPower));
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

    @OnClick({R.id.back, R.id.screen_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.screen_iv:
                Intent intent = new Intent(this, AccountChooseTimeActivity.class);
                startActivityForResult(intent, CHOOSETIME);
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            switch (requestCode) {
                case CHOOSETIME:
                    chooseTimeType = data.getStringExtra(StaticData.CHOOSE_TIME_TYPE);
                    if (TextUtils.equals("1", chooseTimeType)) {
                        monthlyTime = data.getStringExtra(StaticData.CHOOSE_TIME_FIRST);
                        accountListPresenter.getAccountList("1", monthlyTime, "");
                        accountData.setText(monthlyTime);
                    } else {
                        startTime = data.getStringExtra(StaticData.CHOOSE_TIME_FIRST);
                        endTime = data.getStringExtra(StaticData.CHOOSE_TIME_SECOND);
                        accountListPresenter.getAccountList("1", startTime, endTime);
                        accountData.setText(startTime + "到" + endTime);
                    }
                    break;
                default:
            }
        }
    }

}
