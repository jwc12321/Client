package com.purchase.sls.collection.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.collection.CollectionContract;
import com.purchase.sls.collection.CollectionModule;
import com.purchase.sls.collection.DaggerCollectionComponent;
import com.purchase.sls.collection.adapter.CollectionListAdapter;
import com.purchase.sls.collection.presenter.CollectionListPresenter;
import com.purchase.sls.common.refreshview.HeaderViewLayout;
import com.purchase.sls.common.unit.CommonAppPreferences;
import com.purchase.sls.data.entity.CollectionListResponse;
import com.purchase.sls.shopdetailbuy.ui.ShopDetailActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/4/27.
 * 收藏列表
 */

public class CollectionListActivity extends BaseActivity implements CollectionContract.CollectionView, CollectionListAdapter.OnCollectionItemClickListener {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.edit)
    TextView edit;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.collection_rv)
    RecyclerView collectionRv;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.empty_view)
    NestedScrollView emptyView;
    @BindView(R.id.refreshLayout)
    HeaderViewLayout refreshLayout;
    @BindView(R.id.delete_bg)
    Button deleteBg;

    @Inject
    CollectionListPresenter collectionListPresenter;
    @BindView(R.id.delete_ll)
    LinearLayout deleteLl;

    private CollectionListAdapter collectionListAdapter;
    private int choiceEditInt = 0;
    private List<String> removeList;
    private String[] removeArray;
    private CommonAppPreferences commonAppPreferences;

    private String city;
    private String longitude;
    private String latitude;

    public static void start(Context context) {
        Intent intent = new Intent(context, CollectionListActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_list);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        commonAppPreferences=new CommonAppPreferences(this);
        city=commonAppPreferences.getCity();
        longitude=commonAppPreferences.getLongitude();
        latitude=commonAppPreferences.getLatitude();
        removeList = new ArrayList<>();
        refreshLayout.setOnRefreshListener(mOnRefreshListener);
        collectionListAdapter = new CollectionListAdapter(this,city,longitude,latitude);
        collectionListAdapter.setOnCollectionItemClickListener(this);
        collectionRv.setAdapter(collectionListAdapter);
        collectionListPresenter.getCollectionListInfo();

    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        DaggerCollectionComponent.builder()
                .applicationComponent(getApplicationComponent())
                .collectionModule(new CollectionModule(this))
                .build()
                .inject(this);
    }


    HeaderViewLayout.OnRefreshListener mOnRefreshListener = new HeaderViewLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            collectionListPresenter.getCollectionListInfo();
        }

        @Override
        public void onLoadMore() {
            collectionListPresenter.getMoreCollectionListInfo();
        }

        @Override
        public void onModeChanged(@HeaderViewLayout.Mode int mode) {
        }
    };

    @Override
    public void setPresenter(CollectionContract.CollectionPresenter presenter) {

    }


    @Override
    public void collectionListInfo(CollectionListResponse collectionListResponse) {
        refreshLayout.stopRefresh();
        if (collectionListResponse != null && collectionListResponse.getCollectionInfos() != null && collectionListResponse.getCollectionInfos().size() > 0) {
            emptyView.setVisibility(View.GONE);
            collectionRv.setVisibility(View.VISIBLE);
            collectionListAdapter.setData(collectionListResponse.getCollectionInfos());
            refreshLayout.setCanLoadMore(true);
        } else {
            collectionRv.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            choiceEditInt = 0;
            edit.setText("编辑");
            collectionListAdapter.setType("1");
            deleteLl.setVisibility(View.GONE);
            setMargins(collectionRv,0,0,0,0);
            removeList.clear();
            refreshLayout.setCanLoadMore(false);
        }
    }

    @Override
    public void moreCollectionListInfo(CollectionListResponse collectionListResponse) {
        refreshLayout.stopRefresh();
        if (collectionListResponse != null && collectionListResponse.getCollectionInfos() != null && collectionListResponse.getCollectionInfos().size() > 0) {
            refreshLayout.setCanLoadMore(true);
            collectionListAdapter.addMore(collectionListResponse.getCollectionInfos());
        } else {
            refreshLayout.setCanLoadMore(false);
        }
    }

    @Override
    public void addRemoveSuccess() {
        collectionListPresenter.getCollectionListInfo();
    }

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
                    collectionListAdapter.setType("1");
                    deleteLl.setVisibility(View.GONE);
                    removeList.clear();
                    setMargins(collectionRv,0,0,0,0);
                } else {
                    edit.setText("完成");
                    collectionListAdapter.setType("2");
                    deleteLl.setVisibility(View.VISIBLE);
                    setMargins(collectionRv,0,0,0,70);
                }
                break;
            case R.id.delete_bg:
                removeArray = new String[removeList.size()];
                removeList.toArray(removeArray);
                collectionListPresenter.addRemoveCollection("", "2", removeArray);
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
        ShopDetailActivity.start(this, storeid);
    }


    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }
}
