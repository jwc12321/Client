package com.purchase.sls.common.unit;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * Created by JWC on 2018/5/26.
 */

public class QrCodeUtil {
    private static final String TAG = "QrCodeUtil";

    /**
     * 生成二维码
     *
     * @param content 要生成二维码的内容
     * @param width   生成的二维码宽度
     * @param height  生成的二维码高度
     * @return 二维码图
     */
    @Nullable
    public static Bitmap createQRCode(String content, int width, int height) {
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        ArrayMap<EncodeHintType, Object> qrParams = new ArrayMap<>(2);
        // 设置QR二维码的纠错级别——这里选择最高H级别
        qrParams.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 设置编码方式
        qrParams.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, qrParams);
            // 开始利用二维码数据创建Bitmap图片，分别设为黑白两色
            int w = bitMatrix.getWidth();
            int h = bitMatrix.getHeight();
            int[] data = new int[w * h];

            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    if (bitMatrix.get(x, y))
                        data[y * w + x] = 0xff000000;// 黑色
                    else
                        data[y * w + x] = -1;// -1 相当于0xffffffff 白色
                }
            }

            // 创建一张bitmap图片，采用最高的效果显示
            Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            // 将上面的二维码颜色数组传入，生成图片颜色
            bitmap.setPixels(data, 0, w, 0, 0, w, h);
            return bitmap;
        } catch (WriterException e) {
            Log.e(TAG, "createQRCode: ", e);
        }
        return null;
    }
}
