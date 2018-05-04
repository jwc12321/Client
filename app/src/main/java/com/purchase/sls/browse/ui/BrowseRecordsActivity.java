package com.purchase.sls.browse.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.browse.BrowseContract;
import com.purchase.sls.browse.BrowseModule;
import com.purchase.sls.browse.DaggerBrowseComponent;
import com.purchase.sls.browse.adapter.BrowseRecordsAdapter;
import com.purchase.sls.browse.presenter.BrowsePresenter;
import com.purchase.sls.collection.ui.CollectionListActivity;
import com.purchase.sls.common.refreshview.HeaderViewLayout;
import com.purchase.sls.data.entity.BrowseInfo;
import com.purchase.sls.shopdetailbuy.ui.ShopDetailActivity;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/5/4.
 * 浏览记录页面
 */

public class BrowseRecordsActivity extends BaseActivity implements BrowseContract.BrowseView,BrowseRecordsAdapter.OnBrowseItemClickListener{
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.edit)
    TextView edit;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.browse_records_rv)
    RecyclerView browseRecordsRv;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.empty_view)
    NestedScrollView emptyView;
    @BindView(R.id.refreshLayout)
    HeaderViewLayout refreshLayout;
    @BindView(R.id.delete_bg)
    Button deleteBg;

    @Inject
    BrowsePresenter browsePresenter;

    private BrowseRecordsAdapter browseRecordsAdapter;
    private int choiceEditInt = 0;
    private List<String> removeList;
    private String[] removeArray;

    public static void start(Context context) {
        Intent intent = new Intent(context, BrowseRecordsActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_records);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        removeList = new ArrayList<>();
        refreshLayout.setOnRefreshListener(mOnRefreshListener);
        browseRecordsAdapter = new BrowseRecordsAdapter(this);
        browseRecordsAdapter.setOnBrowseItemClickListener(this);
        browseRecordsRv.setAdapter(browseRecordsAdapter);
        browsePresenter.getBrowseInfo();

    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        DaggerBrowseComponent.builder()
                .applicationComponent(getApplicationComponent())
                .browseModule(new BrowseModule(this))
                .build()
                .inject(this);
    }


    HeaderViewLayout.OnRefreshListener mOnRefreshListener = new HeaderViewLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            browsePresenter.getBrowseInfo();
        }

        @Override
        public void onLoadMore() {
            browsePresenter.getMoreBrowseInfo();
        }

        @Override
        public void onModeChanged(@HeaderViewLayout.Mode int mode) {
        }
    };

    @OnClick({R.id.back, R.id.edit, R.id.delete_bg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.edit:
                choiceEditInt += 1;
                if (choiceEditInt % 2 == 0) {
                    edit.setText("编辑");
                    browseRecordsAdapter.setType("1");
                    deleteBg.setVisibility(View.GONE);
                    removeList.clear();
                } else {
                    edit.setText("完成");
                    browseRecordsAdapter.setType("2");
                    deleteBg.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.delete_bg:
                removeArray = new String[removeList.size()];
                removeList.toArray(removeArray);
                browsePresenter.removeBrowse(removeArray);
                break;
        }
    }

    @Override
    public void addItem(String id) {
        removeList.add(id);
        if (removeList.size() > 0) {
            deleteBg.setEnabled(true);
        } else {
            deleteBg.setEnabled(false);
        }
    }

    @Override
    public void removeItem(String id) {
        removeList.remove(id);
        if (removeList.size() > 0) {
            deleteBg.setEnabled(true);
        } else {
            deleteBg.setEnabled(false);
        }
    }

    @Override
    public void goShopDetail(String storeid) {
        ShopDetailActivity.start(this,storeid);
    }

    @Override
    public void setPresenter(BrowseContract.BrowsePresenter presenter) {

    }

    @Override
    public void renderBrowseInfo(BrowseInfo browseInfo) {
        refreshLayout.stopRefresh();
        if (browseInfo != null && browseInfo.getBrowseItemInfos() != null && browseInfo.getBrowseItemInfos().size() > 0) {
            emptyView.setVisibility(View.GONE);
            browseRecordsRv.setVisibility(View.VISIBLE);
            browseRecordsAdapter.setData(browseInfo.getBrowseItemInfos());
            refreshLayout.setCanLoadMore(true);
        } else {
            browseRecordsRv.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            choiceEditInt=0;
            edit.setText("编辑");
            browseRecordsAdapter.setType("1");
            deleteBg.setVisibility(View.GONE);
            removeList.clear();
            refreshLayout.setCanLoadMore(false);
        }
    }

    @Override
    public void renderMoreBrowseInfo(BrowseInfo browseInfo) {
        refreshLayout.stopRefresh();
        if (browseInfo != null && browseInfo.getBrowseItemInfos() != null &&browseInfo.getBrowseItemInfos().size() > 0) {
            refreshLayout.setCanLoadMore(true);
            browseRecordsAdapter.addMore(browseInfo.getBrowseItemInfos());
        } else {
            refreshLayout.setCanLoadMore(false);
        }
    }

    @Override
    public void removeSuccess() {
        browsePresenter.getBrowseInfo();
    }
}
