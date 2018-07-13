package com.purchase.sls.homepage.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.R;
import com.purchase.sls.data.entity.CateInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/4/24.
 */

public class ScreeningFirstAdapter extends RecyclerView.Adapter<ScreeningFirstAdapter.ScreeningFirstView> {
    private LayoutInflater layoutInflater;
    private List<CateInfo> cateInfos;
    private int selectPosition = 0;

    public void setData(List<CateInfo> cateInfos) {
        this.cateInfos = cateInfos;
        notifyDataSetChanged();
    }

    public void setPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        notifyDataSetChanged();
    }

    @Override
    public ScreeningFirstView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_screening_first, parent, false);
        return new ScreeningFirstView(view);
    }

    @Override
    public void onBindViewHolder(final ScreeningFirstView holder, int position) {
        final CateInfo cateInfo = cateInfos.get(holder.getAdapterPosition());
        holder.choiceImageFirst.setVisibility(position == selectPosition ? View.VISIBLE : View.INVISIBLE);
        holder.choiceImageFirst.setSelected(position==selectPosition);
        holder.choiceTypeFirst.setText(cateInfo.getName());
        holder.choiceFirstRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onFirstItemOnClickListener != null) {
                    onFirstItemOnClickListener.onFirstItemClick(cateInfo.getId(), holder.getAdapterPosition());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return cateInfos == null ? 0 : cateInfos.size();
    }

    public static class ScreeningFirstView extends RecyclerView.ViewHolder {

        @BindView(R.id.choice_image_first)
        ImageView choiceImageFirst;
        @BindView(R.id.choice_type_first)
        TextView choiceTypeFirst;
        @BindView(R.id.choice_sum_first)
        TextView choiceSumFirst;
        @BindView(R.id.choice_first_rl)
        RelativeLayout choiceFirstRl;

        public ScreeningFirstView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnFirstItemOnClickListener {
        void onFirstItemClick(String id, int position);
    }

    private OnFirstItemOnClickListener onFirstItemOnClickListener;

    public void setFirstOnItemOnClickListener(OnFirstItemOnClickListener onFirstItemOnClickListener) {
        this.onFirstItemOnClickListener = onFirstItemOnClickListener;
    }

}
