package com.purchase.sls.ordermanage.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.data.entity.LogisticRracesInfo;
import com.purchase.sls.ordermanage.adapter.LogisticsAdapter;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/6/8.
 * 物流详情
 */

public class LogisticsDetailsActivity extends BaseActivity {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.logistics_company)
    TextView logisticsCompany;
    @BindView(R.id.logistics_number)
    TextView logisticsNumber;
    @BindView(R.id.logistics_rv)
    RecyclerView logisticsRv;
    private String expressName;
    private String expressNum;
    private LogisticsAdapter logisticsAdapter;
    private List<LogisticRracesInfo> logisticRracesInfos;

    public static void start(Context context, String expressName, String expressNum, List<LogisticRracesInfo> logisticRracesInfos) {
        Intent intent = new Intent(context, LogisticsDetailsActivity.class);
        intent.putExtra(StaticData.EXPTESS_NAME, expressName);
        intent.putExtra(StaticData.EXPTESS_NUM, expressNum);
        intent.putExtra(StaticData.EXPTESS_LOGIS, (Serializable) logisticRracesInfos);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics_details);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initView();
    }

    private void initView() {
        expressName = getIntent().getStringExtra(StaticData.EXPTESS_NAME);
        expressNum = getIntent().getStringExtra(StaticData.EXPTESS_NUM);
        logisticRracesInfos = (List<LogisticRracesInfo>) getIntent().getSerializableExtra(StaticData.EXPTESS_LOGIS);
        logisticsCompany.setText("物流公司:" + expressName);
        logisticsNumber.setText("快递编号:" + expressNum);
        logisticsAdapter = new LogisticsAdapter();
        logisticsRv.setAdapter(logisticsAdapter);
        if (logisticRracesInfos != null) {
            Collections.reverse(logisticRracesInfos);
            logisticsAdapter.setData(logisticRracesInfos);
        }
        logisticsRv.setNestedScrollingEnabled(false);
        logisticsRv.setFocusableInTouchMode(false);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
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
