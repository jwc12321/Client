package com.purchase.sls.address.ui;

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
import com.purchase.sls.address.AddressContract;
import com.purchase.sls.address.AddressModule;
import com.purchase.sls.address.DaggerAddressComponent;
import com.purchase.sls.address.adapter.AddressListAdapter;
import com.purchase.sls.address.presenter.AddressListPresenter;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.common.refreshview.HeaderViewLayout;
import com.purchase.sls.data.entity.AddressInfo;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/5/29.
 */

public class AddressListActivity extends BaseActivity implements AddressContract.AddressListView, AddressListAdapter.OnEventClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.add_address)
    TextView addAddress;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.address_rv)
    RecyclerView addressRv;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.empty_view)
    NestedScrollView emptyView;
    @BindView(R.id.refreshLayout)
    HeaderViewLayout refreshLayout;

    private String backAddressStr;//0：不返回 1：返回
    @Inject
    AddressListPresenter addressListPresenter;

    private AddressListAdapter addressListAdapter;

    public static void start(Context context, String type) {
        Intent intent = new Intent(context, AddressListActivity.class);
        intent.putExtra(StaticData.BACK_ADDRESS, type);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);
        ButterKnife.bind(this);
        setHeight(back, title, addAddress);
        initView();
    }

    private void initView() {
        backAddressStr = getIntent().getStringExtra(StaticData.BACK_ADDRESS);
        refreshLayout.setOnRefreshListener(mOnRefreshListener);
        addAdapter();
    }

    private void addAdapter() {
        addressListAdapter = new AddressListAdapter();
        addressListAdapter.setOnEventClickListener(this);
        addressRv.setAdapter(addressListAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        addressListPresenter.getAddressList("1");
    }


    HeaderViewLayout.OnRefreshListener mOnRefreshListener = new HeaderViewLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            addressListPresenter.getAddressList("0");
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
        DaggerAddressComponent.builder()
                .applicationComponent(getApplicationComponent())
                .addressModule(new AddressModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void setPresenter(AddressContract.AddressListPresenter presenter) {

    }

    @Override
    public void renderAddressList(List<AddressInfo> addressInfos) {
        refreshLayout.stopRefresh();
        addressListAdapter.setData(addressInfos);
    }

    @Override
    public void defaultSuccess() {
        addressListPresenter.getAddressList("1");
    }

    @Override
    public void deleteSuccess() {
        addressListPresenter.getAddressList("1");
    }

    @Override
    public void checkDfAdderss(String id) {
        addressListPresenter.setDefault(id);
    }

    @Override
    public void deleteAddress(String id) {
        addressListPresenter.deleteAddress(id);
    }

    @Override
    public void editAddress(AddressInfo addressInfo) {
        AddAddressActivity.start(this, "0", addressInfo);
    }

    @Override
    public void backAddress(AddressInfo addressInfo) {
        if(TextUtils.equals("1",backAddressStr)) {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable(StaticData.CHOICE_ADDRESS, addressInfo);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @OnClick({R.id.back, R.id.add_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.add_address:
                AddAddressActivity.start(this, "1", null);
                break;
            default:
        }
    }
}
