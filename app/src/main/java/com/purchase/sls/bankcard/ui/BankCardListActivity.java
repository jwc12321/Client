package com.purchase.sls.bankcard.ui;

import android.app.Activity;
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
import com.purchase.sls.bankcard.BankCardContract;
import com.purchase.sls.bankcard.BankCardModule;
import com.purchase.sls.bankcard.DaggerBankCardComponent;
import com.purchase.sls.bankcard.adapter.BankCardAdapter;
import com.purchase.sls.bankcard.presenter.BankCardsPresenter;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.common.refreshview.HeaderViewLayout;
import com.purchase.sls.data.entity.BankCardInfo;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/11/27.
 */

public class BankCardListActivity extends BaseActivity implements BankCardContract.BankCardsView,BankCardAdapter.ItemClickListener{
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.add)
    ImageView add;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.bankcard_rv)
    RecyclerView bankcardRv;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.empty_view)
    NestedScrollView emptyView;
    @BindView(R.id.refreshLayout)
    HeaderViewLayout refreshLayout;

    @Inject
    BankCardsPresenter bankCardsPresenter;

    private BankCardAdapter bankCardAdapter;

    private String isBankInfo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bankcard_list);
        ButterKnife.bind(this);
        setHeight(back,title,add);
        initView();
    }

    private void initView(){
        isBankInfo=getIntent().getStringExtra(StaticData.IS_BANK_INTO);
        refreshLayout.setOnRefreshListener(mOnRefreshListener);
        refreshLayout.setCanLoadMore(false);
        addAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bankCardsPresenter.getBankCards("1");
    }

    private void addAdapter(){
        bankCardAdapter=new BankCardAdapter();
        bankCardAdapter.setItemClickListener(this);
        bankcardRv.setAdapter(bankCardAdapter);
    }

    HeaderViewLayout.OnRefreshListener mOnRefreshListener = new HeaderViewLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            bankCardsPresenter.getBankCards("0");
        }

        @Override
        public void onLoadMore() {
        }

        @Override
        public void onModeChanged(@HeaderViewLayout.Mode int mode) {
        }
    };

    @Override
    protected void initializeInjector() {
        DaggerBankCardComponent.builder()
                .applicationComponent(getApplicationComponent())
                .bankCardModule(new BankCardModule(this))
                .build()
                .inject(this);
    }

    @OnClick({R.id.back,R.id.add})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.add:
                AddBankCardActivity.start(this);
                break;
            default:
        }
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void setPresenter(BankCardContract.BankCardsPresenter presenter) {

    }

    @Override
    public void renderBankCards(List<BankCardInfo> bankCardInfos) {
        refreshLayout.stopRefresh();
        if(bankCardInfos!=null&&bankCardInfos.size()>0){
            bankcardRv.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }else {
            bankcardRv.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
        bankCardAdapter.setData(bankCardInfos);
    }

    @Override
    public void deleteSuccess() {
        bankCardsPresenter.getBankCards("1");
    }

    @Override
    public void backBankInfo(BankCardInfo bankCardInfo) {
        if(TextUtils.equals("1",isBankInfo)) {
            Intent intent = new Intent();
            intent.putExtra(StaticData.BANK_INFO, bankCardInfo);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public void untyingBank(String id) {
        bankCardsPresenter.deleteBankCard(id);
    }
}
