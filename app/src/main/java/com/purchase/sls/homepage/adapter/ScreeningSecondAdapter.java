package com.purchase.sls.homepage.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.R;
import com.purchase.sls.data.entity.SortInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/4/24.
 */

public class ScreeningSecondAdapter extends RecyclerView.Adapter<ScreeningSecondAdapter.ScreeningFirstView> {
    private LayoutInflater layoutInflater;
    private List<SortInfo> sortInfos;
    private int selectPosition = 0;

    public void setData(List<SortInfo> sortInfos) {
        this.sortInfos = sortInfos;
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
        View view = layoutInflater.inflate(R.layout.adapter_screening_second, parent, false);
        return new ScreeningFirstView(view);
    }

    @Override
    public void onBindViewHolder(final ScreeningFirstView holder, int position) {
        final SortInfo sortInfo = sortInfos.get(holder.getAdapterPosition());
        holder.choiceImageSecond.setVisibility(position == selectPosition ? View.VISIBLE : View.INVISIBLE);
        holder.choiceTypeSecond.setSelected(position == selectPosition);
        holder.choiceTypeSecond.setText(sortInfo.getVel());
        holder.choiceSecondRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSecondItemOnClickListenern != null) {
                    onSecondItemOnClickListenern.onSecondItemClick(sortInfo.getKey(), holder.getAdapterPosition());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return sortInfos == null ? 0 : sortInfos.size();
    }

    public static class ScreeningFirstView extends RecyclerView.ViewHolder {

        @BindView(R.id.choice_image_second)
        ImageView choiceImageSecond;
        @BindView(R.id.choice_type_second)
        TextView choiceTypeSecond;
        @BindView(R.id.choice_second_rl)
        RelativeLayout choiceSecondRl;

        public ScreeningFirstView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnSecondItemOnClickListener {
        void onSecondItemClick(String sort, int position);
    }

    private OnSecondItemOnClickListener onSecondItemOnClickListenern;

    public void setOnSecondItemOnClickListener(OnSecondItemOnClickListener onSecondItemOnClickListenern) {
        this.onSecondItemOnClickListenern = onSecondItemOnClickListenern;
    }

}
