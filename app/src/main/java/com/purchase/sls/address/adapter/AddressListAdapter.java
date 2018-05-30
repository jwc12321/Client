package com.purchase.sls.address.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.purchase.sls.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/5/30.
 */

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.AddressListView> {
    private LayoutInflater layoutInflater;

    @Override
    public AddressListView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_address_list, parent, false);
        return new AddressListView(view);
    }

    @Override
    public void onBindViewHolder(AddressListView holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class AddressListView extends RecyclerView.ViewHolder {
        @BindView(R.id.agreement_check)
        CheckBox agreementCheck;
        @BindView(R.id.delete_address)
        Button deleteAddress;
        @BindView(R.id.edit_address)
        Button editAddress;
        @BindView(R.id.back_address_ll)
        LinearLayout backAddressLl;

        public AddressListView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData() {
        }
    }

    public interface OnEventClickListener {
        void checkDfAdderss();//设置为默认地址

        void deleteAddress();//删除地址

        void editAddress();//编辑地址

        void backAddress();//返回地址
    }

    private OnEventClickListener onEventClickListener;

    public void setOnEventClickListener(OnEventClickListener onEventClickListener) {
        this.onEventClickListener = onEventClickListener;
    }
}
