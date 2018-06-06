package com.purchase.sls.common.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.purchase.sls.R;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by JWC on 2018/6/5.
 */

public class TearDownView extends LinearLayout {

    public long endTime = 0L;
    private String type;
    private boolean mIsAttachedToWindow = false;

    private static final int MESSAGE_WHAT = 0;

    private TearDownHandler mHandler = new TearDownHandler(this);

    private int textSize = 0;
    private TextView dayTextView, hourTextView, minutsTextView, secondTextView;

    public TearDownView(Context context) {
        super(context);
        init(context, null);

    }

    public TearDownView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);

    }

    public TearDownView(Context context,
                        AttributeSet attributeSet, int paramInt) {
        super(context, attributeSet, paramInt);
        init(context, attributeSet);
    }

    //type=0：还没开始 type=1：已经开始，但是还没结束
    public void startTearDown(long endTime, String type) {
        this.endTime = endTime;
        this.type = type;
        if (mIsAttachedToWindow) {
            mHandler.removeMessages(MESSAGE_WHAT);
            mHandler.sendEmptyMessageDelayed(MESSAGE_WHAT, 1000);
        }
    }

    private void init(Context context, AttributeSet attributeSet) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.tear_down_layout, this);
        dayTextView = (TextView) findViewById(R.id.day_time);
        hourTextView = (TextView) findViewById(R.id.hour_time);
        minutsTextView = (TextView) findViewById(R.id.minuts_time);
        secondTextView = (TextView) findViewById(R.id.second_time);
    }


    @Override
    protected void onAttachedToWindow() {
        // TODO Auto-generated method stub
        mHandler.removeMessages(MESSAGE_WHAT);
        mHandler.sendEmptyMessageDelayed(MESSAGE_WHAT, 1000);
        mIsAttachedToWindow = true;
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        // TODO Auto-generated method stub
        mHandler.removeMessages(MESSAGE_WHAT);
        mIsAttachedToWindow = false;
        super.onDetachedFromWindow();
    }

    public static String formatDateByLine(long timestamp) {
        String pattern = "dd:HH:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(new Date(timestamp * 1000));
    }
    public void tearDown() {
        if (mIsAttachedToWindow) {
            long remainTime;
            if (TextUtils.equals("0", type)) {
                remainTime = System.currentTimeMillis() / 1000 - endTime;
            } else {
                remainTime = endTime - System.currentTimeMillis() / 1000;
            }
            if (remainTime > 0) {
                String str = formatDateByLine(remainTime);
                String[] splitStr = str.split(":");
                if (splitStr.length == 4) {
//                    int dayInt = 0;
//                    if (splitStr[0].startsWith("0") && splitStr[0].length() == 2) {
//                        dayInt = Integer.parseInt(String.valueOf(splitStr[0].charAt(1))) - 1;
//                    } else if (!splitStr[0].startsWith("0") && splitStr[0].length() == 2) {
//                        dayInt = Integer.parseInt(splitStr[0]) - 1;
//                    }
                    dayTextView.setText(String.valueOf(remainTime/(3600 * 24)));
                    hourTextView.setText(splitStr[1]);
                    minutsTextView.setText(splitStr[2]);
                    secondTextView.setText(splitStr[3]);
                }
                mHandler.sendEmptyMessageDelayed(MESSAGE_WHAT, 1000);
            } else {
//          setText("抢购结束");
            }
        }
    }


    public static class TearDownHandler extends Handler {
        private WeakReference<TearDownView> mTextViewRef;

        public TearDownHandler(TearDownView view) {
            mTextViewRef = new WeakReference<TearDownView>(view);
        }

        @Override
        public void handleMessage(Message msg) {
            TearDownView textView = mTextViewRef.get();
            if (null != textView) {
                removeMessages(MESSAGE_WHAT);
                textView.tearDown();
            }
        }
    }

    public void cancel() {
        if (mHandler != null && mHandler.hasMessages(MESSAGE_WHAT)) {
            mHandler.removeMessages(MESSAGE_WHAT);
        }
    }
}
