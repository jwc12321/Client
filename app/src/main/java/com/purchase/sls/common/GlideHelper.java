package com.purchase.sls.common;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

public class GlideHelper {

    public static void load(String url, int placeHolder, ImageView target){
        if(target!=null&&target.getContext()!=null) {
            Glide.with(target.getContext())
                    .load(url)
                    .placeholder(placeHolder)
                    .into(new ImageTarget(target));
        }
    }

    public static void load(Activity activity, String url, int placeHolder, ImageView target){
        if(activity!=null) {
            Glide.with(activity)
                    .load(url)
                    .placeholder(placeHolder)
                    .into(new ImageTarget(target));
        }
    }
    public static void load(Fragment fragment, String url, int placeHolder, ImageView target){
        if(fragment!=null) {
            Glide.with(fragment)
                    .load(url)
                    .placeholder(placeHolder)
                    .into(new ImageTarget(target));
        }
    }
    private static class ImageTarget extends SimpleTarget<GlideDrawable>{

        private final ImageView mImageView;

        public ImageTarget(ImageView imageView){
            mImageView = imageView;
        }

        @Override
        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
            mImageView.setImageDrawable(resource);
        }

        @Override
        public void onLoadFailed(Exception e, Drawable errorDrawable) {
            mImageView.setImageDrawable(errorDrawable);
        }
    }
}
