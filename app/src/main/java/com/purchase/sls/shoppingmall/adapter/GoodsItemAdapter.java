package com.purchase.sls.shoppingmall.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.purchase.sls.R;
import com.purchase.sls.common.GlideHelper;
import com.purchase.sls.data.entity.GoodsItemInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/7/4.
 */

public class GoodsItemAdapter extends RecyclerView.Adapter<GoodsItemAdapter.GoodsItemView> {
    private LayoutInflater layoutInflater;
    private List<GoodsItemInfo> goodsItemInfos;
    private Context context;

    public GoodsItemAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<GoodsItemInfo> goodsItemInfos) {
        this.goodsItemInfos = goodsItemInfos;
        notifyDataSetChanged();
    }

    public void addMore(List<GoodsItemInfo> moreList) {
        int pos = goodsItemInfos.size();
        goodsItemInfos.addAll(moreList);
        notifyItemRangeInserted(pos, moreList.size());
    }

    @Override
    public GoodsItemView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_goods_rd, parent, false);
        return new GoodsItemView(view);
    }

    @Override
    public void onBindViewHolder(GoodsItemView holder, int position) {
        final GoodsItemInfo goodsItemInfo = goodsItemInfos.get(holder.getAdapterPosition());
        holder.bindData(goodsItemInfo);
        holder.goodsItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.goGoodsDetail(goodsItemInfo.getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return goodsItemInfos == null ? 0 : goodsItemInfos.size();
    }

    public class GoodsItemView extends RecyclerView.ViewHolder {
        @BindView(R.id.goods_item)
        LinearLayout goodsItem;
        @BindView(R.id.photo)
        RoundedImageView photo;
        @BindView(R.id.goods_name)
        TextView goodsName;
        @BindView(R.id.goods_price)
        TextView goodsPrice;
        @BindView(R.id.goods_voucher)
        TextView goodsVoucher;
        @BindView(R.id.commission)
        TextView commission;

        public GoodsItemView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(GoodsItemInfo goodsItemInfo) {
            GlideHelper.load((Activity) context, goodsItemInfo.getGoodsImg(), R.mipmap.no_url_icon, photo);
            goodsName.setText(goodsItemInfo.getGoodsName());
            goodsPrice.setText("¥" + goodsItemInfo.getPrice());
            if(TextUtils.isEmpty(goodsItemInfo.getQuanPrice())
                    ||TextUtils.equals("0",goodsItemInfo.getQuanPrice())
                    ||TextUtils.equals("0.0",goodsItemInfo.getQuanPrice())
                    ||TextUtils.equals("0.00",goodsItemInfo.getQuanPrice())){
                goodsVoucher.setVisibility(View.INVISIBLE);
            }else {
                goodsVoucher.setVisibility(View.VISIBLE);
                goodsVoucher.setText("劵可减" + goodsItemInfo.getQuanPrice());
            }
            if (TextUtils.isEmpty(goodsItemInfo.getGoodsMostCommission())||TextUtils.equals("0",goodsItemInfo.getGoodsMostCommission())
                    ||TextUtils.equals("0.0",goodsItemInfo.getGoodsMostCommission())
                    ||TextUtils.equals("0.00",goodsItemInfo.getGoodsMostCommission())){
                commission.setVisibility(View.INVISIBLE);
            }else {
                commission.setVisibility(View.VISIBLE);
                commission.setText("最高可获得佣金"+goodsItemInfo.getGoodsMostCommission()+"元");
            }
        }
    }

    public interface OnItemClickListener {
        void goGoodsDetail(String goodsid);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
