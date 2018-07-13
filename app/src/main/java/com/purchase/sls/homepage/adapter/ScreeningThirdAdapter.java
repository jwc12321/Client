package com.purchase.sls.homepage.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.purchase.sls.R;
import com.purchase.sls.data.entity.ScreenInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/4/21.
 */

public class ScreeningThirdAdapter extends RecyclerView.Adapter<ScreeningThirdAdapter.ScreeningThirdView> {

    private LayoutInflater layoutInflater;
    private List<ScreenInfo> screenInfos;
    private int selectPosition = 0;

    public void setData(List<ScreenInfo> screenInfos) {
        this.screenInfos = screenInfos;
        notifyDataSetChanged();
    }

    public void setPosittion(int lastSelectPosition, int selectPosition) {
        this.selectPosition = selectPosition;
        notifyItemChanged(lastSelectPosition);
        notifyItemChanged(selectPosition);
    }

    @Override
    public ScreeningThirdView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_screening_third, parent, false);
        return new ScreeningThirdView(view);
    }

    @Override
    public void onBindViewHolder(final ScreeningThirdView holder, int position) {
        final ScreenInfo screenInfo = screenInfos.get(holder.getAdapterPosition());
        holder.itemName.setText(screenInfo.getVel());
        holder.itemName.setSelected(selectPosition == position);
        holder.itemLl.setSelected(selectPosition == position);
        holder.itemLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onThirdItemOnClickListener.onThirdItemClick(screenInfo.getKey(), holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return screenInfos == null ? 0 : screenInfos.size();
    }

    public class ScreeningThirdView extends RecyclerView.ViewHolder {

        @BindView(R.id.item_name)
        TextView itemName;
        @BindView(R.id.item_ll)
        LinearLayout itemLl;

        public ScreeningThirdView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnThirdItemOnClickListener {
        void  onThirdItemClick(String screenId, int itemPosition);
    }

    private static OnThirdItemOnClickListener onThirdItemOnClickListener;

    public void setOnThirdItemOnClickListener(OnThirdItemOnClickListener onThirdItemOnClickListener) {
        this.onThirdItemOnClickListener = onThirdItemOnClickListener;
    }
}
