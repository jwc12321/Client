package com.purchase.sls.account.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.purchase.sls.R;
import com.purchase.sls.common.GlideHelper;
import com.purchase.sls.common.unit.FormatUtil;
import com.purchase.sls.data.entity.AccountItemInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/5/2.
 */

public class AccountListAdapter extends RecyclerView.Adapter<AccountListAdapter.AccountListView> {
    private LayoutInflater layoutInflater;
    private List<AccountItemInfo> accountItemInfos;
    private static Context context;

    public AccountListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<AccountItemInfo> accountItemInfos) {
        this.accountItemInfos = accountItemInfos;
        notifyDataSetChanged();
    }

    public void addMore(List<AccountItemInfo> moreList) {
        int pos = accountItemInfos.size();
        accountItemInfos.addAll(moreList);
        notifyItemRangeInserted(pos, moreList.size());
    }

    @Override
    public AccountListView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_account_list, parent, false);
        return new AccountListView(view);
    }

    @Override
    public void onBindViewHolder(AccountListView holder, int position) {
        final AccountItemInfo accountItemInfo = accountItemInfos.get(holder.getAdapterPosition());
        holder.bindData(accountItemInfo);
        holder.itemRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onAccountItemClickListener!=null){
                    onAccountItemClickListener.goAccountDetail(accountItemInfo.getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return accountItemInfos == null ? 0 : accountItemInfos.size();
    }

    public class AccountListView extends RecyclerView.ViewHolder {
        @BindView(R.id.item_rl)
        RelativeLayout itemRl;
        @BindView(R.id.photo)
        RoundedImageView photo;
        @BindView(R.id.business_nama)
        TextView businessNama;
        @BindView(R.id.business_time)
        TextView businessTime;
        @BindView(R.id.business_price)
        TextView businessPrice;

        public AccountListView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(AccountItemInfo accountItemInfo) {
            GlideHelper.load((Activity) context, accountItemInfo.getzPics(), R.mipmap.client_v330_ic_homepage_circle_1, photo);
            businessNama.setText(accountItemInfo.getTitle());
            businessTime.setText(FormatUtil.formatNewDate(accountItemInfo.getCreatedAt(), "MM月dd日"));
            businessPrice.setText("-" + accountItemInfo.getPrice());
        }
    }


    public interface OnAccountItemClickListener {
        void goAccountDetail(String billid);
    }

    private OnAccountItemClickListener onAccountItemClickListener;

    public void setOnCollectionItemClickListener(OnAccountItemClickListener onAccountItemClickListener) {
        this.onAccountItemClickListener = onAccountItemClickListener;
    }
}
