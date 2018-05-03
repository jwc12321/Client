package com.purchase.sls.energy.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.purchase.sls.R;
import com.purchase.sls.common.unit.FormatUtil;
import com.purchase.sls.data.entity.EnergyIncomeDetail;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/5/3.
 */

public class EnergyItemAdapter extends RecyclerView.Adapter<EnergyItemAdapter.EnergyItemView> {

    private LayoutInflater layoutInflater;
    private List<EnergyIncomeDetail> energyIncomeDetails;

    public void setData(List<EnergyIncomeDetail> energyIncomeDetails) {
        this.energyIncomeDetails = energyIncomeDetails;
        notifyDataSetChanged();
    }

    public void addMore(List<EnergyIncomeDetail> moreList) {
        int pos = energyIncomeDetails.size();
        energyIncomeDetails.addAll(moreList);
        notifyItemRangeInserted(pos, moreList.size());
    }

    @Override
    public EnergyItemView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_energy_item, parent, false);
        return new EnergyItemView(view);
    }

    @Override
    public void onBindViewHolder(EnergyItemView holder, int position) {
        EnergyIncomeDetail energyIncomeDetail = energyIncomeDetails.get(holder.getAdapterPosition());
        holder.bindData(energyIncomeDetail);
    }

    @Override
    public int getItemCount() {
        return energyIncomeDetails == null ? 0 : energyIncomeDetails.size();
    }

    public class EnergyItemView extends RecyclerView.ViewHolder {
        @BindView(R.id.business_name)
        TextView businessName;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.number)
        TextView number;

        public EnergyItemView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(EnergyIncomeDetail energyIncomeDetail) {
            businessName.setText(energyIncomeDetail.getRemarks());
            time.setText(FormatUtil.formatDateByLine(energyIncomeDetail.getCreatedAt()));
            number.setText(energyIncomeDetail.getAmount());
        }
    }
}
