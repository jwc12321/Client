package com.purchase.sls.shoppingmall.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.purchase.sls.R;
import com.purchase.sls.common.GlideHelper;
import com.purchase.sls.data.entity.MallCategoryInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/12/11.
 */

public class MallCategoryAdapter extends RecyclerView.Adapter<MallCategoryAdapter.MallCategoryView> {
    private LayoutInflater layoutInflater;
    private Context context;
    private List<MallCategoryInfo> mallCategoryInfos;

    public MallCategoryAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<MallCategoryInfo> mallCategoryInfos) {
        this.mallCategoryInfos = mallCategoryInfos;
        notifyDataSetChanged();
    }

    @Override
    public MallCategoryView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_mall_category, parent, false);
        return new MallCategoryView(view);
    }

    @Override
    public void onBindViewHolder(MallCategoryView holder, int position) {
        final MallCategoryInfo mallCategoryInfo = mallCategoryInfos.get(holder.getAdapterPosition());
        holder.bindData(mallCategoryInfo);
        holder.mallCategoryLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.returnCateId(mallCategoryInfo.getId(),mallCategoryInfo.getCatename());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mallCategoryInfos == null ? 0 : mallCategoryInfos.size();
    }

    public class MallCategoryView extends RecyclerView.ViewHolder {

        @BindView(R.id.icon)
        RoundedImageView icon;
        @BindView(R.id.text)
        TextView text;
        @BindView(R.id.mallCategory_ll)
        LinearLayout mallCategoryLl;
        public MallCategoryView(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bindData(MallCategoryInfo mallCategoryInfo) {
            GlideHelper.load((Activity) context, mallCategoryInfo.getPicurl(), R.mipmap.app_icon, icon);
            text.setText(mallCategoryInfo.getCatename());
        }
    }

    public interface ItemClickListener {
        void returnCateId(String id,String name);
    }

    private ItemClickListener itemClickListener;

    public void setOnItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

}
