package com.purchase.sls.homepage.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.purchase.sls.R;
import com.purchase.sls.common.cityList.style.citylist.bean.CityInfoBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/7/20.
 */

public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.AreaView> {
    private LayoutInflater layoutInflater;
    private List<CityInfoBean> cityInfoBeans;
    private String choiceCity;


    public void setData(List<CityInfoBean> cityInfoBeans,String choiceCity) {
        this.choiceCity=choiceCity;
        this.cityInfoBeans = cityInfoBeans;
        notifyDataSetChanged();
    }

    @Override
    public AreaView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_area, parent, false);
        return new AreaView(view);
    }

    @Override
    public void onBindViewHolder(AreaView holder, int position) {
        final CityInfoBean cityInfoBean = cityInfoBeans.get(holder.getAdapterPosition());
        if(TextUtils.isEmpty(choiceCity)){
            holder.area.setText(cityInfoBean.getName());
        }else {
            if (holder.getAdapterPosition() == 0) {
                holder.area.setText("全城");
            } else {
                holder.area.setText(cityInfoBean.getName());
            }
        }
        if(TextUtils.equals(choiceCity,cityInfoBean.getName())){
            holder.area.setSelected(true);
        }else {
            holder.area.setSelected(false);
        }
        holder.area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener!=null){
                    itemClickListener.returnArea(cityInfoBean);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cityInfoBeans == null ? 0 : cityInfoBeans.size();
    }

    public class AreaView extends RecyclerView.ViewHolder {
        @BindView(R.id.area)
        TextView area;
        public AreaView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface ItemClickListener {
        void returnArea(CityInfoBean cityInfoBean);
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
