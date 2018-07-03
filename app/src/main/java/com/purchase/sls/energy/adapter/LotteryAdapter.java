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

public class LotteryAdapter extends RecyclerView.Adapter<LotteryAdapter.LotteryVeiw> {
    private LayoutInflater layoutInflater;
    private Context context;
    private List<ActivityInfo> activityInfos;

    public LotteryAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ActivityInfo> activityInfos) {
        this.activityInfos = activityInfos;
        notifyDataSetChanged();
    }


    @Override
    public LotteryVeiw onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_lottery, parent, false);
        return new LotteryVeiw(view);
    }

    @Override
    public void onBindViewHolder(LotteryVeiw holder, int position) {
        final ActivityInfo activityInfo = activityInfos.get(holder.getAdapterPosition());
        holder.bindData(activityInfo);
        holder.goLottery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onLotteryItemClickListener != null) {
                    onLotteryItemClickListener.goLottery(activityInfo);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return activityInfos == null ? 0 : activityInfos.size();
    }

    public class LotteryVeiw extends RecyclerView.ViewHolder {
        @BindView(R.id.start_time)
        TextView startTime;
        @BindView(R.id.lottery_energy)
        TextView lotteryEnergy;
        @BindView(R.id.lottery_name)
        TextView lotteryName;
        @BindView(R.id.lottery_iv)
        ImageView lotteryIv;
        @BindView(R.id.surplus_number)
        TextView surplusNumber;
        @BindView(R.id.go_lottery)
        RelativeLayout goLottery;

        public LotteryVeiw(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(ActivityInfo activityInfo) {
            startTime.setText("开始时间:" + FormatUtil.formatDateByLine(activityInfo.getStartTime()));
            lotteryEnergy.setText(activityInfo.getPrice());
            lotteryName.setText("能量抢"+activityInfo.getpName());
            GlideHelper.load((Activity) context, activityInfo.getActLogo(), R.mipmap.app_icon, lotteryIv);
            surplusNumber.setText("剩余" + activityInfo.getCount() + "名额");
        }
    }

    public interface OnLotteryItemClickListener {
        void goLottery(ActivityInfo activityInfo);
    }

    private OnLotteryItemClickListener onLotteryItemClickListener;

    public void setOnLotteryItemClickListener(OnLotteryItemClickListener onLotteryItemClickListener) {
        this.onLotteryItemClickListener = onLotteryItemClickListener;
    }
}
