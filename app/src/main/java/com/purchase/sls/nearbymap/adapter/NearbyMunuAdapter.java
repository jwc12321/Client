package com.purchase.sls.nearbymap.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.R;
import com.purchase.sls.data.entity.NearbyInfoResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/4/21.
 */

public class NearbyMunuAdapter extends RecyclerView.Adapter<NearbyMunuAdapter.NearbyMunuView> {

    private LayoutInflater layoutInflater;
    private List<NearbyInfoResponse> nearbyInfoResponses;
    private int selectPosition = 0;

    public void setMunuList(List<NearbyInfoResponse> nearbyInfoResponses,int position) {
        this.selectPosition=position;
        this.nearbyInfoResponses = nearbyInfoResponses;
        notifyDataSetChanged();
    }

    public void setPosittion(int lastSelectPosition, int selectPosition) {
        this.selectPosition = selectPosition;
        notifyItemChanged(lastSelectPosition);
        notifyItemChanged(selectPosition);
    }

    @Override
    public NearbyMunuView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_nearby_munu, parent, false);
        return new NearbyMunuView(view);
    }

    @Override
    public void onBindViewHolder(final NearbyMunuView holder, int position) {
        NearbyInfoResponse nearbyInfoResponse = nearbyInfoResponses.get(holder.getAdapterPosition());
        holder.munuName.setText(nearbyInfoResponse.getName());
        holder.munuName.setSelected(selectPosition == position);
        holder.underline.setSelected(selectPosition == position);
        holder.munuLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMenuItemClickListener.menuItemClickListener(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return nearbyInfoResponses == null ? 0 : nearbyInfoResponses.size();
    }

    public class NearbyMunuView extends RecyclerView.ViewHolder {

        @BindView(R.id.munu_name)
        TextView munuName;
        @BindView(R.id.underline)
        View underline;
        @BindView(R.id.munu_ll)
        LinearLayout munuLl;

        public NearbyMunuView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnMenuItemClickListener {
        void menuItemClickListener(int menuPosition);
    }

    private OnMenuItemClickListener onMenuItemClickListener;

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.onMenuItemClickListener = onMenuItemClickListener;
    }
}
