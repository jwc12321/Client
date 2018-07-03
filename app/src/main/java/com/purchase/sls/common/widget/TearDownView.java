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

    public void tearDown() {
        if (mIsAttachedToWindow) {
            long remainTime= endTime - System.currentTimeMillis() / 1000;
            if (remainTime >0) {
                long day = 0;
                long hour = 0;
                long min = 0;
                long sec = 0;
                day = remainTime / (24 * 60 * 60);
                hour = (remainTime / (60 * 60) - day * 24);
                min = (remainTime / 60 - day * 24 * 60 - hour * 60);
                sec = (remainTime - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
                dayTextView.setText(String.valueOf(day));
                hourTextView.setText(String.valueOf(hour));
                minutsTextView.setText(String.valueOf(min));
                secondTextView.setText(String.valueOf(sec));
                mHandler.sendEmptyMessageDelayed(MESSAGE_WHAT, 1000);
            } else {
                if(timeOutListener!=null) {
                    if (TextUtils.equals("0", type)) {
                        timeOutListener.timeIn();
                    } else {
                        timeOutListener.timeOut();
                        cancel();
                    }
                }
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

    public interface TimeOutListener {
        void timeIn();//从没开始到开始
        void timeOut();//从还没结束到结束
    }

    private TimeOutListener timeOutListener;

    public void setTimeOutListener(TimeOutListener timeOutListener) {
        this.timeOutListener = timeOutListener;
    }
}
