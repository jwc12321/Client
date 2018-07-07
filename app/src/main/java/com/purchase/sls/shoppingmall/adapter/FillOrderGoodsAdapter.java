package com.purchase.sls.shoppingmall.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.purchase.sls.R;
import com.purchase.sls.common.GlideHelper;
import com.purchase.sls.data.entity.GoodsOrderInfo;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/7/7.
 */

public class FillOrderGoodsAdapter extends RecyclerView.Adapter<FillOrderGoodsAdapter.FillOrderGoodsView> {
    private LayoutInflater layoutInflater;
    private Context context;
    private List<GoodsOrderInfo> goodsOrderInfos;

    public FillOrderGoodsAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<GoodsOrderInfo> goodsOrderInfos) {
        this.goodsOrderInfos = goodsOrderInfos;
        notifyDataSetChanged();
    }

    @Override
    public FillOrderGoodsView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_fill_order_goods, parent, false);
        return new FillOrderGoodsView(view);
    }

    @Override
    public void onBindViewHolder(FillOrderGoodsView holder, int position) {
        GoodsOrderInfo goodsOrderInfo = goodsOrderInfos.get(holder.getAdapterPosition());
        holder.bindData(goodsOrderInfo);
    }

    @Override
    public int getItemCount() {
        return goodsOrderInfos == null ? 0 : goodsOrderInfos.size();
    }

    public class FillOrderGoodsView extends RecyclerView.ViewHolder {
        @BindView(R.id.photo)
        RoundedImageView photo;
        @BindView(R.id.goods_name)
        TextView goodsName;
        @BindView(R.id.goods_spec)
        TextView goodsSpec;
        @BindView(R.id.goods_price)
        TextView goodsPrice;
        @BindView(R.id.reduce_number)
        ImageView reduceNumber;
        @BindView(R.id.goods_count)
        TextView goodsCount;
        public FillOrderGoodsView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(GoodsOrderInfo goodsOrderInfo) {
            GlideHelper.load((Activity) context, goodsOrderInfo.getGoodsImg(), R.mipmap.app_icon, photo);
            goodsName.setText(goodsOrderInfo.getGoodsName());
            goodsSpec.setText(goodsOrderInfo.getSkuinfo());
            goodsCount.setText(goodsOrderInfo.getGoodsnum());
            goodsPrice.setText("¥ " + goodsOrderInfo.getPrice());
        }
    }
}
