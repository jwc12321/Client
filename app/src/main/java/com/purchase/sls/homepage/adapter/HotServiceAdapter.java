package com.purchase.sls.homepage.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
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

public class HotServiceAdapter extends RecyclerView.Adapter<HotServiceAdapter.HotServiceView> {
    private LayoutInflater layoutInflater;
    private static Context context;
    private List<BannerHotResponse.StorecateInfo> storecateInfos;

    public HotServiceAdapter(Context context) {
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
        View view = layoutInflater.inflate(R.layout.hot_service_link_item, parent, false);
        return new HotServiceView(view);
    }

    @Override
    public void onBindViewHolder(HotServiceView holder, int position) {
        BannerHotResponse.StorecateInfo storecateInfo = storecateInfos.get(holder.getAdapterPosition());
        holder.bindData(storecateInfo);
    }

    @Override
    public int getItemCount() {
        return storecateInfos==null?0:storecateInfos.size();
    }

    public static class HotServiceView extends RecyclerView.ViewHolder {
        @BindView(R.id.icon)
        RoundedImageView icon;
        @BindView(R.id.text)
        TextView text;

        public HotServiceView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(BannerHotResponse.StorecateInfo storecateInfo) {
            GlideHelper.load((Activity) context, storecateInfo.getPic(), R.mipmap.client_v330_ic_homepage_circle_1, icon);
            text.setText(storecateInfo.getName());
        }
    }
}
