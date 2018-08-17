package com.purchase.sls.homepage.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.R;
import com.purchase.sls.common.GlideHelper;
import com.purchase.sls.common.unit.DistanceUnits;
import com.purchase.sls.data.entity.CollectionStoreInfo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/4/21.
 */

public class LikeStoreAdapter extends RecyclerView.Adapter<LikeStoreAdapter.LikeStoreView> {
    private LayoutInflater layoutInflater;
    private List<CollectionStoreInfo> collectionStoreInfos;
    private Context context;
    private String longitude;
    private String latitude;
    private String city;
    BigDecimal kmdistanceBd = new BigDecimal(1000).setScale(0, RoundingMode.HALF_UP);

    public LikeStoreAdapter(Context context) {
        this.context = context;
    }

    public LikeStoreAdapter(Context context, String city, String longitude, String latitude) {
        this.context = context;
        this.longitude = longitude;
        this.latitude = latitude;
        this.city = city;
    }

    public void setCity(String city, String longitude, String latitude) {
        this.city = city;
        this.longitude = longitude;
        this.latitude = latitude;
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
        holder.likestoreRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onLikeStoreClickListener != null) {
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

        @BindView(R.id.shop_icon)
        ImageView shopIcon;
        @BindView(R.id.store_name)
        TextView storeName;
        @BindView(R.id.shop_city)
        TextView shopCity;
        @BindView(R.id.shop_distance)
        TextView shopDistance;
        @BindView(R.id.shop_name)
        TextView shopName;
        @BindView(R.id.popularity_number)
        TextView popularityNumber;
        @BindView(R.id.return_energy)
        TextView returnEnergy;
        @BindView(R.id.likestore_rl)
        RelativeLayout likestoreRl;
        public LikeStoreView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(CollectionStoreInfo collectionStoreInfo) {
            GlideHelper.load((Activity) context, collectionStoreInfo.getzPics(), R.mipmap.default_store_icon, shopIcon);
            storeName.setText(collectionStoreInfo.getTitle());
            popularityNumber.setText("月均人气" + collectionStoreInfo.getBuzz());
            shopName.setText(collectionStoreInfo.getName());
            shopCity.setText(collectionStoreInfo.getPoiname());
            String addressXy = collectionStoreInfo.getAddressXy();
            if (!TextUtils.isEmpty(collectionStoreInfo.getDistanceUm()) && !TextUtils.equals("0", collectionStoreInfo.getDistanceUm())) {
                shopDistance.setVisibility(View.VISIBLE);
                shopDistance.setText(collectionStoreInfo.getDistanceUm() + "KM");
            } else {
                if (TextUtils.isEmpty(latitude) || TextUtils.isEmpty(longitude) || TextUtils.isEmpty(addressXy)) {
                    shopDistance.setVisibility(View.GONE);
                } else {
                    String[] addressXys = addressXy.split(",");
                    if (addressXy != null && addressXys.length > 1 && !TextUtils.isEmpty(addressXys[0]) && !TextUtils.isEmpty(addressXys[1])) {
                        shopDistance.setVisibility(View.VISIBLE);
                        String mdistance = String.valueOf(DistanceUnits.getDistance(Double.parseDouble(longitude), Double.parseDouble(latitude), Double.parseDouble(addressXys[0]), Double.parseDouble(addressXys[1])));
                        BigDecimal mdistanceBd = new BigDecimal(mdistance).setScale(2, RoundingMode.HALF_UP);
                        shopDistance.setText(mdistanceBd.divide(kmdistanceBd, 2, BigDecimal.ROUND_DOWN) + "KM");
                    } else {
                        shopDistance.setVisibility(View.GONE);
                    }
                    shopDistance.setVisibility(View.VISIBLE);
                }
            }
            if (TextUtils.isEmpty(collectionStoreInfo.getRebate()) || TextUtils.equals("0", collectionStoreInfo.getRebate())) {
                returnEnergy.setVisibility(View.INVISIBLE);
                returnEnergy.setText("");
            } else {
                returnEnergy.setVisibility(View.VISIBLE);
                returnEnergy.setText("补贴"+collectionStoreInfo.getRebate() + "%的能量");
            }
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
