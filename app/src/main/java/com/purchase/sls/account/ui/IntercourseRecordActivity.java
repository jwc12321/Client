package com.purchase.sls.account.ui;

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
import com.purchase.sls.account.adapter.IntercourseRecordAdapter;
import com.purchase.sls.account.presenter.IntercourseRecordPresenter;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.common.refreshview.HeaderViewLayout;
import com.purchase.sls.data.entity.IntercourseRecordInfo;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/5/2.
 * 来往记录
 */

public class IntercourseRecordActivity extends BaseActivity implements AccountContract.IntercourseRecordView {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.ir_rv)
    RecyclerView irRv;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.empty_view)
    NestedScrollView emptyView;
    @BindView(R.id.refreshLayout)
    HeaderViewLayout refreshLayout;

    private IntercourseRecordAdapter intercourseRecordAdapter;
    private String storeid;
    @Inject
    IntercourseRecordPresenter intercourseRecordPresenter;

    public static void start(Context context, String storeid) {
        Intent intent = new Intent(context, IntercourseRecordActivity.class);
        intent.putExtra(StaticData.BUSINESS_STOREID, storeid);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intercourse_record);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        storeid = getIntent().getStringExtra(StaticData.BUSINESS_STOREID);
        refreshLayout.setOnRefreshListener(mOnRefreshListener);
        addAdapter();
        intercourseRecordPresenter.getIntercourseRecordInfo(storeid);
    }

    private void addAdapter() {
        intercourseRecordAdapter = new IntercourseRecordAdapter();
        irRv.setAdapter(intercourseRecordAdapter);
    }

    HeaderViewLayout.OnRefreshListener mOnRefreshListener = new HeaderViewLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            intercourseRecordPresenter.getIntercourseRecordInfo(storeid);
        }

        @Override
        public void onLoadMore() {
            intercourseRecordPresenter.getMoreIntercourseRecordInfo(storeid);
        }

        @Override
        public void onModeChanged(@HeaderViewLayout.Mode int mode) {
        }
    };

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void setPresenter(AccountContract.IntercourseRecordPresenter presenter) {

    }

    @Override
    protected void initializeInjector() {
        DaggerAccountComponent.builder()
                .applicationComponent(getApplicationComponent())
                .accountModule(new AccountModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void intercourseRecordInfo(IntercourseRecordInfo intercourseRecordInfo) {
        refreshLayout.stopRefresh();
        if (intercourseRecordInfo != null && intercourseRecordInfo.getIrItemInfos() != null && intercourseRecordInfo.getIrItemInfos().size() > 0) {
            irRv.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            refreshLayout.setCanLoadMore(true);
            intercourseRecordAdapter.setData(intercourseRecordInfo.getIrItemInfos());
        } else {
            irRv.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            refreshLayout.setCanLoadMore(false);
        }
    }

    @Override
    public void moreIntercourseRecordInfo(IntercourseRecordInfo intercourseRecordInfo) {
        refreshLayout.stopRefresh();
        if (intercourseRecordInfo != null && intercourseRecordInfo.getIrItemInfos() != null && intercourseRecordInfo.getIrItemInfos().size() > 0) {
            intercourseRecordAdapter.addMore(intercourseRecordInfo.getIrItemInfos());
            refreshLayout.setCanLoadMore(true);
        } else {
            refreshLayout.setCanLoadMore(false);
        }
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
