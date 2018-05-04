package com.purchase.sls.browse.adapter;

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
import com.purchase.sls.data.entity.BrowseInfo;
import com.purchase.sls.data.entity.CollectionListInfo;
import com.purchase.sls.data.entity.CollectionStoreInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/4/21.
 */

public class BrowseRecordsAdapter extends RecyclerView.Adapter<BrowseRecordsAdapter.BrowseRecordsView> {

    private LayoutInflater layoutInflater;
    private List<BrowseInfo.BrowseItemInfo> browseItemInfos;
    private static Context context;
    private String behavior = "1";//1：正常状态2：有选中删除选项

    public BrowseRecordsAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<BrowseInfo.BrowseItemInfo> browseItemInfos) {
        this.browseItemInfos = browseItemInfos;
        notifyDataSetChanged();
    }

    public void addMore(List<BrowseInfo.BrowseItemInfo> moreList) {
        int pos = browseItemInfos.size();
        browseItemInfos.addAll(moreList);
        notifyItemRangeInserted(pos, moreList.size());
    }

    public void setType(String behavior) {
        this.behavior = behavior;
        notifyDataSetChanged();
    }

    @Override
    public BrowseRecordsView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_browse_records, parent, false);
        return new BrowseRecordsView(view);
    }

    @Override
    public void onBindViewHolder(BrowseRecordsView holder, int position) {
        final BrowseInfo.BrowseItemInfo browseItemInfo = browseItemInfos.get(holder.getAdapterPosition());
        holder.bindData(browseItemInfo);
        holder.choiceItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (onBrowseItemClickListener != null) {
                    if (isChecked) {
                        onBrowseItemClickListener.addItem(browseItemInfo.getId());
                    } else {
                        onBrowseItemClickListener.removeItem(browseItemInfo.getId());
                    }
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return browseItemInfos == null ? 0 : browseItemInfos.size();
    }

    public class BrowseRecordsView extends RecyclerView.ViewHolder {
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

        public BrowseRecordsView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(BrowseInfo.BrowseItemInfo browseItemInfo) {
            choiceItem.setVisibility(TextUtils.equals("1", behavior) ? View.GONE : View.VISIBLE);
            BrowseInfo.BrowseItemInfo.Store store = browseItemInfo.getStore();
            GlideHelper.load((Activity) context, store.getzPics(), R.mipmap.client_v330_ic_homepage_circle_1, shopIcon);
            storeName.setText(store.getTitle());
            shopName.setText(store.getName());
        }
    }

    public interface OnBrowseItemClickListener {
        void addItem(String storeId);

        void removeItem(String storeId);
    }

    private OnBrowseItemClickListener onBrowseItemClickListener;

    public void setOnBrowseItemClickListener(OnBrowseItemClickListener onBrowseItemClickListener) {
        this.onBrowseItemClickListener = onBrowseItemClickListener;
    }
}
