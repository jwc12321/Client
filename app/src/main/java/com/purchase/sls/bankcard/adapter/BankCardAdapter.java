package com.purchase.sls.bankcard.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.R;
import com.purchase.sls.data.entity.BankCardInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/11/27.
 */

public class BankCardAdapter extends RecyclerView.Adapter<BankCardAdapter.BankCardView> {
    private LayoutInflater layoutInflater;
    private List<BankCardInfo> bankCardInfos;

    public void setData(List<BankCardInfo> bankCardInfos) {
        this.bankCardInfos = bankCardInfos;
        notifyDataSetChanged();
    }

    @Override
    public BankCardView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_bankcard, parent, false);
        return new BankCardView(view);
    }

    @Override
    public void onBindViewHolder(BankCardView holder, int position) {
        final BankCardInfo bankCardInfo = bankCardInfos.get(holder.getAdapterPosition());
        if (position % 3 == 0) {
            holder.cardBg.setBackgroundResource(R.mipmap.first_bankcard_icon);
        }else if(position % 3 == 1){
            holder.cardBg.setBackgroundResource(R.mipmap.second_bankcard_icon);
        }else {
            holder.cardBg.setBackgroundResource(R.mipmap.third_bankcard_icon);
        }
        holder.bindData(bankCardInfo);
        holder.itemBankcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.backBankInfo(bankCardInfo);
                }
            }
        });
        holder.untying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.untyingBank(bankCardInfo.getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return bankCardInfos == null ? 0 : bankCardInfos.size();
    }

    public class BankCardView extends RecyclerView.ViewHolder {
        @BindView(R.id.untying)
        TextView untying;
        @BindView(R.id.bank_name)
        TextView bankName;
        @BindView(R.id.bank_type)
        TextView bankType;
        @BindView(R.id.card_number)
        TextView cardNumber;
        @BindView(R.id.item_bankcard)
        LinearLayout itemBankcard;
        @BindView(R.id.card_bg)
        RelativeLayout cardBg;

        public BankCardView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(BankCardInfo bankCardInfo) {
            bankName.setText(bankCardInfo.getBankName());
            String number = bankCardInfo.getBankNumber();
            if(!TextUtils.isEmpty(number)) {
                cardNumber.setText(number.substring(number.length()-4, number.length()));
            }
        }
    }


    public interface ItemClickListener {
        void backBankInfo(BankCardInfo bankCardInfo);

        void untyingBank(String id);
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
