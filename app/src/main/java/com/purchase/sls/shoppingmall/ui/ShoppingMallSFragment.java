package com.purchase.sls.shoppingmall.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.BaseFragment;
import com.purchase.sls.R;
import com.purchase.sls.common.refreshview.HeaderViewLayout;
import com.purchase.sls.common.widget.GradationScrollView;
import com.purchase.sls.common.widget.GridSpacesItemDecoration;
import com.purchase.sls.common.widget.newbanner.Banner;
import com.purchase.sls.common.widget.newbanner.GlideImageLoader;
import com.purchase.sls.common.widget.newbanner.listener.OnBannerListener;
import com.purchase.sls.data.entity.GoodsItemInfo;
import com.purchase.sls.data.entity.GoodsItemList;
import com.purchase.sls.data.entity.MallBannerInfo;
import com.purchase.sls.data.entity.MallCategoryInfo;
import com.purchase.sls.shoppingmall.DaggerShoppingMallComponent;
import com.purchase.sls.shoppingmall.ShoppingMallContract;
import com.purchase.sls.shoppingmall.ShoppingMallModule;
import com.purchase.sls.shoppingmall.adapter.GoodsItemAdapter;
import com.purchase.sls.shoppingmall.adapter.MallCategoryAdapter;
import com.purchase.sls.shoppingmall.presenter.ShoppingMallSPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/12/11.
 */

public class ShoppingMallSFragment extends BaseFragment implements ShoppingMallContract.ShoppingMallSView,OnBannerListener,MallCategoryAdapter.ItemClickListener,GoodsItemAdapter.OnItemClickListener {

    @BindView(R.id.search_tt)
    TextView searchTt;
    @BindView(R.id.search_ll)
    LinearLayout searchLl;
    @BindView(R.id.shopping_cart)
    ImageView shoppingCart;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.mallCategory_rv)
    RecyclerView mallCategoryRv;
    @BindView(R.id.recommend_rv)
    RecyclerView recommendRv;
    @BindView(R.id.scrollview)
    GradationScrollView scrollview;
    @BindView(R.id.refreshLayout)
    HeaderViewLayout refreshLayout;
    @Inject
    ShoppingMallSPresenter shoppingMallSPresenter;
    private MallCategoryAdapter mallCategoryAdapter;
    private List<String> bannerImages;
    private GoodsItemAdapter goodsItemAdapter;

    public static ShoppingMallSFragment newInstance() {
        ShoppingMallSFragment shoppingMallSFragment = new ShoppingMallSFragment();
        return shoppingMallSFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_shopping_malls, container, false);
        ButterKnife.bind(this, rootview);
        return rootview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHeight(searchLl, null, shoppingCart);
        initView();
    }

    private void initView(){
        refreshLayout.setOnRefreshListener(mOnRefreshListener);
        bannerInitialization();
        categoryAdapter();
        rdAdapter();
    }


    private boolean isFirstLoad = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isFirstLoad) {
            if (getUserVisibleHint()) {
                isFirstLoad = false;
                if (shoppingMallSPresenter != null) {
                    shoppingMallSPresenter.getMallBanner();
                    shoppingMallSPresenter.getMallCategory();
                    shoppingMallSPresenter.getGoodsItemInfo();
                }
            }
        }
    }

    //初始化banner
    private void bannerInitialization() {
        //简单使用
        banner.setImageLoader(new GlideImageLoader())
                .setOnBannerListener(this)
                .start();
    }


    HeaderViewLayout.OnRefreshListener mOnRefreshListener = new HeaderViewLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            shoppingMallSPresenter.getMallBanner();
            shoppingMallSPresenter.getMallCategory();
            shoppingMallSPresenter.getGoodsItemInfo();
        }

        @Override
        public void onLoadMore() {
            shoppingMallSPresenter.getMoreGoodsItemInfo();
        }

        @Override
        public void onModeChanged(@HeaderViewLayout.Mode int mode) {
        }
    };

    //设置热门
    private void categoryAdapter() {
        mallCategoryRv.setLayoutManager(new GridLayoutManager(getActivity(), 5));
        int space = 41;
        mallCategoryRv.addItemDecoration(new GridSpacesItemDecoration(space, false));
        mallCategoryAdapter = new MallCategoryAdapter(getActivity());
        mallCategoryAdapter.setOnItemClickListener(this);
        mallCategoryRv.setAdapter(mallCategoryAdapter);
    }

    private void rdAdapter(){
        goodsItemAdapter = new GoodsItemAdapter(getActivity());
        goodsItemAdapter.setOnItemClickListener(this);
        recommendRv.setAdapter(goodsItemAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void OnBannerClick(int position) {
    }

    @Override
    public void setPresenter(ShoppingMallContract.ShoppingMallSPresenter presenter) {

    }

    @Override
    public void returnCateId(String id,String name) {
        GoodsSearchItemActivity.start(getActivity(),"",id,name);
    }

    @Override
    public void renderMallBanner(List<MallBannerInfo> mallBannerInfos) {
        refreshLayout.stopRefresh();
        bannerImages = new ArrayList<>();
        if(mallBannerInfos!=null&&mallBannerInfos.size()>0) {
            for (MallBannerInfo mallBannerInfo : mallBannerInfos) {
                bannerImages.add(mallBannerInfo.getBanner());
            }
        }
        banner.update(bannerImages);
    }

    @Override
    public void renderMallCategory(List<MallCategoryInfo> mallCategoryInfos) {
        refreshLayout.stopRefresh();
        mallCategoryAdapter.setData(mallCategoryInfos);
    }

    @Override
    public void renderRdGoodsItems(GoodsItemList goodsItemList) {
        refreshLayout.stopRefresh();
        if(goodsItemList!=null){
            if(goodsItemList.getGoodsItemInfos()!=null&&goodsItemList.getGoodsItemInfos().size()>0){
                refreshLayout.setCanLoadMore(true);
            }else {
                refreshLayout.setCanLoadMore(false);
            }
            goodsItemAdapter.setData(goodsItemList.getGoodsItemInfos());
        }else {
            goodsItemAdapter.setData(null);
            refreshLayout.setCanLoadMore(false);
        }
    }

    @Override
    public void renderMoreRdGoodsItems(GoodsItemList goodsItemList) {
        refreshLayout.stopRefresh();
        if(goodsItemList!=null){
            if(goodsItemList.getGoodsItemInfos()!=null&&goodsItemList.getGoodsItemInfos().size()>0){
                goodsItemAdapter.addMore(goodsItemList.getGoodsItemInfos());
                refreshLayout.setCanLoadMore(true);
            }else {
                refreshLayout.setCanLoadMore(false);
            }
        }else {
            goodsItemAdapter.setData(null);
            refreshLayout.setCanLoadMore(false);
        }

    }

    @Override
    protected void initializeInjector() {
        DaggerShoppingMallComponent.builder()
                .applicationComponent(getApplicationComponent())
                .shoppingMallModule(new ShoppingMallModule(this))
                .build()
                .inject(this);
    }

    @OnClick({R.id.search_ll, R.id.shopping_cart})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_ll:
                GoodsSearchActivity.start(getActivity());
                break;
            case R.id.shopping_cart:
                ShoppingCartActivity.start(getActivity());
                break;
            default:
        }
    }

    @Override
    public void goGoodsDetail(String goodsid) {
        GoodsDetailActivity.start(getActivity(), goodsid);
    }
}
