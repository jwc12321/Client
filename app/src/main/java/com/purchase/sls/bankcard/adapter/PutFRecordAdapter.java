package com.purchase.sls.bankcard.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.purchase.sls.R;
import com.purchase.sls.common.unit.FormatUtil;
import com.purchase.sls.data.entity.BankCardInfo;
import com.purchase.sls.data.entity.PfRecrodInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/11/27.
 */

public class PutFRecordAdapter extends RecyclerView.Adapter<PutFRecordAdapter.PutFRecordView> {
    private LayoutInflater layoutInflater;
    private List<PfRecrodInfo> pfRecrodInfos;

    public void setData(List<PfRecrodInfo> pfRecrodInfos) {
        this.pfRecrodInfos = pfRecrodInfos;
        notifyDataSetChanged();
    }

    public void addMore(List<PfRecrodInfo> moreList) {
        int pos = pfRecrodInfos.size();
        pfRecrodInfos.addAll(moreList);
        notifyItemRangeInserted(pos, moreList.size());
    }

    @Override
    public PutFRecordView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_put_f_record, parent, false);
        return new PutFRecordView(view);
    }

    @Override
    public void onBindViewHolder(PutFRecordView holder, int position) {
        final PfRecrodInfo pfRecrodInfo = pfRecrodInfos.get(holder.getAdapterPosition());
        holder.bindData(pfRecrodInfo);
        holder.itemRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener!=null){
                    itemClickListener.backRecordId(pfRecrodInfo.getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return pfRecrodInfos == null ? 0 : pfRecrodInfos.size();
    }

    public class PutFRecordView extends RecyclerView.ViewHolder {
        @BindView(R.id.type)
        TextView type;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.icon)
        ImageView icon;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.bank_name)
        TextView bankName;
        @BindView(R.id.state)
        TextView state;
        @BindView(R.id.item_record)
        LinearLayout itemRecord;
        public PutFRecordView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(PfRecrodInfo pfRecrodInfo) {
            type.setText("佣金提现");
            price.setText(pfRecrodInfo.getPrice());
            time.setText(FormatUtil.formatDateByLine(pfRecrodInfo.getCreatedAt()));
            bankName.setText(pfRecrodInfo.getBankName());
            String stateStr=pfRecrodInfo.getStatus();
            if(TextUtils.equals("0",stateStr)){
                state.setText("待审核");
            }else if(TextUtils.equals("1",stateStr)){
                state.setText("审核通过");
            }else if(TextUtils.equals("2",stateStr)){
                state.setText("已打款");
            }else if(TextUtils.equals("-1",stateStr)){
                state.setText("审核未通过");
            }else if(TextUtils.equals("-2",stateStr)){
                state.setText("打款失败");
            }
            if(TextUtils.equals("2",stateStr)){
                icon.setBackgroundResource(R.mipmap.pf_success_icon);
            }else {
                icon.setBackgroundResource(R.mipmap.pf_fail_icon);
            }
        }
    }

    public interface ItemClickListener {
        void backRecordId(String id);
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

}
