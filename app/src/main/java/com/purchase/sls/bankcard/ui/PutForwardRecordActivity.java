package com.purchase.sls.bankcard.ui;

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
import com.purchase.sls.bankcard.BankCardContract;
import com.purchase.sls.bankcard.BankCardModule;
import com.purchase.sls.bankcard.DaggerBankCardComponent;
import com.purchase.sls.bankcard.adapter.PutFRecordAdapter;
import com.purchase.sls.bankcard.presenter.PutForwardRecordPresenter;
import com.purchase.sls.common.refreshview.HeaderViewLayout;
import com.purchase.sls.data.entity.PutForwardList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/11/27.
 */

public class PutForwardRecordActivity extends BaseActivity implements BankCardContract.PutForwardRecordView,PutFRecordAdapter.ItemClickListener{
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.pf_record_rv)
    RecyclerView pfRecordRv;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.empty_view)
    NestedScrollView emptyView;
    @BindView(R.id.refreshLayout)
    HeaderViewLayout refreshLayout;

    @Inject
    PutForwardRecordPresenter putForwardRecordPresenter;

    private PutFRecordAdapter putFRecordAdapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, PutForwardRecordActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_forward_record);
        ButterKnife.bind(this);
        setHeight(back,title,null);
        initView();
    }
    private void initView(){
        refreshLayout.setOnRefreshListener(mOnRefreshListener);
        addAdapter();
        putForwardRecordPresenter.getPutForwardRecords("1");
    }

    private void addAdapter(){
        putFRecordAdapter=new PutFRecordAdapter();
        putFRecordAdapter.setItemClickListener(this);
        pfRecordRv.setAdapter(putFRecordAdapter);

    }

    HeaderViewLayout.OnRefreshListener mOnRefreshListener = new HeaderViewLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            putForwardRecordPresenter.getPutForwardRecords("0");
        }

        @Override
        public void onLoadMore() {
          putForwardRecordPresenter.getMorePutForwardRecords();
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
    public void setPresenter(BankCardContract.PutForwardRecordPresenter presenter) {

    }

    @Override
    public void renderPutFRecords(PutForwardList putForwardList) {
        refreshLayout.stopRefresh();
        if(putForwardList!=null&&putForwardList.getPfRecrodInfos()!=null&&putForwardList.getPfRecrodInfos().size()>0){
            pfRecordRv.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            refreshLayout.setCanLoadMore(true);
            putFRecordAdapter.setData(putForwardList.getPfRecrodInfos());
        }else {
            pfRecordRv.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            refreshLayout.setCanLoadMore(false);
        }
    }

    @Override
    public void renderMorePutFRecords(PutForwardList putForwardList) {
        refreshLayout.stopRefresh();
        if(putForwardList!=null&&putForwardList.getPfRecrodInfos()!=null&&putForwardList.getPfRecrodInfos().size()>0){
            refreshLayout.setCanLoadMore(true);
            putFRecordAdapter.addMore(putForwardList.getPfRecrodInfos());
        }else {
            refreshLayout.setCanLoadMore(false);
        }
    }

    @Override
    protected void initializeInjector() {
        DaggerBankCardComponent.builder()
                .applicationComponent(getApplicationComponent())
                .bankCardModule(new BankCardModule(this))
                .build()
                .inject(this);
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

    @Override
    public void backRecordId(String id) {
        PutForwardDetailActivity.start(this,id);
    }
}
