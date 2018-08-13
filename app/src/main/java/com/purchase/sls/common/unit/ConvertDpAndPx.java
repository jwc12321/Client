package com.purchase.sls.common.unit;

import android.content.Context;

public class ConvertDpAndPx {
    /**
     * dp转换成px,代码写的是像素,而XML中写的是单位密度
     * @param context
     * @param dp
     * @return
     */
    public static int Dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * px转换成dp,代码写的是像素,而XML中(dp)写的是单位密度
     * @param context
     * @param px
     * @return
     */
    public static int Px2Dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

}
