package com.purchase.sls.common.widget;

import android.content.Context;
import android.graphics.Color;
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
    private TextView firstColon;
    private TextView secondColon;
    private TextView thirdColon;
    private TextView fourthColon;

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
        firstColon = (TextView) findViewById(R.id.first_colon);
        secondColon = (TextView) findViewById(R.id.second_colon);
        thirdColon = (TextView) findViewById(R.id.third_colon);
        fourthColon = (TextView) findViewById(R.id.fourth_colon);
    }

    public void setTextColor(String colorType){
        if(TextUtils.equals("0",colorType)){
            dayTextView.setTextColor(Color.parseColor("#ff6528"));
            hourTextView.setTextColor(Color.parseColor("#ff6528"));
            minutsTextView.setTextColor(Color.parseColor("#ff6528"));
            secondTextView.setTextColor(Color.parseColor("#ff6528"));
        }else {
            dayTextView.setTextColor(Color.parseColor("#FFFFFF"));
            hourTextView.setTextColor(Color.parseColor("#FFFFFF"));
            minutsTextView.setTextColor(Color.parseColor("#FFFFFF"));
            secondTextView.setTextColor(Color.parseColor("#FFFFFF"));
            firstColon.setTextColor(Color.parseColor("#FFFFFF"));
            secondColon.setTextColor(Color.parseColor("#FFFFFF"));
            thirdColon.setTextColor(Color.parseColor("#FFFFFF"));
            fourthColon.setTextColor(Color.parseColor("#FFFFFF"));
        }
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
                if(String.valueOf(day).length()==1){
                    dayTextView.setText("0"+day);
                }else {
                    dayTextView.setText(String.valueOf(day));
                }
                if(String.valueOf(hour).length()==1){
                    hourTextView.setText("0"+hour);
                }else {
                    hourTextView.setText(String.valueOf(hour));
                }
                if(String.valueOf(min).length()==1){
                    minutsTextView.setText("0"+min);
                }else {
                    minutsTextView.setText(String.valueOf(min));
                }
                if(String.valueOf(sec).length()==1){
                    secondTextView.setText("0"+sec);
                }else {
                    secondTextView.setText(String.valueOf(sec));
                }
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
