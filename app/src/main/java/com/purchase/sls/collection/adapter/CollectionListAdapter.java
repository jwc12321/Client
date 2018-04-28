package com.purchase.sls.collection.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.purchase.sls.R;
import com.purchase.sls.common.GlideHelper;
import com.purchase.sls.data.entity.CollectionListInfo;
import com.purchase.sls.data.entity.CollectionStoreInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/4/21.
 */

public class CollectionListAdapter extends RecyclerView.Adapter<CollectionListAdapter.CollectionListView> {

    private LayoutInflater layoutInflater;
    private List<CollectionListInfo> collectionListInfos;
    private static Context context;
    private String behavior = "1";//1：正常状态2：有选中删除选项

    public CollectionListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<CollectionListInfo> collectionListInfos) {
        this.collectionListInfos = collectionListInfos;
        notifyDataSetChanged();
    }

    public void addMore(List<CollectionListInfo> moreList) {
        int pos = collectionListInfos.size();
        collectionListInfos.addAll(moreList);
        notifyItemRangeInserted(pos, moreList.size());
    }

    public void setType(String behavior) {
        this.behavior = behavior;
        notifyDataSetChanged();
    }

    @Override
    public CollectionListView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_collection_list, parent, false);
        return new CollectionListView(view);
    }

    @Override
    public void onBindViewHolder(CollectionListView holder, int position) {
        final CollectionListInfo collectionListInfo = collectionListInfos.get(holder.getAdapterPosition());
        holder.bindData(collectionListInfo);
        holder.choiceItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (onCollectionItemClickListener != null) {
                    if (isChecked) {
                        onCollectionItemClickListener.addItem(collectionListInfo.getId());
                    } else {
                        onCollectionItemClickListener.removeItem(collectionListInfo.getId());
                    }
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return collectionListInfos == null ? 0 : collectionListInfos.size();
    }

    public class CollectionListView extends RecyclerView.ViewHolder {
        @BindView(R.id.collection_item_ll)
        LinearLayout collectionItemLl;
        @BindView(R.id.choice_item)
        CheckBox choiceItem;
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

        public CollectionListView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(CollectionListInfo collectionListInfo) {
            choiceItem.setVisibility(TextUtils.equals("1", behavior) ? View.GONE : View.VISIBLE);
            CollectionStoreInfo collectionStoreInfo = collectionListInfo.getCollectionStoreInfo();
            GlideHelper.load((Activity) context, collectionStoreInfo.getzPics(), R.mipmap.client_v330_ic_homepage_circle_1, shopIcon);
            storeName.setText(collectionStoreInfo.getTitle());
            shopName.setText(collectionStoreInfo.getName());
        }
    }

    public interface OnCollectionItemClickListener {
        void addItem(String storeId);

        void removeItem(String storeId);
    }

    private OnCollectionItemClickListener onCollectionItemClickListener;

    public void setOnCollectionItemClickListener(OnCollectionItemClickListener onCollectionItemClickListener) {
        this.onCollectionItemClickListener = onCollectionItemClickListener;
    }
}
