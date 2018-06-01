package com.purchase.sls.energy.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.purchase.sls.R;

import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/6/1.
 */

public class SpikeAdapter extends RecyclerView.Adapter<SpikeAdapter.SpikeVeiw> {
    private LayoutInflater layoutInflater;

    public void setData() {
        notifyDataSetChanged();
    }

    @Override
    public SpikeVeiw onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_spike, parent, false);
        return new SpikeVeiw(view);
    }

    @Override
    public void onBindViewHolder(SpikeVeiw holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class SpikeVeiw extends RecyclerView.ViewHolder {

        public SpikeVeiw(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData() {

        }
    }

    public interface OnSpikeItemClickListener {
    }

    private OnSpikeItemClickListener onSpikeItemClickListener;

    public void setOnSpikeItemClickListener(OnSpikeItemClickListener onSpikeItemClickListener) {
        this.onSpikeItemClickListener = onSpikeItemClickListener;
    }
}
