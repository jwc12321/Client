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
import com.purchase.sls.data.entity.TopCateInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/4/20.
 */

public class TopCateAdapter extends RecyclerView.Adapter<TopCateAdapter.HotServiceView> {
    private LayoutInflater layoutInflater;
    private Context context;
    private List<TopCateInfo> topCateInfos;

    public TopCateAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<TopCateInfo> topCateInfos) {
        this.topCateInfos = topCateInfos;
        notifyDataSetChanged();
    }

    @Override
    public HotServiceView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_topcate, parent, false);
        return new HotServiceView(view);
    }

    @Override
    public void onBindViewHolder(HotServiceView holder, int position) {
        final TopCateInfo topCateInfo = topCateInfos.get(holder.getAdapterPosition());
        holder.bindData(topCateInfo);
        holder.hotItemLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTopCateItemClickListener.topCateItemClickListener(topCateInfo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return topCateInfos == null ? 0 : topCateInfos.size();
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

        public void bindData(TopCateInfo topCateInfo) {
            GlideHelper.load((Activity) context, topCateInfo.getPic(), R.mipmap.app_icon, icon);
            text.setText(topCateInfo.getName());
        }
    }

    public interface OnTopCateItemClickListener {
        void topCateItemClickListener(TopCateInfo topCateInfo);
    }

    private OnTopCateItemClickListener onTopCateItemClickListener;

    public void setOnTopCateItemClickListener(OnTopCateItemClickListener onTopCateItemClickListener) {
        this.onTopCateItemClickListener = onTopCateItemClickListener;
    }

}
