package com.purchase.sls.shoppingmall.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.common.refreshview.HeaderViewLayout;
import com.purchase.sls.data.entity.GoodsItemList;
import com.purchase.sls.shoppingmall.DaggerShoppingMallComponent;
import com.purchase.sls.shoppingmall.ShoppingMallContract;
import com.purchase.sls.shoppingmall.ShoppingMallModule;
import com.purchase.sls.shoppingmall.adapter.GoodsSearchItemAdapter;
import com.purchase.sls.shoppingmall.presenter.GoodsSearchPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/7/5.
 */

public class GoodsSearchItemActivity extends BaseActivity implements ShoppingMallContract.GoodsSearchView, GoodsSearchItemAdapter.OnItemClickListener{
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.sort_sv_tv)
    TextView sortSvTv;
    @BindView(R.id.sort_sv_iv)
    ImageView sortSvIv;
    @BindView(R.id.sort_sv_ll)
    LinearLayout sortSvLl;
    @BindView(R.id.sort_p_tt)
    TextView sortPTt;
    @BindView(R.id.sort_p_iv)
    ImageView sortPIv;
    @BindView(R.id.sort_p_ll)
    LinearLayout sortPLl;
    @BindView(R.id.sort_v_tt)
    TextView sortVTt;
    @BindView(R.id.sort_v_iv)
    ImageView sortVIv;
    @BindView(R.id.sort_v_ll)
    LinearLayout sortVLl;
    @BindView(R.id.goods_item_rv)
    RecyclerView goodsItemRv;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.empty_view)
    NestedScrollView emptyView;
    @BindView(R.id.refreshLayout)
    HeaderViewLayout refreshLayout;

    private int sortSVint = 0;
    private int sortVint = 0;
    private int sortPint = 0;

    private String sortType = "1";//排序类型 1销量2价格3券
    private String sortUd = "2";//排序规则 1小到大/ 2大到小
    private String sortId;
    private String sortKeyword;

    private GoodsSearchItemAdapter goodsSearchItemAdapter;
    @Inject
    GoodsSearchPresenter goodsSearchPresenter;

    public static void start(Context context,String searchGoods) {
        Intent intent = new Intent(context, GoodsSearchItemActivity.class);
        intent.putExtra(StaticData.SEARCH_GOODS,searchGoods);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_search_item);
        ButterKnife.bind(this);
        setHeight(back,title,null);
        initView();
    }


    private void initView() {
        sortKeyword=getIntent().getStringExtra(StaticData.SEARCH_GOODS);
        title.setText(sortKeyword);
        refreshLayout.setOnRefreshListener(mOnRefreshListener);
        goodsItemRv.setLayoutManager(new GridLayoutManager(this, 2));
        goodsSearchItemAdapter = new GoodsSearchItemAdapter(this);
        goodsSearchItemAdapter.setOnItemClickListener(this);
        goodsItemRv.setAdapter(goodsSearchItemAdapter);
        goodsSearchPresenter.getGoodsItems("1", sortKeyword, sortType, sortUd, sortId);
    }


    HeaderViewLayout.OnRefreshListener mOnRefreshListener = new HeaderViewLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            goodsSearchPresenter.getGoodsItems("0", sortKeyword, sortType, sortUd, sortId);
        }

        @Override
        public void onLoadMore() {
            goodsSearchPresenter.getMoreGoodsItems(sortKeyword, sortType, sortUd, sortId);
        }

        @Override
        public void onModeChanged(@HeaderViewLayout.Mode int mode) {
        }
    };

    @Override
    protected void initializeInjector() {
        DaggerShoppingMallComponent.builder()
                .applicationComponent(getApplicationComponent())
                .shoppingMallModule(new ShoppingMallModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void setPresenter(ShoppingMallContract.GoodsSearchPresenter presenter) {

    }

    @Override
    public void renderGoodsItems(GoodsItemList goodsItemList) {
        refreshLayout.stopRefresh();
        if (goodsItemList != null && goodsItemList.getGoodsItemInfos() != null && goodsItemList.getGoodsItemInfos().size() > 0) {
            goodsItemRv.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            refreshLayout.setCanLoadMore(true);
            goodsSearchItemAdapter.setData(goodsItemList.getGoodsItemInfos());
        } else {
            goodsItemRv.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            refreshLayout.setCanLoadMore(false);
        }
    }

    @Override
    public void renderMoreGoodsItems(GoodsItemList goodsItemList) {
        refreshLayout.stopRefresh();
        if (goodsItemList != null && goodsItemList.getGoodsItemInfos() != null && goodsItemList.getGoodsItemInfos().size() > 0) {
            refreshLayout.setCanLoadMore(true);
            goodsSearchItemAdapter.addMore(goodsItemList.getGoodsItemInfos());
        } else {
            refreshLayout.setCanLoadMore(false);
        }
    }

    @Override
    public void goGoodsDetail(String goodsid) {
        GoodsDetailActivity.start(this, goodsid);
    }


    @OnClick({R.id.back ,R.id.sort_sv_ll, R.id.sort_v_ll, R.id.sort_p_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.sort_sv_ll://销量
                sortType = "1";
                changeTextColor(sortType);
                sortSVint += 1;
                changeSort(sortSvIv, sortSVint);
                break;
            case R.id.sort_p_ll://价格
                sortType = "2";
                changeTextColor(sortType);
                sortPint += 1;
                changeSort(sortPIv, sortPint);
                break;
            case R.id.sort_v_ll://劵额
                sortType = "3";
                changeTextColor(sortType);
                sortVint += 1;
                changeSort(sortVIv, sortVint);
                break;
            default:
        }
    }

    //改变点击分类。字体颜色改变，另外两个重置
    private void changeTextColor(String type) {
        sortSvTv.setSelected(TextUtils.equals("1", type));
        sortPTt.setSelected(TextUtils.equals("2", type));
        sortVTt.setSelected(TextUtils.equals("3", type));
        if (TextUtils.equals("1", type)) {
            sortVIv.setBackgroundResource(R.mipmap.sort_no_icon);
            sortPIv.setBackgroundResource(R.mipmap.sort_no_icon);
            sortVint = 0;
            sortPint = 0;
        } else if (TextUtils.equals("2", type)) {
            sortSvIv.setBackgroundResource(R.mipmap.sort_no_icon);
            sortVIv.setBackgroundResource(R.mipmap.sort_no_icon);
            sortSVint = 0;
            sortVint = 0;
        } else {
            sortSvIv.setBackgroundResource(R.mipmap.sort_no_icon);
            sortPIv.setBackgroundResource(R.mipmap.sort_no_icon);
            sortSVint = 0;
            sortPint = 0;
        }
    }

    //改变排序up和down
    private void changeSort(ImageView iv, int number) {
        if (number % 2 == 0) {
            iv.setBackgroundResource(R.mipmap.sort_up_icon);
            sortUd = "1";
            goodsSearchPresenter.getGoodsItems("1", sortKeyword, sortType, sortUd, sortId);
        } else if (number % 2 == 1) {
            iv.setBackgroundResource(R.mipmap.sort_down_icon);
            sortUd = "2";
            goodsSearchPresenter.getGoodsItems("1", sortKeyword, sortType, sortUd, sortId);
        }
    }
}
