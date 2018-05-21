package com.purchase.sls.shopdetailbuy.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.purchase.sls.R;
import com.purchase.sls.data.entity.ChoiceMapInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/4/28.
 */

public class ChoiceMapAdapter extends RecyclerView.Adapter<ChoiceMapAdapter.ChoiceMapView> {
    private LayoutInflater layoutInflater;
    private List<ChoiceMapInfo> choiceMapInfos;

    public void setData(List<ChoiceMapInfo> choiceMapInfos) {
        this.choiceMapInfos = choiceMapInfos;
        notifyDataSetChanged();
    }

    @Override
    public ChoiceMapView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_choice_map, parent, false);
        return new ChoiceMapView(view);
    }

    @Override
    public void onBindViewHolder(ChoiceMapView holder, int position) {
        final ChoiceMapInfo choiceMapInfo = choiceMapInfos.get(holder.getAdapterPosition());
        holder.bindData(choiceMapInfo);
        holder.choiceMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onMapItemClick != null) {
                    onMapItemClick.mapItemClick(choiceMapInfo);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return choiceMapInfos == null ? 0 : choiceMapInfos.size();
    }

    public static class ChoiceMapView extends RecyclerView.ViewHolder {
        @BindView(R.id.choice_map)
        Button choiceMap;
        public ChoiceMapView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(ChoiceMapInfo choiceMapInfo) {
            choiceMap.setText(choiceMapInfo.getAppName());
        }
    }

    public interface OnMapItemClick {
        void mapItemClick(ChoiceMapInfo choiceMapInfo);
    }

    private OnMapItemClick onMapItemClick;

    public void setonMapItemClick(OnMapItemClick onMapItemClick) {
        this.onMapItemClick = onMapItemClick;
    }
}
