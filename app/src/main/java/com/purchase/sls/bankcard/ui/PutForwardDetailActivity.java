package com.purchase.sls.bankcard.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.bankcard.BankCardContract;
import com.purchase.sls.bankcard.BankCardModule;
import com.purchase.sls.bankcard.DaggerBankCardComponent;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.data.entity.PfRecrodDetail;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/11/27.
 */

public class PutForwardDetailActivity extends BaseActivity implements BankCardContract.PutForwardDetailView{
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
    @BindView(R.id.purforward_bank)
    TextView purforwardBank;
    @BindView(R.id.putforward_orderno)
    TextView putforwardOrderno;

    public static void start(Context context,String recordId) {
        Intent intent = new Intent(context, PutForwardDetailActivity.class);
        intent.putExtra(StaticData.RECORD_ID,recordId);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_forward_detail);
        ButterKnife.bind(this);
        setHeight(back,title,null);
    }

    private void initView(){

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

    }
}
