package com.purchase.sls.homepage.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.purchase.sls.R;
import com.purchase.sls.common.GlideHelper;
import com.purchase.sls.data.entity.CollectionStoreInfo;
import com.purchase.sls.data.entity.LikeStoreResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/4/21.
 */

public class LikeStoreAdapter extends RecyclerView.Adapter<LikeStoreAdapter.LikeStoreView> {

    private LayoutInflater layoutInflater;
    private List<CollectionStoreInfo> collectionStoreInfos;
    private static Context context;

    public LikeStoreAdapter(Context context) {
        this.context = context;
    }

    public void setLikeInfos(List<CollectionStoreInfo> collectionStoreInfos) {
        this.collectionStoreInfos = collectionStoreInfos;
        notifyDataSetChanged();
    }

    public void addMore(List<CollectionStoreInfo> moreList) {
        int pos = collectionStoreInfos.size();
        collectionStoreInfos.addAll(moreList);
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
        final CollectionStoreInfo collectionStoreInfo = collectionStoreInfos.get(holder.getAdapterPosition());
        holder.bindData(collectionStoreInfo);
        holder.likestoreLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onLikeStoreClickListener!=null){
                    onLikeStoreClickListener.likeStoreClickListener(collectionStoreInfo.getId());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return collectionStoreInfos == null ? 0 : collectionStoreInfos.size();
    }

    public class LikeStoreView extends RecyclerView.ViewHolder {
        @BindView(R.id.likestore_ll)
        LinearLayout likestoreLl;
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

        public void bindData(CollectionStoreInfo collectionStoreInfo) {
            GlideHelper.load((Activity) context, collectionStoreInfo.getzPics(), R.mipmap.client_v330_ic_homepage_circle_1, shopIcon);
            storeName.setText(collectionStoreInfo.getTitle());
            shopName.setText(collectionStoreInfo.getName());
        }
    }

    public interface OnLikeStoreClickListener {
        void likeStoreClickListener(String storeid);
    }

    private OnLikeStoreClickListener onLikeStoreClickListener;

    public void setOnLikeStoreClickListener(OnLikeStoreClickListener onLikeStoreClickListener) {
        this.onLikeStoreClickListener = onLikeStoreClickListener;
    }
}
