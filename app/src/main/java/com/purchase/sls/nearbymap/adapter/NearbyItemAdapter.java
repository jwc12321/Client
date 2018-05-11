package com.purchase.sls.nearbymap.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.purchase.sls.R;
import com.purchase.sls.data.entity.NearbyInfoResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/4/21.
 */

public class NearbyItemAdapter extends RecyclerView.Adapter<NearbyItemAdapter.NearbyIemView> {

    private LayoutInflater layoutInflater;
    private List<NearbyInfoResponse.CateInfo> cateInfos;
    private int selectPosition = 0;

    public void setItemList(List<NearbyInfoResponse.CateInfo> cateInfos,int position) {
        this.selectPosition=position;
        this.cateInfos = cateInfos;
        notifyDataSetChanged();
    }

    public void setPosittion(int lastSelectPosition, int selectPosition) {
        this.selectPosition = selectPosition;
        notifyItemChanged(lastSelectPosition);
        notifyItemChanged(selectPosition);
    }

    @Override
    public NearbyIemView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_nearby_item, parent, false);
        return new NearbyIemView(view);
    }

    @Override
    public void onBindViewHolder(final NearbyIemView holder, int position) {
        final NearbyInfoResponse.CateInfo cateInfo = cateInfos.get(holder.getAdapterPosition());
        holder.itemName.setText(cateInfo.getName());
        holder.itemName.setSelected(selectPosition == position);
        holder.itemLl.setSelected(selectPosition == position);
        holder.itemLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.itemClickListener(cateInfo,holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return cateInfos == null ? 0 : cateInfos.size();
    }

    public class NearbyIemView extends RecyclerView.ViewHolder {

        @BindView(R.id.item_name)
        TextView itemName;
        @BindView(R.id.item_ll)
        LinearLayout itemLl;

        public NearbyIemView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void itemClickListener(NearbyInfoResponse.CateInfo cateInfo,int itemPosition);
    }

    private static OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
