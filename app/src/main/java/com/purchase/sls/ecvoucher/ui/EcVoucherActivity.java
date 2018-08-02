package com.purchase.sls.ecvoucher.ui;

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
import com.purchase.sls.BuildConfig;
import com.purchase.sls.R;
import com.purchase.sls.common.refreshview.HeaderViewLayout;
import com.purchase.sls.common.unit.TokenManager;
import com.purchase.sls.data.entity.EcVoucherInfo;
import com.purchase.sls.data.entity.WebViewDetailInfo;
import com.purchase.sls.ecvoucher.DaggerEcVoucherComponent;
import com.purchase.sls.ecvoucher.EcVoucherContract;
import com.purchase.sls.ecvoucher.EcVoucherModule;
import com.purchase.sls.ecvoucher.adapter.EcVoucherAdapter;
import com.purchase.sls.ecvoucher.presenter.EcVoucherInfosPresenter;
import com.purchase.sls.webview.ui.WebViewActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/8/2.
 * 兑换券
 */

public class EcVoucherActivity extends BaseActivity implements EcVoucherContract.EcVoucherInfosView,EcVoucherAdapter.ItemClickListener{
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.ecvocher_rv)
    RecyclerView ecvocherRv;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.empty_view)
    NestedScrollView emptyView;
    @BindView(R.id.refreshLayout)
    HeaderViewLayout refreshLayout;

    @Inject
    EcVoucherInfosPresenter ecVoucherInfosPresenter;

    private EcVoucherAdapter ecVoucherAdapter;
    private WebViewDetailInfo webViewDetailInfo;

    public static void start(Context context) {
        Intent intent = new Intent(context, EcVoucherActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecvocher);
        ButterKnife.bind(this);
        setHeight(back,title,null);
        initView();
    }

    private void initView(){
        refreshLayout.setOnRefreshListener(mOnRefreshListener);
        ecVoucherAdapter=new EcVoucherAdapter();
        ecVoucherAdapter.setItemClickListener(this);
        ecvocherRv.setAdapter(ecVoucherAdapter);
        ecVoucherInfosPresenter.getEcVoucherInfo("1");
    }


    HeaderViewLayout.OnRefreshListener mOnRefreshListener = new HeaderViewLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            ecVoucherInfosPresenter.getEcVoucherInfo("0");
        }

        @Override
        public void onLoadMore() {
            ecVoucherInfosPresenter.getMoreEcVoucherInfo();
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
    public void setPresenter(EcVoucherContract.EcVoucherInfosPresenter presenter) {

    }

    @Override
    protected void initializeInjector() {
        DaggerEcVoucherComponent.builder()
                .applicationComponent(getApplicationComponent())
                .ecVoucherModule(new EcVoucherModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void renderEcVoucherInfo(EcVoucherInfo ecVoucherInfo) {
        refreshLayout.stopRefresh();
        if(ecVoucherInfo!=null){
            if(ecVoucherInfo.getEcVoucherItems()!=null&&ecVoucherInfo.getEcVoucherItems().size()>0){
                ecvocherRv.setVisibility(View.VISIBLE);
                emptyView.setVisibility(View.GONE);
                refreshLayout.setCanLoadMore(true);
            }else {
                ecvocherRv.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
                refreshLayout.setCanLoadMore(false);
            }
            ecVoucherAdapter.setData(ecVoucherInfo.getEcVoucherItems());
        }
    }

    @Override
    public void renderMoreEcVoucherInfo(EcVoucherInfo ecVoucherInfo) {
        if(ecVoucherInfo.getEcVoucherItems()!=null&&ecVoucherInfo.getEcVoucherItems().size()>0){
            refreshLayout.setCanLoadMore(true);
            ecVoucherAdapter.addMore(ecVoucherInfo.getEcVoucherItems());
        }else {
            refreshLayout.setCanLoadMore(false);
        }
    }

    @Override
    public void golookQrCode(String orderNum) {
        webViewDetailInfo = new WebViewDetailInfo();
        webViewDetailInfo.setTitle("二维码");
        String url= BuildConfig.API_BASE_URL+"home/actorder/qrcode?orderNum="+orderNum+"&Token="+ TokenManager.getToken();
        webViewDetailInfo.setUrl(url);
        WebViewActivity.start(this, webViewDetailInfo);
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
