package com.purchase.sls.shoppingmall.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.purchase.sls.R;
import com.purchase.sls.common.GlideHelper;
import com.purchase.sls.data.entity.ShoppingCartInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/7/4.
 */

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ShoppingCartView> {
    private LayoutInflater layoutInflater;
    private Context context;
    private String type;
    private List<ShoppingCartInfo> shoppingCartInfos;

    public ShoppingCartAdapter(Context context) {
        this.context = context;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setData(List<ShoppingCartInfo> shoppingCartInfos) {
        this.shoppingCartInfos = shoppingCartInfos;
        notifyDataSetChanged();
    }


    @Override
    public ShoppingCartView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_shopping_cart, parent, false);
        return new ShoppingCartView(view);
    }

    @Override
    public void onBindViewHolder(final ShoppingCartView holder, int position) {
        ShoppingCartInfo shoppingCartInfo = shoppingCartInfos.get(holder.getAdapterPosition());
        holder.bindData(shoppingCartInfo);
        holder.choiceItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemClickListener.checkGroup(holder.getAdapterPosition(), ((CheckBox) v).isChecked());//向外暴露接口
                    }
                }
        );
        holder.addNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener!=null){
                    itemClickListener.doIncrease(holder.getAdapterPosition(),holder.goodsCount,holder.choiceItem.isChecked());
                }
            }
        });
        holder.reduceNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener!=null){
                    itemClickListener.doReduce(holder.getAdapterPosition(),holder.goodsCount,holder.choiceItem.isChecked());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return shoppingCartInfos == null ? 0 : shoppingCartInfos.size();
    }

    public class ShoppingCartView extends RecyclerView.ViewHolder {
        @BindView(R.id.choice_item)
        CheckBox choiceItem;
        @BindView(R.id.photo)
        RoundedImageView photo;
        @BindView(R.id.goods_name)
        TextView goodsName;
        @BindView(R.id.goods_spec)
        TextView goodsSpec;
        @BindView(R.id.goods_price)
        TextView goodsPrice;
        @BindView(R.id.add_number)
        ImageView addNumber;
        @BindView(R.id.goods_count)
        TextView goodsCount;
        @BindView(R.id.reduce_number)
        ImageView reduceNumber;

        public ShoppingCartView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(ShoppingCartInfo shoppingCartInfo) {
            GlideHelper.load((Activity) context, shoppingCartInfo.getUrl(), R.mipmap.app_icon, photo);
            goodsName.setText(shoppingCartInfo.getName());
            goodsCount.setText(shoppingCartInfo.getCount()+"");
            goodsPrice.setText(shoppingCartInfo.getPrice());
            boolean choosed = shoppingCartInfo.isChoosed();
            if (choosed){
                choiceItem.setChecked(true);
            }else{
                choiceItem.setChecked(false);
            }
        }
    }

    public interface ItemClickListener {
        void checkGroup(int position, boolean isChecked);
        void doIncrease(int position, View showCountView, boolean isChecked);
        void doReduce(int position, View showCountView, boolean isChecked);
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
