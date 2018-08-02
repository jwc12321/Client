package com.purchase.sls.ecvoucher.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.purchase.sls.R;
import com.purchase.sls.common.unit.FormatUtil;
import com.purchase.sls.data.entity.EcVoucherItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/8/2.
 */

public class EcVoucherAdapter extends RecyclerView.Adapter<EcVoucherAdapter.EcVoucherView> {
    private LayoutInflater layoutInflater;
    private List<EcVoucherItem> ecVoucherItems;


    public void setData(List<EcVoucherItem> ecVoucherItems) {
        this.ecVoucherItems = ecVoucherItems;
        notifyDataSetChanged();
    }

    public void addMore(List<EcVoucherItem> moreList) {
        int pos = ecVoucherItems.size();
        ecVoucherItems.addAll(moreList);
        notifyItemRangeInserted(pos, moreList.size());
    }

    @Override
    public EcVoucherView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_ecvoucher, parent, false);
        return new EcVoucherView(view);
    }

    @Override
    public void onBindViewHolder(EcVoucherView holder, int position) {
        final EcVoucherItem ecVoucherItem = ecVoucherItems.get(holder.getAdapterPosition());
        holder.bindData(ecVoucherItem);
        holder.lookBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener!=null){
                    itemClickListener.golookQrCode(ecVoucherItem.getOrderNum());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return ecVoucherItems==null?0:ecVoucherItems.size();
    }

    public class EcVoucherView extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.look_bt)
        Button lookBt;
        public EcVoucherView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(EcVoucherItem ecVoucherItem) {
            name.setText(ecVoucherItem.getGoods_name());
            time.setText(FormatUtil.formatDateByLine(ecVoucherItem.getStartTime())+"-"+FormatUtil.formatDateByLine(ecVoucherItem.getEndTime()));
        }
    }

    public interface ItemClickListener {
        void golookQrCode(String orderNum);
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
