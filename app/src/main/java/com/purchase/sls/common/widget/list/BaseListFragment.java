package com.purchase.sls.common.widget.list;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.purchase.sls.BaseFragment;
import com.purchase.sls.R;
import com.purchase.sls.common.decoration.SimpleItemDecoration;
import com.purchase.sls.common.refreshview.HeaderViewLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 公共的下拉刷新
 */
public abstract class BaseListFragment<T> extends BaseFragment implements HeaderViewLayout.OnRefreshListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.empty_content)
    TextView emptyContent;
    @BindView(R.id.empty_view)
    NestedScrollView emptyView;
    @BindView(R.id.refreshLayout)
    HeaderViewLayout refresh;
    private boolean moreLoadable = false;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(getContentRes(), container, false);
        ButterKnife.bind(this, view);
        refresh.setOnRefreshListener(this);
//        recyclerView.addItemDecoration(getItemDecoration());
        return view;
    }

    public interface OnListIsEmpty {
        void listIsEmpty();
    }

    private OnListIsEmpty onListIsEmpty;

    public void setOnListIsEmpty(OnListIsEmpty onListIsEmpty) {
        this.onListIsEmpty = onListIsEmpty;
    }

    protected int getContentRes() {
        return R.layout.fragment_with_list;
    }

    @SuppressWarnings("unchecked")
    public void render(@Nullable List<T> list) {
//        if (view != null){
        if(refresh!=null) {
            refresh.stopRefresh();
        }
        if (list == null || list.isEmpty()) {
            if (onListIsEmpty != null) {
                onListIsEmpty.listIsEmpty();
            }
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
//            setEmptyView(R.drawable.order, "当前没有订单，赶快去看看需要什么服务吧");
            if (moreLoadable) {
                refresh.setCanLoadMore(false);
            }
        } else {
            if (moreLoadable) {
                refresh.setCanLoadMore(true);
            }
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            if (adapter == null) {
                adapter = initAdapter(list);
                recyclerView.setAdapter(adapter);
            }
//            else {
//                ((Refreshable<T>) adapter).refresh(list);
//            }
            ((Refreshable<T>) adapter).refresh(list);
        }
//        }

    }

    @SuppressWarnings("unchecked")
    public void renderMore(@Nullable List<T> list) {
        refresh.stopRefresh();
        refresh.setCanLoadMore(!(list == null || list.isEmpty()));
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        ((MoreLoadable<T>) adapter).addMore(list);
    }

    public abstract RecyclerView.Adapter initAdapter(List<T> list);


    public void setEmptyView(@DrawableRes int resId, @StringRes int msg) {
        emptyContent.setText(msg);
        emptyContent.setCompoundDrawablesWithIntrinsicBounds(0, resId, 0, 0);
    }

    public void setEmptyView(@DrawableRes int resId, String msg) {
        emptyContent.setText(msg);
        emptyContent.setCompoundDrawablesWithIntrinsicBounds(0, resId, 0, 0);
    }

    protected RecyclerView getRecyclerView() {
        return recyclerView;
    }

    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new SimpleItemDecoration(getContext(), SimpleItemDecoration.VERTICAL_LIST);
    }

    public void showEmpty() {
        recyclerView.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
    }

    public void setMoreLoadable(boolean moreLoadable) {
        this.moreLoadable = moreLoadable;
        refresh.setCanLoadMore(moreLoadable);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        ButterKnife.unbind(this);
    }


    @Override
    public void onLoadMore() {

    }

    @Override
    public void onModeChanged(@HeaderViewLayout.Mode int mode) {

    }

    /**
     * 网络发生变化时刻调用
     */
    public void notifyNetWorkChange(boolean isConnected) {
    }
}
