package com.purchase.sls.homepage.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.purchase.sls.R;
import com.purchase.sls.data.entity.CategoriesItemInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoriesItemAdapter extends RecyclerView.Adapter<CategoriesItemAdapter.CategoriesItemView> {
    private LayoutInflater layoutInflater;
    private List<CategoriesItemInfo> categoriesItemInfos;

    public void setData(List<CategoriesItemInfo> categoriesItemInfos) {
        this.categoriesItemInfos = categoriesItemInfos;
        notifyDataSetChanged();
    }

    @Override
    public CategoriesItemView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_categories_item, parent, false);
        return new CategoriesItemView(view);
    }

    @Override
    public void onBindViewHolder(CategoriesItemView holder, int position) {
        final CategoriesItemInfo categoriesItemInfo = categoriesItemInfos.get(holder.getAdapterPosition());
        holder.bindData(categoriesItemInfo);
        holder.categoriesItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onCgItemClickListener!=null){
                    onCgItemClickListener.returnItem(categoriesItemInfo.getId(),categoriesItemInfo.getName());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoriesItemInfos == null ? 0 : categoriesItemInfos.size();
    }

    public class CategoriesItemView extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.categories_item)
        LinearLayout categoriesItem;

        public CategoriesItemView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(CategoriesItemInfo categoriesItemInfo) {
            name.setText(categoriesItemInfo.getName());
        }
    }

    public interface OnCgItemClickListener {
        void returnItem(String id, String name);
    }

    private OnCgItemClickListener onCgItemClickListener;

    public void setOnCgItemClickListener(OnCgItemClickListener onCgItemClickListener) {
        this.onCgItemClickListener = onCgItemClickListener;
    }
}
