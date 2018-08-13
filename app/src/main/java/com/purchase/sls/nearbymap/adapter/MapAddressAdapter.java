package com.purchase.sls.nearbymap.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.purchase.sls.R;
import com.purchase.sls.data.entity.MapAddressInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/8/13.
 */

public class MapAddressAdapter extends RecyclerView.Adapter<MapAddressAdapter.MapAddressViwe> {
    private LayoutInflater layoutInflater;
    private List<MapAddressInfo> mapAddressInfos;

    public void setData(List<MapAddressInfo> mapAddressInfos) {
        this.mapAddressInfos = mapAddressInfos;
        notifyDataSetChanged();
    }

    @Override
    public MapAddressViwe onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_map_address, parent, false);
        return new MapAddressViwe(view);
    }

    @Override
    public void onBindViewHolder(MapAddressViwe holder, int position) {
        final MapAddressInfo mapAddressInfo = mapAddressInfos.get(holder.getAdapterPosition());
        holder.bindData(mapAddressInfo);
        holder.addressLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onEventClickListener!=null){
                    onEventClickListener.backAddress(mapAddressInfo);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mapAddressInfos == null ? 0 : mapAddressInfos.size();
    }

    public class MapAddressViwe extends RecyclerView.ViewHolder {
        @BindView(R.id.address)
        TextView address;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.address_ll)
        LinearLayout addressLl;

        public MapAddressViwe(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(MapAddressInfo mapAddressInfo) {
            title.setText(mapAddressInfo.getTitle());
            address.setText(mapAddressInfo.getAddress());
        }
    }

    public interface OnEventClickListener {
        void backAddress(MapAddressInfo mapAddressInfo);
    }

    private OnEventClickListener onEventClickListener;

    public void setOnEventClickListener(OnEventClickListener onEventClickListener) {
        this.onEventClickListener = onEventClickListener;
    }
}
