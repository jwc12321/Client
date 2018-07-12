package com.purchase.sls.goodsordermanage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.R;
import com.purchase.sls.common.widget.list.MoreLoadable;
import com.purchase.sls.common.widget.list.Refreshable;
import com.purchase.sls.data.entity.GoodsOrderItemInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/7/9.
 */

public class GoodsOrderAdapter extends RecyclerView.Adapter<GoodsOrderAdapter.GoodsOrderView> implements Refreshable<GoodsOrderItemInfo>, MoreLoadable<GoodsOrderItemInfo> {
    private LayoutInflater layoutInflater;
    private List<GoodsOrderItemInfo> goodsOrderItemInfos;
    private Context context;

    public GoodsOrderAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<GoodsOrderItemInfo> goodsOrderItemInfos) {
        this.goodsOrderItemInfos = goodsOrderItemInfos;
        notifyDataSetChanged();
    }

    @Override
    public GoodsOrderView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_goods_order, parent, false);
        return new GoodsOrderView(view);
    }

    @Override
    public void onBindViewHolder(final GoodsOrderView holder, int position) {
        final GoodsOrderItemInfo goodsOrderItemInfo = goodsOrderItemInfos.get(holder.getAdapterPosition());
        holder.bindData(goodsOrderItemInfo);
        holder.goodsItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hostAction != null) {
                    hostAction.goOrderDetail(goodsOrderItemInfo.getOrdernum());
                }
            }
        });
        holder.payBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hostAction != null) {
                    if (TextUtils.equals("0", goodsOrderItemInfo.getType()) && TextUtils.equals("付款", holder.payBt.getText().toString())) {
                        hostAction.payOrder(goodsOrderItemInfo.getOrdernum());
                    } else if (TextUtils.equals("2", goodsOrderItemInfo.getType()) && TextUtils.equals("确认收货", holder.payBt.getText().toString())) {
                        hostAction.completeOrder(goodsOrderItemInfo.getOrdernum());
                    } else if (TextUtils.equals("3", goodsOrderItemInfo.getType()) && TextUtils.equals("删除订单", holder.payBt.getText().toString())) {
                        hostAction.deleteOrder(goodsOrderItemInfo.getOrdernum());
                    }else if (TextUtils.equals("4", goodsOrderItemInfo.getType()) && TextUtils.equals("删除订单", holder.payBt.getText().toString())) {
                        hostAction.deleteOrder(goodsOrderItemInfo.getOrdernum());
                    }
                }
            }
        });

        holder.seeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hostAction != null) {
                    if (TextUtils.equals("0", goodsOrderItemInfo.getType()) && TextUtils.equals("取消订单", holder.seeBt.getText().toString())) {
                        hostAction.cancelOrder(goodsOrderItemInfo.getOrdernum());
                    } else if (TextUtils.equals("2", goodsOrderItemInfo.getType()) && TextUtils.equals("查看物流", holder.seeBt.getText().toString())) {
                        if (goodsOrderItemInfo.getGoodsInfos() != null && goodsOrderItemInfo.getGoodsInfos().size() > 0) {
                            hostAction.seeLogistics(goodsOrderItemInfo.getOrdernum(), goodsOrderItemInfo.getGoodsInfos().get(0).getWuliu());
                        }
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return goodsOrderItemInfos == null ? 0 : goodsOrderItemInfos.size();
    }

    @Override
    public void refresh(List<GoodsOrderItemInfo> list) {
        this.goodsOrderItemInfos = list;
        notifyDataSetChanged();
    }

    @Override
    public void addMore(List<GoodsOrderItemInfo> list) {
        int pos = goodsOrderItemInfos.size();
        goodsOrderItemInfos.addAll(list);
        notifyItemRangeInserted(pos, list.size());
    }

    public class GoodsOrderView extends RecyclerView.ViewHolder implements GoodsOrderItemAdapter.ItemClickListener {
        @BindView(R.id.goods_rv)
        RecyclerView goodsRv;
        @BindView(R.id.goods_price)
        TextView goodsPrice;
        @BindView(R.id.goods_type)
        TextView goodsType;
        @BindView(R.id.pay_bt)
        Button payBt;
        @BindView(R.id.see_bt)
        Button seeBt;
        @BindView(R.id.goods_item)
        RelativeLayout goodsItem;

        private GoodsOrderItemAdapter goodsOrderItemAdapter;

        public GoodsOrderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            goodsOrderItemAdapter = new GoodsOrderItemAdapter(context);
            goodsOrderItemAdapter.setItemClickListener(this);
            goodsRv.setAdapter(goodsOrderItemAdapter);
        }

        public void bindData(GoodsOrderItemInfo goodsOrderItemInfo) {
            goodsPrice.setText("¥" + goodsOrderItemInfo.getOprice());
            goodsOrderItemAdapter.setData(goodsOrderItemInfo.getGoodsInfos(), goodsOrderItemInfo.getOrdernum());
            buttonType(goodsOrderItemInfo.getType());
        }

        //0未付款1已付款2已发货3完成订单
        private void buttonType(String type) {
            if (TextUtils.equals("0", type)) {
                payBt.setVisibility(View.VISIBLE);
                seeBt.setVisibility(View.VISIBLE);
                payBt.setText("付款");
                seeBt.setText("取消订单");
                goodsType.setVisibility(View.VISIBLE);
                goodsType.setText("待付款");
            } else if (TextUtils.equals("1", type)) {
                payBt.setVisibility(View.GONE);
                seeBt.setVisibility(View.GONE);
                goodsType.setVisibility(View.GONE);
                goodsType.setText("");
            } else if (TextUtils.equals("2", type)) {
                payBt.setVisibility(View.VISIBLE);
                seeBt.setVisibility(View.VISIBLE);
                goodsType.setVisibility(View.VISIBLE);
                payBt.setText("确认收货");
                payBt.setText("查看物流");
                goodsType.setText("待收货");
            } else if (TextUtils.equals("3", type)) {
                payBt.setVisibility(View.VISIBLE);
                seeBt.setVisibility(View.GONE);
                goodsType.setVisibility(View.VISIBLE);
                payBt.setText("删除订单");
                goodsType.setText("已完成");
            } else if (TextUtils.equals("4", type)) {
                payBt.setVisibility(View.VISIBLE);
                seeBt.setVisibility(View.GONE);
                goodsType.setVisibility(View.VISIBLE);
                payBt.setText("删除订单");
                goodsType.setText("已取消");
            }
        }

        @Override
        public void goOrderDetail(String orderNum) {
            if (hostAction != null) {
                hostAction.goOrderDetail(orderNum);
            }
        }
    }


    public interface HostAction {
        void cancelOrder(String orderNum);//取消订单

        void payOrder(String orderNum);//支付

        void seeLogistics(String orderNum, String expressName);//查看物流

        void completeOrder(String orderNum);//完成订单

        void deleteOrder(String orderNum);//删除订单

        void goOrderDetail(String orderNum);//订单详情
    }

    private HostAction hostAction;

    public void setHostAction(HostAction hostAction) {
        this.hostAction = hostAction;
    }
}
