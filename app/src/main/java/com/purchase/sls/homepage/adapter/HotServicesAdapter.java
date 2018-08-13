package com.purchase.sls.homepage.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.purchase.sls.R;
import com.purchase.sls.common.GlideHelper;
import com.purchase.sls.data.entity.BannerHotResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/4/20.
 */

public class HotServicesAdapter extends RecyclerView.Adapter<HotServicesAdapter.HotServiceView> {
    private LayoutInflater layoutInflater;
    private Context context;
    private List<BannerHotResponse.StorecateInfo> storecateInfos;

    public HotServicesAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<BannerHotResponse.StorecateInfo> storecateInfos) {
        this.storecateInfos = storecateInfos;
        notifyDataSetChanged();
    }

    @Override
    public HotServiceView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_hot_service_s, parent, false);
        return new HotServiceView(view);
    }

    @Override
    public void onBindViewHolder(HotServiceView holder, int position) {
        final BannerHotResponse.StorecateInfo storecateInfo = storecateInfos.get(holder.getAdapterPosition());
        holder.bindData(storecateInfo);
        holder.hotItemLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHotItemClickListener.hotItemClickListener(storecateInfo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return storecateInfos == null ? 0 : storecateInfos.size();
    }

    public class HotServiceView extends RecyclerView.ViewHolder {
        @BindView(R.id.icon)
        RoundedImageView icon;
        @BindView(R.id.text)
        TextView text;
        @BindView(R.id.hot_item_ll)
        LinearLayout hotItemLl;
        public HotServiceView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(BannerHotResponse.StorecateInfo storecateInfo) {
            GlideHelper.load((Activity) context, storecateInfo.getPic(), R.mipmap.app_icon, icon);
            text.setText(storecateInfo.getName());
        }
    }

    public interface OnHotItemClickListener {
        void hotItemClickListener(BannerHotResponse.StorecateInfo storecateInfo);
    }

    private OnHotItemClickListener onHotItemClickListener;

    public void setOnHotItemClickListener(OnHotItemClickListener onHotItemClickListener) {
        this.onHotItemClickListener = onHotItemClickListener;
    }

}
