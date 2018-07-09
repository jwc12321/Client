package com.purchase.sls.goodsordermanage.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.purchase.sls.R;
import com.purchase.sls.common.GlideHelper;
import com.purchase.sls.data.entity.GoodsInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/7/9.
 */

public class GoodsOrderItemAdapter extends RecyclerView.Adapter<GoodsOrderItemAdapter.GoodsOrderItemView> {
    private LayoutInflater layoutInflater;
    private List<GoodsInfo> goodsInfos;
    private Context context;
    private String orderNum;

    public GoodsOrderItemAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<GoodsInfo> goodsInfos,String orderNum) {
        this.orderNum=orderNum;
        this.goodsInfos = goodsInfos;
        notifyDataSetChanged();
    }

    @Override
    public GoodsOrderItemView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_goods_order_item, parent, false);
        return new GoodsOrderItemView(view);
    }

    @Override
    public void onBindViewHolder(GoodsOrderItemView holder, int position) {
        GoodsInfo goodsInfo = goodsInfos.get(holder.getAdapterPosition());
        holder.bindData(goodsInfo);
        holder.orderItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener!=null){
                    itemClickListener.goOrderDetail(orderNum);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return goodsInfos == null ? 0 : goodsInfos.size();
    }

    public class GoodsOrderItemView extends RecyclerView.ViewHolder {
        @BindView(R.id.photo)
        RoundedImageView photo;
        @BindView(R.id.goods_name)
        TextView goodsName;
        @BindView(R.id.goods_spec)
        TextView goodsSpec;
        @BindView(R.id.reduce_number)
        ImageView reduceNumber;
        @BindView(R.id.goods_count)
        TextView goodsCount;
        @BindView(R.id.order_item)
        RelativeLayout orderItem;

        public GoodsOrderItemView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(GoodsInfo goodsInfo) {
            GlideHelper.load((Activity) context, goodsInfo.getGoodsImg(), R.mipmap.app_icon, photo);
            goodsName.setText(goodsInfo.getGoodsName());
            goodsSpec.setText(goodsInfo.getSkuinfo());
            goodsCount.setText(goodsInfo.getGoodsnum());
        }
    }

    public interface ItemClickListener {
        void goOrderDetail(String orderNum);//订单详情
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
