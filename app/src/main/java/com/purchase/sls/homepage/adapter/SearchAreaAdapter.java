package com.purchase.sls.homepage.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.purchase.sls.R;
import com.purchase.sls.common.cityList.style.citylist.bean.CityInfoBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/7/20.
 */

public class SearchAreaAdapter extends RecyclerView.Adapter<SearchAreaAdapter.SearchAreaView> {
    private LayoutInflater layoutInflater;
    private List<CityInfoBean> cityInfoBeans;


    public void setData(List<CityInfoBean> cityInfoBeans) {
        this.cityInfoBeans = cityInfoBeans;
        notifyDataSetChanged();
    }

    @Override
    public SearchAreaView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_search_area, parent, false);
        return new SearchAreaView(view);
    }

    @Override
    public void onBindViewHolder(SearchAreaView holder, int position) {
        final CityInfoBean cityInfoBean = cityInfoBeans.get(holder.getAdapterPosition());
        holder.area.setText(cityInfoBean.getName());
        holder.itemArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.returnSearchArea(cityInfoBean);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cityInfoBeans == null ? 0 : cityInfoBeans.size();
    }

    public class SearchAreaView extends RecyclerView.ViewHolder {
        @BindView(R.id.area)
        TextView area;
        @BindView(R.id.item_area)
        LinearLayout itemArea;

        public SearchAreaView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface ItemClickListener {
        void returnSearchArea(CityInfoBean cityInfoBean);
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
