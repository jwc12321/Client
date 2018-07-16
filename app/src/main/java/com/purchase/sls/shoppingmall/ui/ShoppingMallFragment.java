package com.purchase.sls.shoppingmall.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
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
import com.purchase.sls.common.widget.Banner.Banner;
import com.purchase.sls.common.widget.GridSpacesItemDecoration;
import com.purchase.sls.data.RemoteDataException;
import com.purchase.sls.data.entity.GoodsItemList;
import com.purchase.sls.data.entity.GoodsParentInfo;
import com.purchase.sls.data.entity.SMBannerInfo;
import com.purchase.sls.shoppingmall.DaggerShoppingMallComponent;
import com.purchase.sls.shoppingmall.ShoppingMallContract;
import com.purchase.sls.shoppingmall.ShoppingMallModule;
import com.purchase.sls.shoppingmall.adapter.GoodsItemAdapter;
import com.purchase.sls.shoppingmall.adapter.GoodsParentAdapter;
import com.purchase.sls.shoppingmall.presenter.GoodsListPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/4/19.
 */

public class ShoppingMallFragment extends BaseFragment implements ShoppingMallContract.GoodsListView, GoodsParentAdapter.OnParentClickListener, GoodsItemAdapter.OnItemClickListener {
    @BindView(R.id.shopping_cart)
    ImageView shoppingCart;
    @BindView(R.id.search_tt)
    TextView searchTt;
    @BindView(R.id.search_ll)
    LinearLayout searchLl;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.sort_sv_tv)
    TextView sortSvTv;
    @BindView(R.id.sort_sv_iv)
    ImageView sortSvIv;
    @BindView(R.id.sort_sv_ll)
    LinearLayout sortSvLl;
    @BindView(R.id.sort_v_tt)
    TextView sortVTt;
    @BindView(R.id.sort_v_iv)
    ImageView sortVIv;
    @BindView(R.id.sort_v_ll)
    LinearLayout sortVLl;
    @BindView(R.id.sort_p_tt)
    TextView sortPTt;
    @BindView(R.id.sort_p_iv)
    ImageView sortPIv;
    @BindView(R.id.sort_p_ll)
    LinearLayout sortPLl;
    @BindView(R.id.goods_parent_rv)
    RecyclerView goodsParentRv;
    @BindView(R.id.goods_item_rv)
    RecyclerView goodsItemRv;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.empty_view)
    NestedScrollView emptyView;
    @BindView(R.id.refreshLayout)
    HeaderViewLayout refreshLayout;

    private List<String> bannerImages;

    private int sortSVint = 0;
    private int sortVint = 0;
    private int sortPint = 0;

    private String sortType = "1";//排序类型 1销量2价格3券
    private String sortUd = "2";//排序规则 1小到大/ 2大到小
    private String sortId;
    private String sortKeyword;

    private int lastSelectPosition = 0;
    private String firstIn = "1";

    @Inject
    GoodsListPresenter goodsListPresenter;

    private GoodsItemAdapter goodsItemAdapter;
    private GoodsParentAdapter goodsParentAdapter;

    public ShoppingMallFragment() {
    }

    public static ShoppingMallFragment newInstance() {
        ShoppingMallFragment shoppingMallFragment = new ShoppingMallFragment();
        return shoppingMallFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_shopping_mall, container, false);
        ButterKnife.bind(this, rootview);
        return rootview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHeight(searchLl, null, shoppingCart);
        initView();
    }

    private void initView() {
        refreshLayout.setOnRefreshListener(mOnRefreshListener);
        goodsParentAdapter = new GoodsParentAdapter();
        goodsParentAdapter.setOnParentClickListener(this);
        goodsParentRv.setAdapter(goodsParentAdapter);
        goodsItemRv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        int space = 20;
        goodsItemRv.addItemDecoration(new GridSpacesItemDecoration(space, false));
        goodsItemAdapter = new GoodsItemAdapter(getActivity());
        goodsItemAdapter.setOnItemClickListener(this);
        goodsItemRv.setAdapter(goodsItemAdapter);
    }


    private boolean isFirstLoad = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isFirstLoad) {
            if (getUserVisibleHint()) {
                isFirstLoad = false;
                if (goodsListPresenter != null) {
                    goodsListPresenter.getSMBannerInfo();
                    goodsListPresenter.getGoodsParents();
                }
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (!isFirstLoad && getUserVisibleHint() && TextUtils.equals("0", firstIn)) {
            if (goodsListPresenter != null) {
                goodsListPresenter.getSMBannerInfo();
                goodsListPresenter.getGoodsParents();
            }
            firstIn = "1";
        }
    }

    @Override
    public void showError(Throwable e) {
        if (e != null && e instanceof RemoteDataException && ((RemoteDataException) e).isAuthFailed()) {
            firstIn="0";
        }
        super.showError(e);
    }

    @Override
    protected void initializeInjector() {
        DaggerShoppingMallComponent.builder()
                .applicationComponent(getApplicationComponent())
                .shoppingMallModule(new ShoppingMallModule(this))
                .build()
                .inject(this);
    }


    HeaderViewLayout.OnRefreshListener mOnRefreshListener = new HeaderViewLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            goodsListPresenter.getGoodsItems("0", sortKeyword, sortType, sortUd, sortId);
        }

        @Override
        public void onLoadMore() {
            goodsListPresenter.getMoreGoodsItems(sortKeyword, sortType, sortUd, sortId);
        }

        @Override
        public void onModeChanged(@HeaderViewLayout.Mode int mode) {
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.sort_sv_ll, R.id.sort_v_ll, R.id.sort_p_ll, R.id.search_ll, R.id.shopping_cart})
    public void onClick(View view) {
        switch (view.getId()) {
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
            case R.id.search_ll:
                GoodsSearchActivity.start(getActivity());
                break;
            case R.id.shopping_cart:
                ShoppingCartActivity.start(getActivity());
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
            goodsListPresenter.getGoodsItems("1", sortKeyword, sortType, sortUd, sortId);
        } else if (number % 2 == 1) {
            iv.setBackgroundResource(R.mipmap.sort_down_icon);
            sortUd = "2";
            goodsListPresenter.getGoodsItems("1", sortKeyword, sortType, sortUd, sortId);
        }
    }


    @Override
    public void setPresenter(ShoppingMallContract.GoodsListPresenter presenter) {

    }

    @Override
    public void smBannerInfo(List<SMBannerInfo> smBannerInfos) {
        if (smBannerInfos != null && smBannerInfos.size() > 0) {
            banner.setVisibility(View.VISIBLE);
            bannerImages = new ArrayList<>();
            for (SMBannerInfo smBannerInfo : smBannerInfos) {
                bannerImages.add(smBannerInfo.getBanner());
            }
            banner.setImages(bannerImages);
        } else {
            banner.setVisibility(View.GONE);
        }
    }

    @Override
    public void renderGoodsParents(List<GoodsParentInfo> goodsParentInfos) {
        if (goodsParentInfos != null && goodsParentInfos.size() > 0) {
            goodsParentAdapter.setData(goodsParentInfos);
            sortId = goodsParentInfos.get(0).getId();
            goodsListPresenter.getGoodsItems("0", sortKeyword, sortType, sortUd, sortId);
        } else {
            dismissLoading();
        }
    }

    @Override
    public void renderGoodsItems(GoodsItemList goodsItemList) {
        refreshLayout.stopRefresh();
        if (goodsItemList != null && goodsItemList.getGoodsItemInfos() != null && goodsItemList.getGoodsItemInfos().size() > 0) {
            refreshLayout.setCanLoadMore(true);
            goodsItemAdapter.setData(goodsItemList.getGoodsItemInfos());
        } else {
            refreshLayout.setCanLoadMore(false);
        }
    }

    @Override
    public void renderMoreGoodsItems(GoodsItemList goodsItemList) {
        refreshLayout.stopRefresh();
        if (goodsItemList != null && goodsItemList.getGoodsItemInfos() != null && goodsItemList.getGoodsItemInfos().size() > 0) {
            refreshLayout.setCanLoadMore(true);
            goodsItemAdapter.addMore(goodsItemList.getGoodsItemInfos());
        } else {
            refreshLayout.setCanLoadMore(false);
        }
    }

    @Override
    public void choiceWhat(int position, String goodsid) {
        goodsParentAdapter.setPosittion(lastSelectPosition, position);
        lastSelectPosition = position;
        sortId = goodsid;
        goodsListPresenter.getGoodsItems("1", sortKeyword, sortType, sortUd, sortId);
    }

    @Override
    public void goGoodsDetail(String goodsid) {
        GoodsDetailActivity.start(getActivity(), goodsid);
    }
}
