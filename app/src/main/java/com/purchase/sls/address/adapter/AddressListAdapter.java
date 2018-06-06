package com.purchase.sls.address.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.purchase.sls.R;
import com.purchase.sls.data.entity.AddressInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/5/30.
 */

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.AddressListView> {
    private LayoutInflater layoutInflater;
    private List<AddressInfo> addressInfos;

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
        final AddressInfo addressInfo = addressInfos.get(holder.getAdapterPosition());
        holder.bindData(addressInfo);
        holder.checkDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onEventClickListener != null) {
                    onEventClickListener.checkDfAdderss(addressInfo.getId());
                }
            }
        });
        holder.deleteAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onEventClickListener != null) {
                    onEventClickListener.deleteAddress(addressInfo.getId());
                }
            }
        });
        holder.editAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onEventClickListener != null) {
                    onEventClickListener.editAddress(addressInfo);
                }
            }
        });
        holder.backAddressLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onEventClickListener != null) {
                    onEventClickListener.backAddress(addressInfo);
                }
            }
        });
    }

    public void setData(List<AddressInfo> addressInfos) {
        this.addressInfos = addressInfos;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return addressInfos == null ? 0 : addressInfos.size();
    }

    public class AddressListView extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.tel)
        TextView tel;
        @BindView(R.id.address)
        TextView address;
        @BindView(R.id.check_default)
        ImageView checkDefault;
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

        public void bindData(AddressInfo addressInfo) {
            name.setText(addressInfo.getUsername());
            tel.setText(addressInfo.getTel());
            address.setText(addressInfo.getProvince() + addressInfo.getCity() + addressInfo.getCountry() + addressInfo.getAddress());
            if (TextUtils.equals("1", addressInfo.getType())) {
                checkDefault.setSelected(true);
            } else {
                checkDefault.setSelected(false);
            }
        }
    }

    public interface OnEventClickListener {
        void checkDfAdderss(String id);//设置为默认地址

        void deleteAddress(String id);//删除地址

        void editAddress(AddressInfo addressInfo);//编辑地址

        void backAddress(AddressInfo addressInfo);//返回地址
    }

    private OnEventClickListener onEventClickListener;

    public void setOnEventClickListener(OnEventClickListener onEventClickListener) {
        this.onEventClickListener = onEventClickListener;
    }
}
