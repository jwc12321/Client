package com.purchase.sls.coupon.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.purchase.sls.R;
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
        CouponInfo couponInfo = couponInfos.get(holder.getAdapterPosition());
        holder.bindData(couponInfo);
        holder.useBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBtClick != null) {
                    onBtClick.btClick();
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

        public CouponListView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(CouponInfo couponInfo) {
            price.setText(couponInfo.getQuanInfo().getPrice());
            leastCost.setText("满" + couponInfo.getQuanInfo().getLeastCost() + "可用");
            priceLl.setSelected(TextUtils.equals("0", availableType) ? true : false);
            businessName.setText(couponInfo.getQuanInfo().getTitle());
            businessTime.setText(couponInfo.getQuanInfo().getStarttime() + "到" + couponInfo.getQuanInfo().getEndtime());
            useBt.setVisibility(TextUtils.equals("0", availableType) ? View.VISIBLE : View.GONE);

        }
    }

    public interface OnBtClick {
        void btClick();
    }

    private OnBtClick onBtClick;

    public void setonCouponItemClick(OnBtClick onBtClick) {
        this.onBtClick = onBtClick;
    }
}
