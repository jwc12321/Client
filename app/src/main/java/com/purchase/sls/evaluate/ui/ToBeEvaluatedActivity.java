package com.purchase.sls.evaluate.ui;

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
import com.purchase.sls.collection.ui.CollectionListActivity;
import com.purchase.sls.common.refreshview.HeaderViewLayout;
import com.purchase.sls.data.entity.ToBeEvaluationInfo;
import com.purchase.sls.evaluate.DaggerEvaluateComponent;
import com.purchase.sls.evaluate.EvaluateContract;
import com.purchase.sls.evaluate.EvaluateModule;
import com.purchase.sls.evaluate.adapter.ToBeEvaluateAdapter;
import com.purchase.sls.evaluate.presenter.ToBeEvaluationPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/5/5.
 * 待评价
 */

public class ToBeEvaluatedActivity extends BaseActivity implements EvaluateContract.ToBeEvaluationView, ToBeEvaluateAdapter.OnItemClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.to_be_evaluation_rv)
    RecyclerView toBeEvaluationRv;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.empty_view)
    NestedScrollView emptyView;
    @BindView(R.id.refreshLayout)
    HeaderViewLayout refreshLayout;

    @Inject
    ToBeEvaluationPresenter toBeEvaluationPresenter;
    private ToBeEvaluateAdapter toBeEvaluateAdapter;


    public static void start(Context context) {
        Intent intent = new Intent(context, ToBeEvaluatedActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_be_evaluated);
        ButterKnife.bind(this);
        setHeight(back,title,null);
        initView();
    }

    private void initView() {
        refreshLayout.setOnRefreshListener(mOnRefreshListener);
        toBeEvaluateAdapter = new ToBeEvaluateAdapter(this);
        toBeEvaluateAdapter.setOnItemClickListener(this);
        toBeEvaluationRv.setAdapter(toBeEvaluateAdapter);
        toBeEvaluationPresenter.getToBeEvaluation("1");
    }


    HeaderViewLayout.OnRefreshListener mOnRefreshListener = new HeaderViewLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            toBeEvaluationPresenter.getToBeEvaluation("0");
        }

        @Override
        public void onLoadMore() {
            toBeEvaluationPresenter.getMoreToBeEvaluation();
        }

        @Override
        public void onModeChanged(@HeaderViewLayout.Mode int mode) {
        }
    };

    @Override
    protected void initializeInjector() {
        DaggerEvaluateComponent.builder()
                .applicationComponent(getApplicationComponent())
                .evaluateModule(new EvaluateModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void setPresenter(EvaluateContract.ToBeEvaluationPresenter presenter) {

    }

    @Override
    public void renderToBeEvaluation(ToBeEvaluationInfo toBeEvaluationInfo) {
        refreshLayout.stopRefresh();
        if (toBeEvaluationInfo != null && toBeEvaluationInfo.getToBeEvaluationItems() != null && toBeEvaluationInfo.getToBeEvaluationItems().size() > 0) {
            toBeEvaluationRv.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            refreshLayout.setCanLoadMore(true);
            toBeEvaluateAdapter.setData(toBeEvaluationInfo.getToBeEvaluationItems());
        } else {
            toBeEvaluationRv.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            refreshLayout.setCanLoadMore(false);
        }
    }

    @Override
    public void renderMoreToBeEvaluation(ToBeEvaluationInfo toBeEvaluationInfo) {
        refreshLayout.stopRefresh();
        if (toBeEvaluationInfo != null && toBeEvaluationInfo.getToBeEvaluationItems() != null && toBeEvaluationInfo.getToBeEvaluationItems().size() > 0) {
            refreshLayout.setCanLoadMore(true);
            toBeEvaluateAdapter.addMore(toBeEvaluationInfo.getToBeEvaluationItems());
        } else {
            refreshLayout.setCanLoadMore(false);
        }
    }

    @Override
    public void goEvaluate(String storeId,String orderId,String businessName) {
        SubmitEvaluateActivity.start(this,storeId,orderId,businessName);
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
