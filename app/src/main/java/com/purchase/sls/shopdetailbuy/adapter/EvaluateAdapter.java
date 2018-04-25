package com.purchase.sls.shopdetailbuy.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.purchase.sls.R;
import com.purchase.sls.common.GlideHelper;
import com.purchase.sls.common.unit.FormatUtil;
import com.purchase.sls.common.widget.MyRatingBar;
import com.purchase.sls.data.entity.EvaluateInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/4/25.
 */

public class EvaluateAdapter extends RecyclerView.Adapter<EvaluateAdapter.EvaluateView> {


    private LayoutInflater layoutInflater;
    private List<EvaluateInfo.EvaluateItemInfo> evaluateItemInfos;
    private static Context context;

    public EvaluateAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<EvaluateInfo.EvaluateItemInfo> evaluateItemInfos) {
        this.evaluateItemInfos = evaluateItemInfos;
        notifyDataSetChanged();
    }

    @Override
    public EvaluateView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_evaluate, parent, false);
        return new EvaluateView(view);
    }

    @Override
    public void onBindViewHolder(EvaluateView holder, int position) {
        EvaluateInfo.EvaluateItemInfo evaluateItemInfo = evaluateItemInfos.get(holder.getAdapterPosition());
        holder.bindData(evaluateItemInfo);
    }

    @Override
    public int getItemCount() {
        return evaluateItemInfos == null ? 0 : evaluateItemInfos.size();
    }

    public static class EvaluateView extends RecyclerView.ViewHolder {

        @BindView(R.id.people_icon)
        ImageView peopleIcon;
        @BindView(R.id.people_name)
        TextView peopleName;
        @BindView(R.id.rating_bar)
        MyRatingBar ratingBar;
        @BindView(R.id.people_time)
        TextView peopleTime;

        public EvaluateView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(EvaluateInfo.EvaluateItemInfo evaluateItemInfo) {
            List<EvaluateInfo.EvaluateItemInfo.usersInfo> usersInfos = evaluateItemInfo.getUsersInfos();
            if (usersInfos != null && usersInfos.size() > 0 && usersInfos.get(0) != null) {
                GlideHelper.load((Activity) context, usersInfos.get(0).getAvatar(), R.mipmap.client_v330_ic_homepage_circle_1, peopleIcon);
                peopleName.setText(usersInfos.get(0).getUsername());
            }
            ratingBar.setmScope(Float.parseFloat(evaluateItemInfo.getStarts()));
            peopleTime.setText(FormatUtil.formatDate(evaluateItemInfo.getCreatedAt()));
        }
    }
}
