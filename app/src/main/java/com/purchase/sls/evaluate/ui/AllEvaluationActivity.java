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
import com.purchase.sls.common.StaticData;
import com.purchase.sls.common.refreshview.HeaderViewLayout;
import com.purchase.sls.common.widget.dialog.PhotoPreviewDialog;
import com.purchase.sls.data.entity.AllEvaluationInfo;
import com.purchase.sls.evaluate.DaggerEvaluateComponent;
import com.purchase.sls.evaluate.EvaluateContract;
import com.purchase.sls.evaluate.EvaluateModule;
import com.purchase.sls.evaluate.adapter.AllEvaluateAdapter;
import com.purchase.sls.evaluate.presenter.AllEvaluationPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/5/5.
 */

public class AllEvaluationActivity extends BaseActivity implements EvaluateContract.AllEvaluationView,AllEvaluateAdapter.OnPictureOnClickListener {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.evaluation_total)
    TextView evaluationTotal;
    @BindView(R.id.evaluation_rv)
    RecyclerView evaluationRv;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.empty_view)
    NestedScrollView emptyView;
    @BindView(R.id.refreshLayout)
    HeaderViewLayout refreshLayout;

    @Inject
    AllEvaluationPresenter allEvaluationPresenter;
    private AllEvaluateAdapter allEvaluateAdapter;

    private String storeId;

    public static void start(Context context, String storeId) {
        Intent intent = new Intent(context, AllEvaluationActivity.class);
        intent.putExtra(StaticData.BUSINESS_STOREID, storeId);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_evaluation);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        storeId = getIntent().getStringExtra(StaticData.BUSINESS_STOREID);
        refreshLayout.setOnRefreshListener(mOnRefreshListener);
        allEvaluateAdapter = new AllEvaluateAdapter(this,"2");
        allEvaluateAdapter.setOnPictureOnClickListener(this);
        evaluationRv.setAdapter(allEvaluateAdapter);
        allEvaluationPresenter.getAllEvaluation("1",storeId);
    }

    @Override
    protected void initializeInjector() {
        DaggerEvaluateComponent.builder()
                .applicationComponent(getApplicationComponent())
                .evaluateModule(new EvaluateModule(this))
                .build()
                .inject(this);
    }

    HeaderViewLayout.OnRefreshListener mOnRefreshListener = new HeaderViewLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            allEvaluationPresenter.getAllEvaluation("0",storeId);
        }

        @Override
        public void onLoadMore() {
            allEvaluationPresenter.getMoreAllEvaluation(storeId);
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
    public void setPresenter(EvaluateContract.AllEvaluationPresenter presenter) {

    }

    @Override
    public void renderAllEvaluation(AllEvaluationInfo allEvaluationInfo) {
        refreshLayout.stopRefresh();
        if (allEvaluationInfo != null) {
            evaluationTotal.setText("网友评价(" + allEvaluationInfo.getTotal() + ")");
            if (allEvaluationInfo.getEvaluateInfo() != null && allEvaluationInfo.getEvaluateInfo().getEvaluateItemInfos() != null
                    && allEvaluationInfo.getEvaluateInfo().getEvaluateItemInfos().size() > 0) {
                evaluationRv.setVisibility(View.VISIBLE);
                emptyView.setVisibility(View.GONE);
                refreshLayout.setCanLoadMore(true);
                allEvaluateAdapter.setData(allEvaluationInfo.getEvaluateInfo().getEvaluateItemInfos());
            } else {
                evaluationRv.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
                refreshLayout.setCanLoadMore(false);
            }
        }
    }

    @Override
    public void renderMoreAllEvaluation(AllEvaluationInfo allEvaluationInfo) {
        refreshLayout.stopRefresh();
        if (allEvaluationInfo != null && allEvaluationInfo.getEvaluateInfo() != null && allEvaluationInfo.getEvaluateInfo().getEvaluateItemInfos() != null
                && allEvaluationInfo.getEvaluateInfo().getEvaluateItemInfos().size() > 0) {
            refreshLayout.setCanLoadMore(true);
            allEvaluateAdapter.addMore(allEvaluationInfo.getEvaluateInfo().getEvaluateItemInfos());
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

    @Override
    public void zoom(int position, List<String> photos) {
        PhotoPreviewDialog previewDialog = new PhotoPreviewDialog.Builder()
                .index(position)
                .path(photos)
                .build();
        previewDialog.setIndex(position);
        previewDialog.show(getSupportFragmentManager(), null);
    }
}
