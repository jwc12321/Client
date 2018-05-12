package com.purchase.sls.evaluate.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.purchase.sls.R;
import com.purchase.sls.common.GlideHelper;
import com.purchase.sls.common.unit.FormatUtil;
import com.purchase.sls.data.entity.ToBeEvaluationInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/5/5.
 */

public class ToBeEvaluateAdapter extends RecyclerView.Adapter<ToBeEvaluateAdapter.ToBeEvaluateView> {
    @BindView(R.id.photo)
    RoundedImageView photo;
    @BindView(R.id.business_name)
    TextView businessName;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.use_bt)
    Button useBt;
    @BindView(R.id.item_rl)
    RelativeLayout itemRl;
    private LayoutInflater layoutInflater;
    private List<ToBeEvaluationInfo.ToBeEvaluationItem> toBeEvaluationItems;
    private static Context context;

    public ToBeEvaluateAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ToBeEvaluationInfo.ToBeEvaluationItem> toBeEvaluationItems) {
        this.toBeEvaluationItems = toBeEvaluationItems;
        notifyDataSetChanged();
    }

    public void addMore(List<ToBeEvaluationInfo.ToBeEvaluationItem> moreList) {
        int pos = toBeEvaluationItems.size();
        toBeEvaluationItems.addAll(moreList);
        notifyItemRangeInserted(pos, moreList.size());
    }

    @Override
    public ToBeEvaluateView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_to_be_evaluate, parent, false);
        return new ToBeEvaluateView(view);
    }

    @Override
    public void onBindViewHolder(ToBeEvaluateView holder, int position) {
        final ToBeEvaluationInfo.ToBeEvaluationItem toBeEvaluationItem = toBeEvaluationItems.get(holder.getAdapterPosition());
        holder.bindData(toBeEvaluationItem);
        holder.useBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.goEvaluate(toBeEvaluationItem.getStoreid(),toBeEvaluationItem.getOrderid(),toBeEvaluationItem.getTitle());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return toBeEvaluationItems == null ? 0 : toBeEvaluationItems.size();
    }

    public class ToBeEvaluateView extends RecyclerView.ViewHolder {
        @BindView(R.id.photo)
        RoundedImageView photo;
        @BindView(R.id.business_name)
        TextView businessName;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.address)
        TextView address;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.use_bt)
        Button useBt;
        @BindView(R.id.item_rl)
        RelativeLayout itemRl;

        public ToBeEvaluateView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(ToBeEvaluationInfo.ToBeEvaluationItem toBeEvaluationItem) {
            GlideHelper.load((Activity) context, toBeEvaluationItem.getzPics(), R.mipmap.app_icon, photo);
            businessName.setText(toBeEvaluationItem.getTitle());
            time.setText("消费时间 :" + FormatUtil.formatDateByLine(toBeEvaluationItem.getCreatedAt()));
            address.setText(toBeEvaluationItem.getAddress());
            price.setText("总价:¥" + toBeEvaluationItem.getAllprice());
        }
    }

    public interface OnItemClickListener {
        void goEvaluate(String storeId,String orderId,String businessName);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
