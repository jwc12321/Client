package com.purchase.sls.coupon.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.R;
import com.purchase.sls.common.unit.FormatUtil;
import com.purchase.sls.common.widget.list.MoreLoadable;
import com.purchase.sls.common.widget.list.Refreshable;
import com.purchase.sls.data.entity.CouponInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/5/3.
 */

public class CouponListAdapter extends RecyclerView.Adapter<CouponListAdapter.CouponListView> implements Refreshable<CouponInfo>, MoreLoadable<CouponInfo> {
    private LayoutInflater layoutInflater;
    private List<CouponInfo> couponInfos;
    private String availableType;

    public CouponListAdapter(String availableType) {
        this.availableType = availableType;
    }

    public void setData(List<CouponInfo> couponInfos) {
        this.couponInfos = couponInfos;
        notifyDataSetChanged();
    }

    @Override
    public CouponListView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_coupon_list, parent, false);
        return new CouponListView(view);
    }

    @Override
    public void onBindViewHolder(CouponListView holder, int position) {
        final CouponInfo couponInfo = couponInfos.get(holder.getAdapterPosition());
        holder.bindData(couponInfo);
        holder.useBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBtClick != null) {
                    if (TextUtils.equals("3", couponInfo.getAddSc())) {
                        onBtClick.btClick("3");
                    } else {
                        onBtClick.btClick("0");
                    }
                }
            }
        });
        holder.couponDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onBtClick!=null){
                    onBtClick.couponDetail(couponInfo.getQid());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return couponInfos == null ? 0 : couponInfos.size();
    }

    @Override
    public void refresh(List<CouponInfo> list) {
        this.couponInfos = list;
        notifyDataSetChanged();
    }

    @Override
    public void addMore(List<CouponInfo> list) {
        int pos = couponInfos.size();
        couponInfos.addAll(list);
        notifyItemRangeInserted(pos, list.size());
    }

    public class CouponListView extends RecyclerView.ViewHolder {
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.least_cost)
        TextView leastCost;
        @BindView(R.id.price_ll)
        LinearLayout priceLl;
        @BindView(R.id.business_name)
        TextView businessName;
        @BindView(R.id.business_time)
        TextView businessTime;
        @BindView(R.id.use_bt)
        Button useBt;
        @BindView(R.id.coupon_detail)
        RelativeLayout couponDetail;

        public CouponListView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(CouponInfo couponInfo) {
            price.setText(couponInfo.getQuanInfo().getPrice());
            if (TextUtils.equals("3", couponInfo.getAddSc())) {
                leastCost.setText("抵金劵");
                businessTime.setText("永久");
            } else {
                leastCost.setText("满" + couponInfo.getQuanInfo().getLeastCost() + "可用");
                businessTime.setText(couponInfo.getQuanInfo().getStarttime() + "到" + FormatUtil.formatDateYear(couponInfo.getExpire_at()));
            }
            priceLl.setSelected(TextUtils.equals("0", availableType) ? true : false);
            businessName.setText(couponInfo.getQuanInfo().getTitle());
            if (TextUtils.equals("0", couponInfo.getStatus())) {
                useBt.setEnabled(true);
                useBt.setText("立即使用");
                useBt.setTextColor(Color.rgb(255, 101, 40));
                couponDetail.setEnabled(true);
            } else if (TextUtils.equals("1", couponInfo.getStatus())) {
                useBt.setEnabled(false);
                useBt.setText("已使用");
                useBt.setTextColor(Color.rgb(224, 224, 224));
                couponDetail.setEnabled(false);
            } else {
                useBt.setEnabled(false);
                useBt.setText("已失效");
                useBt.setTextColor(Color.rgb(224, 224, 224));
                couponDetail.setEnabled(false);
            }
        }
    }

    public interface OnBtClick {
        void btClick(String mainGo);

        void couponDetail(String qid);
    }

    private OnBtClick onBtClick;

    public void setonCouponItemClick(OnBtClick onBtClick) {
        this.onBtClick = onBtClick;
    }
}
