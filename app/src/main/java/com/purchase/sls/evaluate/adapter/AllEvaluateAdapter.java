package com.purchase.sls.evaluate.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.purchase.sls.R;
import com.purchase.sls.common.GlideHelper;
import com.purchase.sls.common.unit.FormatUtil;
import com.purchase.sls.common.widget.MyRatingBar;
import com.purchase.sls.data.entity.CollectionListInfo;
import com.purchase.sls.data.entity.EvaluateInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/4/25.
 */

public class AllEvaluateAdapter extends RecyclerView.Adapter<AllEvaluateAdapter.AllEvaluateView> {
    private LayoutInflater layoutInflater;
    private List<EvaluateInfo.EvaluateItemInfo> evaluateItemInfos;
    private static Context context;

    public AllEvaluateAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<EvaluateInfo.EvaluateItemInfo> evaluateItemInfos) {
        this.evaluateItemInfos = evaluateItemInfos;
        notifyDataSetChanged();
    }

    public void addMore(List<EvaluateInfo.EvaluateItemInfo> moreList) {
        int pos = evaluateItemInfos.size();
        evaluateItemInfos.addAll(moreList);
        notifyItemRangeInserted(pos, moreList.size());
    }


    @Override
    public AllEvaluateView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_all_evaluate, parent, false);
        return new AllEvaluateView(view);
    }

    @Override
    public void onBindViewHolder(AllEvaluateView holder, int position) {
        EvaluateInfo.EvaluateItemInfo evaluateItemInfo = evaluateItemInfos.get(holder.getAdapterPosition());
        holder.bindData(evaluateItemInfo);
    }

    @Override
    public int getItemCount() {
        return evaluateItemInfos == null ? 0 : evaluateItemInfos.size();
    }

    public class AllEvaluateView extends RecyclerView.ViewHolder implements PhotoAdapter.OnPictureOnClickListener{


        @BindView(R.id.people_icon)
        RoundedImageView peopleIcon;
        @BindView(R.id.people_name)
        TextView peopleName;
        @BindView(R.id.rating_bar)
        MyRatingBar ratingBar;
        @BindView(R.id.people_time)
        TextView peopleTime;
        @BindView(R.id.evaluate_tv)
        TextView evaluateTv;
        @BindView(R.id.photo_recycler_view)
        RecyclerView photoRecyclerView;

        PhotoAdapter photoAdapter;

        public AllEvaluateView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            photoAdapter=new PhotoAdapter(context);
            photoAdapter.setOnPictureOnClickListener(this);
            photoRecyclerView.setLayoutManager(new GridLayoutManager(context, 3));
            photoRecyclerView.setAdapter(photoAdapter);
        }

        public void bindData(EvaluateInfo.EvaluateItemInfo evaluateItemInfo) {
            List<EvaluateInfo.EvaluateItemInfo.usersInfo> usersInfos = evaluateItemInfo.getUsersInfos();
            if (usersInfos != null && usersInfos.size() > 0 && usersInfos.get(0) != null) {
                GlideHelper.load((Activity) context, usersInfos.get(0).getAvatar(), R.mipmap.app_icon, peopleIcon);
                peopleName.setText(usersInfos.get(0).getUsername());
            }
            ratingBar.setmScope(Float.parseFloat(evaluateItemInfo.getStarts()));
            peopleTime.setText(FormatUtil.formatDate(evaluateItemInfo.getCreatedAt()));
            evaluateTv.setText(evaluateItemInfo.getWords());
            if(evaluateItemInfo.getPics()!=null&&evaluateItemInfo.getPics().size()>0){
                photoAdapter.setPhotoList(evaluateItemInfo.getPics());
                photoRecyclerView.setVisibility(View.VISIBLE);
            }else {
                photoRecyclerView.setVisibility(View.GONE);
            }
        }

        @Override
        public void zomm(int position, List<String> photos) {
            if ( onPictureOnClickListener != null )
                onPictureOnClickListener.zoom(position, photos);
        }
    }

    public interface OnPictureOnClickListener{
        void zoom(int position, List<String> photos);
    }
    private OnPictureOnClickListener onPictureOnClickListener;

    public void setOnPictureOnClickListener(OnPictureOnClickListener onPictureOnClickListener) {
        this.onPictureOnClickListener = onPictureOnClickListener;
    }
}
