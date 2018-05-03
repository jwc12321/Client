package com.purchase.sls.account.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.purchase.sls.R;
import com.purchase.sls.common.unit.FormatUtil;
import com.purchase.sls.data.entity.IntercourseRecordInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/5/2.
 */

public class IntercourseRecordAdapter extends RecyclerView.Adapter<IntercourseRecordAdapter.IntercourseRecordView> {
    private LayoutInflater layoutInflater;
    private List<IntercourseRecordInfo.IRItemInfo> irItemInfos;
    private String title;

    public IntercourseRecordAdapter(String title) {
        this.title = title;
    }

    public void setData(List<IntercourseRecordInfo.IRItemInfo> irItemInfos) {
        this.irItemInfos = irItemInfos;
        notifyDataSetChanged();
    }

    public void addMore(List<IntercourseRecordInfo.IRItemInfo> moreList) {
        int pos = irItemInfos.size();
        irItemInfos.addAll(moreList);
        notifyItemRangeInserted(pos, moreList.size());
    }

    @Override
    public IntercourseRecordView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_intercourse_record, parent, false);
        return new IntercourseRecordView(view);
    }

    @Override
    public void onBindViewHolder(IntercourseRecordView holder, int position) {
        IntercourseRecordInfo.IRItemInfo irItemInfo = irItemInfos.get(holder.getAdapterPosition());
        holder.bindData(irItemInfo);
    }

    @Override
    public int getItemCount() {
        return irItemInfos == null ? 0 : irItemInfos.size();
    }

    public class IntercourseRecordView extends RecyclerView.ViewHolder {
        @BindView(R.id.business_name)
        TextView businessName;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.state)
        TextView state;

        public IntercourseRecordView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(IntercourseRecordInfo.IRItemInfo irItemInfo) {
            businessName.setText(title);
            time.setText(FormatUtil.formatDateByLine(irItemInfo.getCreatedAt()));
            price.setText(irItemInfo.getAllprice());
            if(TextUtils.equals("1",irItemInfo.getStatus())){
                state.setText("交易成功");
            }else {
                state.setText("交易失败");
            }
        }
    }
}
