package com.purchase.sls.common.widget.Banner.transform;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;

/**
 * 图片旋转切换动画效果
 * Created by JWC on 2018/4/20.
 */

public class RotateDownPageTransformer implements ViewPager.PageTransformer {
    private static final float ROT_MAX = 20.0f;
    private float mRot;
    @Override
    public void transformPage(View view, float position) {
        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            ViewHelper.setRotation(view, 0);

        } else if (position <= 1) { // [-1,1]
            // Modify the default slide transition to shrink the page as well
            if (position < 0) {

                mRot = (ROT_MAX * position);
                ViewHelper.setPivotX(view, view.getMeasuredWidth() * 0.5f);
                ViewHelper.setPivotY(view, view.getMeasuredHeight());
                ViewHelper.setRotation(view, mRot);
            } else {

                mRot = (ROT_MAX * position);
                ViewHelper.setPivotX(view, view.getMeasuredWidth() * 0.5f);
                ViewHelper.setPivotY(view, view.getMeasuredHeight());
                ViewHelper.setRotation(view, mRot);
            }

            // Scale the page down (between MIN_SCALE and 1)

            // Fade the page relative to its size.

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            ViewHelper.setRotation(view, 0);
        }
    }
}
