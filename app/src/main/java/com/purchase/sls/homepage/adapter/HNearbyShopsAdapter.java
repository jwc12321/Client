package com.purchase.sls.homepage.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.purchase.sls.R;
import com.purchase.sls.common.GlideHelper;
import com.purchase.sls.common.unit.DistanceUnits;
import com.purchase.sls.data.entity.HNearbyShopsInfo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HNearbyShopsAdapter extends RecyclerView.Adapter<HNearbyShopsAdapter.HNearbyShopsView> {

    private LayoutInflater layoutInflater;
    private Context context;
    private List<HNearbyShopsInfo> hNearbyShopsInfos;
    private String longitude;
    private String latitude;

    BigDecimal kmdistanceBd = new BigDecimal(1000).setScale(0, RoundingMode.HALF_UP);
    public HNearbyShopsAdapter(Context context) {
        this.context = context;
    }

    public void setCoordinate(String longitude, String latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public void setData(List<HNearbyShopsInfo> hNearbyShopsInfos) {
        this.hNearbyShopsInfos = hNearbyShopsInfos;
        notifyDataSetChanged();
    }

    @Override
    public HNearbyShopsView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_h_nearby_shops, parent, false);
        return new HNearbyShopsView(view);
    }

    @Override
    public void onBindViewHolder(HNearbyShopsView holder, int position) {
        final HNearbyShopsInfo hNearbyShopsInfo = hNearbyShopsInfos.get(holder.getAdapterPosition());
        holder.bindData(hNearbyShopsInfo);
        holder.nearbyShopItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onNearbyShopClickListener!=null){
                    onNearbyShopClickListener.nearbyShopClickListener(hNearbyShopsInfo.getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return hNearbyShopsInfos == null ? 0 : hNearbyShopsInfos.size();
    }

    public class HNearbyShopsView extends RecyclerView.ViewHolder {
        @BindView(R.id.shop_icon)
        ImageView shopIcon;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.distance)
        TextView distance;
        @BindView(R.id.nearby_shop_item)
        LinearLayout nearbyShopItem;
        public HNearbyShopsView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(HNearbyShopsInfo hNearbyShopsInfo) {
            GlideHelper.load((Activity) context, hNearbyShopsInfo.getzPics(), R.mipmap.app_icon, shopIcon);
            name.setText(hNearbyShopsInfo.getTitle());
            String addressXy = hNearbyShopsInfo.getAddressXy();
            if (TextUtils.isEmpty(latitude) || TextUtils.isEmpty(longitude) || TextUtils.isEmpty(addressXy)) {
                distance.setText("");
            } else {
                String[] addressXys = addressXy.split(",");
                if (addressXy != null && addressXys.length > 1 && !TextUtils.isEmpty(addressXys[0]) && !TextUtils.isEmpty(addressXys[1])) {
                    String mdistance = String.valueOf(DistanceUnits.getDistance(Double.parseDouble(longitude), Double.parseDouble(latitude), Double.parseDouble(addressXys[0]), Double.parseDouble(addressXys[1])));
                    BigDecimal mdistanceBd = new BigDecimal(mdistance).setScale(2, RoundingMode.HALF_UP);
                    distance.setText(mdistanceBd.divide(kmdistanceBd, 2, BigDecimal.ROUND_DOWN) + "KM");
                } else {
                    distance.setText("");
                }
            }
        }
    }

    public interface OnNearbyShopClickListener {
        void nearbyShopClickListener(String storeid);
    }

    private OnNearbyShopClickListener onNearbyShopClickListener;

    public void setOnNearbyShopClickListener(OnNearbyShopClickListener onNearbyShopClickListener) {
        this.onNearbyShopClickListener = onNearbyShopClickListener;
    }
}
