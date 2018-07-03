package com.purchase.sls.energy.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.R;
import com.purchase.sls.common.GlideHelper;
import com.purchase.sls.common.unit.FormatUtil;
import com.purchase.sls.data.entity.ActivityInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/6/1.
 */

public class SpikeAdapter extends RecyclerView.Adapter<SpikeAdapter.SpikeVeiw> {
    private LayoutInflater layoutInflater;
    private Context context;
    private List<ActivityInfo> activityInfos;

    public SpikeAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ActivityInfo> activityInfos) {
        this.activityInfos = activityInfos;
        notifyDataSetChanged();
    }


    @Override
    public SpikeVeiw onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_spike, parent, false);
        return new SpikeVeiw(view);
    }

    @Override
    public void onBindViewHolder(SpikeVeiw holder, int position) {
        final ActivityInfo activityInfo = activityInfos.get(holder.getAdapterPosition());
        holder.bindData(activityInfo);
        holder.goSpike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onSpikeItemClickListener!=null){
                    onSpikeItemClickListener.goSpike(activityInfo);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return activityInfos == null ? 0 : activityInfos.size();
    }

    public class SpikeVeiw extends RecyclerView.ViewHolder {
        @BindView(R.id.start_time)
        TextView startTime;
        @BindView(R.id.spike_energy)
        TextView spikeEnergy;
        @BindView(R.id.spike_name)
        TextView spikeName;
        @BindView(R.id.spike_iv)
        ImageView spikeIv;
        @BindView(R.id.surplus_number)
        TextView surplusNumber;
        @BindView(R.id.go_spike)
        RelativeLayout goSpike;
        public SpikeVeiw(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(ActivityInfo activityInfo) {
            startTime.setText("开始时间:"+FormatUtil.formatDateByLine(activityInfo.getStartTime()));
            spikeEnergy.setText(activityInfo.getPrice());
            spikeName.setText("能量抢"+activityInfo.getpName());
            GlideHelper.load((Activity) context, activityInfo.getActLogo(), R.mipmap.app_icon, spikeIv);
            surplusNumber.setText("剩余"+activityInfo.getCount()+"件");
        }
    }

    public interface OnSpikeItemClickListener {
        void goSpike(ActivityInfo activityInfo);
    }

    private OnSpikeItemClickListener onSpikeItemClickListener;

    public void setOnSpikeItemClickListener(OnSpikeItemClickListener onSpikeItemClickListener) {
        this.onSpikeItemClickListener = onSpikeItemClickListener;
    }
}
