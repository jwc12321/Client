package com.purchase.sls.nearbymap.adapter;

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
import com.purchase.sls.data.entity.MapMarkerInfo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/4/21.
 */

public class MapMarkerStoreAdapter extends RecyclerView.Adapter<MapMarkerStoreAdapter.LikeStoreView> {
    private LayoutInflater layoutInflater;
    private List<MapMarkerInfo> mapMarkerInfos;
    private Context context;
    private String longitude;
    private String latitude;
    BigDecimal kmdistanceBd = new BigDecimal(1000).setScale(0, RoundingMode.HALF_UP);

    public MapMarkerStoreAdapter(Context context) {
        this.context = context;
    }

    public MapMarkerStoreAdapter(Context context, String longitude, String latitude) {
        this.context = context;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public void setCity(String longitude, String latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public void setData(List<MapMarkerInfo> mapMarkerInfos) {
        this.mapMarkerInfos = mapMarkerInfos;
        notifyDataSetChanged();
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
        final MapMarkerInfo mapMarkerInfo = mapMarkerInfos.get(holder.getAdapterPosition());
        holder.bindData(mapMarkerInfo);
        holder.likestoreRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onMapMarkerClickListener != null) {
                    onMapMarkerClickListener.mapMarkerClickListener(mapMarkerInfo.getId());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mapMarkerInfos == null ? 0 : mapMarkerInfos.size();
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

        public void bindData(MapMarkerInfo mapMarkerInfo) {
            GlideHelper.load((Activity) context, mapMarkerInfo.getzPics(), R.mipmap.app_icon, shopIcon);
            storeName.setText(mapMarkerInfo.getTitle());
            popularityNumber.setText("月均人气" + mapMarkerInfo.getBuzz());
            shopName.setText(mapMarkerInfo.getName());
            shopCity.setText(mapMarkerInfo.getPoiname());
            String addressXy = mapMarkerInfo.getAddressXy();
            if (!TextUtils.isEmpty(mapMarkerInfo.getDistanceUm()) && !TextUtils.equals("0", mapMarkerInfo.getDistanceUm())) {
                shopDistance.setVisibility(View.VISIBLE);
                shopDistance.setText(mapMarkerInfo.getDistanceUm() + "KM");
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
                }
            }
            if (TextUtils.isEmpty(mapMarkerInfo.getRebate()) || TextUtils.equals("0", mapMarkerInfo.getRebate())) {
                returnEnergy.setVisibility(View.INVISIBLE);
                returnEnergy.setText("");
            } else {
                returnEnergy.setVisibility(View.VISIBLE);
                returnEnergy.setText("补贴" + mapMarkerInfo.getRebate() + "%的能量");
            }
        }
    }

    public interface OnMapMarkerClickListener {
        void mapMarkerClickListener(String storeid);
    }

    private OnMapMarkerClickListener onMapMarkerClickListener;

    public void setOnMapMarkerClickListener(OnMapMarkerClickListener onMapMarkerClickListener) {
        this.onMapMarkerClickListener = onMapMarkerClickListener;
    }
}
