package com.purchase.sls.shoppingmall.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.purchase.sls.R;
import com.purchase.sls.data.entity.GoodsParentInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/7/4.
 */

public class GoodsParentAdapter extends RecyclerView.Adapter<GoodsParentAdapter.GoodsParentView> {
    private LayoutInflater layoutInflater;
    private List<GoodsParentInfo> goodsParentInfos;
    private int selectPosition=0;

    public void setData(List<GoodsParentInfo> goodsParentInfos) {
        this.goodsParentInfos = goodsParentInfos;
        notifyDataSetChanged();
    }

    public void setPosittion(int lastSelectPosition,int selectPosition) {
        this.selectPosition = selectPosition;
        notifyItemChanged(lastSelectPosition);
        notifyItemChanged(selectPosition);
    }

    @Override
    public GoodsParentView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_goods_parent, parent, false);
        return new GoodsParentView(view);
    }

    @Override
    public void onBindViewHolder(final GoodsParentView holder, int position) {
        final GoodsParentInfo goodsParentInfo = goodsParentInfos.get(holder.getAdapterPosition());
        holder.goodsParentName.setText(goodsParentInfo.getCatename());
        if(position==selectPosition){
            holder.parent.setBackgroundResource(R.color.appText1);
            holder.choiceIv.setVisibility(View.VISIBLE);
        }else {
            holder.parent.setBackgroundResource(R.color.backGround23);
            holder.choiceIv.setVisibility(View.INVISIBLE);
        }
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onParentClickListener!=null){
                    onParentClickListener.choiceWhat(holder.getAdapterPosition(),goodsParentInfo.getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return goodsParentInfos == null ? 0 : goodsParentInfos.size();
    }

    public class GoodsParentView extends RecyclerView.ViewHolder {
        @BindView(R.id.choice_iv)
        ImageView choiceIv;
        @BindView(R.id.goods_parent_name)
        TextView goodsParentName;
        @BindView(R.id.parent)
        LinearLayout parent;

        public GoodsParentView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnParentClickListener {
        void choiceWhat(int position,String goodsid);
    }

    private OnParentClickListener onParentClickListener;

    public void setOnParentClickListener(OnParentClickListener onParentClickListener) {
        this.onParentClickListener = onParentClickListener;
    }

}
