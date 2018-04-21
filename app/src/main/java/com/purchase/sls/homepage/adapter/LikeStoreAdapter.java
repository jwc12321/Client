package com.purchase.sls.homepage.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.purchase.sls.R;
import com.purchase.sls.common.GlideHelper;
import com.purchase.sls.data.entity.LikeStoreResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/4/21.
 */

public class LikeStoreAdapter extends RecyclerView.Adapter<LikeStoreAdapter.LikeStoreView> {

    private LayoutInflater layoutInflater;
    private List<LikeStoreResponse.likeInfo> likeInfos;
    private static Context context;

    public LikeStoreAdapter(Context context) {
        this.context = context;
    }

    public void setLikeInfos(List<LikeStoreResponse.likeInfo> likeInfos) {
        this.likeInfos = likeInfos;
        notifyDataSetChanged();
    }

    public void addMore(List<LikeStoreResponse.likeInfo> moreList) {
        int pos = likeInfos.size();
        likeInfos.addAll(moreList);
        notifyItemRangeInserted(pos, moreList.size());
    }

    @Override
    public LikeStoreView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_like_store, parent, false);
        return new LikeStoreView(view);
    }

    @Override
    public void onBindViewHolder(LikeStoreView holder, int position) {
        LikeStoreResponse.likeInfo likeInfo = likeInfos.get(holder.getAdapterPosition());
        holder.bindData(likeInfo);

    }

    @Override
    public int getItemCount() {
        return likeInfos == null ? 0 : likeInfos.size();
    }

    public class LikeStoreView extends RecyclerView.ViewHolder {
        @BindView(R.id.shop_icon)
        ImageView shopIcon;
        @BindView(R.id.store_name)
        TextView storeName;
        @BindView(R.id.shop_name)
        TextView shopName;
        @BindView(R.id.shop_city)
        TextView shopCity;
        @BindView(R.id.shop_distance)
        TextView shopDistance;
        @BindView(R.id.shop_consumption)
        TextView shopConsumption;
        public LikeStoreView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(LikeStoreResponse.likeInfo likeInfo) {
            GlideHelper.load((Activity) context, likeInfo.getzPics(), R.mipmap.client_v330_ic_homepage_circle_1, shopIcon);
            storeName.setText(likeInfo.getTitle());
            LikeStoreResponse.likeInfo.BcateInfo bcateInfo=likeInfo.getBcateInfo();
            shopName.setText(bcateInfo.getName());
        }
    }
}
