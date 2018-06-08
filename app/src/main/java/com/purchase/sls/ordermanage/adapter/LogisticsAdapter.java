package com.purchase.sls.ordermanage.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.purchase.sls.R;
import com.purchase.sls.data.entity.LogisticRracesInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/6/8.
 */

public class LogisticsAdapter extends RecyclerView.Adapter<LogisticsAdapter.LogisticsView> {
    private LayoutInflater layoutInflater;
    private List<LogisticRracesInfo> logisticRracesInfos;

    public void setData(List<LogisticRracesInfo> logisticRracesInfos) {
        this.logisticRracesInfos = logisticRracesInfos;
        notifyDataSetChanged();
    }

    @Override
    public LogisticsView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_logistics, parent, false);
        return new LogisticsView(view);
    }

    @Override
    public void onBindViewHolder(LogisticsView holder, int position) {
        LogisticRracesInfo logisticsInfo = logisticRracesInfos.get(holder.getAdapterPosition());
        if (logisticsInfo != null) {
            if (logisticRracesInfos.size() == 1) {
                holder.upIcon.setVisibility(View.GONE);
                holder.inIcon.setVisibility(View.VISIBLE);
                holder.downIcon.setVisibility(View.GONE);
                holder.inIcon.setBackgroundResource(R.mipmap.yellow_point);
                holder.logisticLl.setBackgroundResource(R.color.backGround17);
            } else {
                if (holder.getAdapterPosition() == 0) {
                    holder.upIcon.setVisibility(View.GONE);
                    holder.inIcon.setVisibility(View.VISIBLE);
                    holder.downIcon.setVisibility(View.VISIBLE);
                    holder.inIcon.setBackgroundResource(R.mipmap.yellow_point);
                    holder.downIcon.setBackgroundResource(R.drawable.yellow_line);
                    holder.logisticLl.setBackgroundResource(R.color.backGround17);
                } else if (holder.getAdapterPosition() == logisticRracesInfos.size() - 1) {
                    holder.upIcon.setVisibility(View.VISIBLE);
                    holder.inIcon.setVisibility(View.VISIBLE);
                    holder.downIcon.setVisibility(View.GONE);
                    holder.inIcon.setBackgroundResource(R.mipmap.ash_point);
                    holder.upIcon.setBackgroundResource(R.drawable.ash_line);
                    holder.logisticLl.setBackgroundResource(R.color.backGround18);
                } else {
                    holder.upIcon.setVisibility(View.VISIBLE);
                    holder.inIcon.setVisibility(View.VISIBLE);
                    holder.downIcon.setVisibility(View.VISIBLE);
                    holder.inIcon.setBackgroundResource(R.mipmap.ash_point);
                    holder.upIcon.setBackgroundResource(R.drawable.ash_line);
                    holder.downIcon.setBackgroundResource(R.drawable.ash_line);
                    holder.logisticLl.setBackgroundResource(R.color.backGround18);
                }
            }
            holder.logisticsAddress.setText(logisticsInfo.getAcceptStation());
            holder.logisticsTime.setText(logisticsInfo.getAcceptTime());
        }
    }

    @Override
    public int getItemCount() {
        return logisticRracesInfos == null ? 0 : logisticRracesInfos.size();
    }

    public class LogisticsView extends RecyclerView.ViewHolder {
        @BindView(R.id.up_icon)
        ImageView upIcon;
        @BindView(R.id.in_icon)
        ImageView inIcon;
        @BindView(R.id.down_icon)
        ImageView downIcon;
        @BindView(R.id.logistics_address)
        TextView logisticsAddress;
        @BindView(R.id.logistics_time)
        TextView logisticsTime;
        @BindView(R.id.logistic_ll)
        LinearLayout logisticLl;

        public LogisticsView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
