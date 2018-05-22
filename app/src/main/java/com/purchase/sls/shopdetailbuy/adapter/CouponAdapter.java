package com.purchase.sls.shopdetailbuy.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.R;
import com.purchase.sls.data.entity.CouponInfo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/4/28.
 */

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.CouponView> {
    private LayoutInflater layoutInflater;
    private List<CouponInfo> couponInfos;
    private String priceStr;//用户输入的金额

    public CouponAdapter() {
    }

    public void setData(String priceStr, List<CouponInfo> couponInfos) {
        this.priceStr = priceStr;
        this.couponInfos = couponInfos;
        notifyDataSetChanged();
    }

    @Override
    public CouponView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_coupon, parent, false);
        return new CouponView(view);
    }

    @Override
    public void onBindViewHolder(CouponView holder, int position) {
        final CouponInfo couponInfo = couponInfos.get(holder.getAdapterPosition());
        holder.bindData(couponInfo);
        holder.choiceItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCouponItemClick != null) {
                    onCouponItemClick.couponItemClick(couponInfo);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return couponInfos == null ? 0 : couponInfos.size();
    }

    public class CouponView extends RecyclerView.ViewHolder {
        @BindView(R.id.choice_item)
        RelativeLayout choiceItem;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.least_cost)
        TextView leastCost;
        @BindView(R.id.validday)
        TextView validday;
        @BindView(R.id.can_use_tt)
        TextView canUseTt;

        public CouponView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(CouponInfo couponInfo) {
            BigDecimal priceDecimal = new BigDecimal(priceStr).setScale(2, RoundingMode.HALF_UP);
            BigDecimal leastCostDecimal = new BigDecimal(couponInfo.getQuanInfo().getLeastCost()).setScale(2, RoundingMode.HALF_UP);
            if (priceDecimal.compareTo(leastCostDecimal) >= 0) {
                canUseTt.setSelected(true);
                choiceItem.setEnabled(true);
                canUseTt.setText("立即使用");
            } else {
                canUseTt.setSelected(false);
                choiceItem.setEnabled(false);
                canUseTt.setText("无法使用");
            }
            price.setText(couponInfo.getQuanInfo().getPrice());
            leastCost.setText("满"+couponInfo.getQuanInfo().getLeastCost()+"可用");
            validday.setText("领取后"+couponInfo.getQuanInfo().getValidday()+"天有效");
        }
    }

    public interface onCouponItemClick {
        void couponItemClick(CouponInfo couponInfo);
    }

    private onCouponItemClick onCouponItemClick;

    public void setonCouponItemClick(onCouponItemClick onCouponItemClick) {
        this.onCouponItemClick = onCouponItemClick;
    }
}
