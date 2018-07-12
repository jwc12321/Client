package com.purchase.sls.shopdetailbuy.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.R;
import com.purchase.sls.data.entity.CouponInfo;
import com.purchase.sls.data.entity.QuanInfo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/4/28.
 */

public class ReceiveCouponAdapter extends RecyclerView.Adapter<ReceiveCouponAdapter.ReceiveCouponView> {
    private LayoutInflater layoutInflater;
    private List<QuanInfo> quanInfos;

    public void setData(List<QuanInfo> quanInfos) {
        this.quanInfos = quanInfos;
        notifyDataSetChanged();
    }

    @Override
    public ReceiveCouponView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_receive_coupon, parent, false);
        return new ReceiveCouponView(view);
    }

    @Override
    public void onBindViewHolder(final ReceiveCouponView holder, int position) {
        final QuanInfo quanInfo = quanInfos.get(holder.getAdapterPosition());
        holder.bindData(quanInfo);
        holder.choiceItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onEventClicking != null) {
                    if (TextUtils.equals("3", quanInfo.getAddSc())) {
                        onEventClicking.shopVItemClick(holder.getAdapterPosition());
                    } else {
                        onEventClicking.couponItemClick(quanInfo.getId(), holder.getAdapterPosition());
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return quanInfos == null ? 0 : quanInfos.size();
    }

    public class ReceiveCouponView extends RecyclerView.ViewHolder {
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

        public ReceiveCouponView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(QuanInfo quanInfo) {
            if (TextUtils.equals("1", quanInfo.getCanReceive())) {
                canUseTt.setSelected(true);
                choiceItem.setEnabled(true);
                canUseTt.setText("立即领取");
            } else if(TextUtils.equals("2", quanInfo.getCanReceive())){
                canUseTt.setSelected(false);
                choiceItem.setEnabled(false);
                canUseTt.setText("无法领取");
            }else {
                canUseTt.setSelected(false);
                choiceItem.setEnabled(false);
                canUseTt.setText("已领取");
            }
            price.setText(quanInfo.getPrice());
            if (TextUtils.equals("3", quanInfo.getAddSc())) {
                leastCost.setText("商城抵用券");
                validday.setText("永久");
            } else {
                leastCost.setText("满" + quanInfo.getLeastCost() + "可用");
                validday.setText("领取后" + quanInfo.getValidday() + "天有效");
            }
        }
    }

    public interface OnEventClicking {
        void couponItemClick(String id, int position);

        void shopVItemClick(int position);
    }

    private OnEventClicking onEventClicking;

    public void setOnEventClicking(OnEventClicking onEventClicking) {
        this.onEventClicking = onEventClicking;
    }
}
