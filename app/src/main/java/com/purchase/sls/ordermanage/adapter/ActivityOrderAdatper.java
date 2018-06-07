package com.purchase.sls.ordermanage.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.purchase.sls.R;
import com.purchase.sls.common.GlideHelper;
import com.purchase.sls.common.unit.FormatUtil;
import com.purchase.sls.common.widget.list.MoreLoadable;
import com.purchase.sls.common.widget.list.Refreshable;
import com.purchase.sls.data.entity.ActivityOrderInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/6/7.
 */

public class ActivityOrderAdatper extends RecyclerView.Adapter<ActivityOrderAdatper.ActivityOrderView> implements Refreshable<ActivityOrderInfo>, MoreLoadable<ActivityOrderInfo> {
    private LayoutInflater layoutInflater;
    private List<ActivityOrderInfo> activityOrderInfos;
    private Context context;

    public ActivityOrderAdatper(Context context) {
        this.context = context;
    }

    public void setData(List<ActivityOrderInfo> activityOrderInfos) {
        this.activityOrderInfos = activityOrderInfos;
        notifyDataSetChanged();
    }


    @Override
    public ActivityOrderView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_activity_order, parent, false);
        return new ActivityOrderView(view);
    }

    @Override
    public void onBindViewHolder(final ActivityOrderView holder, int position) {
        ActivityOrderInfo activityOrderInfo = activityOrderInfos.get(holder.getAdapterPosition());
        holder.bindData(activityOrderInfo);
        holder.deleteBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hostAction != null) {
                    hostAction.deleteOrder();
                }
            }
        });
        holder.itemLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hostAction != null) {
                    hostAction.goOrderDetail();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return activityOrderInfos == null ? 0 : activityOrderInfos.size();
    }

    @Override
    public void refresh(List<ActivityOrderInfo> list) {
        this.activityOrderInfos = list;
        notifyDataSetChanged();

    }

    @Override
    public void addMore(List<ActivityOrderInfo> list) {
        int pos = activityOrderInfos.size();
        activityOrderInfos.addAll(list);
        notifyItemRangeInserted(pos, list.size());
    }


    public class ActivityOrderView extends RecyclerView.ViewHolder {
        @BindView(R.id.order_time)
        TextView orderTime;
        @BindView(R.id.order_status)
        TextView orderStatus;
        @BindView(R.id.shop_iv)
        ImageView shopIv;
        @BindView(R.id.shop_name)
        TextView shopName;
        @BindView(R.id.energy_number)
        TextView energyNumber;
        @BindView(R.id.original_price)
        TextView originalPrice;
        @BindView(R.id.delete_bt)
        Button deleteBt;
        @BindView(R.id.item_ll)
        LinearLayout itemLl;
        @BindView(R.id.activity_type_iv)
        ImageView activityTypeIv;
        public ActivityOrderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(ActivityOrderInfo activityOrderInfo) {
            orderTime.setText("下单时间: " + FormatUtil.formatDateByLine(activityOrderInfo.getCreateTime()));
            setOrderStatus(activityOrderInfo.getStatus(), activityOrderInfo.getActType());
            GlideHelper.load((Activity) context, activityOrderInfo.getGoodsLogo(), R.mipmap.app_icon, shopIv);
            shopName.setText(activityOrderInfo.getGoodsName());
            energyNumber.setText(activityOrderInfo.getPrice()+"能量");
            originalPrice.setText("¥" + activityOrderInfo.getGoodsPrice());
            originalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            if (TextUtils.equals("2", activityOrderInfo.getStatus()) || TextUtils.equals("11", activityOrderInfo.getStatus())) {
                deleteBt.setVisibility(View.VISIBLE);
            } else {
                deleteBt.setVisibility(View.GONE);
            }
            if (TextUtils.equals("1", activityOrderInfo.getActType())) {
                activityTypeIv.setBackgroundResource(R.mipmap.spike);
            } else if (TextUtils.equals("2", activityOrderInfo.getActType())) {
                activityTypeIv.setBackgroundResource(R.mipmap.exchange);
            } else {
                activityTypeIv.setBackgroundResource(R.mipmap.lottery);
            }
        }

        //设置状态 0未收获,1已发货,2已收获,10未开将,11未中奖',
        private void setOrderStatus(String status, String actType) {
            if (TextUtils.equals("3", actType)) {
                if (TextUtils.equals("0", status)) {
                    orderStatus.setText("已中奖 待发货");
                    orderStatus.setTextColor(Color.parseColor("#FFA850"));
                } else if (TextUtils.equals("1", status)) {
                    orderStatus.setText("已中奖 待收货");
                    orderStatus.setTextColor(Color.parseColor("#E8192D"));
                } else if (TextUtils.equals("2", status)) {
                    orderStatus.setText("已中奖 已收获");
                    orderStatus.setTextColor(Color.parseColor("#198732"));
                } else if (TextUtils.equals("10", status)) {
                    orderStatus.setText("未开将");
                    orderStatus.setTextColor(Color.parseColor("#0C92C0"));
                } else if (TextUtils.equals("11", status)) {
                    orderStatus.setText("未中奖");
                }
            } else {
                if (TextUtils.equals("0", status)) {
                    orderStatus.setText("待发货");
                    orderStatus.setTextColor(Color.parseColor("#FFA850"));
                } else if (TextUtils.equals("1", status)) {
                    orderStatus.setText("待收货");
                    orderStatus.setTextColor(Color.parseColor("#E8192D"));
                } else if (TextUtils.equals("2", status)) {
                    orderStatus.setText("已收获");
                    orderStatus.setTextColor(Color.parseColor("#198732"));
                } else if (TextUtils.equals("10", status)) {
                    orderStatus.setText("未开将");
                    orderStatus.setTextColor(Color.parseColor("#0C92C0"));
                } else if (TextUtils.equals("11", status)) {
                    orderStatus.setText("未中奖");
                }
            }
        }

    }

    public interface HostAction {
        void deleteOrder();

        void goOrderDetail();
    }

    private HostAction hostAction;

    public void setHostAction(HostAction hostAction) {
        this.hostAction = hostAction;
    }
}
