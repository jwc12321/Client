package com.purchase.sls.bankcard.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.bankcard.BankCardContract;
import com.purchase.sls.bankcard.BankCardModule;
import com.purchase.sls.bankcard.DaggerBankCardComponent;
import com.purchase.sls.bankcard.presenter.PutForwardDetailPresenter;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.common.unit.FormatUtil;
import com.purchase.sls.data.entity.PfRecrodDetail;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/11/27.
 */

public class PutForwardDetailActivity extends BaseActivity implements BankCardContract.PutForwardDetailView {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.total_price)
    TextView totalPrice;
    @BindView(R.id.type)
    TextView type;
    @BindView(R.id.current_state)
    TextView currentState;
    @BindView(R.id.putforward_type)
    TextView putforwardType;
    @BindView(R.id.putforward_number)
    TextView putforwardNumber;
    @BindView(R.id.apply_time)
    TextView applyTime;
    @BindView(R.id.putforward_bank)
    TextView putforwardBank;
    @BindView(R.id.putforward_orderno)
    TextView putforwardOrderno;

    @Inject
    PutForwardDetailPresenter putForwardDetailPresenter;
    @BindView(R.id.fail_tv)
    TextView failTv;
    @BindView(R.id.fail_explain)
    TextView failExplain;
    @BindView(R.id.fail_ll)
    LinearLayout failLl;
    private String recordId;

    public static void start(Context context, String recordId) {
        Intent intent = new Intent(context, PutForwardDetailActivity.class);
        intent.putExtra(StaticData.RECORD_ID, recordId);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_forward_detail);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initView();
    }

    private void initView() {
        recordId = getIntent().getStringExtra(StaticData.RECORD_ID);
        putForwardDetailPresenter.getPutForwardDetail(recordId);
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

    @Override
    protected void initializeInjector() {
        DaggerBankCardComponent.builder()
                .applicationComponent(getApplicationComponent())
                .bankCardModule(new BankCardModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void setPresenter(BankCardContract.PutForwardDetailPresenter presenter) {

    }

    @Override
    public void renderPutForwardDetail(PfRecrodDetail pfRecrodDetail) {
        if (pfRecrodDetail != null) {
            totalPrice.setText("¥" + pfRecrodDetail.getPrice());
            String stateStr = pfRecrodDetail.getStatus();
            if (TextUtils.equals("0", stateStr)) {
                currentState.setText("待审核");
            } else if (TextUtils.equals("1", stateStr)) {
                currentState.setText("审核通过");
            } else if (TextUtils.equals("2", stateStr)) {
                currentState.setText("已打款");
            } else if (TextUtils.equals("-1", stateStr)) {
                currentState.setText("审核未通过");
            } else if (TextUtils.equals("-2", stateStr)) {
                currentState.setText("打款失败");
            }
            putforwardNumber.setText("¥" + pfRecrodDetail.getPrice());
            applyTime.setText(FormatUtil.formatDateByLine(pfRecrodDetail.getCreatedAt()));
            putforwardBank.setText(pfRecrodDetail.getBankName());
            putforwardOrderno.setText(pfRecrodDetail.getWithdrawNo());
            if (TextUtils.equals("-1", stateStr) || TextUtils.equals("-2", stateStr)) {
                failTv.setVisibility(View.VISIBLE);
                failLl.setVisibility(View.VISIBLE);
                failExplain.setText(pfRecrodDetail.getRemark());
            } else {
                failTv.setVisibility(View.GONE);
                failLl.setVisibility(View.GONE);
            }
        }
    }
}
