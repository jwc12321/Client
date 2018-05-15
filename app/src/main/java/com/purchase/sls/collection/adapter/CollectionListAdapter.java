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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.R;
import com.purchase.sls.common.GlideHelper;
import com.purchase.sls.common.unit.DistanceUnits;
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
    private String longitude;
    private String latitude;
    private String city;

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

    public CollectionListAdapter(Context context, String city,String longitude, String latitude) {
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

    @Override
    public CollectionListView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_collection_list, parent, false);
        return new CollectionListView(view);
    }

    @Override
    public void onBindViewHolder(final CollectionListView holder, int position) {
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
        holder.likestoreRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onCollectionItemClickListener!=null) {
                    if (TextUtils.equals("1", behavior)) {
                        onCollectionItemClickListener.goShopDetail(collectionListInfo.getStoreid());
                    }else {
                        if(holder.choiceItem.isChecked()){
                            holder.choiceItem.setChecked(false);
                            onCollectionItemClickListener.removeItem(collectionListInfo.getId());
                        }else {
                            holder.choiceItem.setChecked(true);
                            onCollectionItemClickListener.addItem(collectionListInfo.getId());
                        }
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
        @BindView(R.id.likestore_rl)
        RelativeLayout likestoreRl;

        public CollectionListView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(CollectionListInfo collectionListInfo) {
            choiceItem.setVisibility(TextUtils.equals("1", behavior) ? View.GONE : View.VISIBLE);
            choiceItem.setChecked(false);
            CollectionStoreInfo collectionStoreInfo = collectionListInfo.getCollectionStoreInfo();
            GlideHelper.load((Activity) context, collectionStoreInfo.getzPics(), R.mipmap.app_icon, shopIcon);
            storeName.setText(collectionStoreInfo.getTitle());
            popularityNumber.setText("月均人气" + collectionStoreInfo.getBuzz());
            perCapita.setText("人均" + collectionStoreInfo.getAverage() + "元");
            shopName.setText(collectionStoreInfo.getName());
            shopCity.setText(city);
            String addressXy = collectionStoreInfo.getAddressXy();
            if (TextUtils.isEmpty(latitude) || TextUtils.isEmpty(longitude) || TextUtils.isEmpty(addressXy)) {
                shopDistance.setVisibility(View.GONE);
            } else {
                String[] addressXys = addressXy.split(",");
                if (addressXy != null && addressXys.length > 1 && !TextUtils.isEmpty(addressXys[0]) && !TextUtils.isEmpty(addressXys[1])) {
                    shopDistance.setVisibility(View.VISIBLE);
                    shopDistance.setText(String.valueOf(DistanceUnits.getDistance(Double.parseDouble(longitude), Double.parseDouble(latitude), Double.parseDouble(addressXys[0]), Double.parseDouble(addressXys[1])))+"米");
                } else {
                    shopDistance.setVisibility(View.GONE);
                }
                shopDistance.setVisibility(View.VISIBLE);
            }
            if (TextUtils.isEmpty(collectionStoreInfo.getRebate())||TextUtils.equals("0", collectionStoreInfo.getRebate())) {
                returnLl.setVisibility(View.GONE);
            }else {
                returnEnergy.setText("每消费一单返消费金额的"+collectionStoreInfo.getRebate()+"%的能量");
                returnLl.setVisibility(View.VISIBLE);
            }
        }
    }

    public interface OnCollectionItemClickListener {
        void addItem(String storeId);
        void removeItem(String storeId);
        void goShopDetail(String storeid);
    }

    private OnCollectionItemClickListener onCollectionItemClickListener;

    public void setOnCollectionItemClickListener(OnCollectionItemClickListener onCollectionItemClickListener) {
        this.onCollectionItemClickListener = onCollectionItemClickListener;
    }
}
