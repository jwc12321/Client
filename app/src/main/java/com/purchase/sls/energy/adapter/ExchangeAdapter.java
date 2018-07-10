package com.purchase.sls.energy.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.R;
import com.purchase.sls.common.GlideHelper;
import com.purchase.sls.data.entity.ActivityInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/6/1.
 */

public class ExchangeAdapter extends RecyclerView.Adapter<ExchangeAdapter.ExchangeVeiw> {
    private LayoutInflater layoutInflater;
    private Context context;
    private List<ActivityInfo> activityInfos;

    public ExchangeAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ActivityInfo> activityInfos) {
        this.activityInfos = activityInfos;
        notifyDataSetChanged();
    }


    @Override
    public ExchangeVeiw onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_exchange, parent, false);
        return new ExchangeVeiw(view);
    }

    @Override
    public void onBindViewHolder(ExchangeVeiw holder, int position) {
        final ActivityInfo activityInfo = activityInfos.get(holder.getAdapterPosition());
        holder.bindData(activityInfo);
        holder.goExchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onExchangeItemClickListener != null) {
                    onExchangeItemClickListener.goExchange(activityInfo);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return activityInfos == null ? 0 : activityInfos.size();
    }

    public class ExchangeVeiw extends RecyclerView.ViewHolder {
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.go_exchange)
        RelativeLayout goExchange;
        @BindView(R.id.exchange_iv)
        ImageView exchangeIv;
        @BindView(R.id.energy_ll)
        LinearLayout energyLl;

        public ExchangeVeiw(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(ActivityInfo activityInfo) {
            price.setText(activityInfo.getPrice());
            name.setText("能量兑换"+activityInfo.getpName());
            GlideHelper.load((Activity) context, activityInfo.getActLogo(), R.mipmap.app_icon, exchangeIv);
            int dh = (int) (exchangeIv.getDrawable().getBounds().height());
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) energyLl.getLayoutParams();
            lp.setMargins(30, dh-33, 22, 10);
            energyLl.setLayoutParams(lp);
        }
    }

    public interface OnExchangeItemClickListener {
        void goExchange(ActivityInfo activityInfo);
    }

    private OnExchangeItemClickListener onExchangeItemClickListener;

    public void setOnExchangeItemClickListener(OnExchangeItemClickListener onExchangeItemClickListener) {
        this.onExchangeItemClickListener = onExchangeItemClickListener;
    }
}
