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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.R;
import com.purchase.sls.common.GlideHelper;
import com.purchase.sls.common.unit.DistanceUnits;
import com.purchase.sls.data.entity.BrowseInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/4/21.
 */

public class BrowseRecordsAdapter extends RecyclerView.Adapter<BrowseRecordsAdapter.BrowseRecordsView> {

    private LayoutInflater layoutInflater;
    private List<BrowseInfo.BrowseItemInfo> browseItemInfos;
    private Context context;
    private String behavior = "1";//1：正常状态2：有选中删除选项
    private String longitude;
    private String latitude;
    private String city;

    public BrowseRecordsAdapter(Context context) {
        this.context = context;
    }
    public BrowseRecordsAdapter(Context context, String city,String longitude, String latitude) {
        this.context = context;
        this.longitude = longitude;
        this.latitude = latitude;
        this.city = city;
    }

    public void setCity(String city, String longitude, String latitude) {
        this.city = city;
        this.longitude = longitude;
        this.latitude = latitude;
        notifyDataSetChanged();
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
    public void onBindViewHolder(final BrowseRecordsView holder, int position) {
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
        holder.collectionItemLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBrowseItemClickListener != null) {
                    if (TextUtils.equals("1", behavior)) {
                        onBrowseItemClickListener.goShopDetail(browseItemInfo.getStore().getId());
                    }else {
                        if(holder.choiceItem.isChecked()){
                            holder.choiceItem.setChecked(false);
                            onBrowseItemClickListener.removeItem(browseItemInfo.getId());
                        }else {
                            holder.choiceItem.setChecked(true);
                            onBrowseItemClickListener.addItem(browseItemInfo.getId());
                        }
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
        @BindView(R.id.choice_item)
        CheckBox choiceItem;
        @BindView(R.id.shop_icon)
        ImageView shopIcon;
        @BindView(R.id.store_name)
        TextView storeName;
        @BindView(R.id.popularity_number)
        TextView popularityNumber;
        @BindView(R.id.per_capita)
        TextView perCapita;
        @BindView(R.id.shop_name)
        TextView shopName;
        @BindView(R.id.shop_city)
        TextView shopCity;
        @BindView(R.id.shop_distance)
        TextView shopDistance;
        @BindView(R.id.return_energy)
        TextView returnEnergy;
        @BindView(R.id.return_ll)
        LinearLayout returnLl;
        @BindView(R.id.collection_item_ll)
        RelativeLayout collectionItemLl;

        public BrowseRecordsView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(BrowseInfo.BrowseItemInfo browseItemInfo) {
            choiceItem.setVisibility(TextUtils.equals("1", behavior) ? View.GONE : View.VISIBLE);
            choiceItem.setChecked(false);
            BrowseInfo.BrowseItemInfo.Store store = browseItemInfo.getStore();
            GlideHelper.load((Activity) context, store.getzPics(), R.mipmap.app_icon, shopIcon);
            storeName.setText(store.getTitle());
            popularityNumber.setText("月均人气" + store.getBuzz());
            perCapita.setText("人均" + store.getAverage() + "元");
            shopName.setText(store.getName());
            shopCity.setText(city);
            String addressXy = store.getAddressXy();
            if (TextUtils.isEmpty(latitude) || TextUtils.isEmpty(longitude) || TextUtils.isEmpty(addressXy)) {
                shopDistance.setVisibility(View.GONE);
            } else {
                String[] addressXys = addressXy.split(",");
                if (addressXy != null && addressXys.length > 1 && !TextUtils.isEmpty(addressXys[0]) && !TextUtils.isEmpty(addressXys[1])) {
                    shopDistance.setVisibility(View.VISIBLE);
                    shopDistance.setText(String.valueOf(DistanceUnits.getDistance(Double.parseDouble(longitude), Double.parseDouble(latitude), Double.parseDouble(addressXys[0]), Double.parseDouble(addressXys[1]))) + "米");
                } else {
                    shopDistance.setVisibility(View.GONE);
                }
                shopDistance.setVisibility(View.VISIBLE);
            }
        }
    }

    public interface OnBrowseItemClickListener {
        void addItem(String storeId);

        void removeItem(String storeId);

        void goShopDetail(String storeid);
    }

    private OnBrowseItemClickListener onBrowseItemClickListener;

    public void setOnBrowseItemClickListener(OnBrowseItemClickListener onBrowseItemClickListener) {
        this.onBrowseItemClickListener = onBrowseItemClickListener;
    }
}
