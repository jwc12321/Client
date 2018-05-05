package com.purchase.sls.evaluate.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.makeramen.roundedimageview.RoundedImageView;
import com.purchase.sls.R;
import com.purchase.sls.common.GlideHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2017/5/9.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoView> {
    private LayoutInflater layoutInflater;
    private List<String> photoList;
    private Context context;

    public PhotoAdapter(Context context) {
        this.context = context;
    }

    public void setPhotoList(List<String> photoList) {
        this.photoList = photoList;
        notifyDataSetChanged();
    }

    @Override
    public PhotoView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_order_photo, parent, false);
        return new PhotoView(view);
    }

    @Override
    public void onBindViewHolder(final PhotoView holder, int position) {
        final String photoUrl = photoList.get(holder.getAdapterPosition());
        GlideHelper.load((Activity) context, photoUrl, R.mipmap.ic_launcher, holder.photo);
        if (onPictureOnClickListener != null) {
            holder.photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onPictureOnClickListener.zomm(holder.getAdapterPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return photoList == null ? 0 : photoList.size();
    }

    public static class PhotoView extends RecyclerView.ViewHolder {
        @BindView(R.id.photo)
        RoundedImageView photo;
        public PhotoView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnPictureOnClickListener {
        void zomm(int position);
    }

    private OnPictureOnClickListener onPictureOnClickListener;

    public void setOnPictureOnClickListener(OnPictureOnClickListener onPictureOnClickListener) {
        this.onPictureOnClickListener = onPictureOnClickListener;
    }
}
