package com.purchase.sls.evaluate.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.purchase.sls.R;

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
    private Activity activity;

    public PhotoAdapter(Context context,Activity activity) {
        this.context = context;
        this.activity=activity;
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
        Glide.with(context)
                .load(photoUrl)
                .asBitmap()
                .placeholder(R.mipmap.app_icon)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .override(70,70)
                .skipMemoryCache(true)
                .into(holder.photo);
        if (onPictureOnClickListener != null) {
            holder.photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onPictureOnClickListener.zomm(holder.getAdapterPosition(),photoList);
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
        ImageView photo;
        public PhotoView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnPictureOnClickListener {
        void zomm(int position, List<String> photos);
    }

    private OnPictureOnClickListener onPictureOnClickListener;

    public void setOnPictureOnClickListener(OnPictureOnClickListener onPictureOnClickListener) {
        this.onPictureOnClickListener = onPictureOnClickListener;
    }
}
