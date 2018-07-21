package com.purchase.sls.homepage.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.purchase.sls.R;
import com.purchase.sls.common.cityList.style.citylist.sortlistview.SortModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/7/20.
 */

public class SortRvAdapter extends RecyclerView.Adapter<SortRvAdapter.SortRvView> {
    private LayoutInflater layoutInflater;
    private List<SortModel> sortModels;

    public void setData(List<SortModel> sortModels) {
        this.sortModels = sortModels;
        notifyDataSetChanged();
    }

    @Override
    public SortRvView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.sortlistview_item, parent, false);
        return new SortRvView(view);
    }

    @Override
    public void onBindViewHolder(SortRvView holder, int position) {
        final SortModel sortModel = sortModels.get(holder.getAdapterPosition());
        int section = getSectionForPosition(sortModel);

        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            holder.catalog.setVisibility(View.VISIBLE);
            holder.catalog.setText(sortModel.getSortLetters());
        } else {
            holder.catalog.setVisibility(View.GONE);
        }
        holder.title.setText(sortModel.getName());
        holder.areaItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.returnArea(sortModel);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return sortModels == null ? 0 : sortModels.size();
    }

    public class SortRvView extends RecyclerView.ViewHolder {
        @BindView(R.id.catalog)
        TextView catalog;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.area_item)
        LinearLayout areaItem;

        public SortRvView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public int getSectionForPosition(SortModel sortModel) {
        return sortModel.getSortLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getItemCount(); i++) {
            String sortStr = sortModels.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return -1;
    }

    public interface ItemClickListener {
        void returnArea(SortModel sortModel);
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
